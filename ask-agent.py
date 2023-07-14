import os

# Set OpenAI API key
with open('openai-key.txt', 'r') as f:
    key = f.read().strip()

os.environ['OPENAI_API_KEY'] = key

from llm_prompt_creator import prompt as PR
from langchain.tools import BaseTool
from langchain.agents import initialize_agent
from langchain.chains.conversation.memory import ConversationBufferWindowMemory
from langchain.chat_models import ChatOpenAI
from typing import Optional
from langchain.text_splitter import CharacterTextSplitter
import json
from glob import glob
  
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
    
    def _arun(self, folder_path: str):
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
    model_name='gpt-3.5-turbo'
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

tools = [UnitTestGenerator(), CodeTestingTool(), DocumentSearch()]

# initialize agent with tools
agent = initialize_agent(
    agent='zero-shot-react-description',
    tools=tools,
    llm=llm,
    verbose=True,
    max_iterations=3,
    early_stopping_method='generate',
    memory=conversational_memory
)

#agent("Can you write unit tests for the code in the folder called javafiles?")
#agent("Can you test the code in the javafiles folder?")
#agent("Can you write unit tests for the code in the folder called javafiles, and then test the code in the javafiles folder?")
#agent("What is the capital of China?")
#agent("Can you look up the current weather in my area?")
agent("Can you search the documentation in the folder called docs-search?")
