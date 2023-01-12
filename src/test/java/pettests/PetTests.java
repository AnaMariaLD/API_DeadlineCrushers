package pettests;

import entities.Pet;
import entities.auxiliaries.Category;
import entities.auxiliaries.Tags;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.PetService;
import steps.PetSteps;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static service.uritemplate.PetServiceUri.PET_BY_ID;
import static service.uritemplate.PetServiceUri.PET_BY_STATUS;

public class PetTests {
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

    @Test
    public void createPetTest() {
        Pet expectedPet = createPet();
        Pet actualPet = PetSteps.createPet(expectedPet);
        Assert.assertEquals(actualPet.getName(), expectedPet.getName(),
                "Expected pet name does not match created pet name");
    }


    @Test
    public void getPetByStatusTest() {
        Response petListResponse = PetSteps.getPetByStatus("available");
        List<Pet> petList = petListResponse.jsonPath().getList("", Pet.class);

        boolean foundNotAvailable = false;
        for( Pet pet: petList){
            if(!pet.getStatus().equals("available")){
                foundNotAvailable = true;
                break;
            }
        }

        Assert.assertFalse(foundNotAvailable,"Pet with not available status was found!");
    }

    private Pet createPet() {
        Random random = new Random();
        Long id = random.nextLong(0L, (long) pow(10, 20));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Tags> tags = new ArrayList<>();
        Tags tag = new Tags().setId(id).setName("something" + timestamp);
        tags.add(tag);
        Category category = new Category().setId(id).setName("dog" + timestamp);
        return new Pet()
                .setId(id)
                .setCategory(category)
                .setName("Bobby" + timestamp)
                .setPhotoUrls(Arrays.asList("sourceOfPhoto" + timestamp))
                .setTags(tags)
                .setStatus("available");
    }



}
