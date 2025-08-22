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

- Filter todos (All / Active / Completed)

