/**
 * Project: a01029917Comp2613Lab05
 * 
 * File: Lab05.java
 * Date: May 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import a01029917.Lab05;
import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.CompareByJoinDate;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReport {

    public static final String LINE = new String(new char[148]).replace("\0", "-");
    public static final String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-25s %-12s %-12s %-15s %-30s %-20s";
    public static final String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-25s %-12s %-12s %-15s %-30s %-20s";

    private static final String OUT_FILENAME = "customers_report.txt";

    /**
     * default constructor
     */
    public CustomerReport() {
    }

    /**
     * Sorts HashMap of Customers by Joined Date from oldest to newest and writes report to file
     * 
     * @param customers
     *            A HashMap of Customers
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */

    public static void writeToFile(Map<Long, Customer> customers) throws ApplicationException {

        PrintStream out = null;
        File filename = new File(OUT_FILENAME);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);

            out = new PrintStream(fileOut);
            out.println(Lab05.STARTTIME);
            out.println("Customers Report");
            out.println(LINE);
            out.println(String.format(HEADER_FORMAT, "#", "ID", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone", "Email", "Join Date"));
            out.println(LINE);
            CompareByJoinDate compare = new CompareByJoinDate();
            customers = compare.sortByJoinDate(customers);
            int i = 0;
            for (Map.Entry<Long, Customer> customer1 : customers.entrySet()) {
                LocalDate date = customer1.getValue().getJoinDate();

                out.println(String.format(CUSTOMER_FORMAT, ++i, customer1.getValue().getIdentifier(), //
                        customer1.getValue().getFirstName(), //
                        customer1.getValue().getLastName(), //
                        customer1.getValue().getStreetName(), //
                        customer1.getValue().getCity(), //
                        customer1.getValue().getPostalCode(), //
                        customer1.getValue().getPhoneNumber(), //
                        customer1.getValue().getEmailAddress(), //
                        date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))));
            }
        } catch (FileNotFoundException e) {
            Lab05.LOG.error(e.getMessage());
        } finally {

            out.println(Lab05.calculateEndTimeAndDuration());
            out.close();
        }
    }

    /**
     * Sorts HashMap by Joined Date from oldest to newest and prints report to console.
     * Not used for Lab 5.
     * 
     * @param customers
     *            A Hashmap of customers to print
     */
    @Deprecated
    public static void printToConsole(Map<Long, Customer> customers) {

        int i = 0;

        System.out.println("Customer Report");
        System.out.println(LINE);
        System.out.printf(HEADER_FORMAT, "#", "ID", "First name ", "Last name ", "Street ", "City ", "Postal Code ", "Phone ", "Email", "Join Date");
        System.out.println(LINE);

        CompareByJoinDate compare = new CompareByJoinDate();
        customers = compare.sortByJoinDate(customers);

        for (Map.Entry<Long, Customer> customer1 : customers.entrySet()) {
            LocalDate date = customer1.getValue().getJoinDate();

            System.out.printf(CUSTOMER_FORMAT, ++i, customer1.getValue().getIdentifier(), //
                    customer1.getValue().getFirstName(), //
                    customer1.getValue().getLastName(), //
                    customer1.getValue().getStreetName(), //
                    customer1.getValue().getCity(), //
                    customer1.getValue().getPostalCode(), //
                    customer1.getValue().getPhoneNumber(), //
                    customer1.getValue().getEmailAddress(), //
                    date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
        }
    }
}