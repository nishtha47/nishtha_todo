## TodoMVC Automation Framework (Playwright + RestAssured + Cucumber + Java)

This project provides a hybrid test automation framework for the TodoMVC React App.It covers both UI automation( Playwright+Java)
and API testing (RestAssured),integrated with Cucumber BDD for behavior-driven testing.

## Project Structure

```
src/test/java/
├── pages/                  # Page Object classes for UI automation
│   └── TodoPage.java
├── stepdefinitions/        # Step definitions for Cucumber scenarios
│   ├── TodoUISteps.java
│   ├── TodoclientapiSteps.java
│   └── Hooks.java
├── runners/                # Cucumber TestNG runner classes
│   └── TestRunner.java
└── api/                    # REST API client for Todos
    └── Todoclientapi.java

src/test/resources/
├── features/               # Gherkin feature files
│   ├── todo_ui.feature
│   └── todo_api.feature
└── config.properties       # Configurations (Base URL, credentials, etc.)
```

## Features Automated

# UI Scenarios (Playwright)

- Add a new todo

- Edit an existing todo

- Delete a todo

- Toggle (complete/uncomplete) todos

# API Scenarios (RestAssured)

- Create a todo

- Fetch all todos

- Toggle completion via API

- Delete a todo and validate deletion

- Validate completed vs active todos

# Tech Stack

- Java 17+

- Playwright for Java (UI automation)

- Cucumber + TestNG (BDD execution)

- RestAssured (API testing)

- Maven (build & dependency management)

 # ⚙️ Setup & Installation

# Clone the repository:

```
git clone https://github.com/nishtha47/nishtha_todo.git
cd todo-playwright-cucumber-java
```
# Install dependencies:

```
mvn clean install
```

# Run the TodoMVC app locally (example with JSON server):

```
npx json-server --watch db.json --port 8080

```

The app/API should be accessible at http://localhost:8080/todos


## Running Tests

# Run UI tests:

```
mvn test -Dcucumber.filter.tags="@ui"

```
# Run API tests:

```
mvn test -Dcucumber.filter.tags="@api"

```
# Run all tests:

```
mvn test
```
## Reports

# Cucumber HTML report generated under root of project:

# Naming convention:

reports<Date-time-stamp>test-output>

# Report format:
  - PDF
  - SPARK
  - Screenshots

## Sample Gherkin Scenarios

# UI

```
Scenario: Add a new todo item
  Given I open the TodoMVC app
  When I add a todo "Buy groceries"
  Then I should see the todo "Buy groceries" in the list
```

# API

```
Scenario: Delete a todo via API
  Given I add a todo "Read book" via API
  When I delete the first todo via API
  Then the first todo should not be present via API
```

## Best Practices Implemented

- Page Object Model (POM) for UI automation

- Reusable RestAssured client for APIs

- Config-driven setup (URLs, ports, etc.)

- Separation of concerns (UI vs API vs Step Definitions)

- Assertions with TestNG.Assert

  ## Mocking API Server

  # Create the JSON file

  Create a file called db.json in your project root:

   
    - You can add more todos as needed.

     - This will act as your API database.

  ```
  {
  "todos": [
    { "id": 1, "title": "Buy groceries", "completed": false },
    { "id": 2, "title": "Walk the dog", "completed": true }
  ] }
  
  ```


# Approach -1

# Create a Dockerfile (optional)

If you want a Docker container to serve the API:

```
# Use official Node.js image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json if you have (optional)
# COPY package*.json ./

# Install json-server globally
RUN npm install -g json-server

# Copy your JSON file into the container
COPY db.json .

# Expose port
EXPOSE 3000

# Command to start json-server
CMD ["json-server", "--watch", "db.json", "--host", "0.0.0.0", "--port", "3000"]

```

# Build the Docker image

From project root run

```
docker build -t todomvc-api .

```

# Run the Docker container

```
docker run -d -p 8080:3000 --name todomvc-api todomvc-api

```

Now your API server is accessible at:

```

http://localhost:8080/todos

```

# Approach -2

# Run json-server in Docker

From Project root run

```
docker run -d -p 8080:3000 -v $(pwd)/db.json:/data/db.json --name todomvc-api clue/json-server --watch /data/db.json --host 0.0.0.0
```
# Test the API

```
curl http://localhost:8080/todos
```

GET → list all todos

POST → add a todo

PATCH → update a todo

DELETE → delete a todo

# Stop the Container

```
docker stop todomvc-api
docker rm todomvc-api

```

# Json Output i got from api url


<img width="2610" height="1948" alt="image" src="https://github.com/user-attachments/assets/c108ceff-b0c7-4f50-8c01-7ac5cf4c61e6" />


## TestAutomation Report for UI Test Cases


<img width="1992" height="891" alt="image" src="https://github.com/user-attachments/assets/4bca56e5-14d9-41a0-8df1-1d70342c8e9d" />


## TestAutomation Report for API Test Cases


<img width="3278" height="1770" alt="image" src="https://github.com/user-attachments/assets/43c2cff1-c75f-477b-9c77-8232c8fb015b" />



 

    






- Filter todos (All / Active / Completed)

