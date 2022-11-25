package gui.models;

import be.Role;
import be.User;
import bll.IRoleManager;
import bll.IUserManager;
import bll.RoleManager;
import bll.UserManager;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RoleModel {
    private final IRoleManager roleManager;
    private ObservableList<Role> rolesList;

    public RoleModel() throws UserManagerException, UserDAOException {
        this.roleManager = new RoleManager();
        getRolesList();
    }

    public List<Role> getRolesList() throws UserDAOException {
        List<Role> fetchedRoles = roleManager.getRoles();
        return rolesList = FXCollections.observableArrayList(fetchedRoles);
    }

}
