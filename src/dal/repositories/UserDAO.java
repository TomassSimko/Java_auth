package dal.repositories;

import be.Role;
import be.User;
import dal.db.DbConnection;
import dal.repositories.interfaces.IUserDAO;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class UserDAO implements IUserDAO {

    private final DbConnection connection;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERS_EMAIL = "email";
    private static final String COLUMN_USERS_PASSWORD = "password";
    private static final String COLUMN_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_USER_LASTNAME = "last_name";
    private static final String COLUMN_USER_IS_ACTIVE = "is_active";
    private static final String COLUMN_USER_PROFILE_PICTURE = "profile_picture";
    private static final String COLUMN_USER_PICTURE_PATH = "picture_url";


    public UserDAO() throws IOException {
        connection = new DbConnection();
    }

   //  TODO : Implement many to many for roles and fix saving only file path not blob

    public List<User> getUsers() throws Exception {
        List<User> userList = new ArrayList<>();

        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_USER;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            HashMap<Integer,Role> roleHashMap = getRoleHashMap();
            List<Role> roles = new ArrayList<>();
            File file = new File("/fff");
            // this is preparation for
            // composition  manipulation plump this one in the User object once roles created inside
            while (rs.next()) {
                var test = rs.getBlob(8);
                userList.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        file,
                        rs.getString(7),
                        roles
                       ));
            }
        }
        return userList;
    }

    // TODO : FIX RETURNING CREATE USER
    public User createUser(String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception {
        User createdUser = null;
        try (Connection con = connection.getConnection()) {
           //  FileInputStream stream = new FileInputStream(pictureURL);

            String sql = "INSERT INTO " + TABLE_USER + "("
                    + COLUMN_USERS_EMAIL + ","
                    + COLUMN_USERS_PASSWORD + ","
                    + COLUMN_USER_FIRSTNAME + ","
                    + COLUMN_USER_LASTNAME + ","
                    + COLUMN_USER_IS_ACTIVE + ","
                    + COLUMN_USER_PROFILE_PICTURE + ","
                    + COLUMN_USER_PICTURE_PATH +
                    ")"
                    + "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setBoolean(5, isActive);
          //  preparedStatement.setBinaryStream(6, stream, pictureURL.length());
            preparedStatement.setString(7, pictureURL.getAbsolutePath());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
         //  createdUser = new User(id, email, passwordHash, firstName, lastName, isActive, photoFile, roles, pictureURL.getAbsolutePath());
        }
        return createdUser;
    }

    public void updateUser(User user, String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception {
        try (Connection con = connection.getConnection()) {
           // FileInputStream stream = new FileInputStream(pictureURL); // get rid of this here
            String sql = "UPDATE " + TABLE_USER + " SET "
                    + COLUMN_USERS_EMAIL + "= ?" + ","
                    + COLUMN_USERS_PASSWORD + "= ?" + ","
                    + COLUMN_USER_FIRSTNAME + "= ?" + ","
                    + COLUMN_USER_LASTNAME + "= ?" + ","
                    + COLUMN_USER_IS_ACTIVE + "= ? " + ","
                    + COLUMN_USER_PROFILE_PICTURE + "= ? " + ","
                    + COLUMN_USER_PICTURE_PATH + "= ?"
                    + "WHERE "
                    + COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setBoolean(5, isActive);
           //  preparedStatement.setBinaryStream(6, stream, pictureURL.length());
            preparedStatement.setString(7, pictureURL.getAbsolutePath());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();

            user.setEmail(email);
            user.setPassword(passwordHash);
            user.setFirstName(firstName);
            user.setFirstName(lastName);
        }
    }

    public void deleteUser(User currentUser) throws Exception {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM " + TABLE_USER + " WHERE " +  COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
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
