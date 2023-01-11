package entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
