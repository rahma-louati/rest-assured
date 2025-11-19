package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {


    @BeforeClass
    public void beforeClass(){

        String apiKey = System.getenv("POSTMAN_API_KEY");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com")
                .addHeader("X-API-Key", apiKey).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public  void validate_put_request__bdd_style() {
        String workspaceId = "f8a1611e-5e9e-4c4a-8ab4-a0a7689d5196";
        String payload = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"First_Workspace_Rahma\",\n" +
                "    \"type\": \"personal\",\n" +
                "    \"description\": \"Espace de test QA Rahma\"\n" +
                "  }\n" +
                "}\n";
    given().
                body(payload).
                pathParam("workspaceId",workspaceId).
    when().
                 put("/workspaces/{workspaceId}").
    then().
                assertThat().
                body("workspace.name", equalTo("First_Workspace_Rahma"), "workspace.id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                        "workspace.id", equalTo(workspaceId));


    }
}

