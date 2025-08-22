@ui
Feature: TodoMVC React App

  # ✅ Basic Functional Scenarios
  @ui
  Scenario: Add a new todo item
    Given I open the TodoMVC app
    When I add a todo "Buy groceries"
    Then I should see the todo "Buy groceries" in the list
  @ui
  Scenario: Complete a todo
    Given I open the TodoMVC app
    When I add a todo "Walk the dog"
    And I mark the first todo as completed
    Then the first todo should be completed

  @ui
  Scenario: Delete a todo
    Given I open the TodoMVC app
    When I add a todo "Write blog post"
    And I delete the first todo
    Then I should see 0 todos in the list


  # 🚀 Performance Scenarios
  @ui
  Scenario: Add multiple todos quickly
    Given I open the TodoMVC app
    When I add a todo "Task 1"
    And I add a todo "Task 2"
    And I add a todo "Task 3"
    And I add a todo "Task 4"
    And I add a todo "Task 5"
    Then I should see 5 todos in the list

  @ui
  Scenario: Stress test by adding 50 todos
    Given I open the TodoMVC app
    When I add 50 todos
    Then I should see 50 todos in the list


  # ⚠️ Negative Scenarios
  @ui
  Scenario: Add an empty todo
    Given I open the TodoMVC app
    When I add a todo ""
    Then I should see 0 todos in the list
    
  @ui
  Scenario: Add a todo with only spaces
    Given I open the TodoMVC app
    When I add a todo "     "
    Then I should see 0 todos in the list

# ========================= TOGGLE SCENARIOS =========================
  @ui
  Scenario: Toggle a single todo
    Given I open the TodoMVC app
    When I add a todo "Finish homework"
    And I toggle the first todo
    Then the first todo should be completed
    And I toggle the first todo
    Then the first todo should be active

  Scenario: Toggle all todos
    Given I open the TodoMVC app
    When I add a todo "Task A"
    And I add a todo "Task B"
    And I toggle all todos
    Then all todos should be completed

  # ========================= FILTER SCENARIOS =========================
  @ui
  Scenario: Filter active todos
    Given I open the TodoMVC app
    When I add a todo "Task 1"
    And I add a todo "Task 2"
    And I mark the first todo as completed
    When I filter todos by "Active"
    Then I should see only "active" todos

  @ui
  Scenario: Filter completed todos
    Given I open the TodoMVC app
    When I add a todo "Task 1"
    And I add a todo "Task 2"
    And I mark the first todo as completed
    When I filter todos by "Completed"
    Then I should see only "completed" todos        

# Edit Scenario
  @ui
  Scenario: Edit a todo
    Given I open the TodoMVC app
    When I add a todo "Read a book"
    And I edit the first todo to "Read two books"
    Then I should see the todo "Read two books" in the list

