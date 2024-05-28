import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String password){
        Connection connection = null;

        try{
            // Change the driver to MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Change the URL format to MySQL
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, user, password);

            if (connection != null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }
        } catch (Exception e){

            System.out.println(e.getMessage());
        }

        return connection;
    }
}
