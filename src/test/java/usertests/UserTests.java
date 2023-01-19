package usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Pet;
import entities.User;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.UserServiceSteps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public void createUserTest() throws IOException {
        user = createUserBody();
        writeUserDataInFile("CreatedUser",user);
        response = UserServiceSteps.createUser(user);
        User expectedUser = response.body().as(User.class);
        writeUserDataInFile("RetrievedUser", expectedUser);
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
    private Map<String, Object> mapUserData(User user){
        Map<String,Object> userDataMap = new HashMap<String,Object>();
        userDataMap.put("id",user.getId());
        userDataMap.put("username", user.getUsername());
        userDataMap.put("firstName",user.getFirstName());
        userDataMap.put("lastName",user.getLastName());
        userDataMap.put("email",user.getEmail());
        userDataMap.put("password",user.getPassword());
        userDataMap.put("phone",user.getPhone());
        userDataMap.put("userStatus",user.getUserStatus());
        return userDataMap;
    }

    private void writeUserDataInFile(String fileName, User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"), mapUserData(user));
        try (InputStream is = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"))) {
            Allure.addAttachment(fileName,"application/json",is,".json");
        }
    }
}
