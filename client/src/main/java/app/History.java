package app;

import app.tableOptions.HistoryOptions;
import com.beust.jcommander.JCommander;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class History {

    public static void dayQuery(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("Select * from History where symbol = ? ");
        HistoryOptions ho = new HistoryOptions();
        JCommander.newBuilder()
                .addObject(ho)
                .build()
                .parse(options);

        try {
            Connection conn = ds.getConnection();
            Date date = null;
            if(ho.date != null) {
                date = Date.valueOf(ho.date);
                sb.append("and date = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, ho.symbol);

            if(date != null)
                preparedStmt.setDate(2, date);

            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        }catch (Exception e) {
            System.err.println("Could not generate resultSet");
        }
    }
}
