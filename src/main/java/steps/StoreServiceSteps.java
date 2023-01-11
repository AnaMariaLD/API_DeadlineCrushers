package steps;

import entities.Order;
import service.StoreService;

import static service.uritemplate.StoreServiceUri.ORDER_BY_ID;

public class StoreServiceSteps {
    public static final StoreService STORE_SERVICE = StoreService.getInstance();

    public static Order postOrder(Order order){
        return STORE_SERVICE.postRequest(ORDER_BY_ID, order).as(Order.class);
    }

    public static String deleteOrder(String orderId){
        return STORE_SERVICE.deleteRequest(ORDER_BY_ID,orderId).body().path("message");
    }
}
