import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;

public class OrderListTest extends BaseTest {

    @Before
    @Override
    public void setUp() {
        // Вызов метода setUp() из BaseTest для установки базового URI и генерации случайных учетных данных
        super.setUp();
    }

    @Test
    @DisplayName("Получение списка заказов с авторизацией")
    @Description("Проверка, что возвращается корректный список заказов при авторизации")
    public void testGetOrderListWithAuthorization() {
        // Создание пользователя и получение токена доступа
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");

        // Проверка, что возвращается корректный список заказов
        OrderListHelper.validateOrderListWithAuthorization(accessToken);
    }

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверка, что возвращается соответствующее сообщение об ошибке при попытке получить список заказов без авторизации")
    public void testGetOrderListWithoutAuthorization() {
        OrderListHelper.validateOrderListWithoutAuthorization();
    }
}
