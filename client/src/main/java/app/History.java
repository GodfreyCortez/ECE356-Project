package app;

import app.tableOptions.HistoryOptions;
import com.beust.jcommander.JCommander;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

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
            Date startDate = null;
            Date endDate = null;

            if(ho.dateRange != null) {
                startDate = Date.valueOf(ho.dateRange.get(0));
                endDate = Date.valueOf(ho.dateRange.get(1));
                sb.append("and date >= ? and date <= ?");
            } else if(ho.date != null) {
                date = Date.valueOf(ho.date);
                sb.append("and date = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, ho.symbol);

            if(ho.dateRange != null) {
                preparedStmt.setDate(2, startDate);
                preparedStmt.setDate(3, endDate);
            } else if(date != null)
                preparedStmt.setDate(2, date);

            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        }catch (Exception e) {
            System.err.println("Could not generate resultSet");
        }
    }
}
