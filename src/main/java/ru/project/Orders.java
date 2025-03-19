package ru.project;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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

    @Step("Проверка ответа при создании заказа")
    public void checkOrderingResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Step("Проверка ответа при создании заказа без ингредиентов")
    public void checkOrderingWithoutIngredientsResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка ответа при создании заказа без ингредиентов")
    public void checkOrderingWithoutHashResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(500);
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

    @Step("Проверка ответа при получении заказов с авторизацией")
    public void checkOrderListResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders.total", notNullValue());
    }

    @Step("Проверка ответа при получении заказов без авторизации")
    public void checkOrderListWithoutAuthResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}