package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class NewsOptions {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-s", "-symbol" }, description = "The symbol for the stock")
    public String symbol = null;

    @Parameter(names = { "-p", "-publisher" }, description = "The publisher of the article")
    public String publisher = null;

    @Parameter(names = { "-d", "-dates" }, description = "A singular day to be checked")
    public String date = null;

    @Parameter(names = {"-r","-range"}, arity=2, description = "A date range to check the particular stock articles")
    public List<String> dateRange = null;
}
