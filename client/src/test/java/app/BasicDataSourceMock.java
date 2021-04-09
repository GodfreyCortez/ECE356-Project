package app;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class BasicDataSourceMock extends BasicDataSource {
    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionMock();
    }
}
