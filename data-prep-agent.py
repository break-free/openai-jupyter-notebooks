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
COLUMN_NAME: list = Field(description="the names of the columns that need to be modified for data cleaning/preparation. use python list formatting.")

class csvSchema(BaseModel):
    csv_file: str = CSV_FILE

class standardToolArgs(BaseModel):
    csv_file: str = CSV_FILE
    column_name: list = COLUMN_NAME


class CreateDataPrepPlanTool(BaseTool, BaseSettings):
    name = "Create Data Prep Plan"
    description = (
        "Use this tool to create a step by step plan for cleaning/feature engineering a dataset for machine learning. "
        "Always use this tool first when asked to prepare a dataset for machine learning models. "
        "After determining the data preparation steps, use the tools you have available to perform those steps. "
        "If there are no tools available for a specific step, skip that step and inform the user of what needs to be completed. "
        "Then move to the next step and use the tools to complete those if available."
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
        If there is a column that should appear in multiple steps that may be conflicting, include it only in the step that makes the most sense for that column.

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
                print("added list of rows")

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


class HandleOutliersTool(BaseTool, BaseSettings):
    name = "Handle Outliers Tool"
    description = (
        "Use this tool to handle outlier values in a dataset. If the concern is handling negative values, use the Handle Negative Values Tool instead."
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
        
        # keep track of which columns get updated
        changedCols = []
        notChangedCols = []
        
        for col in column_name:
            # given the context, ask LLM how it would handle the outliers
            data = df.head(10)
            buf = io.StringIO()
            s = df.info(buf=buf)
            info = buf.getvalue()
            descStats = df.describe()

            bins = pd.cut(df[col], bins=20, include_lowest=True)
            bucket_counts = bins.value_counts().sort_index()

            # skew of a normal distribution is -1 to 1, so anything far outside this range indicates big outlier
            skew = df[col].skew()

            f = open("datasetInfo.json")
            jsonFile = json.load(f)
            targetVar = jsonFile["Column Descriptions"]["Target"]
            dataDescription = jsonFile["Column Descriptions"]["Description"]
            columnsDescription = jsonFile["Column Descriptions"]["Columns"]

            # In the future the LLM should determine between different options for how to handle outliers.
            # Right now, it's not good enough at determining the best option based on the business context.
            # - Replace outliers with the median (median)
            # - Remove outliers from the dataset (trim)
            # - Floor and cap the outliers to the lower and upper quartiles (floor and cap)
            # - Do nothing because the outliers are valid in the business context (keep)
            
            message = f'''
            Which values should be considered outliers and adjusted to the median in the '{col}' column?
            Use the data set information below to help with this determination. Make the best decision based on the data available.

            Respond with two numbers: 1 low number for which everything equal to or below that value should be adjusted to the median, and
            1 high number for which everything equal to or above that value should be adjusted to the median. Also provide an explanation for the decision.

            Respond with this format: ['low number', 'high number', 'explanation']
            If there are no outliers on a given end of the data, reply with 'N/A' instead of a number.
            Remember, any number you provide for the low/high number is to be considered inclusive for median adjustment.

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

            Column Distribution for {col}:
            {bucket_counts}

            Skew of {col}: {skew}
            '''
            result = askLLM(message, "gpt-4")
            print(result)
            response = ast.literal_eval(result)

            # get the numbers for below and above which need to be handled as outliers
            try:
                lowNum = ast.literal_eval(response[0]) #if number is returned, need the correct type
            except:
                lowNum = response[0]
            try:
                highNum = ast.literal_eval(response[1]) #if number is returned, need the correct type
            except:
                highNum = response[1]
            
            # for now always use median; in future this should be dynamic based on the LLM response
            median = df[col].median()

            if lowNum == 'N/A' and highNum != 'N/A':
                df[col] = df[col].apply(lambda x: median if x >= highNum else x)
            elif lowNum != 'N/A' and highNum == 'N/A':
                df[col] = df[col].apply(lambda x: median if x <= lowNum else x)
            elif lowNum != 'N/A' and highNum != 'N/A':
                df[col] = df[col].apply(lambda x: median if (x <= lowNum) or (x >= highNum) else x)

            # write updated file and add column to the appropriate list to be tracked as changed or not
            if lowNum != 'N/A' or highNum != 'N/A':
                df.to_csv(updatedCSV, index=False)
                changedCols.append(col)
            else:
                notChangedCols.append(col)

        return f"Successfully handled outliers for the following columns: {changedCols}. No changes made for the following columns: {notChangedCols}."
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


class HandleNegativeValuesTool(BaseTool, BaseSettings):
    name = "Handle Negative Values Tool"
    description = (
        "Use this remove negative values from columns when those negative values don't make sense in the business context."
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
            median = df[col].median()
            df[col] = df[col].apply(lambda x: median if x < 0 else x)

        df.to_csv(updatedCSV, index=False)

        return "Negative values successfully replaced."
    
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

tools.append(CreateDataPrepPlanTool())
tools.append(TextPreprocessingTool())
tools.append(RemoveColumnsTool())
tools.append(EncodeCategoricalValuesTool())
tools.append(HandleOutliersTool())
tools.append(HandleNegativeValuesTool())

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

agent("Can you prepare a dataset for machine learning models? Use Bank_Personal_Loan.csv located in the datasets/ folder.")
#agent("Can you preprocess text in datasets/feedback1.csv and convert the Reports column into categories?")


# df = pd.read_csv("datasets/Bank_Personal_Loan.csv")
# data = df.head(10)
# buf = io.StringIO()
# s = df.info(buf=buf)
# info = buf.getvalue()
# descStats = df.describe()

# #skew of a normal distribution is -1 to 1, so anything far outside this range indicates big outlier
# print(df['Experience'].skew())

# f = open("datasetInfo.json")
# jsonFile = json.load(f)
# targetVar = jsonFile["Column Descriptions"]["Target"]
# dataDescription = jsonFile["Column Descriptions"]["Description"]
# columnsDescription = jsonFile["Column Descriptions"]["Columns"]


# bins = pd.cut(df["Experience"], bins=20, include_lowest=True)
# bucket_counts = bins.value_counts().sort_index()
# print(bucket_counts)
# print(df["Experience"].value_counts())
