import ru.project.NewUser;
import ru.project.UserSteps;
import ru.project.Token;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import io.restassured.response.Response;
import org.junit.After;

public class NewUserTest {
    NewUser newUser;
    UserSteps userSteps;
    Token token;
    String accessToken;

    @Before
    public void before() {
        userSteps = new UserSteps();
        token = new Token();
        newUser = NewUser.createValidUser();
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUserTest() {
        userSteps.createUser(newUser);
        accessToken = Token.receivingToken(newUser);
        assertThat("accessToken должен быть не null", accessToken, notNullValue());
    }

    @Test
    @DisplayName("Невозможность создания уже существующего пользователя")
    public void failRegistrationExistedUserTest() {
        NewUser newUser = NewUser.createValidUser();
        userSteps.createUser(newUser);
        Response response = userSteps.createUser(newUser);
        userSteps.checkCreateExistingUserResponse(response);
    }

    @Test
    @DisplayName("Невозможность создания пользователя без обязательного поля Имя")
    public void createUserWithoutNameFail() {
        NewUser newUser = NewUser.createUserWithoutName();
        Response response = userSteps.createUser(newUser);
        userSteps.checkCreateUserWithoutRequiredFieldsResponse(response);
    }

    @Test
    @DisplayName("Невозможность создания пользователя без обязательного поля Пароль")
    public void createUserWithoutPasswordFail() {
        NewUser newUser = NewUser.createUserWithoutPassword();
        Response response = userSteps.createUser(newUser);
        userSteps.checkCreateUserWithoutRequiredFieldsResponse(response);
    }

    @Test
    @DisplayName("Невозможность создания пользователя без обязательного поля Почта")
    public void createUserWithoutEmailFail() {
        NewUser newUser = NewUser.createUserWithoutEmail();
        Response response = userSteps.createUser(newUser);
        userSteps.checkCreateUserWithoutRequiredFieldsResponse(response);
    }

    @After
    public void delete() {
        if (accessToken != null) {
            UserSteps.deleteUser(accessToken);
        }
    }
}