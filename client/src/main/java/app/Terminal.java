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
        console.printf("Welcome to the stocks app! For more information type 'manual', to quit type 'quit'\r\n");
        while(true) {
            String input = console.readLine("stocks>").toLowerCase();
            String[] commandList = input.split(" ");

            String[] options = Arrays.copyOfRange(commandList, 1, commandList.length);
            switch(commandList[0]) {
                case "manual":
                    // print manual
                    Manual.printManual(console);
                    break;
                case "stock":
                    //get stock
                    break;
                case "indicator":
                    //get indicator
                    break;
                case "news":
                    //get news
                    break;
                case "info":
                    //get history
                    History.dayQuery(options, this.ds);
                    break;
                case "comment":
                    //get comment
                    break;
                case "sector":
                    //get sector
                    break;
                case "publisher":
                    //get publisher
                    break;
                case "add": // if first string is add, we need to check what the second string is
                    switch(commandList[1]){
                        case "stock":
                            //add stock
                            break;
                        case "indicator":
                            //add indicator
                            break;
                        case "news":
                            //add news
                            break;
                        case "history":
                            //add history
                            break;
                        case "comment":
                            //add comment
                            break;
                        default:
                            //invalid option entered
                            console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                            break;
                    }
                    break;
                case "delete": // if first string is delete, we need to check what the second string is
                    switch(commandList[1]){
                        case "stock":
                            //delete stock
                            break;
                        case "indicator":
                            //delete indicator
                            break;
                        case "history":
                            //delete history
                            break;
                        case "comment":
                            //delete comment
                            break;
                        default:
                            //invalid option entered
                            console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                            break;
                    }
                    break;
                case "quit":
                    //quit program
                    console.printf("Goodbye!\r\n");
                    return;
                default:
                    //invalid option entered
                    console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                    break;
            }
        }
    }

}
