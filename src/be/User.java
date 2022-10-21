package be;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User(int id,String email,String password,String firstName,String lastName){
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return id;
    }
    public void setUserId(int userId) {
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
        return password;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

  //  public String getFullName() {
       // return this.firstName + this.lastName;
   // }
}
