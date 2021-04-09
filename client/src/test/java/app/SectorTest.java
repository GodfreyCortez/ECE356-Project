package app;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;

public class SectorTest {
    @Test
    public void testGetSectors() {
        BasicDataSource ds = new BasicDataSourceMock();
        Sector.getSectors(ds);
    }
}
