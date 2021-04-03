package app;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;

public class Terminal {
    private BasicDataSource ds = null;
    private Console console = null;

    Terminal(BasicDataSource ds, Console console) {
        this.ds = ds;
        this.console = console;
    }

    public void start() {
        console.printf("Welcome to the stocks app! For more information type 'man', to quit type 'q'");
        while(true) {
            String input = console.readLine("stocks>").toLowerCase();
            String[] commandList = input.split(" ");

            try {
                String[] options = Arrays.copyOfRange(commandList, 1, commandList.length);
                switch(commandList[0]) {
                    case "info":
                        History.dayQuery(options, this.ds);
                        break;
                    case "quit":
                        ds.close();
                        break;
                }
            } catch(SQLException e) {
                System.err.println("Couldn't close connection successfully");
                System.err.println("SQLException: " + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
                System.err.println("VendorError: " + e.getErrorCode());
            }

        }
    }

}
