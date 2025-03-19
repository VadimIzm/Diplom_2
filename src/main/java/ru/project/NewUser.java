package ru.project;
import io.qameta.allure.Step;

import java.util.Random;

public class NewUser extends Endpoints{
    public String email;
    public String password;
    public String name;

    public NewUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Формирование данных пользователя с валидными данными")
    public static NewUser createValidUser() {
        final String email = "emaill" + new Random().nextInt(10000) + "@bk.ru";
        final String password = "123456" + new Random().nextInt(10000);
        final String name = "user" + new Random().nextInt(10000);
        return new NewUser(email, password, name);
    }

    @Step("Формирование данных пользователя без поля email")
    public static NewUser createUserWithoutEmail() {
        final String password = "passwordWithoutEmail";
        final String name = "UsernameWithoutEmail";
        return new NewUser(null, password, name);
    }

    @Step("Формирование данных пользователя без поля Пароль")
    public static NewUser createUserWithoutPassword() {
        final String email = "vadimizmWithoutPassword@yandex.ru";
        final String name = "usernameWithoutPassword";
        return new NewUser(email, null, name);
    }

    @Step("Формирование данных пользователя без поля Имя")
    public static NewUser createUserWithoutName() {
        final String email = "vadimizmWithoutName@yandex.ru";
        final String password = "passWordWithoutName";
        return new NewUser(email, password, null);
    }
}