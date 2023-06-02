# openai-jupyter-notebooks

## Prerequisites

### 1. Git Configure
Make sure to run the following command so that you include the Jupyter Notebook Output Filter:

    git config --local include.path ../.gitconfig
### 2. Setup an OpenAI API Key
You should create an `openai-key.txt` file locally in your repo and populate it with your openAI API Key value (see https://platform.openai.com/account/api-keys). This file is ignored by git so make sure that all API Key Values make it to that file explicitly. 

You only need the API Key value in `openai-key.txt`; there is no additional formatting necessary.