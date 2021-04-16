package app;

import java.io.Console;

public class Manual {
    private static String header(String str) {
        return "\t" + str + "\r\n";
    }
    private static String command(String str) {
        return "\t\t" + str + "\r\n";
    }
    private static String option(String str) {
        return "\t\t\t" + str + "\r\n";
    }
    private static String stock() {
        return  header("Stock:") +
                command("stock [-sec <sector>]") +
                command("add stock") +
                command("delete stock -s <stock>");
    }
    private static String indicator() {
        return  header("Indicator:") +
                command("indicator [-s <stock>][-y <year>][-sec <sector>][-buy][-notBuy]") +
                option("-buy will retrieve a list of stocks that you should buy at the beginning of a given year and sell at the end of the year, specify a year") +
                option("-notBuy will retrieve a list of stocks that will decrease in value and you should not buy in a given year, specify a year") +
                command("add indicator") +
                command("delete indicator -s <stock> [-y <year>]");
    }
    private static String news() {
        return  header("News:") +
                command("news [-p][-s][-d <date>, -r <start-date> <end-date>][-sec]") +
                command("add news");
    }
    private static String history() {
        return  header("History:") +
                command("info <-s <stock>> [-d <date>, -r <start-date> <end-date>][-52][-g][-exp -sec <sector> -y <year>][-cheap -sec <sector> -y <year>]") +
                option("-g option will give general information about a stock (avg volume in past year, 52 week high and low, latest price info available)") +
                option("-52 allows you to retrieve the 52 week high and low of the most recent date, specify an end date to get the 52 week range for that") +
                option("-exp will retrieve the top 10 most expensive stocks by sector in a given year, specify a year and sector") +
                option("-cheap will to retrieve the top 10 cheapest stocks by sector in a given year, specify a year and sector") +
                command("add history") +
                command("delete history <-s <stock>>[-d]");
    }
    private static String comment() {
        return  header("Comment:") +
                command("comment [-s <stock>][-d <date>, -r <start-date> <end-date>]") +
                command("add comment") +
                command("delete comment -s <stock> [-d <date>, -r <start-date> <end-date>]");
    }
    private static String sector() {
        return  header("Sector:") +
                command("sector");
    }
    private static String publisher() {
        return  header("Publisher:") +
                command("publisher");
    }
    private static String sql() {
        return header("SQL:") +
               command("sql <sql query>");
    }
    public static void printManual(Console console) {
        String str = "Program Commands:\r\n" +
                stock() +
                "\n" +
                sector() +
                "\n" +
                indicator() +
                "\n" +
                publisher() +
                "\n" +
                news() +
                "\n" +
                history() +
                "\n" +
                comment() +
                "\n" +
                sql() +
                "\n";
        console.printf(str);
    }
}
