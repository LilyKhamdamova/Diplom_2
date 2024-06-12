import org.junit.Before;
import org.junit.After;
import java.net.HttpURLConnection;
import static io.restassured.RestAssured.baseURI;
public class BaseTest {

    protected String accessToken;
    protected UserCredentials registrationCredentials;

    @Before
    public void setUp() {
        baseURI = Adresses.BASE_URI;
        registrationCredentials = UserCredentials.random();
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            UserHelper.deleteUserInformation(accessToken)
                    .then()
                    .log().all()
                    .statusCode(HttpURLConnection.HTTP_ACCEPTED);
        }
    }
}
