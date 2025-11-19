package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JacksonApi_JSONObject {
    @BeforeClass
    public void beforeClass(){

        String apiKey = System.getenv("POSTMAN_API_KEY");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeader("X-API-Key", apiKey)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();

        RestAssured.responseSpecification = responseSpecification;
    }


    @Test
    public void validate_post_request_payload_as_map() throws JsonProcessingException {

        HashMap<String, Object> mainObject = new HashMap<>();
        HashMap<String, Object> nestedObject = new HashMap<>();

        nestedObject.put("name", "myWorkspace2");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");

        mainObject.put("workspace", nestedObject);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainObject);

    given().
                body(mainObjectStr).
    when().
                post("/workspaces").
    then().
                spec(responseSpecification).
                assertThat().
                body("workspace.name", equalTo("myWorkspace2")).
                body("workspace.id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")).
                log().all();
    }

    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode NestedObjectNode = objectMapper.createObjectNode();
        NestedObjectNode.put("name", "myWorkspace3");
        NestedObjectNode.put("type", "personal");
        NestedObjectNode.put("description", "Rest Assured created this");

        ObjectNode mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.set("workspace", NestedObjectNode);

        String mainObjectStr = objectMapper.writeValueAsString(mainObjectNode);

    given().
                body(mainObjectStr).
    when().
                post("/workspaces").
    then().
                spec(responseSpecification).
                assertThat().
                body("workspace.name", equalTo("myWorkspace3")).
                body("workspace.id", matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")).
                log().all();
    }

}


