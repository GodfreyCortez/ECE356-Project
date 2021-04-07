package app;

import app.tableOptions.NewsOptions;
import com.beust.jcommander.JCommander;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
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
        StringBuilder sb = new StringBuilder("select * from News as n");
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
            String sector = null;

            if(no.sector != null) {
                sector = no.sector;
                sb.setLength(0);
                sb.append("select n.*,s.sector from News as n inner join Stock as s using (symbol) ");
            }

            if(no.symbol != null || no.publisher != null || no.date != null || no.dateRange != null || no.sector != null)
                sb.append(" where ");

            boolean otherConditions = false;
            if(no.symbol != null) {
                sb.append(" symbol = ? ");
                otherConditions = true;
            }

            if(no.publisher != null) {
                publisher = no.publisher;
                if(otherConditions)
                    sb.append(" and ");
                sb.append(" publisher like ? ");
                otherConditions = true;
            }

            if(no.dateRange != null) {
                if(otherConditions)
                    sb.append(" and ");
                startDate = Date.valueOf(no.dateRange.get(0));
                endDate = Date.valueOf(no.dateRange.get(1));
                sb.append("and date >= ? and date <= ?");
                otherConditions = true;
            } else if(no.date != null) {
                date = Date.valueOf(no.date);
                if(otherConditions)
                    sb.append(" and ");
                sb.append(" date = ? ");
                otherConditions = true;
            }

            if(no.sector != null) {
                if(otherConditions)
                    sb.append(" and ");
                sb.append(" sector = ? ");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            int parameterCount = 0;
            if(no.symbol != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, no.symbol);
            }
            if(no.publisher != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, publisher);
            }

            if(no.dateRange != null) {
                parameterCount++;
                preparedStmt.setDate(parameterCount, startDate);
                parameterCount++;
                preparedStmt.setDate(parameterCount, endDate);
            } else if(no.date != null) {
                parameterCount++;
                preparedStmt.setDate(parameterCount, date);
            }

            if(sector != null) {
                parameterCount++;
                preparedStmt.setString(parameterCount, sector);
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