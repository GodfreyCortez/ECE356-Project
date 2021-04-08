package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.List;

public class IndicatorOptions {
    @Parameter(names = { "-s", "-symbol" }, description = "The symbol for the stock")
    public String symbol = null;

    @Parameter(names = { "-y", "-year" }, description = "A singular year to be checked")
    public Integer year = null;

    @Parameter(names = { "-sec", "-sector" }, variableArity = true, description = "The sector of the stock")
    public List<String> sector = null;
}
