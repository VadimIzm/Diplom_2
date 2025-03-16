package ru.project;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class UserSteps extends Endpoints{
    public static final String USER = "/auth/user";

    @Step("Создание пользователя")
    public Response createUser(NewUser user){
        return given()
                .spec(baseUri())
                .and()
                .body(user)
                .when()
                .post(authRegister());
    }

    @Step("Логин пользователя")
    public Response loginUser(NewUser user) {
        return given()
                .spec(baseUri())
                .when()
                .and()
                .body(user)
                .post(loginUser());
    }

    @Step ("Изменение данных c авторизацией")
    public Response editUser(NewUser user, String authentication) {
        return given()
                .headers(
                        "Authorization", authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(baseUri())
                .when()
                .and()
                .body(user)
                .patch(USER);
    }

    @Step ("Изменение данных без авторизации")
    public Response editUserWithOutToken(NewUser user) {
        return given()
                .spec(baseUri())
                .when()
                .body(user)
                .and()
                .patch(USER);
    }

    @Step("Регистрация и логин")
    public void createUserAndLogin(NewUser user) {
        createUser(user);
        loginUser(user);
    }

    @Step ("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .spec(baseUri())
                .when()
                .delete(USER)
                .then();
    }
}