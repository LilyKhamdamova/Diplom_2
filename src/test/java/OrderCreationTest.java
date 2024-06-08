import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

public class OrderCreationTest {

    private List<String> ingredientIds;

    @Before
    public void setUp() {
        // Отправка запроса для получения списка ингредиентов
        ingredientIds = OrderHelper.getIngredientIds(Adresses.BASE_URI);
    }

    @Test
    @DisplayName("Тест создания заказа с авторизацией")
    @Description("Проверка успешного создания заказа с авторизацией и использованием списка ингредиентов")
    public void testCreateOrderWithAuthorization() {
        // Создание заказа с авторизацией и использованием извлеченных идентификаторов ингредиентов
        OrderRequest orderRequest = new OrderRequest(ingredientIds);
        Response response = OrderHelper.createOrderWithAuthorization(orderRequest, "your_access_token", Adresses.BASE_URI);
        OrderValidator.validateSuccessfulOrderCreation(response);
    }

    @Test
    @DisplayName("Тест создания заказа без авторизации")
    @Description("Проверка ошибки при создании заказа без авторизации и использованием списка ингредиентов")
    public void testCreateOrderWithoutAuthorization() {
        // Создание заказа без авторизации и использованием извлеченных идентификаторов ингредиентов
        OrderRequest orderRequest = new OrderRequest(ingredientIds);
        Response response = OrderHelper.createOrderWithoutAuthorization(orderRequest, Adresses.BASE_URI);
        OrderValidator.validateOrderCreationWithoutAuthorization(response);
    }

    @Test
    @DisplayName("Тест создания заказа без ингредиентов")
    @Description("Проверка ошибки при создании заказа без указания ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        // Создание заказа без ингредиентов
        OrderRequest orderRequest = new OrderRequest(null);
        Response response = OrderHelper.createOrderWithAuthorization(orderRequest, "your_access_token", Adresses.BASE_URI);
        OrderValidator.validateOrderCreationWithoutIngredients(response);
    }

    @Test
    @DisplayName("Тест создания заказа с неверным хешем ингредиентов")
    @Description("Проверка ошибки при создании заказа с неверным хешем ингредиентов")
    public void testCreateOrderWithInvalidIngredientsHash() {
        // Создание заказа с неверным хешем ингредиентов
        OrderRequest orderRequest = new OrderRequest(Arrays.asList("invalid_ingredient_hash"));
        Response response = OrderHelper.createOrderWithAuthorization(orderRequest, "your_access_token", Adresses.BASE_URI);
        OrderValidator.validateOrderCreationWithInvalidIngredientsHash(response);
    }
}
