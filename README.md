# Chatop

A simple API to learn Spring, Spring Security, ACLS, JWT Authentication, Spring Web, DTO, validation, annotations... 
This is the second project of my course as a full-stack Java / Angular developer with OpenClassrooms.

# Installation
## Requirements
- Java 17
- Access to a MySQL or MariadDB server and a database
- Maven
- Git
- Postman for testing purposes

## Installation
1. Clone this repository on your local machine or server and go to directory

``` bash
git clone https://github.com/wilzwert/Chatop.git
cd Chatop
```

2. Install dependencies
Windows : 
``` bash
./mvmn clean install
```

Linux / unix :
``` bash
mvn clean install
```

Or directly in your favorite IDE

3. Configuration

This application uses a .env file to configure most of its parameters.
Please copy the provided .env.example file to .env

Linux / unix :
``` bash 
cp .env.example .env
```

Windows
``` bash 
copy .env.example .env
```

Then setup variables according to your environment ; see .env.example for more information.
Please note that there is a slight difference whether you're using MySQL or MariaDB, although default values should work for both.

3. Create the database and import the schema provided in ressources/db_structure.sql

You can either use a GUI like PhpMyAdmin or load the file from command line :

``` bash
 mysql -u user -p database_name < ressources/db_structure.sql
 ```

# Start the application

``` bash 
./mvn spring-boot:run
```

This will make the application available on the host name and port you chose while configuring. See .env.example for more information.

# Test the API
1. With provided Postman collection : see resources/ChatopAPI.postman_collection.json

Import the collection in Postman : https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman

You can set host and port as collection variables _apiHost_ and _apiPort_, respectively,  by selecting the variables set on ChatopAPI root and editing them. 
Note that apiHost defaults to localhost and apiPort to 8080, as seen in env.examble.

Then run it. 
In case you encounter issues with files to upload, please set files manually for these requests :
- rentals/POST create
- rentals/POST create2
- rentals/PUT update

2. Use the "real" frontend provided by OpenClassrooms
- requirements : NodeJS, npm, Angular CLI 14 ; see repository Readme for more information 
- clone this repository : https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring
``` bash 
git clone https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring
cd  Developpez-le-back-end-en-utilisant-Java-et-Spring
```

- edit file proxy.config.json to match your Chatop API URL and port

- install dependencies
``` bash 
npm install
```

- run frontend
``` bash 
npm run start
```
or
``` bash 
ng serve
```

# Features
## API endpoints
> POST /api/auth/register : register a User (public endpoint)

> POST /api/auth/login : login with email and password, and get a JWT Token (public endpoint)

> GET /api/auth/me : get current User

> DELETE /api/auth/me : delete current User account 

> GET /api/rentals : get a list of all rentals

> GET /api/rental/:id : get an rental by its id

> POST /api/rental : create a Rental

> PUT /api/rental/:id : update a Rental (only if owned by current User)

> DELETE /api/rental/:id : delete a Rental (only if owned by current User)

> POST /api/message : create a message

### API documentation
Once the application is started, you should be able to access the API Documentation (please adjust host and port to your environment).
> http://localhost:8080/swagger-ui/index.html

If you set a custom SWAGGER_PATH in .env, for example 
> SWAGGER_PATH=/api-doc

You can also access Swagger UI on
> http://localhost:8080/api-doc

Please note that event if you did set a custom Swagger path, you will be redirected to /swagger-ui/index.html

## Security
- JWT Token authentication
- Request data validation through DTO
- ACLS and permissions used to control access to resources (implemented for Rentals)

## Persistence
- Entities relationships
- Cascade handling
- File upload / deletion

## Error handling, logging
- Global exception handler generating Http responses with appropriate Http status codes
- Logging levels can be configured in src/main/resources/application.properties. Defaults:

```
logging.level.root=warn
logging.level.org.springframework.security=warn
logging.level.com.openclassrooms.chatop=error
logging.level.com.openclassrooms.chatop.service=error
logging.level.com.openclassrooms.chatop.controller=error
```

Level info for services and controllers can get very verbose under certain circumstances  :)









