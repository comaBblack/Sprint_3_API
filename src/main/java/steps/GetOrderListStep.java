package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class GetOrderListStep {
    @Step("get list of orders")
    public static Response getOrderList(){
       return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/v1/orders");

    }
}
