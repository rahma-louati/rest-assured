package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class AutomatePost {

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
    public  void validate_post_request_bdd_style(){
        String payload = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"First_Workspace\",\n" +
                "    \"type\": \"personal\",\n" +
                "    \"description\": \"Espace de test QA Rahma\"\n" +
                "  }\n" +
                "}\n";
        given().
                body(payload).
        when().
                post("/workspaces").
                then().

                assertThat().
                body("workspace.name",equalTo("First_Workspace"),"workspace.id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));

    }
    @Test
    public  void validate_post_request_non_bdd_style(){
        String payload = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"First_Workspace2\",\n" +
                "    \"type\": \"personal\",\n" +
                "    \"description\": \"Espace de test QA Rahma\"\n" +
                "  }\n" +
                "}\n";

        Response response = with().
                body(payload).
                post("/workspaces");

                assertThat(response.<String>path("workspace.name"), equalTo("First_Workspace2"));
                assertThat(response.<String>path("workspace.id"), matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));

    }
    @Test
    public  void validate_post_request_payload_from_file(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
         when().
                post("/workspaces").
          then().
                log().all().
                assertThat().body("workspace.name", equalTo("First_Workspace3"),"workspace.id",
                        matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));

    }

    @Test
    public  void validate_post_request_payload_as_map(){
        HashMap<String, Object> mainObject = new HashMap<String, Object>();

        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name","myThridWorkspace");
        nestedObject.put("type","personal");
        nestedObject.put("description","Rest Assurd created this");

        mainObject.put("workspace", nestedObject);

        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                log().all().
                assertThat().body("workspace.name", equalTo("myThridWorkspace"),"workspace.id",
                        matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));

    }
}
