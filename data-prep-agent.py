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
from typing import Optional
from langchain.text_splitter import CharacterTextSplitter
import json
from glob import glob
import data_chunker.parser as file_parser
import openai
import pandas as pd
import csv
import tiktoken
import io
import ast

pd.set_option('display.max_colwidth', None)
pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows', None)

def askLLM(message, model="gpt-3.5-turbo-16k"):
    messages = [
    {"role": "user", "content": message},
    ]

    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0.1,
        #stream=True
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


CATEGORY_VALUES: str = Field(description="The category values that a free text column should be mapped to")
COLUMN_NAME: str = Field(description="the name of the column that needs its text values mapped to categories")

class textToCatColumns(BaseModel):
    csv_file: str = CSV_FILE
    #category_values: str = CATEGORY_VALUES
    column_name: str = COLUMN_NAME
    
class AssignCategoryValuesTool(BaseTool, BaseSettings):
    name = "Assign Category Values Tool"
    description = (
        "Use this tool to assign category values in place of free text fields for the provided columns. "
        # "If the user did not provide the category names that need to be mapped to the text values for the "
        # "column name, prompt the user for those values."
    )
    args_schema: Type[textToCatColumns] = textToCatColumns

    def _run(self, csv_file: str, column_name: str):
        # Code to replace free text columns with categories
        df = pd.read_csv(csv_file)
        #df_new = pd.DataFrame(columns=column_name)
        try:
            f = open("categoryValues.json")
            jsonFile = json.load(f)
            category_values = jsonFile[column_name]
        except:
            return f"Please add category values for the {column_name} column in the categoryValues.json file and try again."

        encoding = tiktoken.get_encoding("cl100k_base")
        dataList = []
        tempList = []
        i = 0
        while i < len(df):
            row_to_append = df.iloc[i][column_name]

            #df_new = pd.concat([df_new, row_to_append.to_frame().T], ignore_index=True)
            tempList.append(row_to_append)
            
            # Calculate current token count
            dfAsString = f"{tempList}"
            numTokens = len(encoding.encode(dfAsString))

            if (numTokens >= 1000) or (i == len(df)-1):
                #dataList.append(df_new)
                #df_new = pd.DataFrame(columns=df.columns)
                dataList.append(tempList)
                tempList = []

            i += 1

        #print(len(dataList))

        fullList = []
        for partialDf in dataList:
            print(". ")
            x = pd.Series(partialDf)
            xLength = len(x)
            #print(xLength)

            # message = f'''
            # Categorize the data in the following list into the following category values:\n
            # {category_values}
            # Only use those listed category values. Do not create any new values.
            # \n
            # Respond with all data points in the same order in this list format: ["item1", "item2", "item3"]
            # The response should contain the same number of items as the data series provided below. 
            # Therefore, the response list should contain {xLength} items.
            # \n
            # Data:\n
            # {x}
            # '''
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
            #print(len(newList))
            fullList = fullList + newList
            #print(fullList)

        df.drop(column_name, axis=1, inplace=True)
        #df["Category"] = fullList
        df[column_name] = fullList

        dummyList = []
        dummyList.append(column_name)
        updatedDf = pd.get_dummies(df, columns=dummyList)
        updatedDf.to_csv("updatedFeedback.csv", index=False)

        return "complete"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")

class CreateDataPrepPlan(BaseTool):
    name = "Create Data Prep Plan"
    description = (
        "Use this tool to create a step by step plan for cleaning/feature engineering a dataset for machine learning."
    )

    def _run(self, folder_path: str):
        df = pd.read_csv(folder_path)
        data = df.head()
        buf = io.StringIO()
        s = df.info(buf=buf)
        info = buf.getvalue()

        message = f'''
        What data preparation steps would you recommend for a machine learning model based on the following data summary
        and first 5 row sample of the dataset?

        Data summary:
        {info}

        Data sample:
        {data}
        '''

        return "test"
    
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

tools.append(FindCategoryOpportunitiesTool())
tools.append(AssignCategoryValuesTool())

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
