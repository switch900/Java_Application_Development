/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */

package a01029917.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.Assignment01;
import a01029917.data.Book;
import a01029917.data.Customer;
import a01029917.data.Purchase;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.SortPurchases;

/**
 * Formats output and saves to file
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReport {

    public static final String LINE = new String(new char[196]).replace("\0", "-");
    public static final String CUSTOMER_FORMAT = "%4d. %-6d %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s %7d";
    public static final String HEADER_FORMAT = "%4s. %-6s %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s %7s";
    public static final String BOOKREPORT_HEADER_FORMAT = "%8s. %-12s %-40s %-40s %4s %6.3s %13s %-60s";
    public static final String BOOKREPORT_FORMAT = "%8d. %-12s %-40s %-40s %-4d %-6.3f %-13d %-60s";
    public static final String PURCHASEREPORT_HEADER_FORMAT = "%-24s %-80s %2s";
    public static final String PURCHASEREPORT_FORMAT = "%-24s %-80s $%.2f";

    private static final String OUT_FILENAME = "customers_report.txt";
    private static final String OUT_BOOKREPORT = "book_report.txt";
    private static final String OUT_PURCHASEREPORT = "purchases_report.txt";

    public static final Logger LOG_REPORT = LogManager.getLogger(CustomerReport.class);

    /**
     * default constructor
     */
    public CustomerReport() {
    }

    /**
     * Takes HashMap of Customers and writes formatted report to file
     * 
     * @param customers
     *            A HashMap of Customers
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */

    public static void writeToCustomerReportToFile(Map<Long, Customer> customers) throws ApplicationException {

        PrintStream out = null;
        File filename = new File(OUT_FILENAME);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            out = new PrintStream(fileOut);
            out.println(Assignment01.STARTTIME);
            out.println("Customers Report");
            out.println(LINE);
            out.println(String.format(HEADER_FORMAT, "#", "ID", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone", "Email", "Join Date", "Length"));
            out.println(LINE);
            int i = 0;
            for (Map.Entry<Long, Customer> customer1 : customers.entrySet()) {

                LocalDate date = customer1.getValue().getJoinDate();
                int joinLength = LocalDate.now().getYear() - customer1.getValue().getJoinDate().getYear();

                out.println(String.format(CUSTOMER_FORMAT, ++i, customer1.getValue().getIdentifier(), //
                        truncate(customer1.getValue().getFirstName(), 12), //
                        truncate(customer1.getValue().getLastName(), 12), //
                        truncate(customer1.getValue().getStreetName(), 40), //
                        truncate(customer1.getValue().getCity(), 25), //
                        truncate(customer1.getValue().getPostalCode(), 12), //
                        truncate(customer1.getValue().getPhoneNumber(), 15), //
                        truncate(customer1.getValue().getEmailAddress(), 40), //
                        date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")), //
                        joinLength));
            }
        } catch (FileNotFoundException e) {
            LOG_REPORT.error(e.getMessage());
        } finally {
            out.println(Assignment01.calculateEndTimeAndDuration());
            out.close();
        }
    }

    /**
     * Takes HashMap of Books and writes formatted report to file
     * 
     * @param books
     *            A HashMap of Books
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */
    public static void writeToBookReportToFile(Map<Integer, Book> books) throws ApplicationException {

        PrintStream out = null;
        File filename = new File(OUT_BOOKREPORT);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            out = new PrintStream(fileOut);
            out.println(Assignment01.STARTTIME);
            out.println("Book Report");
            out.println(LINE);
            out.println(String.format(BOOKREPORT_HEADER_FORMAT, "Book ID", "ISBN", "Author", "Title", "Year", "Ratings", "Ratings Count", "Image URL"));
            out.println(LINE);

            for (Map.Entry<Integer, Book> book1 : books.entrySet()) {

                out.println(String.format(BOOKREPORT_FORMAT, //
                        book1.getValue().getBookID(), //
                        truncate(book1.getValue().getiSBN(), 12), //
                        truncate(book1.getValue().getAuthors(), 40), //
                        truncate(book1.getValue().getTitle(), 40), //
                        book1.getValue().getYear(), //
                        book1.getValue().getRatings(), //
                        book1.getValue().getRatingsCount(), //
                        truncate(book1.getValue().getImageURL(), 60)));
            }
        } catch (FileNotFoundException e) {
            LOG_REPORT.error(e.getMessage());
        } finally {
            out.println(Assignment01.calculateEndTimeAndDuration());
            out.close();
        }
    }

    /**
     * Takes HashMap of Purchases and writes formatted report to file
     * 
     * @param purchases
     *            A HashMap of Purchases
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */
    public static void writePurchasesToFile(Map<Integer, Purchase> purchases) throws ApplicationException {

        Map<Integer, Book> books = CustomerReader.readBooks();
        Map<Long, Customer> customers = CustomerReader.customerSplitString();
        String name = null;
        String title = null;
        Double price = 0.0;

        PrintStream out = null;
        File filename = new File(OUT_PURCHASEREPORT);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            out = new PrintStream(fileOut);
            out.println(Assignment01.STARTTIME);
            out.println("Purchase Report");
            out.println(LINE);
            out.println(String.format(PURCHASEREPORT_HEADER_FORMAT, "Name", "Title", "Price"));
            out.println(LINE);

            for (Map.Entry<Integer, Purchase> purchase1 : purchases.entrySet()) {
                if (books.containsKey(purchase1.getValue().getBook_ID())) {
                    title = books.get(purchase1.getValue().getBook_ID()).getTitle();
                }
                if (customers.containsKey(purchase1.getValue().getCustomer_ID())) {
                    name = customers.get(purchase1.getValue().getCustomer_ID()).getFirstName() + " " + customers.get(purchase1.getValue().getCustomer_ID()).getLastName();
                }
                price = purchase1.getValue().getPrice();
                out.println(String.format(PURCHASEREPORT_FORMAT, truncate(name, 24), truncate(title, 80), price));
            }
        } catch (FileNotFoundException e) {
            LOG_REPORT.error(e.getMessage());
        } finally {
            out.println(Assignment01.calculateEndTimeAndDuration());
            out.close();
        }
    }

    /**
     * Takes HashMap of Purchases and writes formatted report to file
     * 
     * @param purchases
     *            A HashMap of Purchases
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */
    public static void writePurchasesToFileWithTotal(Map<Integer, Purchase> purchases) throws ApplicationException {

        SortPurchases total = new SortPurchases();
        Map<Integer, Book> books = CustomerReader.readBooks();
        Map<Long, Customer> customers = CustomerReader.customerSplitString();
        String name = null;
        String title = null;
        Double price = 0.0;

        PrintStream out = null;
        File filename = new File(OUT_PURCHASEREPORT);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);
            out = new PrintStream(fileOut);
            out.println(Assignment01.STARTTIME);
            out.println("Purchase Report");
            out.println(LINE);
            out.println(String.format(PURCHASEREPORT_HEADER_FORMAT, "Name", "Title", "Price"));
            out.println(LINE);

            for (Map.Entry<Integer, Purchase> purchase1 : purchases.entrySet()) {
                if (books.containsKey(purchase1.getValue().getBook_ID())) {
                    title = books.get(purchase1.getValue().getBook_ID()).getTitle();
                }
                if (customers.containsKey(purchase1.getValue().getCustomer_ID())) {
                    name = customers.get(purchase1.getValue().getCustomer_ID()).getFirstName() + " " + customers.get(purchase1.getValue().getCustomer_ID()).getLastName();
                }
                price = purchase1.getValue().getPrice();
                out.println(String.format(PURCHASEREPORT_FORMAT, truncate(name, 24), truncate(title, 80), price));
            }

        } catch (FileNotFoundException e) {
            LOG_REPORT.error(e.getMessage());
        } finally {
            double totalDouble = total.addTotalPrice(purchases);
            out.println(String.format("%s%.2f", "Value of purchases: $", totalDouble));
            out.println(Assignment01.calculateEndTimeAndDuration());
            out.close();
        }
    }

    /**
     * If input String is greater than designated maxWidth then String is subStringed into a shorter
     * String with an ellipsis (...)
     * 
     * @param input
     *            String to truncate
     * @param maxWidth
     *            Maximum width of String
     * @return
     *         Truncated string as a String
     */
    public static String truncate(String input, int maxWidth) {
        String string = null;
        if (input.length() > maxWidth) {
            string = input.substring(0, (maxWidth - 3)) + "...";
        } else {
            string = input;
        }
        return string;
    }
}
