package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.io.File;
import java.util.function.Function;

public class ImageService {

    private static final String BASE_URI = "http://localhost:80/v2/";
    private static ImageService instance;
    protected RequestSpecification requestSpecification;


    protected ImageService() {
        requestSpecification = RestAssured.given();

    }

    public Response postRequest(String body, File file, String id) {
        return requestSpecification.given().multiPart(file).multiPart("additionalMetadata",body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().post(BASE_URI+"pet/"+id+"/uploadImage");

    }

    public static ImageService getInstance() {
        if (instance == null) instance = new ImageService();
        return instance;
    }
}
