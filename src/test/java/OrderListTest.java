import ru.project.NewUser;
import ru.project.Orders;
import org.junit.Before;
import ru.project.UserSteps;
import ru.project.Token;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;

public class OrderListTest {
    NewUser user;
    Orders orders;
    String token;

    @Before
    public void init() {
        UserSteps userSteps = new UserSteps();
        user = NewUser.createValidUser();
        orders = new Orders();
        userSteps.createUser(user);
        token = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя, авторизованный пользователь")
    public void getOrderListTokenTest() {
        Response response = Orders.ordersListWithToken(token);
        orders.checkOrderListResponse(response);
    }

    @Test
    @DisplayName("Невозможность получения заказов конкретного пользователя, неавторизованный пользователь")
    public void getOrderListWithoutTokenTest() {
        Response response = Orders.ordersListWithoutToken();
        orders.checkOrderListWithoutAuthResponse(response);
    }

    @After
    public void delete() {
        if (token != null) {
            UserSteps.deleteUser(token);
        }
    }
}