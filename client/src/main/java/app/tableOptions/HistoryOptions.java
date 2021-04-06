package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class HistoryOptions {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-s", "-symbol" }, description = "The symbol for the stock")
    public String symbol = null;

    @Parameter(names = { "-d", "-dates" }, description = "A singular day to be checked")
    public String date = null;

    @Parameter(names = {"-r","-range"}, arity=2, description = "A date range for to check the particular stock")
    public List<String> dateRange = null;
}