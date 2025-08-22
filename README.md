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
git clone https://github.com/<your-username>/todo-playwright-cucumber-java.git
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

  
 

    






- Filter todos (All / Active / Completed)

