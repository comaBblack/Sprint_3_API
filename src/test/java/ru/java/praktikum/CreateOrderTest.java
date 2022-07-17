package ru.java.praktikum;
import data.CreateOrderTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static steps.CreateOrderStep.orderCreate;

@RunWith(Parameterized.class)
public class CreateOrderTest {


    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {List.of("BLACK", "GRAY")},
                {List.of("BLACK")},
                {List.of( "GRAY")},
                {List.of( "")}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void orderCreateTest(){

        CreateOrderTestData order = new CreateOrderTestData("Kakashi", "Hatake", "Konoha, 31 apt.", "Baumanskaya", "+8 880 555 35 35", 4, "2022-06-06", "Saske, come back to Konoha", color);
        orderCreate(order).then().statusCode(201).and().assertThat().body("track", notNullValue());
    } }

