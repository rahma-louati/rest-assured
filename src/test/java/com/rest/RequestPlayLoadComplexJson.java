package com.rest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class RequestPlayLoadComplexJson {
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
    public void validate_post_request_payload_complex_json() {

        List<Integer> idArrayList = new ArrayList<Integer>();
        idArrayList.add(5);
        idArrayList.add(9);

        HashMap<String, Object> batterHashMap1 = new HashMap<String, Object>();
        batterHashMap1.put("id", "1001");
        batterHashMap1.put("type", "Regular");

        HashMap<String, Object> batterHashMap2 = new HashMap<String, Object>();
        batterHashMap2.put("id", idArrayList);
        batterHashMap2.put("type", "chocolate");

        List<HashMap<String, Object>> batterArrayList = new ArrayList<HashMap<String, Object>>();
        batterArrayList.add(batterHashMap1);
        batterArrayList.add(batterHashMap2);

        HashMap<String, Object> battersMain = new HashMap<>();
        battersMain.put("batter", batterArrayList);

        List<String> typeArrayList = new ArrayList<String>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String, Object> toppingHashMap1 = new HashMap<String, Object>();
        toppingHashMap1.put("id", "5001");
        toppingHashMap1.put("type", "None");

        HashMap<String, Object> toppingHashMap2 = new HashMap<String, Object>();
        toppingHashMap2.put("id", "5002");
        toppingHashMap2.put("type", typeArrayList);

        List<HashMap<String, Object>> toppingArrayList = new ArrayList<HashMap<String, Object>>();
        toppingArrayList.add(toppingHashMap1);
        toppingArrayList.add(toppingHashMap2);

        HashMap<String, Object> mainHashMap = new HashMap<>();
        mainHashMap.put("id", "0001");
        mainHashMap.put("type", "donut");
        mainHashMap.put("name", "Cake");
        mainHashMap.put("ppu", 0.55);
        mainHashMap.put("batters", battersMain);
        mainHashMap.put("topping", toppingArrayList);


    given().
                body(mainHashMap).
                post("/postComplexjson").
    then().
                spec(customResponseSpecification).
                body("msg", equalTo("Success"));
    }


    }
