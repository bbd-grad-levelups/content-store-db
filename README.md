# content-store-db
A content store database repo

# CONCEPT
A mall administrator wishes to centralise the storage of the information that his tenants have, so that visitors to the mall can quickly and easily find all sorts of information relating to the content of these tenants' shops or restaurants. He thus decides to hire us to implement such a storage method, and we have chosen to use a Content Store Database stack with apache jackrabbit, aws EBS/EC2 and Java Maven Backend along with an API call tool such as Postman to demostrate the success of the system.

# HOUSE RULES
## TERRAFORM
Terraform init, validate, plan and apply to startup the 2 S3 buckets, EBS + EC2 backend server, and to connect to the Database holding our content store. Terraform destroy to deallocate the resources.

## BACKEND
### What to install

### How to run locally

### Build and deployment procedures
When you make a pull request, the project will be built on a github action to ensure it builds successfully and you can merge to main. Once all the necessary checks have passed, and with approval, you may merge with main, upon which time there shall be a build and deployment workflow done to the EBS. From there, you can access the api.content-store... for the api calls you wish to make.

### Accessing the API calls
You will need a JWT token set in your authentication headers in order to successfully call the API (spec to come later). Once set, you can access the CRUD operations that will be available to you, the user of the system.

## DATABASE
### How to connect

### How to turn off and on (persistence)

### How to integrate with Backend
