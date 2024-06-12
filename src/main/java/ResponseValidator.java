import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ResponseValidator {

    @Step("Проверка успешного ответа на вход")
    public static void validateSuccessfulLoginResponse(Response response) {
        response.then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Step("Проверка ответа на вход с невалидными учетными данными")
    public static void validateInvalidLoginResponse(Response response) {
        response.then()
                .log().all()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
