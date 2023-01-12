package pettests;

import entities.Pet;
import entities.auxiliaries.Category;
import entities.auxiliaries.Tags;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.PetService;
import steps.PetSteps;
import java.io.File;
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

    @Test(priority = 2)
    public void getPetByIdNegative() {
        String actualPetID = "-23";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test
    public void getPetByIdNegativeWithCharacter() {
        String actualPetID = "wadxcw";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test
    public void createPetTest() {
        Pet expectedPet = createPet();
        Pet actualPet = PetSteps.createPet(expectedPet);
        Assert.assertEquals(actualPet.getName(), expectedPet.getName(),
                "Expected pet name does not match created pet name");
    }

    @Test(dataProvider = "StatusData")
    public void getPetByStatus(String status) {
        Response petListResponse = PetSteps.getPetByStatus(status);
        List<Pet> petList = petListResponse.jsonPath().getList("", Pet.class);

        boolean result = checkIfUnexpectedStatusIsFound(petList,status);
        Assert.assertFalse(result,"Pet with not "+status+" status was found!");
    }

    @DataProvider(name = "StatusData")
    public Object[][] getData(){
        return new Object[][]{
                {"sold"},{"available"},{"pending"}
        };
    }

    @Test
    public void getPetByStatusNegative() {

        Response response = PetSteps.getPetByStatus("teststatus213");
        Assert.assertFalse(response.getStatusCode() == 200, "statuscode 200 for status: teststatus213 !");
    }

    @Test
    public void attachImageToAPet(){
        File file = new File("C:\\Users\\Zoltan_Bone-Greczi\\IdeaProjects\\API_DeadlineCrushers\\src\\main\\java\\resources\\dog.jpg");
        Response response = PetSteps.postPictureToAPet("3","wdwdw",file);

        JsonPath json = response.jsonPath();
        Assert.assertTrue(response.statusCode() == 200, "Status code was not 200!");
        Assert.assertTrue(json.get("message").toString().contains("dog.jpg"),"Response message not contains dog.jpg!");
        Assert.assertTrue(json.get("message").toString().contains("wdwdw"),"Response message metadata not equals expexted metadata ");

    }

    @Test
    public void attachImageToAPetNegative(){
        File file = new File("C:\\Users\\Zoltan_Bone-Greczi\\IdeaProjects\\API_DeadlineCrushers\\src\\main\\java\\resources\\Proiect_pestStore_api.docx");
        Response response = PetSteps.postPictureToAPet("3","wdwdw",file);

        System.out.println(response.body().asString());

        JsonPath json = response.jsonPath();
        Assert.assertFalse(response.statusCode() == 200 , "Status code was 200!");
        Assert.assertFalse(json.get("message").toString().contains("unknown"),"Should not accept unknown files");
        Assert.assertTrue(json.get("message").toString().contains("Proiect_pestStore_api.docx"),"Response message not contains dog.jpg!");
        Assert.assertTrue(json.get("message").toString().contains("wdwdw"),"Response message metadata not equals expexted metadata ");

    }

    private boolean checkIfUnexpectedStatusIsFound(List<Pet> petList, String status){
        System.out.println(petList);

        for(Pet pet: petList){
            if(!pet.getStatus().equals(status)){
                return true;
            }
        }
        return false;
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
