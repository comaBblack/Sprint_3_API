package ru.java.praktikum;
import data.CourierCreateTestData;
import data.CourierLoginTestData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static steps.CourierLoginStep.courierLogin;
import static steps.CreateCourierStep.createCourier;
import static steps.DeleteCourierStep.courierDelete;

public class CourierLoginTest {
    @Before
    public void setUp() {
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierCreateTestData courier = new CourierCreateTestData(login, password, firstName);
        createCourier(courier);
    }



    //курьер может авторизоваться
    //для авторизации нужно передать все обязательные поля
    //успешный запрос возвращает id
    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successCourierLoginTest(){
        //создание курьера
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        CourierCreateTestData courier = new CourierCreateTestData(login, password, firstName);
        createCourier(courier);

        CourierLoginTestData courierLog = new CourierLoginTestData(login, password);
        courierLogin(courierLog).then().statusCode(200);
        int responseId = courierLogin(courierLog).then().extract().path("id");
        //проверка, что запрос возвращает id
        assertNotNull(responseId);
        //удаление данных о курьере
        courierDelete(responseId);

    }
    //если какого-то поля нет, запрос возвращает ошибку
    @Test
    @DisplayName("Ошибка при авторизации курьера без логина")
    public void courierLoginWithoutLoginErrTest(){
        CourierLoginTestData courier = new CourierLoginTestData("", "1234");
        courierLogin(courier).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test
    @DisplayName("Ошибка при авторизации курьера без пароля")
    public void courierLoginWithoutPassErrTest(){
        CourierLoginTestData courier = new CourierLoginTestData("ninjaKakashi", "");
        courierLogin(courier).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    //система вернёт ошибку, если неправильно указать логин или пароль
    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Ошибка при авторизации курьера под неверными учетными данными")
    public void courierLoginWithWrongLogpassErrTest(){
        CourierLoginTestData courier = new CourierLoginTestData("ninjaKakashi", "1111");
        courierLogin(courier).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}