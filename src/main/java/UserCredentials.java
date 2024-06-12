//import org.apache.commons.lang3.RandomStringUtils;
//
//public class UserCredentials {
//    private String email;
//    private String password;
//    private String name;
//
//    public UserCredentials() {
//    }
//
//    public UserCredentials(String email) {
//        this.email = email;
//    }
//
//    public UserCredentials(String email, String password, String username) {
//        this.email = email;
//        this.password = password;
//        this.name = username;
//    }
//
//    public UserCredentials(String email, String password) {
//    }
//
//    public String getEmail() {
//        return email;
//    }
//    public static UserCredentials from(UserCredentials registrationCredentials) {
//        return new UserCredentials(registrationCredentials.getEmail(), registrationCredentials.getPassword());
//    }
//    public static UserCredentials random() {
//        return new UserCredentials("test" + RandomStringUtils.randomAlphabetic(5, 15) + "@ya.ru", "P@ssw0rd123", "UserName");
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}
import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {
    private String email;
    private String password;
    private String name;

    public UserCredentials() {
    }

    public UserCredentials(String email) {
        this.email = email;
    }

    public UserCredentials(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.name = username;
    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public static UserCredentials from(UserCredentials registrationCredentials) {
        return new UserCredentials(registrationCredentials.getEmail(), registrationCredentials.getPassword());
    }

    public static UserCredentials random() {
        return new UserCredentials("test" + RandomStringUtils.randomAlphabetic(5, 15) + "@ya.ru", "P@ssw0rd123", "UserName");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
