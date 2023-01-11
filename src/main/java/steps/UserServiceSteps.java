package steps;

import entities.User;
import io.restassured.response.Response;
import service.UserService;

import java.util.ArrayList;

import static service.uritemplate.UserServiceUri.*;

public class UserServiceSteps {
    private static final UserService USER_SERVICE = UserService.getInstance();

    public static Response createUserList(ArrayList<User> users) {
        return USER_SERVICE.postRequest(USER_LIST, users);
    }

    public static Response getUsername() {
        return USER_SERVICE.getRequest(USER_NAME);
    }

    public static Response getUserLogin(String username, String password) {
        return USER_SERVICE.getRequestQueryUserLogin(USER_LOGIN, username, password);
    }

    public static Response getUserLogout() {
        return USER_SERVICE.getRequest(USER_LOGOUT);
    }

    public static Response createUser(User user) {
        return USER_SERVICE.postRequest(USER, user);
    }

    public static Response getUserByUsernameAfterDelete(String username) {
        return USER_SERVICE.getRequestAfterDelete(USER_BY_USERNAME, username);
    }

    public static void deleteUserByUsername(String username) {
        USER_SERVICE.deleteRequestByUsername(USER_BY_USERNAME, username);
    }

    public static Response updateUser(User user) {
        return USER_SERVICE.putRequest(USER_BY_USERNAME, user);
    }
}
