package app;

import app.tableOptions.IndicatorOptions;
import com.beust.jcommander.JCommander;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
import java.util.List;

public class Indicator {
    public static void getIndicators(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from Indicator");
        IndicatorOptions io = new IndicatorOptions();
        JCommander.newBuilder()
                .addObject(io)
                .build()
                .parse(options);

        try {
            Connection conn = ds.getConnection();

            if(io.symbol != null || io.year != null || io.sector != null)
                sb.append(" where ");

            if(io.symbol != null)
                sb.append(" symbol = ? ");
            if(io.year != null)
                if(io.symbol != null)
                    sb.append(" and ");
                sb.append(" year = ? ");
            if(io.sector != null)
                if(io.symbol != null || io.year != null)
                    sb.append(" and ");
                sb.append(" where sector like ?");

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            // keeps track of the parameterIndex since it will vary depending on which filters are used by the user
            int parameterCount = 0;
            if(io.symbol != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, io.symbol);
            }
            if (io.year != null) {
                parameterCount++;
                preparedStmt.setInt(parameterCount, io.year);
            }
            if(io.sector != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, io.sector);
            }

            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError();
            Printer.printSQLException(e);
        }
    }

    public static void addIndicator(BasicDataSource ds, Console console) {
        String[] columns = { "year", "symbol", "eps", "revenue", "costOfRevenue", "grossProfit", "peRatio", "debtRatio", "dividendYield", "dividendPayoutRatio", "pbValueRatio", "pegRatio", "epsGrowth", "sector", "priceVar" };
        String query = Printer.generateInsert(columns, "Indicator");

        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            List<String> inputs = Printer.retrieveInputs(columns, console);

            preparedStmt.setInt(1, Integer.parseInt(inputs.get(0)));
            preparedStmt.setString(2, inputs.get(1));
            for(int i = 2; i < 12; i++) {
                preparedStmt.setDouble(i+1, Double.parseDouble(inputs.get(i)));
            }
            preparedStmt.setString(14, inputs.get(13));
            preparedStmt.setDouble(15, Double.parseDouble(inputs.get(14)));
            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError();
            Printer.printSQLException(e);
        }
    }

    public static void deleteIndicator(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("delete from Indicator");
        IndicatorOptions io = new IndicatorOptions();
        JCommander.newBuilder()
                .addObject(io)
                .build()
                .parse(options);

        try {
            Connection conn = ds.getConnection();

            if(io.symbol != null)
                sb.append(" where symbol = ?");
            if(io.year != null)
                sb.append(" where year = ?");

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            // keeps track of the parameterIndex since it will vary depending on which filters are used by the user
            int parameterCount = 0;
            if(io.symbol != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, io.symbol);
            }
            if (io.year != null) {
                parameterCount++;
                preparedStmt.setInt(parameterCount, io.year);
            }

            preparedStmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            Printer.printDeleteError();
            Printer.printSQLException(e);
        }
    }
}
