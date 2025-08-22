package stepdefinitions;

import io.cucumber.java.en.*;
import api.Todoclientapi;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;


public class TodoclientapiSteps {

    private Todoclientapi apiClient = new Todoclientapi();
    private String lastAddedTodo;
    private String firstTodoId;

     private static final String BASE_URL = "http://localhost:8080";

    @Given("the Todo API is available via API for UI")
    public void the_todo_api_is_available_via_api_for_ui() {
        apiClient.setBaseUri("http://localhost:8080");
    }

    @When("I add a todo {string} via API")
    public void i_add_a_todo_via_api(String todoText) {
        apiClient.addTodo(todoText);
        lastAddedTodo = todoText;
    }

    @Then("the todo {string} should be present via API")
    public void the_todo_should_be_present_via_api(String todoText) {
        Assert.assertTrue(apiClient.getTodos().contains(todoText), "Todo is not present via API");
    }

    @When("I complete the first todo via API")
    public void i_complete_the_first_todo_via_api() {
        firstTodoId = apiClient.getFirstTodoId();
        apiClient.completeTodoById(firstTodoId);
    }

    @Then("the first todo should be completed via API")
    public void the_first_todo_should_be_completed_via_api() {
        Assert.assertTrue(apiClient.isFirstTodoCompleted(), "First Todo is not marked completed via API");
    }

    /*@When("I delete the first todo via API")
    public void i_delete_the_first_todo_via_api() {
        firstTodoId = apiClient.getFirstTodoId();
        apiClient.deleteTodoById(firstTodoId);
    }*/

    @When("I delete the first todo via API")
public void i_delete_the_first_todo_via_api() {
     firstTodoId = apiClient.getFirstTodoId();
    Assert.assertNotNull(firstTodoId, "❌ Todo ID is null, cannot delete");

    Response deleteResponse = given()
        .baseUri(BASE_URL)
        .when()
        .delete("/todos/{id}", firstTodoId);

    int statusCode = deleteResponse.statusCode();
    System.out.println("🗑️ Delete API response status: " + statusCode);

    // Some APIs return 200 OK, others 204 No Content
    Assert.assertTrue(statusCode == 200 || statusCode == 204,
            "❌ Expected 200 or 204 but got " + statusCode + " with response: " + deleteResponse.asString());

    System.out.println("✅ Todo delete request successful for ID=" + firstTodoId);
}


    @Then("the first todo should not be present via API")
    public void the_first_todo_should_not_be_present_via_api() {
        Assert.assertFalse(apiClient.getTodos().contains(lastAddedTodo), "Todo was not deleted via API");
    }
}
