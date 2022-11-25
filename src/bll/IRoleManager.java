package bll;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;

import java.util.List;

public interface IRoleManager {
    List<Role> getRoles() throws UserDAOException;
}
