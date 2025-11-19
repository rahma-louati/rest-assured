package com.rest;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadFile {

    @Test
    public void Upload_file(){

        File file = new File("C:\\Users\\user\\Desktop\\Automate_Get_Request_Hamcrest_Collection.txt");
        String attributes = "{ \"name\": \"Automate_Get_Request_Hamcrest_Collection.txt\", \"parent\": { \"id\": 1234 } }";
    given().
                baseUri("https://postman-echo.com").
                multiPart("file", file).
                multiPart("attributes", attributes ,"application/json").
                log().all().
    when().
                post("/post").
    then().
                log().all().
                assertThat().
                statusCode(200);

    }
}
