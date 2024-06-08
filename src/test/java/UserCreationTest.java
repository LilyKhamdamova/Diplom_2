import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.baseURI;

public class UserCreationTest {

    private String accessToken;
    private UserCredentials registrationCredentials;

    @Before
    public void setUp() {
        baseURI = Adresses.BASE_URI;
        registrationCredentials = UserCredentials.random();
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

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            UserHelper.deleteUserInformation(accessToken)
                    .then()
                    .log().all()
                    .statusCode(HttpURLConnection.HTTP_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Тест создания пользователя с существующим email")
    @Description("Проверить, что невозможно создать пользователя с уже существующим email")
    public void testUserCreationWithExistingEmail() {
        // Сначала создать пользователя
        Response response = UserHelper.createInvalidUser(registrationCredentials);
        UserHelper.verifyUserCreationSuccess(response);

        // Попытка создать пользователя с тем же email снова
        response = UserHelper.createInvalidUser(registrationCredentials);
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
