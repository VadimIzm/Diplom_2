package ru.project;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Token extends Endpoints{

    @Step("Получение токена")
    public static String receivingToken(NewUser user) {
        Response response = given()
                .spec(baseUri())
                .and()
                .body(user)
                .when()
                .post(loginUser());
        return response.jsonPath().getString("accessToken");
    }
}