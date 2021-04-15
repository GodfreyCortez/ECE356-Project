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
                command("delete stock -s <symbol>");
    }
    private static String indicator() {
        return  header("Indicator:") +
                command("indicator [-s <stock>][-y <year>][-sec <sector>][-buy][-notBuy]") +
                command("add indicator") +
                command("delete indicator [-s <stock>][-y <year>]");
    }
    private static String news() {
        return  header("News:") +
                command("news [-p][-s][-d][-sec]") +
                command("add news");
    }
    private static String history() {
        return  header("History:") +
                command("info <-s <stock>> [-d <date>, -r <start-date> <end-date>][-52]") +
                option("-52 allows you to retrieve the 52 week high and low of the most recent date, specify an end date to get the 52 week range for that") +
                command("add history") +
                command("delete history <-s <stock>>[-d]");
    }
    private static String comment() {
        return  header("Comment:") +
                command("comment [-a][-s][-d]") +
                command("add comment") +
                command("delete comment [-s][-d]");
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
