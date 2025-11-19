package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JacksonApi_JsonArray {

    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                addHeader("x-mock-match-request-body", "false").
                setContentType("application/json; charset=UTF-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);

        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_payload_Json_array_list() throws JsonProcessingException {

        HashMap<String, String> obj5001 = new HashMap<>();
        obj5001.put("id","5001");
        obj5001.put("type","None");

        HashMap<String, String> obj5002 = new HashMap<>();
        obj5002.put("id","5002");
        obj5002.put("type","Glazed");


        List<HashMap<String, String>> jsonList = new ArrayList<>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(jsonList);

    given().
                body(jsonList).
    when().
                post("/post").
    then().
                spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Sucess"));
    }

    @Test
    public void seialize_json_array_using_jackson() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNodeList = objectMapper.createArrayNode();

        ObjectNode obj5001Node = objectMapper.createObjectNode();
        obj5001Node.put("id","5001");
        obj5001Node.put("type","None");

        ObjectNode obj5002Node = objectMapper.createObjectNode();
        obj5002Node.put("id","5002");
        obj5002Node.put("type","Glazed");

        arrayNodeList.add(obj5001Node);
        arrayNodeList.add(obj5002Node);

        String jsonObjectStr = objectMapper.writeValueAsString(arrayNodeList);

    given().
                body(jsonObjectStr).
    when().
                post("/post").
    then().
                spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Sucess"));
    }
}
