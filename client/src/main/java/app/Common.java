package app;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Common {
    public static void query(BasicDataSource ds, String query) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            Printer.printQuery(ps.executeQuery());
            conn.close();
        } catch (SQLException e) {
            Printer.printQueryError(e);
        }
    }
}
