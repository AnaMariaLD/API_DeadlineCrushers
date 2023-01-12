package pettests;

import entities.Pet;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.PetService;
import service.StoreService;

import static service.uritemplate.PetServiceUri.PET_BY_ID;
import static service.uritemplate.PetServiceUri.PET_BY_STATUS;

public class AppTest {
    PetService service = PetService.getInstance();

    @Test
    public void getPetById() {
        String actualPetID = "2";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getBody().as(Pet.class).getId().toString().equals(actualPetID), "The expected Pet ID is not equals to actual Pet ID");
    }

    @Test
    public void getPetByIdNegative() {
        String actualPetID = "-23";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test
    public void getPetByIdNegativeWithCaracter() {
        String actualPetID = "wadxcw";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test
    public void getAllPetByStatusAvailable(){

        Response response = service.getRequest(PET_BY_STATUS,"available");
        System.out.println(response.getBody());
    }



}
