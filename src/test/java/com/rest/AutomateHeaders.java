package com.rest;


import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class AutomateHeaders {

    @Test
    public void multipleHeaders() {
        Header header = new Header("header","value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");
    given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                header(header).
                header(matchHeader).
    when().
                get("/get").
    then().
                log().all().
                assertThat().statusCode(200);
    }

    @Test
    public void multiple_headers_using_Headers() {
        Header header = new Header("header","value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");

        Headers headers = new Headers(header, matchHeader);
    given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                headers(headers).
    when().
                get("/get").
                then().
                log().all().
                assertThat().statusCode(200);
    }

    @Test
    public void multiple_headers_using_Map() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");
    given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                headers(headers).
    when().
                get("/get").
    then().
                log().all().
                assertThat().statusCode(200);
    }

    @Test
    public void multiple_value_header_in_the_request() {
        Header header1 = new Header("multivalueHeader","value1");
        Header header2 = new Header("multivalueHeader","value2");

        Headers headers = new Headers(header1, header2);

    given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
               headers(headers).
                log().headers().
    when().
                get("/get").
    then().
                log().all().
                assertThat().statusCode(200);
    }

    @Test
    public void assert_response_headers() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");
    given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                headers(headers).
    when().
                get("/get").
    then().
                log().all().
                assertThat().statusCode(200).
                headers("responseHeader","resValue2",
                        "x-ratelimit-limit","120");

    }


    @Test
    public void extract_response_headers() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");

        Headers extractedHeaders = given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                headers(headers).
    when().
                get("/get").
    then().
                log().all().
                assertThat().statusCode(200).
                extract().
                headers();
        for (Header header: extractedHeaders){
            System.out.println("header name =" +header.getName() + ",");
            System.out.println("header value =" + header.getValue());
        }
    }

    @Test
    public void extract_multivalue_response_header() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header","value1");
        headers.put("x-mock-match-request-headers","header");

        Headers extractedHeaders = given().
                baseUri("https://c52d75c3-6c48-4a03-a4e3-962b2e319585.mock.pstmn.io").
                headers(headers).
    when().
                get("/get").
    then().
                assertThat().statusCode(200).
                extract().
                headers();
        List <String> values = extractedHeaders.getValues("multiValueHeader");
        for (String value:values){
            System.out.println(value);
        }
    }

}
