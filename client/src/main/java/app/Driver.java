package app;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Driver {
    public static void main(String[] args) {
        Console console = System.console();
        if(console == null) {
            System.err.println("Unable to initialize console");
            return;
        }
        String username = console.readLine("Enter username: ");
        char[] password = console.readPassword("Enter password: ");

        String connectString = "jdbc:mysql://marmoset04.shoshin.uwaterloo.ca:3306/project_37?useSSL=false&allowPublicKeyRetrieval=true";

        try (Connection con = DriverManager.getConnection(connectString, username, password.toString())) {
            String query = "SELECT VERSION()";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Unable to connect to database");
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        }

    }
}
