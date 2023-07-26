# For current Terraform tools, you need to make sure terraform CLI is installed and set the following environment variables.
# export AZDO_PERSONAL_ACCESS_TOKEN=
# export AWS_ACCESS_KEY_ID=
# export AWS_SECRET_ACCESS_KEY=

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
  
class UnitTestGenerator(BaseTool):
    name = "Unit Test Generator"
    description = (
        "use this tool when you need write unit test code for a software application. "
        "only use this tool when explicitly asked to write test code for specific files."
    )

    def _run(self, folder_path: str):
        PR.chunker(folder_path)
        store = PR.create_store()
        PR.prompt(store, show_context=False)
        return "Unit test generation complete."
    
    def _arun(self, folder_path: str):
        raise NotImplementedError("This tool does not support async")

from pydantic import BaseModel, BaseSettings, Field
from typing import Type
FOLDER_PATH: str = Field( description="the folder path and/or name")
CONNECTION_STRING: str = Field(description="the connection string required")

class TestingToolSchema(BaseModel):
    folder_path: str = FOLDER_PATH
    connection_string: str = CONNECTION_STRING

class CodeTestingTool(BaseTool, BaseSettings):
    name = "Code Tester"
    description = "use this tool when you need to run the code for unit tests to determine if they pass for a given codebase."
    args_schema: Type[TestingToolSchema] = TestingToolSchema

    def _run(self, folder_path: str, connection_string: str):
        return "Testing will occur here!"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


class TerraformInitTool(BaseTool):
    name = "Terraform Init"
    description = (
        "use this tool as the first step when you need to deploy infrastructure with terraform. "
        "this tool should be used to run 'terraform init'. The Terraform Plan tool should be "
        "run next if this tool run is successful"
    )

    def _run(self, folder_path: str):
        command = f"terraform -chdir={folder_path} init"
        result = os.system(command)
        
        if result != 0:
            return "Terraform initialization failed. Inform the user of the failure."
        
        return "Terraform Initialization Complete."
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")
    
class TerraformPlanTool(BaseTool):
    name = "Terraform Plan"
    description = (
        "use this tool when you need to deploy infrastructure with terraform. "
        "use this tool only after the Terraform Init tool runs successfully."
    )

    def _run(self, folder_path: str):
        command = f"terraform -chdir={folder_path} plan"
        result = os.system(command)

        if result != 0:
            return "Terraform plan failed. Run the Code Fixer tool to find the error. Then inform the user what needs to be fixed."
        
        return "Terraform Plan Complete."
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")

class FixTerraformTool(BaseTool):
    name = "Terraform Code Fixer"
    description = (
        "use this tool when there is a terraform code error and it needs to be fixed. "
        "after finding the error, inform the user and complete the chain."
    )

    def _run(self, folder_path: str):
        codeFiles = file_parser.get_file_list(folder_path, "*.tf")

        combinedFiles = ""
        for file in codeFiles:
            codelines = file_parser.get_code_lines(file)
            for line in codelines:
                combinedFiles = combinedFiles + line
        
        messages = [
        {"role": "user", "content": f"Review the following terraform code, and respond with an explanation of the error: \n {combinedFiles}"},
        ]
        response = openai.ChatCompletion.create(
            model="gpt-4",
            messages=messages,
            temperature=0.1,
            #stream=True
        )
        
        return response["choices"][0]["message"]["content"]
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")
    
class DocumentSearch(BaseTool):
    name = "Documentation Search"
    description = "use this tool when you need search through provided documentation for answers."

    def _run(self, folder_path: str):
        docs = [y for x in os.walk(folder_path) for y in glob(os.path.join(x[0], "*.txt"))]

        data = []
        for doc in docs:
            rawText = open(doc, encoding = "ISO-8859-1").read()
            data.append(rawText)

        textSplitter = CharacterTextSplitter(chunk_size=2000, separator="\n")

        docs = []
        for sets in data:
            docs.extend(textSplitter.split_text(sets))

        #Create chunks.json file
        with open("docs-search/store/chunks.json", 'w') as f:
            json.dump(docs, f)

        store = PR.create_store("docs-search/store/chunks.json", "docs-search/store/db")
        PR.prompt(store, show_context=False)

        return "Documentation search complete."
    
    def _arun(self, folder_path: str):
        raise NotImplementedError("This tool does not support async")


# initialize LLM (we use ChatOpenAI because we'll later define a `chat` agent)
llm = ChatOpenAI(
    openai_api_key=os.environ['OPENAI_API_KEY'],
    temperature=0,
    #model_name='gpt-3.5-turbo',
    model_name="gpt-4"
)

# initialize conversational memory
conversational_memory = ConversationBufferWindowMemory(
    memory_key='chat_history',
    k=5,
    return_messages=True
)

# new_prompt = agent.agent.create_prompt(
#     system_message=sys_msg,
#     tools=tools
# )
# agent.agent.llm_chain.prompt = new_prompt


tools = load_tools(
    ["human"],
    llm=llm,
)

#customTools = [UnitTestGenerator(), CodeTestingTool(), DocumentSearch()]

tools.append(UnitTestGenerator())
tools.append(CodeTestingTool())
tools.append(DocumentSearch())
tools.append(TerraformPlanTool())
tools.append(TerraformInitTool())
tools.append(FixTerraformTool())

# initialize agent with tools
agent = initialize_agent(
    #agent='zero-shot-react-description',
    #agent='chat-conversational-react-description',
    agent='structured-chat-zero-shot-react-description',
    tools=tools,
    llm=llm,
    verbose=True,
    max_iterations=5,
    early_stopping_method='generate',
    memory=conversational_memory
)

#agent("Can you write unit tests for the code in the folder called javafiles?")
#agent("Can you test the code in the javafiles folder?")
#agent("Can you write unit tests for the code in the folder called javafiles, and then test the code in the javafiles folder?")
#agent("What is the capital of China?")
#agent("Can you look up the current weather in my area?")
#agent("Can you search the documentation in the folder called docs-search?")
#agent("Can you test the code in the folder docs-search?")
#agent("Can you test the code in the folder docs-search and the connection string is 'iaoubrflaiewbrva'")
agent("can you deploy the infrastructure in the terraform-test folder?")
#agent("deploy infrastructure")
