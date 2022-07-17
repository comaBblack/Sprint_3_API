package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient {


    public static RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("http://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();
    }
}
