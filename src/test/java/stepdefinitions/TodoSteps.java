package stepdefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.TodoPage;

import java.util.List;

public class TodoSteps {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private TodoPage todoPage;

    // Public zero-argument constructor for Cucumber
    public TodoSteps() {}

    @Given("I open the TodoMVC app")
    public void i_open_the_todomvc_app() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://todomvc.com/examples/react/dist/");
        todoPage = new TodoPage(page);
    }

    @When("I add a todo {string}")
    public void i_add_a_todo(String todoText) {
        todoPage.addTodo(todoText);
    }

    @Then("I should see the todo {string} in the list")
    public void i_should_see_the_todo(String todoText) {
        Locator todosLocator = page.locator(".todo-list li");
        todosLocator.first().waitFor(new Locator.WaitForOptions().setTimeout(5000));

        List<String> todos = todosLocator.allTextContents().stream()
                .map(String::trim)
                .toList();

        System.out.println("Expected todo: '" + todoText + "'");
        System.out.println("Actual todos: " + todos);

        Assert.assertTrue(
                todos.stream().anyMatch(t -> t.equalsIgnoreCase(todoText)),
                "Todo not found: " + todoText
        );
    }

    @When("I mark the first todo as completed")
    public void i_mark_first_todo_completed() {
        todoPage.completeFirstTodo();
    }

    @Then("the first todo should be completed")
    public void the_first_todo_should_be_completed() {
        Locator firstTodoCheckbox = page.locator(".todo-list li").first().locator(".toggle");
        firstTodoCheckbox.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        Assert.assertTrue(firstTodoCheckbox.isChecked(), "The first todo is not completed");
    }

    @When("I edit the first todo to {string}")
    public void i_edit_first_todo(String newText) {
        todoPage.editFirstTodo(newText);
    }

    @When("I delete the first todo")
    public void i_delete_first_todo() {
        todoPage.deleteFirstTodo();
    }

    



    @Then("I should see {int} todos in the list")
    public void i_should_see_n_todos_in_list(int count) {
        Assert.assertEquals(todoPage.getTodoCount(), count, "Todo count mismatch");
    }

    // --------------------------
    // 🔹 Negative Test Scenarios
    // --------------------------

    @When("I try to add an empty todo")
    public void i_try_to_add_empty_todo() {
        todoPage.addTodo(""); // Blank input
    }

    @Then("the todo list should still have {int} todos")
    public void the_todo_list_should_still_have(int expectedCount) {
        Assert.assertEquals(todoPage.getTodoCount(), expectedCount, "Unexpected todo count after invalid add");
    }

    @When("I add the same todo {string} twice")
    public void i_add_same_todo_twice(String todoText) {
        todoPage.addTodo(todoText);
        todoPage.addTodo(todoText);
    }

    @Then("the todo list should not have duplicate {string}")
    public void the_todo_list_should_not_have_duplicate(String todoText) {
        List<String> todos = page.locator(".todo-list li").allTextContents();
        long count = todos.stream().filter(t -> t.equalsIgnoreCase(todoText)).count();
        Assert.assertEquals(count, 1, "Duplicate todo found: " + todoText);
    }

    @When("I add a todo with special characters {string}")
    public void i_add_todo_with_special_characters(String todoText) {
        todoPage.addTodo(todoText);
    }

    // --------------------------
    // 🔹 Performance Test Scenarios
    // --------------------------

    @When("I add {int} todos")
    public void i_add_multiple_todos(int count) {
        for (int i = 1; i <= count; i++) {
            todoPage.addTodo("Todo " + i);
        }
    }

    @Then("I should see {int} todos added successfully")
    public void i_should_see_todos_added_successfully(int count) {
        Assert.assertEquals(todoPage.getTodoCount(), count, "Mismatch in bulk todos added");
    }

    @When("I rapidly add {int} todos")
    public void i_rapidly_add_todos(int count) {
        page.locator(".new-todo").focus();
        for (int i = 1; i <= count; i++) {
            page.keyboard().type("Rapid Todo " + i);
            page.keyboard().press("Enter");
        }
    }

    @Then("the app should remain responsive with {int} todos")
    public void the_app_should_remain_responsive(int count) {
        Assert.assertEquals(todoPage.getTodoCount(), count, "App did not handle rapid additions correctly");
    }

     // --------------------------
    // 🔹 Toggle Scenarios
    // --------------------------

    @When("I toggle the first todo")
    public void i_toggle_the_first_todo() {
        todoPage.toggleTodo(0);
    }

    @Then("the first todo should be active")
    public void the_first_todo_should_be_active() {
        Assert.assertFalse(todoPage.isTodoCompleted(0), "First todo is not active");
    }

    @When("I toggle all todos")
    public void i_toggle_all_todos() {
        todoPage.toggleAllTodos();
    }

    @Then("all todos should be completed")
    public void all_todos_should_be_completed() {
        for (Boolean status : todoPage.getAllTodosStatus()) {
            Assert.assertTrue(status, "Not all todos are completed");
        }
    }

    // --------------------------
    // 🔹 Filter Scenarios
    // --------------------------

    //@When("I filter todos by Active")
    @When("I filter todos by {string}")
    public void i_filter_todos_by(String filter) {
    todoPage.filterTodos(filter);
}

    public void i_filter_todos_by_active() {
        todoPage.filterTodos("Active");
    }

    @Then("I should see only {string} todos")
public void i_should_see_only_todos(String status) {
    String normalized = status.toLowerCase(); // normalize input
    for (int i = 0; i < todoPage.getTodoCount(); i++) {
        if (normalized.equals("active")) {
            Assert.assertFalse(todoPage.isTodoCompleted(i), "Completed todo is visible in Active filter");
        } else if (normalized.equals("completed")) {
            Assert.assertTrue(todoPage.isTodoCompleted(i), "Active todo is visible in Completed filter");
        } else {
            Assert.fail("Unknown filter: " + status);
        }
    }
}


   /*  @Then("I should see only active todos")
    public void i_should_see_only_active_todos() {
        for (int i = 0; i < todoPage.getTodoCount(); i++) {
            Assert.assertFalse(todoPage.isTodoCompleted(i), "Completed todo is visible in Active filter");
        }
    }*/

    @When("I filter todos by Completed")
    public void i_filter_todos_by_completed() {
        todoPage.filterTodos("Completed");
    }

   /* @Then("I should see only completed todos")
    public void i_should_see_only_completed_todos() {
        for (int i = 0; i < todoPage.getTodoCount(); i++) {
            Assert.assertTrue(todoPage.isTodoCompleted(i), "Active todo is visible in Completed filter");
        }
    }*/

    @Then("I close the browser")
    public void i_close_browser() {
        browser.close();
        playwright.close();
    }
}
