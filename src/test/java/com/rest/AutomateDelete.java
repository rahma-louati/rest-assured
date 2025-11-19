package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomateDelete {

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
    public  void validate_delete_request_bdd_style() {
        String workspaceId = "b0507622-8e2b-4129-8a38-0d9a1328929f";
        String payload = "{\n" +
                "  \"workspace\": {\n" +
                "    \"name\": \"First_Workspace\",\n" +
                "    \"type\": \"personal\",\n" +
                "    \"description\": \"Espace de test QA Rahma\"\n" +
                "  }\n" +
                "}\n";

    given().
                pathParam("workspaceId",workspaceId).
    when().
                        delete("/workspaces/{workspaceId}").
    then().
                log().all().
                assertThat().
                body("workspace.id",   matchesPattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                        "workspace.id", equalTo(workspaceId));



    }
}
