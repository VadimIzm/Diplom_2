import ru.project.UserSteps;
import ru.project.NewUser;
import ru.project.Token;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;

public class LoginUserTest {

    UserSteps userSteps;
    NewUser user, incorrectUser;
    String accessToken;

    @Before
    public void init() {
        userSteps = new UserSteps();
        user = NewUser.createValidUser();
        userSteps.createUser(user);
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Логин пользователя")
    public void loginUserTest() {
        Response response = userSteps.loginUser(user);
        userSteps.checkLoginResponse(response);
    }

    @Test
    @DisplayName("Ошибка входа с неверным email")
    public void loginUserIncorrectEmailTest() {
        incorrectUser = NewUser.createValidUser();
        incorrectUser = new NewUser(incorrectUser.email, user.password, user.name);
        Response response = userSteps.loginUser(incorrectUser);
        userSteps.checkWrongLoginPasswordResponse(response);
    }

    @Test
    @DisplayName("Ошибка входа с неверным паролем")
    public void loginUserIncorrectPasswordTest() {
        incorrectUser = NewUser.createValidUser();
        incorrectUser = new NewUser(user.email, incorrectUser.password, user.name);
        Response response = userSteps.loginUser(incorrectUser);
        userSteps.checkWrongLoginPasswordResponse(response);
    }

    @After
    public void delete() {
        if (accessToken != null) {
        UserSteps.deleteUser(accessToken);
    }
    }
}