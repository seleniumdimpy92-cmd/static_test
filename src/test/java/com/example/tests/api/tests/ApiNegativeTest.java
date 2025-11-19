package com.example.tests.api.tests;

import com.example.tests.api.base.ApiBase;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ApiNegativeTest extends ApiBase {

    @Test
    public void notFoundTest() {
        given()
          .when()
            .get("/posts/999999")
          .then()
            .statusCode(404);
    }
}
