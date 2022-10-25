package be;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean activated;

    private String pictureURL;

    public User(Integer id, String email, String password, String firstName, String lastName, Boolean activated, String pictureURL){
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
        this.pictureURL = pictureURL;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer userId) {
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public boolean isActive() {
        return activated;
    }

    public void setActivated(Boolean active) {
        activated = active;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    //  public String getFullName() {
       // return this.firstName + this.lastName;
   // }
}
