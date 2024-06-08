import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.net.HttpURLConnection;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserUpdater {

        @Step("Обновление данных пользователя с авторизацией")
        public static Response updateUserData(String accessToken, String newEmail, String newName) {
            // Создаем тело запроса с новым email и именем пользователя
            UserCredentials updatedUser = new UserCredentials(newEmail, null, newName);

            return given()
                    .header("Authorization", accessToken).log().all()
                    .contentType("application/json")
                    .body(updatedUser)
                    .when()
                    .patch(Adresses.USER)
                    .then().log().all()
                    .extract()
                    .response();
        }

        @Step("Проверка успешного обновления данных пользователя")
    public static void validateUpdateUserDataResponse(Response response, String updatedEmail, String newName) {
        response.then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalToIgnoringCase(updatedEmail))
                .body("user.name", equalToIgnoringCase(newName)); // Compare ignoring case
    }


    @Step("Обновление данных пользователя без авторизации")
    public static Response updateUserDataWithoutAuthorization(UserCredentials updatedUser) {
        return given()
                .contentType("application/json")
                .body(updatedUser)
                .when()
                .patch(Adresses.USER)
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Подготовка данных пользователя")
    public static UserCredentials prepareUserData(String newEmail, UserCredentials registrationCredentials) {
        return new UserCredentials(newEmail, registrationCredentials.getPassword(), registrationCredentials.getName());
    }

    @Step("Проверка ответа об обновлении данных пользователя без авторизации")
    public static void validateUpdateUserDataWithoutAuthorizationResponse(Response response) {
        response.then()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
