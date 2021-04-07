package app;

import de.vandermeer.asciitable.AsciiTable;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Printer {
    public static String generateInsert(String[] columns, String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(tableName).append(" (");
        for(String col : columns) {
            sb.append(col);
            if(!col.equals(columns[columns.length - 1]))
                sb.append(",");
        }
        sb.append(") values (");
        for(String col : columns) {
            sb.append("?");
            if(!col.equals(columns[columns.length - 1]))
                sb.append(",");
        }
        sb.append(");");
        return sb.toString();
    }

    public static List<String> retrieveInputs(String[] columns, Console console) {
        console.printf("Please input the information\r\n");
        List<String> inputs = new ArrayList<>();

        for(String col : columns) {
            inputs.add(console.readLine(col + ": "));
        }
        return inputs;
    }

    public static void printQuery(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsCount = rsmd.getColumnCount();
            AsciiTable at = new AsciiTable();
            at.addRule();
            List<String> cols = new ArrayList<>();
            for(int i = 1; i <= columnsCount; i++) {
                cols.add(rsmd.getColumnName(i));
            }
            at.addRow(cols);
            at.addRule();
            cols.clear();
            while(rs.next()) {
                for(int i = 1; i <= columnsCount; i++) {
                    String res = rs.getString(i);
                    cols.add( res == null ? "N/A" : res);
                }
                at.addRow(cols);
                at.addRule();
                cols.clear();
            }

            at.getContext().setWidth(100);

            System.out.println(at.render());
        } catch (SQLException e) {
            System.err.println("Unable to print results!");
        }
    }

    public static void printInsertError() {
        System.err.println("Unable to insert");
    }

    public static void printQueryError() {
        System.err.println("Could not query the data");
    }

    public static void printDeleteError() {
        System.err.println("Could not delete the data");
    }

    public static void printInsertError(SQLException e) {
        System.err.println("Unable to insert");
        printSQLException(e);
    }

    public static void printQueryError(SQLException e) {
        System.err.println("Could not query the data");
        printSQLException(e);
    }

    public static void printDeleteError(SQLException e) {
        System.err.println("Could not delete the data");
        printSQLException(e);
    }

    public static void printSQLException(SQLException e) {
        System.err.println("SQLException: " + e.getMessage());
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("VendorError: " + e.getErrorCode());
    }
}
