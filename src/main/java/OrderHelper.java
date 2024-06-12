import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderHelper {

    @Step("Получение списка ингредиентов")
    public static List<String> getIngredientIds(String baseUri) {
        Response response = given().get(baseUri + Adresses.INGREDIENTS);
        return response.jsonPath().getList("data._id");
    }

    @Step("Создание заказа с авторизацией")
    public static Response createOrderWithAuthorization(OrderRequest orderRequest, String accessToken, String baseUri) {
        return given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(orderRequest)
                .when()
                .post(baseUri + Adresses.ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAuthorization(OrderRequest orderRequest, String baseUri) {
        return given()
                .contentType("application/json")
                .body(orderRequest)
                .when()
                .post(baseUri + Adresses.ORDERS);
    }
}
