import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

public class UserCreationTest extends BaseTest {

    @Before
    @Override
    public void setUp() {
        // Вызов метода setUp() из BaseTest для установки базового URI и генерации случайных учетных данных
        super.setUp();
    }

    @Test
    @DisplayName("Тест создания пользователя")
    @Description("Проверить, что новый пользователь может быть успешно создан")
    public void testUserCreation() {
        Response response = UserHelper.createValidUser(registrationCredentials);

        // Проверка успешного создания пользователя
        UserHelper.verifyUserCreationSuccess(response);

        // Получить accessToken из ответа
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Тест создания пользователя с существующим email")
    @Description("Проверить, что невозможно создать пользователя с уже существующим email")
    public void testUserCreationWithExistingEmail() {
        // Сначала создать пользователя
        Response response = UserHelper.createValidUser(registrationCredentials);
        UserHelper.verifyUserCreationSuccess(response);

        // Попытка создать пользователя с тем же email снова
        response = UserHelper.createValidUser(registrationCredentials);
        UserHelper.verifyUserCreationFailure(response, HttpURLConnection.HTTP_FORBIDDEN, "User already exists");
    }

    @Test
    @DisplayName("Тест создания пользователя без email")
    @Description("Проверить, что невозможно создать пользователя без email")
    public void testUserCreationWithoutEmail() {
        UserCredentials invalidUser = new UserCredentials("", registrationCredentials.getPassword(), registrationCredentials.getName());

        Response response = UserHelper.createInvalidUser(invalidUser);
        UserHelper.verifyUserCreationFailure(response, HttpURLConnection.HTTP_FORBIDDEN, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Тест создания пользователя без пароля")
    @Description("Проверить, что невозможно создать пользователя без пароля")
    public void testUserCreationWithoutPassword() {
        UserCredentials invalidUser = new UserCredentials(registrationCredentials.getEmail(), "", registrationCredentials.getName());

        Response response = UserHelper.createInvalidUser(invalidUser);
        UserHelper.verifyUserCreationFailure(response, HttpURLConnection.HTTP_FORBIDDEN, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Тест создания пользователя без имени")
    @Description("Проверить, что невозможно создать пользователя без имени")
    public void testUserCreationWithoutName() {
        UserCredentials invalidUser = new UserCredentials(registrationCredentials.getEmail(), registrationCredentials.getPassword(), "");

        Response response = UserHelper.createInvalidUser(invalidUser);
        UserHelper.verifyUserCreationFailure(response, HttpURLConnection.HTTP_FORBIDDEN, "Email, password and name are required fields");
    }
}
