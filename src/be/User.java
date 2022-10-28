package be;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean activated;
    private File photoFile;
    private String pictureURL;
    private List<Role> roles;

    public User(Integer id, String email, String password, String firstName, String lastName, Boolean activated, File photoFile, String pictureURL,List<Role> roles){
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
        this.photoFile = photoFile;
        this.pictureURL = pictureURL;
        this.roles = roles;
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

    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    //  public String getFullName() {
       // return this.firstName + this.lastName;
   // }
}
