package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class StepDefinitions extends Utils {

    //Variable Declarations
    RequestSpecification res;
    ResponseSpecification resSpec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    JsonPath js;
    static String placeIdFromResponse;

    @Given("Add place payload with {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
    res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name,language,address));
    }

    @When("User call {string} with {string} http request")
    public void user_call_with_post_http_request(String endpoint,String method) {
        // Write code here that turns the phrase above into concrete actions
        //COnstructor will be called with value of resource which you pass
        APIResources apiResources = APIResources.valueOf(endpoint);

         resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
         response=res.when().post(apiResources.getResource());

        else if(method.equalsIgnoreCase("GET"))
            response=res.when().get(apiResources.getResource());


    }
    @Then("The API response is {int}")
    public void the_api_response_is(Integer int1) {

        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {


        Assert.assertEquals(getJsonPath(response,keyValue).toString(),expectedValue);

    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String method) throws IOException {

         placeIdFromResponse=getJsonPath(response,"place_id");
        res=given().spec(requestSpecification()).queryParam("place_id",placeIdFromResponse);

        user_call_with_post_http_request(method,"GET");
        String actualName=getJsonPath(response,"name");
        Assert.assertEquals(expectedName,actualName);

    }


    @Given("Delete place payload")
    public void delete_place_payload() throws IOException {


        res = given().spec(requestSpecification()).body(data.deletePayload(placeIdFromResponse));

    }
}
