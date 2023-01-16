package storetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Order;
import entities.auxiliaries.Inventory;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.StoreServiceSteps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OrderTests {

    @Test (groups = { "order", "smoke" }, description = "Placing an order")
    @Description("Test Description: Testing to see if an order is placed by verifying the same order ID is returned")
    public void checkOrderIsPlaced() throws IOException {
        Order expectedOrder = createOrder();
        writeOrderDataInFile("PlacedOrder",expectedOrder);
        Order actualOrder = StoreServiceSteps.postOrder(expectedOrder);
        writeOrderDataInFile("RetrievedOrder",actualOrder);
        Assert.assertEquals(actualOrder.getId(), expectedOrder.getId(),
                "Expected order id is not the same as retrieved order id");

    }

   @Test (groups = { "order" }, description = "Deleting an order by ID")
   @Description("Test Description: Testing if an order of selected ID is deleted by verifying the response message")
   public void checkPostedOrderIsDeleted(){
        Order newOrder = createOrder();
        StoreServiceSteps.postOrder(newOrder);
        Integer orderId = newOrder.getId();
        String responseMessage  = StoreServiceSteps.deleteOrder(orderId.toString());
        Assert.assertEquals(responseMessage,orderId.toString());

   }

    @Test (groups = { "order"})
    public void getOrderById() {
        String orderID = "2";
        Order ourOrder = StoreServiceSteps.getOrderById(orderID);

        Assert.assertEquals((Integer.toString(ourOrder.getId())), orderID,
                "The recieved order does not match the requested id! We recieved order: " + (ourOrder.getId()));
    }

    @Test (groups = { "order", "smoke" })
    public void getInventory() {
        Inventory inventory = StoreServiceSteps.getInventory();

        Assert.assertTrue(inventory.getAvailable() >= 0, "Available stock is not a real value! ( >= 0");
        Assert.assertTrue(inventory.getSold() >= 0, "Sold amount is not a real value! ( >= 0");
        Assert.assertTrue(inventory.getPending() >= 0, "Pending amount is not a real value! ( >= 0");
    }

    private Order createOrder(){
        Random random = new Random();
        int id = random.nextInt(1,10);
        int petId = random.nextInt(0,10);
        int quantity = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(dateTimeFormatter);
        String shipDate = localDateTime.toString();
        String status = "placed";
        return new Order()
                .setId(id)
                .setPetId(petId)
                .setQuantity(quantity)
                .setShipDate(shipDate)
                .setStatus(status)
                .setComplete(true);

    }
    private Map<String, Object> mapOrderData(Order order){
        Map<String,Object> orderDataMap = new HashMap<String,Object>();
        orderDataMap.put("id",order.getId());
        orderDataMap.put("petId", order.getPetId());
        orderDataMap.put("quantity",order.getQuantity());
        orderDataMap.put("shipDate",order.getShipDate());
        orderDataMap.put("status",order.getStatus());
        orderDataMap.put("complete",true);
        return orderDataMap;
    }

    private void writeOrderDataInFile(String fileName, Order order) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"), mapOrderData(order));
        try (InputStream is = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"))) {
            Allure.addAttachment(fileName,"application/json",is,".json");
        }
    }
}
