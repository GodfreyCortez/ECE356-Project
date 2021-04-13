package app;

import app.tableOptions.HistoryOptions;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class History {
    public static void addHistory(BasicDataSource ds, Console console) {
        String[] columns = {"date", "symbol", "open", "high", "low", "close", "adjClose", "volume" };
        String query = Printer.generateInsert(columns, "History");
        List<String> inputs = Printer.retrieveInputs(columns, console);
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);


            preparedStmt.setDate(1, Date.valueOf(inputs.get(0)));
            preparedStmt.setString(2, inputs.get(1));
            for(int i = 2; i < 8; i++) {
                preparedStmt.setDouble(i+1, Double.parseDouble(inputs.get(i)));
            }
            preparedStmt.setInt(8, Integer.parseInt(inputs.get(7)));

            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError();
            Printer.printSQLException(e);
        }
    }

    public static void deleteHistory(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("delete from History where symbol = ?");
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

            if(ho.symbol == null) {
                System.err.println("Symbol to describe is missing, please add it with option -s");
                conn.close();
                return;
            }

            if (ho.dateRange != null) {
                startDate = Date.valueOf(ho.dateRange.get(0));
                endDate = Date.valueOf(ho.dateRange.get(1));
                sb.append("and date >= ? and date <= ?");
            } else if (ho.date != null) {
                date = Date.valueOf(ho.date);
                sb.append("and date = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, ho.symbol);

            if (ho.dateRange != null) {
                preparedStmt.setDate(2, startDate);
                preparedStmt.setDate(3, endDate);
            } else if (date != null)
                preparedStmt.setDate(2, date);

            preparedStmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            Printer.printDeleteError();
            Printer.printSQLException(e);
        }
    }

    public static void dayQuery(String stockSymbol, BasicDataSource ds) {
        String query = "select * from History where symbol = ? order by date desc limit 1;";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, stockSymbol);
            ResultSet rs = ps.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch(SQLException e) {
            Printer.printQueryError(e);
        }
    }

    public static void dayQuery(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from History where symbol = ? ");
        HistoryOptions ho = new HistoryOptions();
        try {
            JCommander.newBuilder()
                    .addObject(ho)
                    .build()
                    .parse(options);
        } catch (ParameterException e) {
            e.usage();
            return;
        }

        if(ho.symbol == null) {
            System.out.println("Please input a symbol with the -s option");
            return;
        }

        if(!ho.all && ho.dateRange == null && ho.date == null) {
            dayQuery(ho.symbol, ds);
            return;
        }

        try {
            Connection conn = ds.getConnection();
            Date date = null;
            Date startDate = null;
            Date endDate = null;

            if(ho.symbol == null) {
                System.err.println("Symbol to describe is missing, please add it with option -s");
                conn.close();
                return;
            }

            if (ho.dateRange != null) {
                startDate = Date.valueOf(ho.dateRange.get(0));
                endDate = Date.valueOf(ho.dateRange.get(1));
                sb.append("and date >= ? and date <= ?");
            } else if (ho.date != null) {
                date = Date.valueOf(ho.date);
                sb.append("and date = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, ho.symbol);

            if (ho.dateRange != null) {
                preparedStmt.setDate(2, startDate);
                preparedStmt.setDate(3, endDate);
            } else if (date != null)
                preparedStmt.setDate(2, date);

            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError();
            Printer.printSQLException(e);
        }
    }
}
