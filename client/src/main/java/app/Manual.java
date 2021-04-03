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
                command("stock [-a]") +
                command("add stock") +
                command("delete stock <symbol>");
    }
    private static String indicator() {
        return  header("Indicator:") +
                command("indicator [-s][-y][-sec]") +
                command("add indicator") +
                command("delete indicator [-s][-y]");
    }
    private static String news() {
        return  header("News:") +
                command("news [-p][-s][-d][-sec]") +
                command("add news");
    }
    private static String history() {
        return  header("History:") +
                command("info [-s][-d]") +
                command("add history") +
                command("delete history [-s][-d]");
    }
    private static String comment() {
        return  header("Comment:") +
                command("comment [-a][-s][-d]") +
                command("add comment") +
                command("delete comment [-s][-d]");
    }
    private static String sector() {
        return  header("Sector:") +
                command("sector [-a]");
    }
    private static String publisher() {
        return  header("Publisher:") +
                command("publisher [-a]");
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
                "\n";
        console.printf(str);
    }
}
