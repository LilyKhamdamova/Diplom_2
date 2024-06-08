import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginHelper {

    @Step("Отправка запроса на вход с валидными учетными данными")
    public static Response sendValidLoginRequest(UserCredentials loginCredentials) {
        return given()
                .contentType("application/json").log().all()
                .body(loginCredentials)
                .post(Adresses.AUTH_LOGIN);
    }

    @Step("Отправка запроса на вход с невалидными учетными данными")
    public static Response sendInvalidLoginRequest(UserCredentials invalidUserCredentials) {
        return given()
                .contentType("application/json")
                .body(invalidUserCredentials)
                .post(Adresses.AUTH_LOGIN);
    }
}
