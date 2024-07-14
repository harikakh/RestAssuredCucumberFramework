package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {

                StepDefinitions steps = new StepDefinitions();
               if(StepDefinitions.placeIdFromResponse == null) {
                   steps.add_place_payload("harman", "English", "India");
                   steps.user_call_with_post_http_request("addPlaceAPI", "Post");
                   steps.verify_place_id_created_maps_to_using("harman", "getPlaceAPI");
               }
    }
}
