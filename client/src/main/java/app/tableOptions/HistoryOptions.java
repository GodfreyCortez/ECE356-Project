package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class HistoryOptions extends Options{

    @Parameter(names = { "-s", "-symbol" }, description = "The symbol for the stock")
    public String symbol = null;

    @Parameter(names = { "-d", "-dates" }, description = "A singular day to be checked")
    public String date = null;

    @Parameter(names = {"-r","-range"}, arity=2, description = "A date range for to check the particular stock")
    public List<String> dateRange = null;

    @Parameter(names = {"-a"}, description = "Get all the stock information")
    public boolean all = false;

    @Parameter(names = {"-52"}, description = "Get the 52 week high and low for a particular stock")
    public boolean weekRange = false;

    @Parameter(names ={"-g", "-general"}, description = "Get the general information about a stock")
    public boolean general = false;
}
