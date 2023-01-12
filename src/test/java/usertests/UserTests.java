package usertests;

import entities.User;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.UserServiceSteps;

import java.util.ArrayList;

public class UserTests {
    private static Response response;
    private static User user;

    @Test (groups = { "user"})
    public void createUserArrayListTest() {
        ArrayList<User> users = createUserListBody();
        response = UserServiceSteps.createUserList(users);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test (groups = { "user"})
    public void getUsernameTest() {
        response = UserServiceSteps.getUsername();
        Assert.assertEquals(response.as(User.class).getUsername(), "TestAPI");
    }

    @Test (groups = { "user", "smoke"})
    public void getLoginTest() {
        response = UserServiceSteps.getUserLogin("a","a");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test (groups = { "user"})
    public void getUserLogoutTest() {
        response = UserServiceSteps.getUserLogout();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test (groups = { "user", "smoke"})
    public void createUserTest() {
        user = createUserBody();
        response = UserServiceSteps.createUser(user);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "createUserTest",groups = { "user"})
    public void deleteUserByUsernameTest() {
        user = createUserBody();

        UserServiceSteps.deleteUserByUsername(user.getUsername());
        response = UserServiceSteps.getUserByUsernameAfterDelete(user.getUsername());

        Assert.assertEquals(response.getStatusCode(), 404, "This is not the expected status code !");
    }

    @Test (groups = { "user"})
    public void updateUserTest(){
        user = createUserBody();
        response = UserServiceSteps.updateUser(user);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
    }

    private User createUserBody() {
        return new User()
                .setId(700)
                .setUsername("apitest")
                .setFirstName("api")
                .setLastName("test")
                .setEmail("api.test@gmail.com")
                .setPassword("qweTY09")
                .setPhone("0767890123")
                .setUserStatus(200);
    }

    private ArrayList<User> createUserListBody() {
        ArrayList<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User()
                .setId(100)
                .setUsername("TestAPI")
                .setFirstName("Test")
                .setLastName("API")
                .setEmail("Test_API@gmail.com")
                .setPassword("Asdfghj123")
                .setPhone("0782700926")
                .setUserStatus(200));
        return listOfUsers;
    }
}
