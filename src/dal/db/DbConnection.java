package dal.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbConnection {
    private static Connection conn;
    private static final String PROP_FILE = "resources/DbConfig.properties";

    public static Connection getConnection() {
        try {
            Properties props = loadProperties();
            Class.forName(props.getProperty("db.driver.url"));
            conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream(PROP_FILE)) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}



