package ru.java.praktikum;
import data.CourierCreateTestData;
import data.CourierLoginTestData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static steps.CourierLoginStep.courierLogin;
import static steps.CreateCourierStep.createCourier;
import static steps.DeleteCourierStep.courierDelete;

public class CourierCreateTest {

    //создание курьера
    //успешный запрос возвращает ok: true
    //запрос возвращает правильный код ответа
    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCreateTest(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        CourierCreateTestData courier = new CourierCreateTestData(login, password, firstName);
        boolean actual = createCourier(courier).then().statusCode(201).extract().body().path("ok");
        assertEquals(true, actual);

        //удаление данных о созданном курьере
        CourierLoginTestData courierLog = new CourierLoginTestData(login, password);
        int responseId = courierLogin(courierLog).then().extract().path("id");

        courierDelete(responseId);
    }

    //нельзя создать двух одинаковых курьеров
    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    @Test
    @DisplayName("Ошибка при создании двух одинаковых курьеров")
    public void createSameCourirerErrTest(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        //создание первого курьера
        CourierCreateTestData courier = new CourierCreateTestData(login, password, firstName);
        createCourier(courier);
        //логин первого курьера, получение id
        CourierLoginTestData courierLog = new CourierLoginTestData(login, password);
        int responseId = courierLogin(courierLog).then().extract().path("id");
        //создание второго курьера с теми же данными
        CourierCreateTestData sameCourier = new CourierCreateTestData(login, password, firstName);
        createCourier(sameCourier).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        //удаление созданного курьера
        courierDelete(responseId);
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля
    //если одного из полей нет, запрос возвращает ошибку;
    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void createCourirerWithoutPassErrTest(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierCreateTestData courier = new CourierCreateTestData(login, "", firstName);
        createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createCourirerWithoutLoginErrTest(){
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierCreateTestData courier = new CourierCreateTestData("", password, firstName);
        createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
