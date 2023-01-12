package service.uritemplate;

public class PetServiceUri {

    public static final UriTemplate PET_BY_ID = new UriTemplate("pet/");
    public static final UriTemplate PET = new UriTemplate("pet");
    public static final UriTemplate PET_BY_STATUS = new UriTemplate("pet/findByStatus");
    public static final UriTemplate UPLOAD_PICTURE_PET_ID = new UriTemplate("pet/%s/uploadImage");
}
