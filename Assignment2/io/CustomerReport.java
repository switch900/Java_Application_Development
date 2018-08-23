/**
 * Project: a01029917Comp2613Lab02
 * File: CustomerReport.java
 * Date: Apr 17, 2018
 * Time: 5:35:08 PM
 */

package a01029917.io;

import a01029917.data.Customer;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReport {

    public static final String LINE = new String(new char[132]).replace("\0", "-");
    public static final String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-25s %-12s %-12s %-15s %-20s%n";
    public static final String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-25s %-12s %-12s %-15s %-20s%n";

    /**
     * 
     */
    public CustomerReport() {

    }

    /**
     * Prints Report Header
     * 
     * @param customers
     */
    public static void printToConsole(Customer[] customers) {

        System.out.println();
        System.out.println("Customer Report");
        System.out.println(LINE);
        System.out.printf(HEADER_FORMAT, "#. ", "ID", "First name ", "Last name ", "Street ", "City ", "Postal Code ", "Phone ", "Email");
        System.out.println(LINE);
        int i = 0;
        for (Customer customer : customers) {
            System.out.printf(CUSTOMER_FORMAT, ++i, customer.getIdentifier(), customer.getFirstName(), customer.getLastName(), customer.getStreetName(), customer.getCity(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getEmailAddress());
        }
    }
}
