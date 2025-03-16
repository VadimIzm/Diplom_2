import ru.project.NewUser;
import ru.project.Orders;
//import org.junit.Before;
import ru.project.UserSteps;
import ru.project.Token;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.After;

public class OrderListTest {
    NewUser user;
    Orders orders;
    String token;

//    @Before
//    public void init() {
//    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя, авторизованный пользователь")
    public void getOrderListTokenTest() {
        UserSteps userSteps = new UserSteps();
        user = NewUser.createValidUser();
        orders = new Orders();
        userSteps.createUser(user);
        token = Token.receivingToken(user);
        Response response = Orders.ordersListWithToken(token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders.total", notNullValue());
    }

    @Test
    @DisplayName("Невозможность получения заказов конкретного пользователя, неавторизованный пользователь")
    public void getOrderListWithoutTokenTest() {
        Response response = Orders.ordersListWithoutToken();
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void delete() {
        if (token != null) {
            UserSteps.deleteUser(token);
        }
    }
}