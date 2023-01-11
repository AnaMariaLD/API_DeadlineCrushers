package steps;

import entities.Pet;
import service.PetService;

import static service.uritemplate.PetServiceUri.PET_BY_ID;

public class PetSteps   {
    private static final PetService PET_SERVICE = PetService.getInstance();

    public static Pet getPetByID(String id){
        return PET_SERVICE.getRequest(PET_BY_ID.toString(),id).as(Pet.class);

    }
}
