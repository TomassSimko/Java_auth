package bll;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {
    private static Connection dbLink;
    private static final String PROP_FILE = "data/DbConfig.properties";

    public static Connection getConnection(){
        String dbName,dbUser,dbPassword,url;
        try{
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(PROP_FILE));

            dbName = databaseProperties.getProperty("db.name");
            dbUser = databaseProperties.getProperty("db.username");
            dbPassword = databaseProperties.getProperty("db.password");
            url = databaseProperties.getProperty("db.url") + dbName;
            Class.forName(databaseProperties.getProperty("db.driver.url"));
            dbLink = DriverManager.getConnection(url,dbUser,dbPassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dbLink;
    }

}
