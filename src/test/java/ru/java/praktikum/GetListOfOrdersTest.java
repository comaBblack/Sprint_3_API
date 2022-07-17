package ru.java.praktikum;
import data.CourierCreateTestData;
import data.CourierLoginTestData;
import data.CreateOrderTestData;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static steps.AcceptOrderStep.orderAccept;
import static steps.CourierLoginStep.courierLogin;
import static steps.CreateCourierStep.createCourier;
import static steps.CreateOrderStep.orderCreate;
import static steps.GetOrderListStep.getOrderList;


public class GetListOfOrdersTest {

    @Test
    @DisplayName("Список заказов")
    public void getOrderListTest(){
        //создание курьера
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierCreateTestData courier = new CourierCreateTestData(login, password, firstName);
        createCourier(courier);

        //логин курьера, получение id
        CourierLoginTestData courierLog = new CourierLoginTestData(login, password);
        int courierId = courierLogin(courierLog).then().extract().path("id");

        //создание заказа, получение id
        CreateOrderTestData order = new CreateOrderTestData("Kakashi", "Hatake", "Konoha, 31 apt.", "Baumanskaya", "+8 880 555 35 35", 4, "2022-06-06", "Saske, come back to Konoha", List.of("BLACK", "GRAY"));
        int orderId = orderCreate(order).then().extract().path("track");

        //принять заказ
        orderAccept(orderId, courierId);

        //проверить список заказов
        getOrderList().then().assertThat().body("orders", notNullValue()).and().body(not(equalTo("error")));
    }
}