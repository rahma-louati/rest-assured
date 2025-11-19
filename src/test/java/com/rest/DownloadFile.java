package com.rest;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {

    @Test
    public void download_file() throws IOException {
        InputStream is =  given().
                baseUri("https://github.com/appium/android-apidemos/releases").

                log().all().
    when().
                get("/appium/android-apidemos/releases/download/v3.1.0/ApiDemos-debug.apk").
    then().
                log().all().
                extract().
                asInputStream();

             OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
             byte[] bytes = new byte[is.available()];
             is.read(bytes);
             os.write(bytes);
             os.close();


    }
}
