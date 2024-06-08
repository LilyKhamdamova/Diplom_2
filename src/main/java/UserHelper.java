import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserHelper {

    @Step("Создать пользователя с валидными данными")
    public static Response createValidUser(UserCredentials userCredentials) {
        return given()
                .contentType(ContentType.JSON)
                .body(userCredentials).log().all()
                .when()
                .post(Adresses.REGISTER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Создать пользователя с невалидными данными")
    public static Response createInvalidUser(UserCredentials invalidUser) {
        return given()
                .contentType(ContentType.JSON)
                .body(invalidUser).log().all()
                .when()
                .post(Adresses.REGISTER);
    }

    @Step("Удалить информацию о пользователе")
    public static Response deleteUserInformation(String accessToken) {
        return given()
                .header("Authorization", accessToken).log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(Adresses.USER)
                .then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"))
                .extract().response();
    }

    @Step("Проверить успешное создание пользователя")
    public static void verifyUserCreationSuccess(Response response) {
        response.then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true));
    }

    @Step("Проверить неуспешное создание пользователя с кодом {expectedStatusCode} и сообщением {expectedMessage}")
    public static void verifyUserCreationFailure(Response response, int expectedStatusCode, String expectedMessage) {
        response.then()
                .log().all()
                .statusCode(expectedStatusCode)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }
}
