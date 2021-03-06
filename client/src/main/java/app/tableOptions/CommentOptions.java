package app.tableOptions;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommentOptions extends Options {
    @Parameter(names = { "-s", "-symbol" }, description = "The symbol for the stock")
    public String symbol = null;

    @Parameter(names = { "-d", "-dates" }, description = "A singular day to be checked")
    public String date = null;

    @Parameter(names = {"-r","-range"}, arity=2, description = "A date range to check for comments")
    public List<String> dateRange = null;
}