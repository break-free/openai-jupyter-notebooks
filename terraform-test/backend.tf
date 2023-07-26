terraform {
  backend "s3" {
    bucket = "test-llm-terraform-backend"
    key    = "test1"
    region = "us-east-1"
  }
  required_providers {
    azuredevops = {
      source  = "microsoft/azuredevops"
      version = ">=0.3.0"
    }
    aws = {
      source = "hashicorp/aws"
      version = ">=5.8.0"
    }
  }
}
