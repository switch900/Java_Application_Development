/**
 * Project: a01029917Comp2613Lab04
 * File: CustomerReport.java
 * Date: May 1, 2018
 * Time: 5:35:08 PM
 */

package a01029917.io;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import a01029917.data.Customer;
import a01029917.data.util.CompareByJoinDate;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReport {

    public static final String LINE = new String(new char[148]).replace("\0", "-");
    public static final String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-25s %-12s %-12s %-15s %-30s %-20s %n";
    public static final String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-25s %-12s %-12s %-15s %-30s %-20s %n";

    /**
     * default constructor
     */
    public CustomerReport() {
    }

    /**
     * Sorts HashMap by Joined Date from oldest to newest and prints report
     * 
     * @param customers
     */
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

    /**
     * SuppressWarnings for Lab3
     */
    @SuppressWarnings("unused")
    public static void suppressSomething() {
        String someday = "Maybe this will be used someday";
    }

    /**
     * @deprecated for Lab3
     */
    @Deprecated
    public static void deprecateSomething() {
        String deprecatedString = "This shouldn't be used again";
        System.out.println(deprecatedString);
    }
}
