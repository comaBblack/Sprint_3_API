package steps;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class AcceptOrderStep {
    @Step("accept order")
    public static void orderAccept(int orderId, int courierId){
        given()
                .spec(getBaseSpec())
                .when()
                .with().params("id", orderId, "courierId",courierId)
                .put("/api/v1/orders/accept/");
    }
}
