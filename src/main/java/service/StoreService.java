package service;

import entities.Order;
import entities.auxiliaries.Inventory;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
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

    public void storeOrderIdAssertion(Response response, String orderID) {
        Order ourOrder = response.as(Order.class);

        Assert.assertEquals((Integer.toString(ourOrder.getId())), orderID);
    }

    public void inventoryExistence(Inventory inventory) {
        Assert.assertTrue(inventory.getAvailable() >= 0);
        Assert.assertTrue(inventory.getSold() >= 0);
        Assert.assertTrue(inventory.getPending() >= 0);
    }
}
