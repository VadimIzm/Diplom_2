import org.junit.Before;
import ru.project.UserSteps;
import ru.project.NewUser;
import ru.project.Token;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;

public class EditUserDataTest {

    UserSteps userSteps;
    NewUser user, newUser;
    String token;

    @Before
    public void init() {
        userSteps = new UserSteps();
        user = NewUser.createValidUser();
        newUser = NewUser.createValidUser();
        userSteps.createUserAndLogin(user);
        token = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Изменение email пользователя c авторизацией")
    public void editUserEmailTest() {
        newUser = new NewUser(newUser.email, user.password, user.name);
        Response response = userSteps.editUser(newUser, token);
        userSteps.checkEditUserResponse(response, newUser.email, newUser.name);
    }

    @Test
    @DisplayName("Изменение имени пользователя c авторизацией")
    public void editUserNameTest() {
        newUser = new NewUser(user.email, user.password, newUser.name);
        Response response = userSteps.editUser(newUser, token);
        userSteps.checkEditUserResponse(response, newUser.email, newUser.name);
    }

    @Test
    @DisplayName("Ошибка при изменении данных пользователя без авторизации")
    public void editUserWithOutTokenTest() {
        Response response = userSteps.editUserWithOutToken(newUser);
        userSteps.checkEditUserWithoutAuthResponse(response);
    }
    @After
    public void delete() {
        if (token != null) {
            UserSteps.deleteUser(token);
        }
    }
}