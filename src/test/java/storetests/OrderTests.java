package storetests;

import entities.Order;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.StoreServiceSteps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderTests {

    @Test
    public void checkOrderIsPlaced(){
        Order expectedOrder = createOrder();
        Order actualOrder = StoreServiceSteps.postOrder(expectedOrder);
        Assert.assertEquals(actualOrder.getId(),expectedOrder.getId(),
                "Expected order id is not the same as retrieved order id");

    }

   @Test
   public void checkPostedOrderIsDeleted(){
        Order newOrder = createOrder();
        StoreServiceSteps.postOrder(newOrder);
        Integer orderId = newOrder.getId();
        String responseMessage  = StoreServiceSteps.deleteOrder(orderId.toString());
        Assert.assertEquals(responseMessage,orderId.toString());

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
}
