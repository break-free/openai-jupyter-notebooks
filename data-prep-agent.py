import os

# Set OpenAI API key
with open('openai-key.txt', 'r') as f:
    key = f.read().strip()

os.environ['OPENAI_API_KEY'] = key

from llm_prompt_creator import prompt as PR
from langchain.tools import BaseTool
from langchain.agents import initialize_agent, load_tools
from langchain.chains.conversation.memory import ConversationBufferWindowMemory
from langchain.chat_models import ChatOpenAI
import json
#from glob import glob
import openai
import pandas as pd
import tiktoken
import io
import ast

pd.set_option('display.max_colwidth', None)
pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows', None)

def askLLM(message, model="gpt-3.5-turbo-16k"):
    messages = [{"role": "user", "content": message},]

    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0,
    )

    return response["choices"][0]["message"]["content"]


########### Data Preparation Tools ###########
from pydantic import BaseModel, BaseSettings, Field
from typing import Type
CSV_FILE: str = Field( description="the csv file name and/or location")

class csvSchema(BaseModel):
    csv_file: str = CSV_FILE

class FindCategoryOpportunitiesTool(BaseTool, BaseSettings):
    name = "Find Category Opportunities Tool"
    description = (
        "Use this tool as the first step when you need to prepare/clean/feature engineer a dataset for "
        "machine learning models or algorithms. This tool will be used to determine if any columns should "
        "be converted to categorical values, and then perform those converstions. If the user did not provide "
        "all arguments required to use this tool, use the human tool to get that information first. Never "
        "pass in a null value, and never use a value that wasn't provided directly by the user. "
    )
    args_schema: Type[csvSchema] = csvSchema

    def _run(self, csv_file: str):
        df = pd.read_csv(csv_file)
        data = df.head(10)
        buf = io.StringIO()
        s = df.info(buf=buf)
        info = buf.getvalue()
        
        # query LLM to determine list of columns to address
        message = f'''
        The following data summary and data sample are from a dataset that will be used for machine learning models.
        Review the columns and determine if there are free text columns that could be converted into categorical values.
        Do not include free text columns that are already broken down into categories.

        Respond only with the names of all relevant columns in the following format: ["column 1", "column 2", "column 3"]

        Data summary:
        {info}

        Data sample:
        {data}
        '''

        catInfoResponse = askLLM(message, model="gpt-4")

        return catInfoResponse
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


#CATEGORY_VALUES: str = Field(description="The category values that a free text column should be mapped to")
COLUMN_NAME: list = Field(description="the names of the columns that need to be modified for data cleaning/preparation. use python list formatting.")

class standardToolArgs(BaseModel):
    csv_file: str = CSV_FILE
    #category_values: str = CATEGORY_VALUES
    column_name: list = COLUMN_NAME
    
class TextPreprocessingTool(BaseTool, BaseSettings):
    name = "Text Preprocessing Tool"
    description = (
        "Use this tool for text preprocessing, specifically to convert free text fields into categorical values. "
        "This tool should never be used to encode categorical values. It is only meant for text preprocessing to convert free text into categories."
    )
    args_schema: Type[standardToolArgs] = standardToolArgs

    def _run(self, csv_file: str, column_name: list):
        # check if updated csv file already exists, and if so use that file
        substr = "."
        idx = csv_file.index(substr)
        updatedCSV = csv_file[:idx] + "_updated" + csv_file[idx:]

        try:
            df = pd.read_csv(updatedCSV)
        except:
            df = pd.read_csv(csv_file)

        # check for category values to map free text to
        if len(column_name) > 1:
            return "error - need dev to handle multiple columns"
        colName = column_name[0]

        try:
            f = open("datasetInfo.json")
            jsonFile = json.load(f)
            category_values = jsonFile["Category Values"][colName]
        except:
            return f"Please add category values for the {colName} column in the datasetInfo.json file and try again."

        # split rows in the dataset into a smaller list of rows
        encoding = tiktoken.get_encoding("cl100k_base")
        dataList = []
        tempList = []
        i = 0
        while i < len(df):
            row_to_append = df.iloc[i][colName]
            tempList.append(row_to_append)
            
            # Calculate current token count
            dfAsString = f"{tempList}"
            numTokens = len(encoding.encode(dfAsString))

            if (numTokens >= 1000) or (i == len(df)-1):
                dataList.append(tempList)
                tempList = []

            i += 1

        # loop through the list of the split dataset and query LLM for each
        fullList = []
        for partialDf in dataList:
            print(". ")
            x = pd.Series(partialDf)
            xLength = len(x)
 
            message = f'''
            Categorize the data in the data series below with the following category values:\n
            {category_values}
            Only use those listed category values. Do not create any new values.
            \n
            Map each individual data point in the series to a category value, and reply with this list format: ["categoryValue1", "categoryValue2", "categoryValue3"]
            The response list should contain the same number of items, {xLength}, and be in the same order as the data series provided below. 
            \n
            Data series:\n
            {x}
            '''
            #print(message)
            #print(len(encoding.encode(message)))

            newCol = askLLM(message, "gpt-4")

            newList = ast.literal_eval(newCol)
            fullList = fullList + newList

        # replace free text column with categorical encoded columns
        df.drop(colName, axis=1, inplace=True)
        df[colName] = fullList

        dummyList = []
        dummyList.append(colName)
        updatedDf = pd.get_dummies(df, columns=dummyList)
        updatedDf.to_csv(updatedCSV, index=False)

        return f"Updated dataset now saved in {updatedCSV}"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")

class CreateDataPrepPlanTool(BaseTool, BaseSettings):
    name = "Create Data Prep Plan"
    description = (
        "Use this tool to create a step by step plan for cleaning/feature engineering a dataset for machine learning. "
        "Always use this tool first when asked to prepare a dataset for machine learning models. "
        "After determining the data preparation steps, use the tools you have available to perform those steps. "
        "If there are no tools available for a specific step, skip that step and inform the user of what needs to be completed. "
        "Then move to the next step."
    )
    args_schema: Type[csvSchema] = csvSchema

    def _run(self, csv_file: str):
        df = pd.read_csv(csv_file)
        data = df.head(10)
        buf = io.StringIO()
        s = df.info(buf=buf)
        info = buf.getvalue()
        descStats = df.describe()
        corr = df.corr(numeric_only=True)

        f = open("datasetInfo.json")
        jsonFile = json.load(f)
        targetVar = jsonFile["Column Descriptions"]["Target"]
        dataDescription = jsonFile["Column Descriptions"]["Description"]
        columnsDescription = jsonFile["Column Descriptions"]["Columns"]

        # query LLM to determine list of columns to address
        message = f'''
        Review the information on the dataset below and determine which data cleaning steps should be done in preparation for machine learning models.
        Be specific in what steps should be completed.

        Data Information:
        The target variable for the dataset is {targetVar}.
        {dataDescription}. The features are:
        {columnsDescription}

        Data summary:
        {info}

        Data sample:
        {data}

        Descriptive Statistics:
        {descStats}

        Correlation Matrix:
        {corr}
        '''

        catInfoResponse = askLLM(message, model="gpt-4")
        return catInfoResponse
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


class RemoveColumnsTool(BaseTool, BaseSettings):
    name = "Remove Columns Tool"
    description = (
        "Use this tool to remove unneeded columns in a dataset for machine learning."
    )
    args_schema: Type[standardToolArgs] = standardToolArgs

    def _run(self, csv_file: str, column_name: list):
        substr = "."
        idx = csv_file.index(substr)
        updatedCSV = csv_file[:idx] + "_updated" + csv_file[idx:]

        try:
            df = pd.read_csv(updatedCSV)
        except:
            df = pd.read_csv(csv_file)
        
        for col in column_name:
            df.drop(col, axis=1, inplace=True)

        df.to_csv(updatedCSV, index=False)

        return f"Successfully removed the following columns: {column_name}"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


class EncodeCategoricalValuesTool(BaseTool, BaseSettings):
    name = "Encode Categorical Values Tool"
    description = (
        "Use this tool encode categorical values."
    )
    args_schema: Type[standardToolArgs] = standardToolArgs

    def _run(self, csv_file: str, column_name: list):
        substr = "."
        idx = csv_file.index(substr)
        updatedCSV = csv_file[:idx] + "_updated" + csv_file[idx:]

        try:
            df = pd.read_csv(updatedCSV)
        except:
            df = pd.read_csv(csv_file)
        
        updatedDf = pd.get_dummies(df, columns=column_name)
        updatedDf.to_csv(updatedCSV, index=False)

        return f"Successfully encoded the following columns: {column_name}"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


########### Set up LLM and Agent ###########
llm = ChatOpenAI(
    openai_api_key=os.environ['OPENAI_API_KEY'],
    temperature=0,
    #model_name='gpt-3.5-turbo'
    model_name="gpt-4"
)

# initialize conversational memory
conversational_memory = ConversationBufferWindowMemory(
    memory_key='chat_history',
    k=5,
    return_messages=True
)

tools = load_tools(
    ["human"],
    llm=llm,
)

#tools.append(FindCategoryOpportunitiesTool())
tools.append(CreateDataPrepPlanTool())
tools.append(RemoveColumnsTool())
tools.append(EncodeCategoricalValuesTool())
tools.append(TextPreprocessingTool())

# initialize agent with tools
agent = initialize_agent(
    agent='structured-chat-zero-shot-react-description',
    tools=tools,
    llm=llm,
    verbose=True,
    max_iterations=5,
    early_stopping_method='generate',
    memory=conversational_memory
)

# The below print statement will show the information that the agent is working with (tool descriptions, etc.)
#print(agent.agent.llm_chain.prompt.messages[0].prompt.template)
# query = input("prompt: ")
# agent(query)
agent("Can you prepare a dataset for machine learning models? Use sampleFeedback50.csv located in the datasets/ folder.")

# test=['Academic Support and Resources', 'Academic Support and Resources', 'Academic Support and Resources', 'Housing and Transportation', 'Activities and Traveling', 'Health and Well-being Support', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Career opportunities', 'Career opportunities', 'Career opportunities', 'Academic Support and Resources', 'Academic Support and Resources', 'Academic Support and Resources', 'Academic Support and Resources', 'Academic Support and Resources', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Career opportunities', 'Career opportunities', 'Career opportunities', 'Athletics and sports', 'Athletics and sports', 'Athletics and sports', 'Career opportunities', 'Career opportunities', 'Career opportunities', 'Financial Support', 'Financial Support', 'Financial Support', 'Financial Support', 'Financial Support', 'Health and Well-being Support', 'Health and Well-being Support', 'Health and Well-being Support', 'Health and Well-being Support', 'Health and Well-being Support', 'Health and Well-being Support', 'Health and Well-being Support', 'International student experience', 'International student experience', 'International student experience', 'International student experience', 'International student experience', 'International student experience', 'International student experience', 'Online learning', 'Online learning', 'Online learning', 'Online learning', 'Online learning', 'Online learning', 'Online learning', 'Student Affairs', 'Student Affairs', 'Student Affairs', 'Student Affairs', 'Student Affairs', 'Student Affairs', 'Student Affairs']
# print(len(test))


#Academic Support and Resources, Athletics and sports, Career opportunities, Financial Support  


# df = pd.read_csv("datasets/feedback1.csv")
# df_new = pd.DataFrame(columns=["Reports"])

# encoding = tiktoken.get_encoding("cl100k_base")
# dataList = []
# tempList = []
# i = 0
# while i < len(df):
#     row_to_append = df.iloc[i]["Reports"]

#     #df_new = pd.concat([df_new, row_to_append.to_frame().T], ignore_index=True)
#     tempList.append(row_to_append)
    
#     # Calculate current token count
#     dfAsString = f"{tempList}"
#     numTokens = len(encoding.encode(dfAsString))

#     if (numTokens >= 2000) or (i == len(df)-1):
#         #dataList.append(df_new)
#         #df_new = pd.DataFrame(columns=df.columns)
#         dataList.append(tempList)
#         tempList = []

#     i += 1

# print(dataList)
# print(len(dataList))





# csv_file = "datasets/feedback1.csv"
# df = pd.read_csv(csv_file)
# data = df.head(10)
# buf = io.StringIO()
# s = df.info(buf=buf)
# info = buf.getvalue()
# descStats = df.describe()
# corr = df.corr(numeric_only=False)

# f = open("datasetInfo.json")
# jsonFile = json.load(f)
# targetVar = jsonFile["Column Descriptions"]["Target"]
# dataDescription = jsonFile["Column Descriptions"]["Description"]
# columnsDescription = jsonFile["Column Descriptions"]["Columns"]

# # query LLM to determine list of columns to address
# message = f'''
# Review the information on the dataset below and determine which data cleaning steps should be done in preparation for machine learning models.
# Be specific in what steps should be completed.

# After determining the data preparation steps, use the tools you have available to perform those steps. If there are no tools available for a
# specific step, inform the user of what needs to be completed.

# Data Information:
# The target variable for the dataset is {targetVar}.
# {dataDescription}. The features are:
# {columnsDescription}

# Data summary:
# {info}

# Data sample:
# {data}

# Descriptive Statistics:
# {descStats}

# Correlation Matrix:
# {corr}
# '''

# print(message)
# encoding = tiktoken.get_encoding("cl100k_base")
# print(len(encoding.encode(message)))


# catInfoResponse = askLLM(message, model="gpt-4")
# print(catInfoResponse)
