package steps;

import data.CourierLoginTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CourierLoginStep {
    @Step("courier login")
    public static Response courierLogin(CourierLoginTestData courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }
}
