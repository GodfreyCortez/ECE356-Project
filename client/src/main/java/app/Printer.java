package app;

import de.vandermeer.asciitable.AsciiTable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Printer {
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
                    cols.add(rs.getString(i));
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
}
