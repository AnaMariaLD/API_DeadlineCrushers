package service;

import io.restassured.response.Response;
import service.uritemplate.UriTemplate;

public class StoreService extends CommonService{
    private static StoreService instance;

    private StoreService() {
    }

    public static StoreService getInstance() {
        if (instance == null) instance = new StoreService();
        return instance;
    }
    public Response getRequest(UriTemplate uri) {
        return super.getRequest(uri.getUri());
    }
    public Response postRequest(UriTemplate uri, Object body) {
        return super.postRequest(uri.getUri(), body);
    }
    public Response deleteRequest(UriTemplate uri, String orderId) {
        return super.deleteRequest(uri.getUri() + orderId);
    }
    public Response putRequest(UriTemplate uri, Object body) {
        return super.putRequest(uri.getUri(), body);
    }
    public Response getRequest(UriTemplate uri, String id) {
        return super.getRequest(uri.getUri(), id);
    }

}
