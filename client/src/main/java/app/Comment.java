package app;

import app.tableOptions.CommentOptions;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Comment {
    public static void addComment(BasicDataSource ds, Console console) {
        String[] columns = {"date","symbol", "comment"};
        String query = Printer.generateInsert(columns, "Comment");
        List<String> inputs = Printer.retrieveInputs(Arrays.copyOfRange(columns, 1, columns.length), console);
        try {
            Connection conn = ds.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);


            preparedStmt.setTimestamp(1,new Timestamp(new java.util.Date().getTime())); //change to get current dateTime
            preparedStmt.setString(2, inputs.get(0));
            preparedStmt.setString(3, inputs.get(1));

            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            Printer.printInsertError(e);
        }
    }

    public static void deleteComment(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("delete from Comment where symbol = ?");
        CommentOptions co = new CommentOptions();
        if(!Common.buildOptions(co, options)) {
            return;
        }

        try {
            Connection conn = ds.getConnection();
            Date date = null;
            Date startDate = null;
            Date endDate = null;

            if(co.symbol == null) {
                System.err.println("Symbol to describe is missing, please add it with option -s");
                conn.close();
                return;
            }

            if (co.dateRange != null) {
                startDate = Date.valueOf(co.dateRange.get(0));
                endDate = Date.valueOf(co.dateRange.get(1));
                sb.append("and convert(date, date) >= ? and convert(date, date) <= ?");
            } else if (co.date != null) {
                date = Date.valueOf(co.date);
                sb.append("and convert(date, date) = ?");
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());
            preparedStmt.setString(1, co.symbol);

            if (co.dateRange != null) {
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

    public static void getComments(String[] options, BasicDataSource ds) {
        StringBuilder sb = new StringBuilder("select * from Comment ");
        CommentOptions co = new CommentOptions();
        if(!Common.buildOptions(co, options)) {
            return;
        }

        try {
            Connection conn = ds.getConnection();
            Date date = null;
            Date startDate = null;
            Date endDate = null;

            if (co.symbol != null || co.dateRange != null || co.date != null){
                sb.append("where ");
                
                if (co.symbol != null && co.dateRange != null) {
                    sb.append(" symbol = ? ");
                    startDate = Date.valueOf(co.dateRange.get(0));
                    endDate = Date.valueOf(co.dateRange.get(1));
                    sb.append(" and convert(date,date) >= ? and convert(date,date) <= ? ");
                } else if (co.symbol != null && co.date != null){
                    sb.append(" symbol = ? ");
                    date = Date.valueOf(co.date);
                    sb.append(" and convert(date,date) = ? ");
                } else if (co.symbol == null && co.dateRange != null){
                    startDate = Date.valueOf(co.dateRange.get(0));
                    endDate = Date.valueOf(co.dateRange.get(1));
                    sb.append(" convert(date,date) >= ? and convert(date,date) <= ? ");
                } else if (co.symbol == null && co.date != null){
                    date = Date.valueOf(co.date);
                    sb.append(" convert(date,date) = ? ");
                }
            }

            sb.append(";");
            PreparedStatement preparedStmt = conn.prepareStatement(sb.toString());

            if (co.symbol != null&&co.dateRange != null) {
                preparedStmt.setString(1, co.symbol);
                preparedStmt.setDate(2, startDate);
                preparedStmt.setDate(3, endDate);
            } else if (co.symbol != null&&co.date != null){
                preparedStmt.setString(1, co.symbol);
                preparedStmt.setDate(2, date);
            } else if (co.symbol == null&&co.dateRange != null){
                preparedStmt.setDate(1, startDate);
                preparedStmt.setDate(2, endDate);
            } else if (co.symbol == null&&co.date != null){
                preparedStmt.setDate(1, date);
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