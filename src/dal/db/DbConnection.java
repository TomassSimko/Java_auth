package dal.db;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    private SQLServerDataSource dataSource;
    private static Connection dbLink;
    private static final String PROP_FILE = "data/DbConfig.properties";

    public DbConnection() {
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(PROP_FILE));

//            String dbName = databaseProperties.getProperty("db.name");
//            String dbUser = databaseProperties.getProperty("db.username");
//            String dbPassword = databaseProperties.getProperty("db.password");
//            String url = databaseProperties.getProperty("db.url") + dbName;
//            Class.forName(databaseProperties.getProperty("db.driver.url"));

            dataSource = new SQLServerDataSource();
            dataSource.setServerName(databaseProperties.getProperty("db.server"));
            dataSource.setDatabaseName(databaseProperties.getProperty("db.name"));
            dataSource.setUser(databaseProperties.getProperty("db.username"));
            dataSource.setPassword(databaseProperties.getProperty("db.password"));
            dataSource.setPortNumber(Integer.parseInt(databaseProperties.getProperty("db.port")));
        } catch (IOException e) {
            System.out.println("Cannot find the property file");
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
