package auth.base.models;

public class UserDto {
    public String id;
    public String email;
    public String passwordHash;
    public String firstName;
    public String lastName;

    public UserDto(String id,String email,String passwordHash,String firstName,String lastName){
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
