package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.util.List;

public class Todoclientapi {

    // Set base URI for REST Assured
    public void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
    }

    // Add a new todo
    public void addTodo(String todoText) {
        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body("{\"title\": \"" + todoText + "\", \"completed\": false}")
            .post("/todos")
            .then()
            .statusCode(201);
    }

    // Get all todo titles
    public List<String> getTodos() {
        Response res = RestAssured
            .given()
            .get("/todos")
            .then()
            .statusCode(200)
            .extract()
            .response();

        return res.jsonPath().getList("title");
    }

    // Get the first todo ID as string
    public String getFirstTodoId() {
        Response res = RestAssured
            .given()
            .get("/todos")
            .then()
            .statusCode(200)
            .extract()
            .response();

        return res.jsonPath().getString("[0].id"); // string ID
    }

    // Complete the todo by ID
    public void completeTodoById(String id) {
        RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body("{\"completed\": true}")
            .patch("/todos/" + id)
            .then()
            .statusCode(200);
    }

    // Check if first todo is completed
    public boolean isFirstTodoCompleted() {
        Response res = RestAssured
            .given()
            .get("/todos")
            .then()
            .statusCode(200)
            .extract()
            .response();

        return res.jsonPath().getBoolean("[0].completed");
    }

    
    // Delete todo by ID
    /*public void deleteTodoById(String id) {
        RestAssured
            .given()
            .delete("/todos/" + id)
            .then()
            .statusCode(200);
    }*/

    public void deleteTodoById(String id) {
        Response response = RestAssured
            .given()
            .delete("/todos/" + id)
            .then()
            .extract()
            .response();

        int statusCode = response.statusCode();
        if (statusCode != 200 && statusCode != 204) {
            throw new AssertionError("❌ Unexpected status code on DELETE: " + statusCode + 
                                     " Body: " + response.asString());
        }
    }
}
