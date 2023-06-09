# openai-jupyter-notebooks

## Prerequisites

### 1. Git Configure
We've setup a .gitconfig file so that all Juptyer Notebook cell outputs are muted to help keep the repo clean and make the comparisons easier to review for future pull requests.


1. Install the Jupyter Command 
    
        pip install Jupyter

> **Warning**
> You must have the Jupyter environment path set correctly prior to running the .gitconfig command below (make sure just typing `Jupyter` in a terminal doesn't fail). For Windows users, the default location that you'll need to add to your `PATH` environment variable (System or User) is `C:\Users\<user>\AppData\Roaming\Python\Python<version>\Scripts`, which is different than the system default installtion path (`C:\Python\Python<version>\Scripts`).

2. Run the following command so that you include the Jupyter Notebook Output Filter:

        git config --local include.path ../.gitconfig



### 2. Setup an OpenAI API Key
You should create an `openai-key.txt` file locally in your repo and populate it with your openAI API Key value (see https://platform.openai.com/account/api-keys). This file is ignored by git so make sure that all API Key Values make it to that file explicitly. 

You only need the API Key value in `openai-key.txt`; there is no additional formatting necessary.

## Jupyter Notebooks
### embeddings-gpt3.5-test
The `embeddings-gpt3.5-test` Notebook uses the Dragon Bank Python application `<insert link here>`

`<insert description for Notebook here>`

#### Sources
`<insert template Jupyter Notebook resources here>`

### OpenAICookbookPromptEngineering
The `OpenAICookbookPromptEngineering` Notebook uses the [apache fineract java web app (git branch 1.8.4)](https://github.com/apache/fineract/tree/1.8.4) as its application code to consume from.

It combs through all the directories for java files and removes erraneous text (multi-line comments, single-line comments, and blank white lines) and unit test code blocks to then store copies of each java file in a new flat directory. It then *currently* arbitrarily but rigidly chunks up those cleaned java files into 1600-token chunks & loads them into a dataframe to be vectorized by openAI.

With the vectorized text, you can then ask a question and context from that dataframe will be automatically included along with your question to chatGPT's API endpoint. Because we're using openAI's python modules directly, we can easily swap out models if needed.

#### Sources
The `OpenAICookbookPromptEngineering` Notebook has been modified from the following two examples:
        
* [Embedding Wikipedia Tokens (Prerequisite for Next Example)](https://github.com/openai/openai-cookbook/blob/2a2753e8d0566fbf21a8270ce6afaf761d7cdee5/examples/Embedding_Wikipedia_articles_for_search.ipynb)
* [Question Answering Using Embeddings](https://github.com/openai/openai-cookbook/blob/main/examples/Question_answering_using_embeddings.ipynb)