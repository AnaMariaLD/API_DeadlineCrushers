package steps;

import entities.Pet;
import io.restassured.response.Response;
import service.PetService;
import service.uritemplate.UriTemplate;

import java.util.List;

import static service.uritemplate.PetServiceUri.*;

public class PetSteps   {
    private static final PetService PET_SERVICE = PetService.getInstance();

    public static Pet getPetByID(String id){
        return PET_SERVICE.getRequest(PET_BY_ID.toString(),id).as(Pet.class);
    }

    public static Response getPetByStatus(String status) {
        return PET_SERVICE.getRequestQuery(PET_BY_STATUS, status);
    }

    public static Pet createPet(Pet pet) {
        return PET_SERVICE.postRequest(PET, pet).as(Pet.class);
    }




}
