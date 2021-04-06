package app.tableOptions;

import com.beust.jcommander.Parameter;

public class StockOptions {

    @Parameter(names = { "-sec", "-sector"}, description="The sector for the stocks to look at")
    public String sector = null;

    @Parameter(names = {"-s"}, description = "The symbol")
    public String symbol = null;
}
