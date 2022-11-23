package be;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String userName;
    private Boolean activated;
    private List<Role> roles;

    public User(Integer id, String email, String password, String userName, Boolean activated,List<Role> roles){
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.activated = activated;
        this.roles = roles;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer userId) {
        this.id = userId;
    }
    public String getUserName() {
        return userName ;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
    public List<Role> getRoles() {
        return roles;
    }
}
