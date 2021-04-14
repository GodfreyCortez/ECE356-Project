package app;

import app.tableOptions.StockOptions;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Stock {
    public static void query(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from Stock");
        StockOptions so = new StockOptions();
        if(!Common.buildOptions(so, options)) {
            return;
        }

        try {
            Connection conn = ds.getConnection();
            String sector = null;
            if(so.sector != null) {
                sb.append(" where sector like ? ");
                sector = String.join(" ", so.sector);
            }
            sb.append(";");

            PreparedStatement preparedStatement = conn.prepareStatement(sb.toString());

            if(so.sector != null)
                preparedStatement.setString(1, sector);

            ResultSet rs = preparedStatement.executeQuery();
            Printer.printQuery(rs);
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError(e);
        }
    }

    public static void addStock(BasicDataSource ds, Console console) {
        String[] columns = { "symbol", "sector" };
        String query = Printer.generateInsert(columns, "Stock");
        List<String> inputs = Printer.retrieveInputs(columns, console);
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);


            preparedStatement.setString(1, inputs.get(0));
            preparedStatement.setString(2, inputs.get(1));

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError(e);
        }
    }

    public static void delete(String[] options, BasicDataSource ds) {
        // Need to delete all from all other tables which foreign key to this one
        StringBuilder sb = new StringBuilder("delete from Stock where symbol = ?;");
        StockOptions so = new StockOptions();
        if(!Common.buildOptions(so, options)) {
            return;
        }

        if(so.symbol == null) {
            System.err.println("Please input a symbol to delete with option -s");
            return;
        }


        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            ps.setString(1, so.symbol);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printDeleteError(e);
        }
    }
}
