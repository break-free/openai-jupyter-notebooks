# openai-jupyter-notebooks

## Prerequisites

### 1. Git Configure
We've setup a .gitconfig file so that all Juptyer Notebook cell outputs are muted to help keep the repo clean and make the comparisons easier to review for future pull requests.


1. Install the Jupyter Command 
    
        pip install Jupyter

> **Warning**
> You must have the Jupyter environment path set correctly prior to running the . gitconfig command below (make sure just typing `Jupyter` in a terminal doesn't fail). For Windows users, the default location that you'll need to add to your `PATH` environment variable (System or User) is `C:\Users\<user>\AppData\Roaming\Python\Python<version>\Scripts`, which is different than the system default installtion path (`C:\Python\Python<version>\Scripts`).

2. Run the following command so that you include the Jupyter Notebook Output Filter:

        git config --local include.path ../.gitconfig



### 2. Setup an OpenAI API Key
You should create an `openai-key.txt` file locally in your repo and populate it with your openAI API Key value (see https://platform.openai.com/account/api-keys). This file is ignored by git so make sure that all API Key Values make it to that file explicitly. 

You only need the API Key value in `openai-key.txt`; there is no additional formatting necessary.