package ru.project;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class Orders extends Endpoints{
    @Step("Создание заказа с авторизацией")
    public static Response orderWithToken(Ingredients ingredients, String token){
        return given()
                .spec(baseUri())
                .headers(
                        "Authorization", token,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(ingredients)
                .when()
                .post(orders());
    }

    @Step("Создание заказа без авторизации")
    public static Response orderWithOutToken(Ingredients ingredients) {
        return given()
                .spec(baseUri())
                .body(ingredients)
                .when()
                .post(orders());
    }

    @Step("Получение заказов конкретного пользователя: авторизованный пользователь")
    public static Response ordersListWithToken(String token) {
        return given()
                .headers(
                        "Authorization", token,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(baseUri())
                .when()
                .get(orders());
    }

    @Step("Получение заказов конкретного пользователя: неавторизованный пользователь")
    public static Response ordersListWithoutToken() {
        return given()
                .spec(baseUri())
                .when()
                .get(orders());
    }
}