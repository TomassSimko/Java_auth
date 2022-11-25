package dal.repositories;

import be.Role;
import be.User;
import be.UserInRole;
import dal.db.DbConnection;
import dal.repositories.interfaces.IUserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO implements IUserDAO {

    private PreparedStatement preparedStatement;
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERS_EMAIL = "email";
    private static final String COLUMN_USERS_PASSWORD = "password";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_IS_ACTIVE = "is_active";

    public UserDAO()  {
    }

    public List<User> getUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        HashMap<Integer,Role> roleMap = getRoleHashMap();

        try(Connection conn = DbConnection.getConnection()){
            String sql = "SELECT * FROM user";
            String sqlJoin = "SELECT * FROM user_role";

            preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement2 = conn.prepareStatement(sqlJoin,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = preparedStatement.executeQuery(sql);
            ResultSet rsJoin = preparedStatement2.executeQuery(sqlJoin);
            while (rs.next()) {
                    userList.add(instantiateUser(rs,rsJoin,roleMap));
            }
        }

        return userList;
    }

    private User instantiateUser(ResultSet rs,ResultSet rsJoin,HashMap<Integer,Role> role) throws SQLException{
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String psw = rs.getString("password");
        String userName = rs.getString("username");
        boolean isActive = rs.getBoolean("is_active");

        List<Role> roleList = new ArrayList<>();

        while(rsJoin.next()){
            int userId = rsJoin.getInt("user_id");
            int roleId = rsJoin.getInt("role_id");
            if(id == userId){
                roleList.add(role.get(roleId));
            }

        }
        rsJoin.beforeFirst();

        return new User(id,email,psw,userName,isActive,roleList);
    }

    // TODO : FIX RETURNING CREATE USER
    public User createUser(String email, String passwordHash, String username, boolean isActive) throws Exception {
        User createdUser = null;
        try (Connection cnn = DbConnection.getConnection()) {
            String sql = "INSERT INTO " + TABLE_USER + "("
                    + COLUMN_USERS_EMAIL + ","
                    + COLUMN_USERS_PASSWORD + ","
                    + COLUMN_USER_USERNAME + ","
                    + COLUMN_USER_IS_ACTIVE +
                    ")"
                    + "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, username);
            preparedStatement.setBoolean(5, isActive);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
         //  createdUser = new User(id, email, passwordHash, firstName, lastName, isActive, photoFile, roles, pictureURL.getAbsolutePath());
       }
        return createdUser;
    }

    public void updateUser(User user, String email, String passwordHash, String username, boolean isActive) throws Exception {
      try (Connection cnn = DbConnection.getConnection()) {
            String sql = "UPDATE " + TABLE_USER + " SET "
                    + COLUMN_USERS_EMAIL + "= ?" + ","
                    + COLUMN_USERS_PASSWORD + "= ?" + ","
                    + COLUMN_USER_USERNAME + "= ?" + ","
                    + COLUMN_USER_IS_ACTIVE + "= ? " + ","
                    + "WHERE "
                    + COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, username);
            preparedStatement.setBoolean(5, isActive);
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();

            user.setEmail(email);
            user.setPassword(passwordHash);
            user.setUserName(username);
        }
    }

    public void deleteUser(User currentUser) throws Exception {
        try (Connection cnn = DbConnection.getConnection()) {
            String sql = "DELETE FROM " + TABLE_USER + " WHERE " +  COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setInt(1, currentUser.getId());
            preparedStatement.execute();
        }
    }

    private HashMap<Integer, Role> getRoleHashMap() throws Exception {
        RoleDAO roleDAO = new RoleDAO();
        List<Role> fetchedRoles = roleDAO.getAllRoles();
        HashMap<Integer,Role> roleHashMap = new HashMap<>();
        for (Role role: fetchedRoles) {
            if(!roleHashMap.containsKey(role.getId())) {
               roleHashMap.put(role.getId(),role);
           }
        }
        return roleHashMap;
    }
}
