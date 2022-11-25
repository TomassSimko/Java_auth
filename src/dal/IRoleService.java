package dal;

import be.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles() throws Exception;

    Role getRoleByName(String role) throws Exception;
}

