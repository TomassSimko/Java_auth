package auth.base.db;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    private Connection dbLink;

    public Connection getConnection(){
        String dbName = "jdbc_base";
        String dbUser = "root";
        String dbPassword = "a4tTx16fPjbw98Dsc";
        String url = "jdbc:mysql://localhost/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url,dbUser,dbPassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dbLink;
    }

}
