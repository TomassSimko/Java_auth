package gui.models;

import be.Role;
import be.User;
import bll.IUserManager;
import bll.UserManager;
import bll.exceptions.UserManagerException;
import javafx.collections.ObservableList;

import java.util.List;

public class RoleModel {
    private final IUserManager userManager;

    public RoleModel() throws UserManagerException {
        this.userManager = new UserManager();
    }

    public List<Role> getRolesList() {
      //  userManager.getAllRoles;
        return List.of();
    }

}
