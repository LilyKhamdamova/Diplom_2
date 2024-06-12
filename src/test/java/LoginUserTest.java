import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.Before;

public class LoginUserTest extends BaseTest {

    private UserCredentials loginCredentials;
    private UserCredentials invalidUserCredentials;

    @Before
    public void setUp() {
        super.setUp();
        loginCredentials = UserCredentials.from(registrationCredentials);
        invalidUserCredentials = new UserCredentials("invalid_email@yandex.ru", "invalid_password");
    }

    @Test
    @DisplayName("Тест входа пользователя")
    @Description("Проверить, что пользователь может успешно войти в систему")
    public void testUserLogin() {
        // Создать пользователя
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");

        // Войти в систему с полученными учетными данными
        Response loginResponse = LoginHelper.sendValidLoginRequest(loginCredentials);
        ResponseValidator.validateSuccessfulLoginResponse(loginResponse);
    }

    @Test
    @DisplayName("Тест логина с неверным логином и паролем")
    @Description("Проверить, что невалидный пользователь не может войти в систему")
    public void testLoginWithInvalidCredentials() {
        // Создать пользователя с валидными данными
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");

        // Попытаться войти в систему с неверными учетными данными
        Response loginResponse = LoginHelper.sendInvalidLoginRequest(invalidUserCredentials);
        ResponseValidator.validateInvalidLoginResponse(loginResponse);
    }
}
