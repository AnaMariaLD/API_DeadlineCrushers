package steps;

import entities.Order;
import io.restassured.response.Response;
import service.StoreService;

import static service.uritemplate.StoreServiceUri.STORE_ORDER;

public class StoreServiceSteps {
    public static final StoreService STORE_SERVICE = StoreService.getInstance();

    public static Order postOrder(Order order){
        return STORE_SERVICE.postRequest(STORE_ORDER, order).as(Order.class);
    }

    public static String deleteOrder(String orderId){
        return STORE_SERVICE.deleteRequest(STORE_ORDER ,orderId).body().path("message");
    }
}
