package dal.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbConnection {

    private static Connection conn = null;
    private static final String PROP_FILE = "resources/DbConfig.properties";

    public static Connection getConnection() {
        if(conn == null){
            try {
                Properties props = loadProperties();
                Class.forName(props.getProperty("db.driver.url"));
                conn = DriverManager.getConnection(props.getProperty("db.url"),
                                                   props.getProperty("db.username"),
                                                   props.getProperty("db.password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream(PROP_FILE)) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //            Properties properties = new Properties();
//            properties.load(new FileInputStream(PROP_FILE));
//            dbUser = properties.getProperty("db.username");
//            dbPassword = properties.getProperty("db.password");
//            url = properties.getProperty("db.url");
//            driverUrl = properties.getProperty("db.driver.url");
//}


    //    public DbConnection() {
//    }
//    public static Connection getConnection() {
//        if (conn == null) {
//            try {
//                Properties props = loadProperties();
//                String url = props.getProperty("db.url");
//                conn = DriverManager.getConnection(url, props.getProperty("db.username"),props.getProperty("db.password"));
//            }
//            catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return conn;
//    }

}

//}

