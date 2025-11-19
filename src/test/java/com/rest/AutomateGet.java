package com.rest;


import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class AutomateGet {

    @Test
    public void validate_get_statut_code() {

        String apiKey = System.getenv("POSTMAN_API_KEY");
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", apiKey).

                when().
                get("/workspaces").

                then().
                log().all().
                assertThat()
                .statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Team Workspace", "Rahma_Workspace1"))
                .body("workspaces.type", hasItems("personal", "team", "personal"))
                .body("workspaces.name", hasItems("My Workspace"))
                .body("workspaces[0].name", equalTo("My Workspace"))
                .body("workspaces.size()", equalTo(3));
    }
        @Test
       public void validate_response_body_hamcrest_learnings(){
            String apiKey = System.getenv("POSTMAN_API_KEY");
            given().
                    baseUri("https://api.postman.com").
                    header("X-API-Key", apiKey).
       when().
                    get("/workspaces").

       then().

                    assertThat().
                    statusCode(200).
                    body("workspaces.name", containsInAnyOrder("My Workspace", "Team Workspace", "Rahma_Workspace1"),
                            "workspaces.name", is(not(emptyArray())),
                            "workspaces.name", hasSize(3),
                            "workspaces.name", everyItem(anyOf(startsWith("My"), startsWith("Team"), startsWith("Rahma"))),
                            "workspaces[0]", hasKey("id"),
                            "workspaces[0]", hasValue("My Workspace"),
                            "workspaces[0]",hasEntry("id", "3bf975d3-0583-4b87-8f45-a7b05aeea030"),
                            "workspaces[0]",not(equalTo(Collections.EMPTY_MAP)),
                            "workspaces[0].name", allOf(startsWith("My"),containsString("Workspace"))
                    );
        }

    @Test
    public void extract_reponse() {
        String apiKey = System.getenv("POSTMAN_API_KEY");
        String response =  given().
                baseUri("https://api.postman.com").
                header("X-API-Key", apiKey).
    when().
                get("/workspaces").
    then().
                assertThat().
                statusCode(200).
                extract().
                response().asString();
        System.out.println("workspaces name = "+ JsonPath.from(response).getString("workspaces[0].name"));
    }

    @Test
    public void hamcrest_assert_on_extracted_reponse() {

        String apiKey = System.getenv("POSTMAN_API_KEY");
        String name = given().
                baseUri("https://api.postman.com").
                header("X-API-Key", apiKey).
    when().
                get("/workspaces").

    then().
                assertThat()
                .statusCode(200)
                .extract()
                .response().path("workspaces[0].name");
        System.out.println("workspaces name = " + name);
        Assert.assertEquals(name, "My Workspace");
    }


    }


