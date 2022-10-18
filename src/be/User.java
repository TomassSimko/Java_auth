package be;

import java.io.Serializable;

public class User implements Serializable {

    private String id;

    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;

    public User(String id,String email,String passwordHash,String firstName,String lastName){
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return id;
    }
    public void setUserId(String userId) {
        this.id = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEncryptedPassword() {
        return passwordHash;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.passwordHash = encryptedPassword;
    }

    public String getFullName() {
        return this.firstName + this.lastName;
    }
}
