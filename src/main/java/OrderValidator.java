import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderValidator {

    @Step("Проверка успешного создания заказа")
    public static void validateSuccessfulOrderCreation(Response response) {
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Step("Проверка ошибки создания заказа без авторизации")
    public static void validateOrderCreationWithoutAuthorization(Response response) {
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка ошибки создания заказа без ингредиентов")
    public static void validateOrderCreationWithoutIngredients(Response response) {
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка ошибки создания заказа с неверным хешем ингредиентов")
    public static void validateOrderCreationWithInvalidIngredientsHash(Response response) {
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR); // код ошибки нужно изменить;
    }
}
