package service;

import io.restassured.response.Response;
import service.uritemplate.UriTemplate;

public class PetService extends CommonService{
    private static PetService instance;

    private PetService() {
    }

    public static PetService getInstance() {
        if (instance == null) instance = new PetService();
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
}
