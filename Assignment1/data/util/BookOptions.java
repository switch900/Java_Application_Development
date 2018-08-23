/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.io.CustomerReader;
import a01029917.io.CustomerReport;

/**
 * Extract the program options from the commandline arguments and store them for safekeeping.
 * Takes input from commandline arguments and passes them to correct method for handling.
 * 
 * @author Andrew Hewitson, A01029917 and scirka
 */
public class BookOptions {

    private static CommandLine commandLine;
    private static Options options = new Options();

    public static final Logger LOG_OPTIONS = LogManager.getLogger(BookOptions.class);

    public static void process(String[] args) throws ParseException {
        commandLine = new DefaultParser().parse(createOptions(), args);
    }

    private static Options createOptions() {

        for (Value value : Value.values()) {
            Option option = null;

            if (value.hasArg) {
                option = Option.builder(value.option).longOpt(value.longOption).hasArg().desc(value.description).build();
            } else {
                option = Option.builder(value.option).longOpt(value.longOption).desc(value.description).build();
            }

            options.addOption(option);
        }

        return options;
    }

    public static void printMenu() {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", createOptions());

    }

    /**
     * @return the commandLine
     */
    public static CommandLine getCommandLine() {
        return commandLine;
    }

    public enum Value {
        HELP("?", "help", false, "Display help"), //
        CUSTOMERS("c", "customers", false, "Print the customer report"), //
        BOOKS("b", "books", false, "Print the books report"), //
        PURCHASES("p", "purchases", false, "Print the purchases report"), //
        TOTAL("t", "total", false, "When combined with the 'purchases' option, also print the total value of the purchases"), //
        BY_AUTHOR("A", "by_author", false, "Sorts the books report by author in ascending order. This option is ignored if 'books' isn't also specified"), //
        BY_LASTNAME("L", "by_lastname", false, "Sorts the purchases report by customer lastname in ascending order. This option is ignored if 'purchases' isn't also specified"), //
        BY_TITLE("T", "by_title", false, "Sorts the purchases report by book title in ascending order. This option is ignored if 'purchases' isn’t also specified"), //
        BY_JOIN_DATE("J", "by_join_date", false, "Sorts the customers report by join date in ascending order. This option is ignored if 'customers' isn't also specified"), //
        CUSTOMER_ID("C", "customer_id", true, "Filters the purchases report, showing only customers that match the customer id"), //
        DESCENDING("d", "descending", false, "Any sorted report is sorted in descending order. Must be combined with 'by_lastname', 'by_title', or 'by_join_date'");

        private final String option;
        private final String longOption;
        private final boolean hasArg;
        private final String description;

        Value(String option, String longOption, boolean hasArg, String description) {
            this.option = option;
            this.longOption = longOption;
            this.hasArg = hasArg;
            this.description = description;
        }

        /**
         * @return the option
         */
        public String getOption() {
            return option;
        }

        /**
         * @return the longOption
         */
        public String getLongOption() {
            return longOption;
        }

        /**
         * @return the hasArg
         */
        public boolean isHasArg() {
            return hasArg;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Checks commandLine args and if args match BookOptions values then
     * implement appropriate method
     * 
     * @throws ApplicationException
     *             If commandLine args are not valid
     */
    public void run() throws ApplicationException {

        CommandLine commandLine = BookOptions.getCommandLine();

        /**
         * If -purchase -by_title (-p -C=XXXX -T -t -d) are specified, the books report is printed and the books are sorted by book's title ascending including the value of the customer's total
         */
        if (commandLine.hasOption(BookOptions.Value.BY_TITLE.getOption()) && commandLine.hasOption(BookOptions.Value.PURCHASES.getOption()) && (commandLine.hasOption(BookOptions.Value.TOTAL.getOption())) && (commandLine.hasOption(BookOptions.Value.CUSTOMER_ID.getOption()))) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFileWithTotal(compare.sortByTitleDescending(CustomerReader.readPurchasesByCustomerID(commandLine.getOptionValue(BookOptions.Value.CUSTOMER_ID.getOption()))));
            LOG_OPTIONS.info("-p -C=XXXX -T -t -d Selected (Purchase Report filtered by customerID number sorted by book title in descending order which displays purchase total)");
        }
        /**
         * If -purchase -book -customer(-c -p -b) are specified, reports for customer, book and purchase are created.
         */
        else if (commandLine.hasOption(BookOptions.Value.PURCHASES.getOption()) && (commandLine.hasOption(BookOptions.Value.CUSTOMERS.getOption())) && commandLine.hasOption(BookOptions.Value.BOOKS.getOption())) {
            CustomerReport.writeToCustomerReportToFile(CustomerReader.customerSplitString());
            CustomerReport.writeToBookReportToFile(CustomerReader.readBooks());
            CustomerReport.writePurchasesToFile(CustomerReader.readPurchases());
            LOG_OPTIONS.info("-c -p -b Selected  (Customer report, Purchase report and Book report)");
        }
        /**
         * If -customers -by_join_date -desc (-c -J -d) are specified, the customers report is printed and the customers sorted by join date in descending order.
         */
        else if (commandLine.hasOption(BookOptions.Value.DESCENDING.getOption()) && (commandLine.hasOption(BookOptions.Value.BY_JOIN_DATE.getOption())) && (commandLine.hasOption(BookOptions.Value.CUSTOMERS.getOption()))) {
            SortCustomers compare = new SortCustomers();
            CustomerReport.writeToCustomerReportToFile(compare.sortByJoinDateDescending(CustomerReader.customerSplitString()));
            LOG_OPTIONS.info("-c -J -d Selected (Customer Report sorted by join date in descending order");
        }
        /**
         * If -purchases -by_lastname -desc (-p -L -d) are specified, the purchases report and the purchases are sorted by last name in descending order.
         */
        else if (commandLine.hasOption(BookOptions.Value.DESCENDING.getOption()) && (commandLine.hasOption(BookOptions.Value.BY_LASTNAME.getOption())) && (commandLine.hasOption(BookOptions.Value.PURCHASES.getOption()))) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFile(compare.sortByLastNameDescending(CustomerReader.readPurchases()));
            LOG_OPTIONS.info("-p -L -d Selected (Purchase Report sorted by last name in descending order)");
        }
        /**
         * If -purchase -by_title (-p -T -t) are specified, the books report is printed and the books are sorted by book's title ascending including the value of the customer's total
         */
        else if (commandLine.hasOption(BookOptions.Value.BY_TITLE.getOption()) && commandLine.hasOption(BookOptions.Value.PURCHASES.getOption()) && (commandLine.hasOption(BookOptions.Value.TOTAL.getOption()))) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFileWithTotal(compare.sortByTitleAscending(CustomerReader.readPurchases()));
            LOG_OPTIONS.info("-p -T -t Selected (Purchase Report sorted by customer's last name which display's purchase total)");
        }
        /**
         * If -purchase -by_title (-p -T) are specified, the books report is printed and the books are sorted by book's title ascending
         */
        else if (commandLine.hasOption(BookOptions.Value.BY_TITLE.getOption()) && commandLine.hasOption(BookOptions.Value.PURCHASES.getOption())) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFileWithTotal(compare.sortByTitleAscending(CustomerReader.readPurchases()));
            LOG_OPTIONS.info("-p -T Selected (Purchase Report sorted by book title)");
        }
        /**
         * If -by_title -descending (-T -d) are specified, book report is printed sorted by book titles descending
         */
        else if (commandLine.hasOption(BookOptions.Value.DESCENDING.getOption()) && (commandLine.hasOption(BookOptions.Value.BY_TITLE.getOption()))) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFile(compare.sortByTitleDescending(CustomerReader.readPurchases()));
            LOG_OPTIONS.info("-T -d Selected (Purchase Report sorted by Title in descending order");
        }
        /**
         * If -purchase -by_lastname (-p -L) are specified, the books report is printed and the books are sorted by customer's last name
         */
        else if (commandLine.hasOption(BookOptions.Value.BY_LASTNAME.getOption()) && commandLine.hasOption(BookOptions.Value.PURCHASES.getOption())) {
            SortPurchases compare = new SortPurchases();
            CustomerReport.writePurchasesToFile(compare.sortByLastNameAscending(CustomerReader.readPurchases()));
            LOG_OPTIONS.info("-p -L Selected (Purchase Report sorted by customer's last name)");
        }
        /**
         * If -books -by_author (-b -A) are specified, the books report is printed and the books are sorted by author.
         */
        else if (commandLine.hasOption(BookOptions.Value.BY_AUTHOR.getOption()) && commandLine.hasOption(BookOptions.Value.BOOKS.getOption())) {
            SortBooks compare = new SortBooks();
            CustomerReport.writeToBookReportToFile(compare.sortAuthor(CustomerReader.readBooks()));
            LOG_OPTIONS.info("-b -A Selected (Book Report sorted by Author)");
        }
        /**
         * If -customers -by_join_date (-c -J) are specified, the customers report is printed and the customers sorted by join date.
         */
        else if (commandLine.hasOption(BookOptions.Value.BY_JOIN_DATE.getOption()) && commandLine.hasOption(BookOptions.Value.CUSTOMERS.getOption())) {
            SortCustomers compare = new SortCustomers();
            CustomerReport.writeToCustomerReportToFile(compare.sortByJoinDateAscending(CustomerReader.customerSplitString()));
            LOG_OPTIONS.info("-c -J Selected (Customer Report sorted by join date)");
        }

        /**
         * if -? is selected help menu is displayed
         */
        else if (commandLine.hasOption(BookOptions.Value.HELP.getOption())) {
            BookOptions.printMenu();
            LOG_OPTIONS.info("-? Selected (Help)");
        }

        /**
         * If -customers (-c) is specified, the customer report is printed
         */
        else if (commandLine.hasOption(BookOptions.Value.CUSTOMERS.getOption())) {
            CustomerReport.writeToCustomerReportToFile(CustomerReader.customerSplitString());
            LOG_OPTIONS.info("-c Selected (Customer Report)");
        }

        /**
         * If -books (-b) is specified, the books report is printed.
         */
        else if (commandLine.hasOption(BookOptions.Value.BOOKS.getOption())) {
            CustomerReport.writeToBookReportToFile(CustomerReader.readBooks());
            LOG_OPTIONS.info("-b Selected (Book Report)");
        }

        /**
         * If -purchases (-p) is specified, the customers report is printed.
         */
        else if (commandLine.hasOption(BookOptions.Value.PURCHASES.getOption())) {
            CustomerReport.writePurchasesToFile(CustomerReader.readPurchases());
            LOG_OPTIONS.info("-p Selected (Purchase Report)");
        }

        /**
         * if -total (-t) is specified, the purchase report with total of all prices is printed.
         */
        else if (commandLine.hasOption(BookOptions.Value.TOTAL.getOption())) {
            CustomerReport.writePurchasesToFileWithTotal(CustomerReader.readPurchases());
            LOG_OPTIONS.info("-t Selected (Purchase Report with purchase total)");
        }

        /**
         * If -customer_id (-C) is specified with the following arg of a customer ID number then purchase report of all books purchase by that customer is printed.
         */
        else if (commandLine.hasOption(BookOptions.Value.CUSTOMER_ID.getOption())) {
            CustomerReport.writePurchasesToFile(CustomerReader.readPurchasesByCustomerID(commandLine.getOptionValue(BookOptions.Value.CUSTOMER_ID.getOption())));
            LOG_OPTIONS.info("-C **** Selected (Purchase report filtered by CustomerID number) ");
        }
        /**
         * Print Customer, Book and Purchase report if no input given
         */
        else if (commandLine.getArgList().isEmpty()) {
            CustomerReport.writeToCustomerReportToFile(CustomerReader.customerSplitString());
            CustomerReport.writeToBookReportToFile(CustomerReader.readBooks());
            CustomerReport.writePurchasesToFile(CustomerReader.readPurchases());
            LOG_OPTIONS.info("No input given. Customer report, Purchase report and Book report have been output");
        }

        /**
         * Print Customer, Book and Purchase report if no input given
         */
        else {
            CustomerReport.writeToCustomerReportToFile(CustomerReader.customerSplitString());
            CustomerReport.writeToBookReportToFile(CustomerReader.readBooks());
            CustomerReport.writePurchasesToFile(CustomerReader.readPurchases());
            LOG_OPTIONS.info("No input given.  Customer report, Purchase report and Book report have been output");
        }
    }
}
