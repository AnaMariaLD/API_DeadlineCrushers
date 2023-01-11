package storetests;

import entities.auxiliaries.Inventory;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import service.StoreService;

import static service.uritemplate.StoreServiceUri.*;

public class AppTest {
    StoreService service = StoreService.getInstance();
    @Test
    public void getOrderById() {
        String orderID = "2";
        Response response = service.getRequest(ORDER_BY_ID, orderID);

        service.storeOrderIdAssertion(response, orderID);
    }

    @Test
    public void getInventory() {
        Inventory inventory = service.getRequest(STORE_INVENTORY).as(Inventory.class);

        service.inventoryExistence(inventory);
    }

}
