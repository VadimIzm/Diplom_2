import ru.project.*;
//import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NewOrderTest {

    NewUser user;
    Orders order;
    String token;

//    @Before
//    public void init() {
//    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderTokenTest() {
        UserSteps userSteps = new UserSteps();
        user = NewUser.createValidUser();
        order = new Orders();
        userSteps.createUser(user);
        token = Token.receivingToken(user);
        Response response = Orders.orderWithToken(Ingredients.orderWithIngredients(), token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithOutTokenTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithIngredients());
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Невозможность создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithoutIngredients());
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Невозможность создания заказа с неверным хешем ингредиентов")
    public void createOrderWithIncorrectIngredientsTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithIncorrectIngredients());
        response.then()
                .assertThat()
                .statusCode(500);
    }

    @After
    public void delete() {
        if (token != null) {
            UserSteps.deleteUser(token);
        }
    }
}