package steps;

import data.CourierCreateTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CreateCourierStep {
    @Step("create courier")
    public static Response createCourier(CourierCreateTestData courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
}
