import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.baseURI;

import java.net.HttpURLConnection;

public class OrderListTest {

    private String accessToken;
    private UserCredentials registrationCredentials;

    @Before
    public void setUp() {
        baseURI = Adresses.BASE_URI;
        registrationCredentials = UserCredentials.random();
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            UserHelper.deleteUserInformation(accessToken)
                    .then()
                    .log().all()
                    .statusCode(HttpURLConnection.HTTP_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Get order list with authorization")
    @Description("Check that the correct order list is returned with authorization")
    public void testGetOrderListWithAuthorization() {
        // Создать юзера
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");

        // Проверить, что вернулся корректный список
        OrderListHelper.validateOrderListWithAuthorization(accessToken);
    }

    @Test
    @DisplayName("Get order list without authorization")
    @Description("Check that the appropriate error message is returned when attempting to get the order list without authorization")
    public void testGetOrderListWithoutAuthorization() {

        OrderListHelper.validateOrderListWithoutAuthorization();
    }
}
