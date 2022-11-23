package dal.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Connection dbLink;

    private static Connection conn = null;
    private static final String PROP_FILE = "resources/DbConfig.properties";
    public DbConnection() {
    }
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("db.url");
                conn = DriverManager.getConnection(url, props.getProperty("db.username"),props.getProperty("db.password"));
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
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
}

//    public static Connection getConnection() {
//        try {
//            Class.forName(driverUrl);
//            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dbLink;
//    }
//}

