package usertests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import service.UserService;

public class AndreiUserTests {
    UserService userService = UserService.getInstance();

    @Test
    public void getUserLogin(){

        }
}
