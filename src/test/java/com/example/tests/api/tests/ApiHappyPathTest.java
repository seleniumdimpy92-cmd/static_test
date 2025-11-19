package com.example.tests.api.tests;

import com.example.tests.api.base.ApiBase;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiHappyPathTest extends ApiBase {

    @Test
    public void getPostHappyPath() {
        given()
          .when()
            .get("/posts/1")
          .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", equalTo(1));
    }
}
