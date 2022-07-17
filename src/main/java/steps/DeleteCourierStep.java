package steps;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static steps.RestAssuredClient.getBaseSpec;

public class DeleteCourierStep {
    @Step("delete courier")
    public static void courierDelete(int responseId){
        given()
                .spec(getBaseSpec())
                .delete("/api/v1/courier/{id}", responseId);
    }

}
