package app;

import app.tableOptions.IndicatorOptions;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
import java.util.List;

public class Indicator {
    public static void getStocksToBuy(BasicDataSource ds, int year) {
        String query = "select * from Indicator where priceVar > 0 and year = ?;";
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, year);
            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError();
            Printer.printSQLException(e);
        }
    }
    public static void getStocksToNotBuy(BasicDataSource ds, int year) {
        String query = "select * from Indicator where priceVar < 0 and year = ?;";
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, year);
            ResultSet rs = preparedStmt.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError();
            Printer.printSQLException(e);
        }
    }
    public static void getIndicators(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from Indicator as n");
        IndicatorOptions io = new IndicatorOptions();
        if(!Common.buildOptions(io, options)) {
            return;
        }

        //stocks to buy or not buy in a specific year
        if(io.buyStock && io.notBuyStock) {
            System.out.println("Please enter only one of the options: -buy or -notBuy");
            return;
        } else if((io.buyStock || io.notBuyStock) && io.year == null) {
            System.out.println("Please specify a year using the -y option. Ex: -buy -y <year>");
            return;
        } else if(io.buyStock) {
            getStocksToBuy(ds, io.year);
            return;
        } else if(io.notBuyStock) {
            getStocksToNotBuy(ds, io.year);
            return;
        }

        try {
            Connection conn = ds.getConnection();
            if(io.sector != null) {
                sb.setLength(0);
                sb.append("select n.*,s.sector from Indicator as n inner join Stock as s using (symbol) ");
            }

            if(io.symbol != null || io.year != null || io.sector != null)
                sb.append(" where ");

            if(io.symbol != null) {
                sb.append(" symbol = ? ");
            }
            if(io.year != null) {
                if (io.symbol != null)
                    sb.append(" and ");
                sb.append(" year = ? ");
            }
            if(io.sector != null) {
                if (io.symbol != null || io.year != null)
                    sb.append(" and ");
                sb.append(" sector like ?");
            }

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
                preparedStmt.setString(parameterCount, String.join(" ", io.sector));
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
        String[] columns = { "year", "symbol", "eps", "revenue", "costOfRevenue", "grossProfit", "peRatio", "debtRatio", "dividendYield", "dividendPayoutRatio", "pbValueRatio", "pegRatio", "epsGrowth","priceVar" };
        String query = Printer.generateInsert(columns, "Indicator");
        List<String> inputs = Printer.retrieveInputs(columns, console);
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);


            preparedStmt.setInt(1, Integer.parseInt(inputs.get(0)));
            preparedStmt.setString(2, inputs.get(1));
            for(int i = 3; i < 14; i++) {
                preparedStmt.setDouble(i, Double.parseDouble(inputs.get(i - 1)));
            }
            preparedStmt.setDouble(14, Double.parseDouble(inputs.get(13)));
            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError();
            Printer.printSQLException(e);
        }
    }

    public static void deleteIndicator(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("delete from Indicator where symbol = ?");
        IndicatorOptions io = new IndicatorOptions();
        if(!Common.buildOptions(io, options)) {
            return;
        }
        if(io.symbol == null) {
            System.err.println("Please input a symbol to delete with the -s option");
            return;
        }

        try {
            Connection conn = ds.getConnection();

            if(io.year != null)
                sb.append(" and year = ?");

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
