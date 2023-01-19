package pettests;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Order;
import entities.Pet;
import entities.auxiliaries.Category;
import entities.auxiliaries.Tags;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.PetService;
import steps.PetSteps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

import static java.lang.Math.pow;
import static service.uritemplate.PetServiceUri.PET_BY_ID;
import static service.uritemplate.PetServiceUri.PET_BY_STATUS;

public class PetTests {
    PetService service = PetService.getInstance();

    @Test(groups = {"pet"})
    public void getPetById() {
        String actualPetID = "2";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getBody().as(Pet.class).getId().toString().equals(actualPetID), "The expected Pet ID is not equals to actual Pet ID");
    }

    @Test(groups = {"pet"})
    public void getPetByIdNegative() {
        String actualPetID = "-23";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test(groups = {"pet"})
    public void getPetByIdNegativeWithCharacter() {
        String actualPetID = "wadxcw";
        Response response = service.getRequest(PET_BY_ID, actualPetID);

        Assert.assertTrue(response.getStatusCode() == 404, "Pet with "+actualPetID+" exists.");
    }

    @Test(groups = {"pet","smoke"})
    public void createPetTest() throws IOException {
        Pet expectedPet = createPet();
        writePetDataInFile("CreatedPet", expectedPet);
        Pet actualPet = PetSteps.createPet(expectedPet);
        writePetDataInFile("RetrievedPet", actualPet);
        Assert.assertEquals(actualPet.getName(), expectedPet.getName(),
                "Expected pet name does not match created pet name");
    }

    @Test(dataProvider = "StatusData", groups = {"pet","smoke"})
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

    @Test(groups = {"pet","smoke"})
    public void getPetByStatusNegative() {

        Response response = PetSteps.getPetByStatus("teststatus213");
        Assert.assertFalse(response.getStatusCode() == 200, "statuscode 200 for status: teststatus213 !");
    }

    @Test(groups = {"pet"})
    public void attachImageToAPet(){
        File file = new File("C:\\Users\\Zoltan_Bone-Greczi\\IdeaProjects\\API_DeadlineCrushers\\src\\main\\java\\resources\\dog.jpg");
        Response response = PetSteps.postPictureToAPet("3","wdwdw",file);

        JsonPath json = response.jsonPath();
        Assert.assertTrue(response.statusCode() == 200, "Status code was not 200!");
        Assert.assertTrue(json.get("message").toString().contains("dog.jpg"),"Response message not contains dog.jpg!");
        Assert.assertTrue(json.get("message").toString().contains("wdwdw"),"Response message metadata not equals expexted metadata ");

    }

    @Test(groups = {"pet","smoke"})
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
    private Map<String, Object> mapPetData(Pet pet){
        Map<String,Object> petDataMap = new HashMap<String,Object>();
        petDataMap.put("id",pet.getId());
        petDataMap.put("category", pet.getCategory());
        petDataMap.put("name",pet.getName());
        petDataMap.put("urls",pet.getPhotoUrls());
        petDataMap.put("tags",pet.getTags());
        petDataMap.put("status",pet.getStatus());
        return petDataMap;
    }

    private void writePetDataInFile(String fileName, Pet pet) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"), mapPetData(pet));
        try (InputStream is = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/src/test/resources/" +fileName+ ".json"))) {
            Allure.addAttachment(fileName,"application/json",is,".json");
        }
    }
}
