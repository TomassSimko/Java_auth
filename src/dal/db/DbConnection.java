package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private static final String PROP_FILE = "resources/DbConfig.properties";

    private String url, dbUser, dbPassword,driverUrl;

    public DbConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PROP_FILE));
            dbUser = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
            url = properties.getProperty("db.url");
            driverUrl = properties.getProperty("db.driver.url");
        } catch (IOException se) {
            System.out.printf("Cannot find the property file %.2f\n", se);
        }
    }


    public Connection getConnection() {
//        String dbName = "jdbc_base";
//        String dbUser = "root";
//        String dbPassword = "a4tTx16fPjbw98Dsc";
//        String url = "jdbc:mysql://localhost/" + dbName;
        try {
            Class.forName(driverUrl);
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbLink;
    }

//    public DbConnection()   {
//        try {
//             Connection connection = null;
//            Properties properties = new Properties();
//            properties.load(new FileInputStream(PROP_FILE));
//
//          String dbName = properties.getProperty("db.name");
//          String dbUser = properties.getProperty("db.username");
//          String dbPassword = properties.getProperty("db.password");
//          String url = properties.getProperty("db.url") + dbName;
//          Class.forName(properties.getProperty("db.driver.url"));
//
//            connection = DriverManager.getConnection(url,dbUser,dbPassword);
//
////            dataSource = new SQLServerDataSource();
////
////            dataSource.setURL("jdbc:mysql://localhost:3306/jdbc_base");
////            dataSource.setUser("root");
////            dataSource.setPassword("a4tTx16fPjbw98Dsc");
//          //  dataSource.setServerName("localhost");
//          //  dataSource.setServerName("localhost");
//            // dataSource.setDatabaseName("jdbc_base");
//           // dataSource.setPortNumber(3306);
//
//       //     Connection test = dataSource.getConnection();
//
//        } catch (IOException|  ClassNotFoundException | SQLException se ) {
//            System.out.printf("Cannot find the property file %.2f\n", se);
//        }
//    }
//
//    /**
//     * Gets the connection and returns it.
//     *
//     * @return
//     * returns connection
//     */
//    public Connection getConnection() {
//        try {
//
//            return connection.getConnection();
//        } catch (SQLServerException ex) {
//            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

}
