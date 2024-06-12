import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateTest extends BaseTest {

    private final String newEmail = UserCredentials.random().getEmail();
    private final String newName = "New Name";

    @Before
    @Override
    public void setUp() {
        // Вызов метода setUp() из BaseTest для установки базового URI и генерации случайных учетных данных
        super.setUp();
    }

    @Test
    @DisplayName("Тест обновления данных пользователя")
    @Description("Проверить, что данные пользователя могут быть успешно обновлены при наличии авторизации")
    public void testUpdateUserData() {
        // Создание пользователя и получение токена доступа
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");

        // Обновление данных пользователя
        Response updateUserResponse = UserUpdater.updateUserData(accessToken, newEmail, newName);

        // Проверка успешного обновления данных пользователя
        UserUpdater.validateUpdateUserDataResponse(updateUserResponse, newEmail, newName);
    }

    @Test
    @DisplayName("Тест обновления данных пользователя без авторизации")
    @Description("Проверить, что невозможно обновить данные пользователя без авторизации")
    public void testUpdateUserDataWithoutAuthorization() {
        // Подготовка данных пользователя для обновления
        UserCredentials updatedUser = new UserCredentials(newEmail, registrationCredentials.getPassword(), newName);

        // Обновление данных пользователя без авторизации
        Response response = UserUpdater.updateUserDataWithoutAuthorization(updatedUser);

        // Проверка, что обновление данных пользователя без авторизации не удалось
        UserUpdater.validateUpdateUserDataWithoutAuthorizationResponse(response);
    }
}
