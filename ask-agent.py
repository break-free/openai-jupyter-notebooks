# Pre-reqs for Terraform Tools:
# Install Terraform CLI
# Create a folder in your current working directory that contains Terraform code to deploy (example in terraform-test/)
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

########### Unit test tools ###########
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

class CodeTestingTool(BaseTool):
    name = "Code Tester"
    description = "use this tool when you need to run the code for unit tests to determine if they pass for a given codebase."

    def _run(self, folder_path: str):
        return "Testing will occur here!"
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")


########### Terraform tools ###########
from pydantic import BaseModel, BaseSettings, Field
from typing import Type
FOLDER_PATH: str = Field( description="the folder path and/or name")
RESOURCE_NAME: str = Field(description="the name for the resources to be created with terraform")

class TerraformSchema(BaseModel):
    folder_path: str = FOLDER_PATH
    resource_name: str = RESOURCE_NAME

class TerraformInitTool(BaseTool):
    name = "Terraform Init"
    description = (
        "Do NOT use this tool until you have received all input arguments from the user directly. "
        "use this tool as the first step when you need to deploy infrastructure with terraform. "
        "this tool should be used to run 'terraform init'. If the user did not provide all arguments "
        "required to use this tool, use the human tool to get that information first. Never pass in a null "
        "value, and never use a value that wasn't provided directly by the user. "
        "The Terraform Plan tool should be run next if this tool run is successful."
    )

    #args_schema: Type[TerraformSchema] = TerraformSchema

    def _run(self, folder_path: str, resource_name: str):
        removeTfFiles = f"rm -r {folder_path}/.terraform/"
        os.system(removeTfFiles)
        removeTfLock = f"rm -r {folder_path}/.terraform.lock.hcl"
        os.system(removeTfLock)

        #resource_name = "test1"

        command = f"terraform -chdir={folder_path} init -backend-config='key={resource_name}'"
        result = os.system(command)
        
        if result != 0:
            return "Terraform initialization failed. Inform the user of the failure and end the chain."
        
        return "Terraform Initialization Complete."
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")
    
# class TerraformPlanTool(BaseTool, BaseSettings):
class TerraformPlanTool(BaseTool):
    name = "Terraform Plan"
    description = (
        "use this tool when you need to deploy infrastructure with terraform. "
        "use this tool only after the Terraform Init tool runs successfully. "
        "if the plan is successful, ask the user if they approve of the plan "
        "before moving on to the terraform apply step."
    )
    #args_schema: Type[TerraformSchema] = TerraformSchema

    def _run(self, folder_path: str, resource_name: str):
        command = f"terraform -chdir={folder_path} plan -var 'TF_VAR_repo_name={resource_name}'"
        result = os.system(command)

        if result != 0:
            return "Terraform plan failed. Run the Code Fixer tool to find the error. Then inform the user what needs to be fixed."
        
        return "Terraform Plan Complete. Ask the user if they approve of the plan."
    
    def _arun(self, query: str):
        raise NotImplementedError("This tool does not support async")
    
class TerraformApplyTool(BaseTool):
    name = "Terraform Apply"
    description = (
        "use this tool when you need to deploy infrastructure with terraform. "
        "use this tool only after the Terraform Plan tool runs successfully. "
        "if the apply is successful, inform the user and end the chain."
    )
    #args_schema: Type[TerraformSchema] = TerraformSchema

    def _run(self, folder_path: str, resource_name: str):
        command = f"terraform -chdir={folder_path} apply -var 'TF_VAR_repo_name={resource_name}'"
        result = os.system(command)

        if result != 0:
            return "Terraform apply failed. Run the Code Fixer tool to find the error. Then inform the user what needs to be fixed."
        
        return "Terraform Apply Complete."
    
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
            combinedFiles = combinedFiles + "\n\n"
        
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


########### Other tools ###########
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


########### Set up LLM and Agent ###########
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

tools = load_tools(
    ["human"],
    llm=llm,
)

tools.append(UnitTestGenerator())
tools.append(CodeTestingTool())
tools.append(DocumentSearch())
tools.append(TerraformPlanTool())
tools.append(TerraformInitTool())
tools.append(FixTerraformTool())
tools.append(TerraformApplyTool())

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

# The below print statement will show the information that the agent is working with (tool descriptions, etc.)
# print(agent.agent.llm_chain.prompt.messages[0].prompt.template)

#agent("Can you write unit tests for the code in the folder called javafiles?")
#agent("Can you test the code in the javafiles folder?")
#agent("Can you write unit tests for the code in the folder called javafiles, and then test the code in the javafiles folder?")
#agent("What is the capital of China?")
#agent("Can you look up the current weather in my area?")
#agent("Can you search the documentation in the folder called docs-search?")
#agent("Can you test the code in the folder docs-search?")
#agent("Can you test the code in the folder docs-search and the connection string is 'iaoubrflaiewbrva'")
#agent("can you deploy the infrastructure in the terraform-test folder with a resource name of test-repo-2?")
#agent("deploy infrastructure")
