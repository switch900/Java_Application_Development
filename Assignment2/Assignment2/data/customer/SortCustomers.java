/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.data.customer;

import java.util.Comparator;

/**
 * Sorts Customers
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

public class SortCustomers {

    /**
     * default constructor
     */
    public SortCustomers() {
    }

    /**
     * Compare Customers by Joined Date
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class CompareByJoinedDate implements Comparator<Customer> {

        @Override
        public int compare(Customer customer1, Customer customer2) {
            return customer1.getJoinDate().compareTo(customer2.getJoinDate());
        }
    }
}
