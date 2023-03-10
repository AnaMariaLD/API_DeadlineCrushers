package service;

import io.restassured.response.Response;
import service.uritemplate.UriTemplate;

public class UserService extends CommonService{
    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }
    public Response getRequest(UriTemplate uri) {
        return super.getRequest(uri.getUri());
    }
    public Response postRequest(UriTemplate uri, Object body) {
        return super.postRequest(uri.getUri(), body);
    }
    public Response deleteRequest(UriTemplate uri) {
        return super.deleteRequest(uri.getUri());
    }
    public Response putRequest(UriTemplate uri, Object body) {
        return super.putRequest(uri.getUri(), body);
    }
    public void deleteRequestByUsername(UriTemplate uri,String username){
        super.deleteRequest(uri.getUri(username));
    }
    public Response getRequestAfterDelete(UriTemplate uri, String username) {
        return super.getRequestAfterDelete(uri.getUri(username));
    }
    public Response getRequestQueryUserLogin(UriTemplate uri, String username, String password){
        return super.getRequestQueryUserLogin(uri.getUri(), username, password);
    }

}
