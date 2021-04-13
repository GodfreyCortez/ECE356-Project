package app;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.util.Arrays;

public class Terminal {
    private BasicDataSource ds;
    private Console console;

    Terminal(BasicDataSource ds, Console console) {
        this.ds = ds;
        this.console = console;
    }

    public void start() {
        console.printf("Welcome to the stocks app! For more information type 'manual', to quit type 'quit'\r\n");
        while(true) {
            String input = console.readLine("stocks>");
            String[] commandList = input.split(" ");

            String[] options = commandList.length < 2 ? new String[0] : Arrays.copyOfRange(commandList, 1, commandList.length);
            switch(commandList[0]) {
                case "manual":
                    // print manual
                    Manual.printManual(console);
                    break;
                case "stock":
                    //get stock
                    Stock.query(options, this.ds);
                    break;
                case "indicator":
                    //get indicator
                    Indicator.getIndicators(options, this.ds);
                    break;
                case "news":
                    //get news
                    News.getNews(options, this.ds);
                    break;
                case "info":
                    //get history
                    History.dayQuery(options, this.ds);
                    break;
                case "comment":
                    //get comment
                    Comment.getComments(options, this.ds);
                    break;
                case "sector":
                    //get sector
                    Sector.getSectors(this.ds);
                    break;
                case "publisher":
                    //get publisher
                    Publisher.getPublishers(this.ds);
                    break;
                case "sql":
                    //execute sql query
                    if(options.length < 1) {
                        console.printf("Invalid sql was inputted\r\n");
                        break;
                    }
                    Common.query(this.ds,String.join(" ", options));
                    break;
                case "add": // if first string is add, we need to check what the second string is
                    if(commandList.length < 2) {
                        console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                        break;
                    }
                    switch(commandList[1]){
                        case "stock":
                            //add stock
                            Stock.addStock(this.ds, console);
                            break;
                        case "indicator":
                            //add indicator
                            Indicator.addIndicator(this.ds, console);
                            break;
                        case "news":
                            //add news
                            News.addNews(this.ds, console);
                            break;
                        case "history":
                            History.addHistory(this.ds, console);
                            break;
                        case "comment":
                            //add comment
                            Comment.addComment(this.ds, console);
                            break;
                        default:
                            //invalid option entered
                            console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                            break;
                    }
                    break;
                case "delete": // if first string is delete, we need to check what the second string is
                    if(commandList.length < 3) {
                        console.printf("Invalid option entered. Please enter valid option from manual!\r\n");
                        break;
                    }
                    switch(commandList[1]){
                        case "stock":
                            //delete stock
                            Stock.delete(Arrays.copyOfRange(commandList, 2, commandList.length), this.ds);
                            break;
                        case "indicator":
                            //delete indicator
                            Indicator.deleteIndicator(Arrays.copyOfRange(commandList, 2, commandList.length), this.ds);
                            break;
                        case "history":
                            History.deleteHistory(Arrays.copyOfRange(commandList, 2, commandList.length), this.ds);
                            break;
                        case "comment":
                            //delete comment
                            Comment.deleteComment(Arrays.copyOfRange(commandList, 2, commandList.length), this.ds);
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
