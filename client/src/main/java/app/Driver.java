package app;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class Driver {
    public static void main(String[] args) {
         try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.err.println("Unable to load driver, exiting...");
            return;
        }

        Console console = System.console();
        if(console == null) {
            System.err.println("Unable to initialize console");
            return;
        }
        String username = console.readLine("Enter username: ");
        char[] password = console.readPassword("Enter password: ");

        String connectString = "jdbc:mysql://marmoset04.shoshin.uwaterloo.ca:3306/project_37?useSSL=false&allowPublicKeyRetrieval=true";

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(connectString);
        ds.setUsername(username);
        ds.setPassword(String.valueOf(password));
        ds.setMinIdle(2);

        Terminal t = new Terminal(ds, console);
        t.start();
//        try (Connection con = DriverManager.getConnection(connectString, username, String.valueOf(password))) {
//            String query = "SELECT VERSION()";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            if (rs.next()) {
//                System.out.println(rs.getString(1));
//            }
//            rs.close();
//        } catch (SQLException e) {
//            System.err.println("Unable to connect to database");
//            System.err.println("SQLException: " + e.getMessage());
//            System.err.println("SQLState: " + e.getSQLState());
//            System.err.println("VendorError: " + e.getErrorCode());
//        }

    }
}
