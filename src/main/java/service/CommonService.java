package service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import log.Log;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

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
        headers.put("accept","application/json");
        headers.put("Content-Type", "application/json");

        requestSpecification.headers(headers);
    }
    protected Response postRequest(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().post(prepareUri.apply(uri));
    }

    protected Response getRequestQueryUserLogin(String uri, String username, String password) {
        return requestSpecification.queryParam("username", username).queryParam("password", password).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    protected Response getRequest(String uri) {
        return requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    public Response getRequest(String uri, String id) {
        return requestSpecification.expect().statusCode(anyOf(is(HttpStatus.SC_OK), is(HttpStatus.SC_NOT_FOUND)))
                .log().ifError()
                .when().get(prepareUri.apply(uri) + id);
    }
    protected Response getRequestQuery(String uri, String status) {
        return requestSpecification.queryParam("status", status).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

        protected Response putRequest(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().put(prepareUri.apply(uri));

    }

    protected Response putRequest(String uri, Object body, String id) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().put(prepareUri.apply(uri));

    }
    protected Response deleteRequest(String uri) {
        return requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().delete(prepareUri.apply(uri));
    }

    protected Response getRequestAfterDelete(String uri) {
        Log.info("Sending the get request to the Uri " + prepareUri.apply(uri));
        Response responseToGetRq = requestSpecification
                .expect()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log()
                .ifError()
                .when()
                .get(prepareUri.apply(uri));
        Log.info("Response body is " + responseToGetRq.asPrettyString());
        return responseToGetRq;
    }

}
