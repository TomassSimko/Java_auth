package dal.db;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    private SQLServerDataSource dataSource;
    private static Connection dbLink;
    private static final String PROP_FILE = "data/DbConfig.properties";


    public DbConnection() {
        try {
            // private static Connection connection = null;
            Properties properties = new Properties();
            properties.load(new FileInputStream(PROP_FILE));

//          String dbName = properties.getProperty("db.name");
//          String dbUser = properties.getProperty("db.username");
//          String dbPassword = properties.getProperty("db.password");
//          String url = properties.getProperty("db.url") + dbName;
//          Class.forName(properties.getProperty("db.driver.url"));
//
//            connection = DriverManager.getConnection(url,dbUser,dbPassword);
            dataSource = new SQLServerDataSource();

            dataSource.setURL(properties.getProperty("db.url"));
            dataSource.setUser(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));

          //  dataSource.setServerName("localhost");
           // dataSource.setDatabaseName("jdbc_base");
          //  dataSource.setPortNumber(3306);

        } catch (IOException se) {
            System.out.printf("Cannot find the property file %.2f\n", se);
        }
    }

    /**
     * Gets the connection and returns it.
     *
     * @return
     * returns connection
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLServerException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
