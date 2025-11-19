package com.example.tests.api.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class ApiBase {
    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
