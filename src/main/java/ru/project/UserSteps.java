package ru.project;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

    @Step("Проверка ответа при логине")
    public void checkLoginResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа при вводе неверного логина/пароля")
    public void checkWrongLoginPasswordResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
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

    @Step("Проверка ответа при создании уже существующего пользователя")
    public void checkCreateExistingUserResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @Step("Проверка ответа при создании пользователя без обязательных полей")
    public void checkCreateUserWithoutRequiredFieldsResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка ответа при создании пользователя без обязательных полей")
    public void checkEditUserResponse(Response response, String email, String name){
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @Step("Проверка ошибки при изменении данных пользователя без авторизации")
    public void checkEditUserWithoutAuthResponse(Response response){
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }




}