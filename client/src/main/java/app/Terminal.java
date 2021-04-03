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
        console.printf("Welcome to the stocks app! For more information type 'man', to quit type 'q'\r\n");
        while(true) {
            String input = console.readLine("stocks>").toLowerCase();
            String[] commandList = input.split(" ");

            String[] options = Arrays.copyOfRange(commandList, 1, commandList.length);
            switch(commandList[0]) {
                case "info":
                    History.dayQuery(options, this.ds);
                    break;
                case "quit":
                    console.printf("Goodbye!\r\n");
                    return;
            }
        }
    }

}
