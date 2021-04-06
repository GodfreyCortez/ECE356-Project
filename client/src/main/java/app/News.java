package app;

import app.tableOptions.HistoryOptions;
import com.beust.jcommander.JCommander;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class News {
    public static void addNews(BasicDataSource ds, Console console) {
        String[] columns = {"date", "headline", "symbol", "publisher", "url"};
        String query = Printer.generateInsert(columns, "News");

        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            List<String> inputs = Printer.retrieveInputs(columns, console);

            preparedStmt.setDate(1, Date.valueOf(inputs.get(0)));
            for(int i = 1; i < 6; i++) {  
                preparedStmt.setString(i+1, inputs.get(i));
            }

            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError();
            Printer.printSQLException(e);
        }
    }

    public static void getNews(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from News where symbol = ? ");
        NewsOptions no = new NewsOptions();
        JCommander.newBuilder()
                .addObject(no)
                .build()
                .parse(options);

        try {
            Connection conn = ds.getConnection();
            Date date = null;
            Date startDate = null;
            Date endDate = null;
            String publisher = null;

            if(no.symbol == null) {
                System.err.println("Symbol to describe is missing, please add it with option -s");
                conn.close();
                return;
            }

            if (no.publisher != null) {
                publisher = no.publisher;
                sb.append("and publisher = ?");
            }

            if (no.dateRange != null) {
                startDate = Date.valueOf(no.dateRange.get(0));
                endDate = Date.valueOf(no.dateRange.get(1));
                sb.append("and date >= ? and date <= ?");
            } else if (no.date != null) {
                date = Date.valueOf(no.date);
                sb.append("and date = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, no.symbol);

            if (no.publisher != null)&&(no.dateRange != null) {
                preparedStmt.setString(2, publisher);
                preparedStmt.setDate(3, startDate);
                preparedStmt.setDate(4, endDate);
            } else if (no.publisher != null)&&(no.date != null) {
                preparedStmt.setString(2, publisher);
                preparedStmt.setDate(3, date);
            } else if (no.publisher == null)&&(no.dateRange != null) {
                preparedStmt.setDate(2, startDate);
                preparedStmt.setDate(3, endDate);
            } else if (no.publisher == null)&&(no.date != null) {
                preparedStmt.setDate(2, date);
            }

            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError();
            Printer.printSQLException(e);
        }
    }
}