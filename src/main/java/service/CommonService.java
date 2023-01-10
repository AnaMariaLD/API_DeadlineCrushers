package service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public abstract class CommonService {
    private static final String BASE_URI = "http://localhost:80/v2/";
    private final Function<String, String> prepareUri = uri -> String.format("%s%s", BASE_URI, uri);
    protected RequestSpecification requestSpecification;

    protected CommonService() {
        requestSpecification = RestAssured.given();
        setCommonParams();
    }

    protected void setCommonParams() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("accept","application/json");
        requestSpecification.headers(headers);
    }
    protected Response postRequest(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().post(prepareUri.apply(uri));
    }

    protected Response getRequestQuery(String uri, String status) {
        return requestSpecification.queryParam("status", status).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    protected Response getRequest(String uri) {
        return requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    protected Response putRequest(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().put(prepareUri.apply(uri));

    }
    protected Response deleteRequest(String uri) {

        return requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().delete(prepareUri.apply(uri));
    }


}
