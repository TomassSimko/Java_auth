package dal.db;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class DbConnection {
    private static Connection dbLink;
    private static final String PROP_FILE = "resources/DbConfig.properties";
    private String url, dbUser, dbPassword, driverUrl;

    public DbConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PROP_FILE));
            dbUser = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
            url = properties.getProperty("db.url");
            driverUrl = properties.getProperty("db.driver.url");
        } catch (IOException ex) {
            System.out.println("Cannot read the property file " + ex);
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(driverUrl);
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbLink;
    }
}
