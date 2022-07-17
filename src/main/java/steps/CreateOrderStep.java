package steps;

import data.CreateOrderTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class CreateOrderStep {
    @Step("create order")
    public static Response orderCreate(CreateOrderTestData order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}
