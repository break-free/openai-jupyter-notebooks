provider "azuredevops" {
  org_service_url       = "https://dev.azure.com/rw2"
}

provider "aws" {
  region     = "us-east-1"
}

data "azuredevops_project" "current_project" {
  name = "Allianz DevOps Starter"
}

resource "azuredevops_git_repository" "repo" {
  project_id = data.azuredevops_project.current_project.project_id
  name  = var.TF_VAR_resource_name
  default_branch = "refs/heads/main"
  initialization {
    init_type = "Clean"
  }
}
