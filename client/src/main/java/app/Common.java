package app;

import app.tableOptions.Options;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
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

    public static boolean buildOptions(Options opt, String[] input) {
        try {
            JCommander.newBuilder()
                    .addObject(opt)
                    .build()
                    .parse(input);
            return true;
        } catch (ParameterException e) {
            e.usage();
            return false;
        }
    }
}
