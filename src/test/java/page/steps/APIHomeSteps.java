package page.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;

public class APIHomeSteps {
    private Response response;

    @Given("User sets base URI as {string}")
    public void user_sets_base_uri_as(String base_URI) {
        System.out.println("User is setting the base URI........");
        RestAssured.baseURI = base_URI;
    }

    @When("User sends GET request to {string}")
    public void user_sends_get_request_to(String endpoint) {
        System.out.println("User is sending the GET request using endpoints........");
        response = RestAssured.get(endpoint);
        /*Response response = RestAssured
                .given()
                    .baseUri("https://jsonplaceholder.typicode.com")
                    .queryParam("userId", 1)
                .when()
                    .get("/posts") //get("/posts/1")
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        response = RestAssured.given().baseUri("https://jsonplaceholder.typicode.com")
                .body("{ \"id\": 1, \"title\": \"Updated Title\", \"body\": \"Updated Body\", \"userId\": 1 }")
                .when().put("/posts/1").then().extract().response();

         */
    }

    @Then("Response status code should be {int}")
    public void response_status_code_should(int expectedStatusCode) {
        System.out.println("User is getting the response code........");
        int statusCode = response.statusCode();
        System.out.println("Expected Status Code: " + expectedStatusCode);
        System.out.println("Actual Status Code: " + statusCode);
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @And("Response should contain {string}")
    public void response_should_contain(String expectedText) {
        System.out.println("User is getting the response body........");
        String responseBody = response.getBody().asString();
        System.out.println("API Response: " + responseBody);
        System.out.println("Expected Text: " + expectedText);
        Assert.assertTrue(response.getBody().asString().contains(expectedText));
    }
}
