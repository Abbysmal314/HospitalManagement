import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

    public static void main(String[] args) {

        try {
            String url = "jdbc:ucanaccess://data/PROJECT_4.accdb";

            Connection conn = DriverManager.getConnection(url);

            System.out.println("Connection Successful!");

            conn.close();

        } catch (Exception e) {

            System.out.println("Connection Failed.");
            e.printStackTrace();
        }
    }
}