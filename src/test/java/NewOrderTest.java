import ru.project.*;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;

public class NewOrderTest {

    NewUser user;
    Orders order;
    String token;

    @Before
    public void init() {
        UserSteps userSteps = new UserSteps();
        user = NewUser.createValidUser();
        order = new Orders();
        userSteps.createUser(user);
        token = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderTokenTest() {
        Response response = Orders.orderWithToken(Ingredients.orderWithIngredients(), token);
        order.checkOrderingResponse(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithOutTokenTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithIngredients());
        order.checkOrderingResponse(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithoutIngredients());
        order.checkOrderingWithoutIngredientsResponse(response);
    }

    @Test
    @DisplayName("Невозможность создания заказа с неверным хешем ингредиентов")
    public void createOrderWithIncorrectIngredientsTest() {
        Response response = Orders.orderWithOutToken(Ingredients.orderWithIncorrectIngredients());
        order.checkOrderingWithoutHashResponse(response);
    }

    @After
    public void delete() {
        if (token != null) {
            UserSteps.deleteUser(token);
        }
    }
}