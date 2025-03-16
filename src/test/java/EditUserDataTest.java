import org.junit.Before;
import ru.project.UserSteps;
import ru.project.NewUser;
import ru.project.Token;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
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
    @DisplayName("Изменение данных пользователя c авторизацией")
    public void editUserTest() {
        Response response = userSteps.editUser(newUser, token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(newUser.email))
                .body("user.name", equalTo(newUser.name));
    }

    @Test
    @DisplayName("Ошибка при изменении данных пользователя без авторизации")
    public void editUserWithOutTokenTest() {
        Response response = userSteps.editUserWithOutToken(newUser);
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