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
        var roleMap = getRoleHashMap();
        try(Connection conn = DbConnection.getConnection()){
            String sql = "SELECT DISTINCT *\n" +
                    " FROM user u \n" +
                    " INNER JOIN user_role ur ON u.id = ur.user_id\n" +
                    " INNER JOIN role r ON ur.role_id = r.id;";
            preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                    //userList.add(instantiateUser(rs,roleMap));
                userList.add(new User( rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                       rs.getBoolean("is_active"),
                       // roleMap.get(rs.getInt("role_id"))
                        ));
            }
        }

        return userList.stream().distinct().collect(Collectors.toList());
    }

    private User instantiateUser(ResultSet rs,HashMap<Integer,Role> role) throws SQLException{
        User obj = new User();

        var id = rs.getInt("id");
        var email = rs.getString("email");
        var password = rs.getString("password");
        var userName = rs.getString("username");
        var isActive = rs.getBoolean("is_active");



        role.get(rs.getInt("role_id"));


       // HashMap<Integer,Role> roleList = new HashMap();
//        obj.setId(rs.getInt(1));
//        obj.setEmail(rs.getString(2));
//        obj.setPassword(rs.getString(3));
//        obj.setUserName(rs.getString(4));
//        obj.setIsActive(rs.getBoolean(5));

      //  obj.getRoles().putAll(role);
      //  List<Role> list = new ArrayList<Role>(roleList.values());
        //obj.getRoles().addAll(list);
       // ratings.add(ratingObj);

        // roles
       // roles.get(obj.setId(rs.getInt(6)));
        //obj.setId(rs.getInt(6));

        return obj;
    }

    //            st = conn.prepareStatement("SELECT DISTINCT " +
//                    "                    FROM user u " +
//                    "                    INNER JOIN user_role ur ON u.id = ur.user_ids " +
//                    "                    INNER JOIN role r ON ur.role_id = r.id");
//            String sql = """
//                    SELECT DISTINCT *
//                    FROM user u
//                    INNER JOIN user_role ur ON u.id = ur.user_id\s
//                    INNER JOIN role r ON ur.role_id = r.id""";


    // Statement stmt = conn.createStatement();
    // ResultSet rs = stmt.executeQuery(sql);

    //  HashMap<Integer,Role> roleHashMap = getRoleHashMap();
    // List<Role> list = new ArrayList<>(roleHashMap.values());


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
