/**
 * Project: a01029917Comp2613Lab05
 * 
 * File: Lab05.java
 * Date: May 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917.data.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a01029917.data.Customer;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

public class CompareByJoinDate {

    /**
     * default constructor
     */
    public CompareByJoinDate() {
    }

    /**
     * Sort HashMap by joinDate value from oldest to newest
     * 
     * @param customers
     *            an unsorted HashMap of customers
     * @return HashMap of sorted customers
     */
    public Map<Long, Customer> sortByJoinDate(Map<Long, Customer> customers) {

        List<Entry<Long, Customer>> list = new LinkedList<Map.Entry<Long, Customer>>(customers.entrySet());

        list.sort((Map.Entry<Long, Customer> customer1, Map.Entry<Long, Customer> customer2) -> customer1.getValue().getJoinDate().compareTo(customer2.getValue().getJoinDate()));

        Map<Long, Customer> sortedustomers = new LinkedHashMap<Long, Customer>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Long, Customer> entry = list.get(i);
            sortedustomers.put(entry.getKey(), entry.getValue());
        }
        return sortedustomers;
    }
}
