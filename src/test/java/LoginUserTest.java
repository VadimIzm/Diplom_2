import ru.project.UserSteps;
import ru.project.NewUser;
import ru.project.Token;
import ru.project.Orders;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;

public class LoginUserTest {

    UserSteps userSteps;
    NewUser user, incorrectUser;
    Orders orders;
    String accessToken;

//    @Before
//    public void init() {
//    }

    @Test
    @DisplayName("Логин пользователя")
    public void loginUserTest() {
        userSteps = new UserSteps();
        user = NewUser.createValidUser();
        //orders = new Orders();
        userSteps.createUser(user);
        accessToken = Token.receivingToken(user);
        Response response = userSteps.loginUser(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Ошибка входа с неверным логином и паролем")
    public void loginUserFailTest() {
        userSteps = new UserSteps();
        incorrectUser = NewUser.createAuthDataWithoutRegistration();
        Response response = userSteps.loginUser(incorrectUser);
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

    @After
    public void delete() {
        if (accessToken != null) {
        UserSteps.deleteUser(accessToken);
    }
    }
}