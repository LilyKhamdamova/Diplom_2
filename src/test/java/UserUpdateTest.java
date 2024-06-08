import io.restassured.response.Response;
import org.junit.Test;

public class UserUpdateTest extends BaseTest {

    private final String newEmail = UserCredentials.random().getEmail();
    private String newName = "New Name";

    @Test
    public void testUpdateUserData() {
        Response response = UserHelper.createValidUser(registrationCredentials);
        accessToken = response.jsonPath().getString("accessToken");
        Response updateUserResponse = UserUpdater.updateUserData(accessToken, newEmail, newName);
        UserUpdater.validateUpdateUserDataResponse(updateUserResponse, newEmail,newName);
    }

    @Test
    public void testUpdateUserDataWithoutAuthorization() {
        UserCredentials updatedUser = UserUpdater.prepareUserData(newEmail, registrationCredentials);
        Response response = UserUpdater.updateUserDataWithoutAuthorization(updatedUser);
        UserUpdater.validateUpdateUserDataWithoutAuthorizationResponse(response);
    }
}
