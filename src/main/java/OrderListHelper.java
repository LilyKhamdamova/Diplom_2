import io.qameta.allure.Step;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListHelper {

    @Step("Проверка списка заказов с авторизацией")
    public static void validateOrderListWithAuthorization(String accessToken) {
        given()
                .header("Authorization", accessToken).log().all()
                .contentType("application/json")
                .when()
                .get(Adresses.BASE_URI + Adresses.ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Step("Проверка списка заказов без авторизации")
    public static void validateOrderListWithoutAuthorization() {
        given().log().all()
                .contentType("application/json")
                .when()
                .get(Adresses.BASE_URI + Adresses.ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
