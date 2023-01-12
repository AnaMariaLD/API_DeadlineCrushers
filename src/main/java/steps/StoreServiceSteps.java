package steps;

import entities.Order;
import entities.auxiliaries.Inventory;
import org.testng.Assert;
import service.StoreService;

import static service.uritemplate.StoreServiceUri.ORDER_BY_ID;
import static service.uritemplate.StoreServiceUri.STORE_INVENTORY;

public class StoreServiceSteps {
    public static final StoreService STORE_SERVICE = StoreService.getInstance();

    public static Order postOrder(Order order){
        return STORE_SERVICE.postRequest(ORDER_BY_ID, order).as(Order.class);
    }

    public static String deleteOrder(String orderId){
        return STORE_SERVICE.deleteRequest(ORDER_BY_ID,orderId).body().path("message");
    }

    public static Order getOrderById (String orderID) {
        return STORE_SERVICE.getRequest(ORDER_BY_ID, orderID).as(Order.class);
    }

    public static Inventory getInventory() {
        return STORE_SERVICE.getRequest(STORE_INVENTORY).as(Inventory.class);
    }
    public void inventoryExistence(Inventory inventory) {
        Assert.assertTrue(inventory.getAvailable() >= 0);
        Assert.assertTrue(inventory.getSold() >= 0);
        Assert.assertTrue(inventory.getPending() >= 0);
    }
}
