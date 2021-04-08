package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.List;

public class StockOptions {

    @Parameter(names = { "-sec", "-sector"}, variableArity = true, description="The sector for the stocks to look at")
    public List<String> sector = null;

    @Parameter(names = {"-s"}, description = "The symbol")
    public String symbol = null;
}
