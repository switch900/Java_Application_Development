/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a01029917.data.Customer;

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
     * Sort HashMap by joinDate value from oldest to newest
     * 
     * @param customers
     *            an unsorted HashMap of customers
     * @return HashMap of sorted customers
     */
    public Map<Long, Customer> sortByJoinDateAscending(Map<Long, Customer> customers) {

        List<Entry<Long, Customer>> list = new LinkedList<Map.Entry<Long, Customer>>(customers.entrySet());

        list.sort((Map.Entry<Long, Customer> customer1, Map.Entry<Long, Customer> customer2) -> customer1.getValue().getJoinDate().compareTo(customer2.getValue().getJoinDate()));

        Map<Long, Customer> sortedustomers = new LinkedHashMap<Long, Customer>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Long, Customer> entry = list.get(i);
            sortedustomers.put(entry.getKey(), entry.getValue());
        }
        return sortedustomers;
    }

    /**
     * Sort HashMap by joinDate value from oldest to newest
     * 
     * @param customers
     *            an unsorted HashMap of customers
     * @return HashMap of sorted customers
     */
    public Map<Long, Customer> sortByJoinDateDescending(Map<Long, Customer> customers) {

        List<Entry<Long, Customer>> list = new LinkedList<Map.Entry<Long, Customer>>(customers.entrySet());
        Collections.reverse(list);
        list.sort((Map.Entry<Long, Customer> customer1, Map.Entry<Long, Customer> customer2) -> customer2.getValue().getJoinDate().compareTo(customer1.getValue().getJoinDate()));

        Map<Long, Customer> sortedCustomers = new LinkedHashMap<Long, Customer>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Long, Customer> entry = list.get(i);
            sortedCustomers.put(entry.getKey(), entry.getValue());
        }

        return sortedCustomers;
    }
}
