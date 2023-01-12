package steps;

import entities.Pet;
import io.restassured.response.Response;
import service.ImageService;
import service.PetService;
import java.io.File;

import static service.uritemplate.PetServiceUri.*;

public class PetSteps   {
    private static final PetService PET_SERVICE = PetService.getInstance();
    private static final ImageService IMAGE_SERVICE = ImageService.getInstance();

    public static Response getPetByStatus(String status) {
        return PET_SERVICE.getRequestQuery(PET_BY_STATUS, status);
    }

    public static Pet createPet(Pet pet) {
        return PET_SERVICE.postRequest(PET, pet).as(Pet.class);
    }

    public static Response postPictureToAPet(String id, String additionalMetadata, File file){

        return IMAGE_SERVICE.postRequest(additionalMetadata, file, id);
    }




}
