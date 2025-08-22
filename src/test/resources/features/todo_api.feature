@api
Feature: Todo API operations

  Background:
    Given the Todo API is available via API for UI

  @api
  Scenario: Add a new Todo
    When I add a todo "Buy groceries" via API
    Then the todo "Buy groceries" should be present via API

  @api
  Scenario: Complete the first Todo
    When I complete the first todo via API
    Then the first todo should be completed via API

  @api
  Scenario: Delete the first Todo
    When I delete the first todo via API
    Then the first todo should not be present via API
