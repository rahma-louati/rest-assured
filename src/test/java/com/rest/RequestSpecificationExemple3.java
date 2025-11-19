package com.rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestSpecificationExemple3 {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        String apiKey = System.getenv("POSTMAN_API_KEY");

        requestSpecification = with().
                baseUri("https://api.postman.com").
                header("X-API-Key", apiKey).
                log().all();

    }

    @org.testng.annotations.Test
    public void validate_status_code() {

        Response response = requestSpecification.get("/workspaces").

    then().
                log().all().
                extract().
                response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void validate_status_body() {
        Response response =requestSpecification.get("/workspaces").
    then().
                log().all().
                extract().
                response();

         assertThat(response.statusCode(),equalTo(200));

         assertThat(response.path("workspaces[0].name").
                 toString(), equalTo("My Workspace"));

    }

}
