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

import a01029917.data.Book;
import a01029917.data.Customer;
import a01029917.data.Purchase;
import a01029917.io.CustomerReader;

/**
 * Sorts purchases
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class SortPurchases {

    public SortPurchases() {
    }

    /**
     * Sort HashMap of Purchases by Customer's Last Name Ascending
     * 
     * @param purchases
     *            an unsorted HashMap of purchases
     * @return HashMap of sorted purchases
     * @throws ApplicationException
     *             if input is not valid
     */
    public Map<Integer, Purchase> sortByLastNameAscending(Map<Integer, Purchase> purchases) throws ApplicationException {

        Map<Long, Customer> customers = CustomerReader.customerSplitString();
        List<Entry<Integer, Purchase>> list = new LinkedList<Map.Entry<Integer, Purchase>>(purchases.entrySet());

        list.sort((Map.Entry<Integer, Purchase> purchase1, Map.Entry<Integer, Purchase> purchase2) -> customers.get(purchase1.getValue().getCustomer_ID()).getLastName().compareTo(customers.get(purchase2.getValue().getCustomer_ID()).getLastName()));

        Map<Integer, Purchase> sortedPurchases = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            sortedPurchases.put(entry.getKey(), entry.getValue());
        }
        return sortedPurchases;
    }

    /**
     * Sort HashMap of Purchases by Customer's Last Name Descending
     * 
     * @param purchases
     *            an unsorted HashMap of purchases
     * @return HashMap of sorted purchases
     * @throws ApplicationException
     *             if input is not valid
     */
    public Map<Integer, Purchase> sortByLastNameDescending(Map<Integer, Purchase> purchases) throws ApplicationException {

        Map<Long, Customer> customers = CustomerReader.customerSplitString();
        List<Entry<Integer, Purchase>> list = new LinkedList<Map.Entry<Integer, Purchase>>(purchases.entrySet());

        list.sort((Map.Entry<Integer, Purchase> purchase1, Map.Entry<Integer, Purchase> purchase2) -> customers.get(purchase1.getValue().getCustomer_ID()).getLastName().compareTo(customers.get(purchase2.getValue().getCustomer_ID()).getLastName()));
        Collections.reverse(list);
        Map<Integer, Purchase> sortedPurchases = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            sortedPurchases.put(entry.getKey(), entry.getValue());
        }
        List<Entry<Integer, Purchase>> reversedList = new LinkedList<Map.Entry<Integer, Purchase>>(sortedPurchases.entrySet());
        Collections.reverse(reversedList);
        Map<Integer, Purchase> reversedMap = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        return reversedMap;
    }

    /**
     * Sort HashMap of Purchases by Book Title Ascending
     * 
     * @param purchases
     *            an unsorted HashMap of purchases
     * @return
     *         HashMap of sorted purchases sorted by Title
     * @throws ApplicationException
     *             if not valid input
     */
    public Map<Integer, Purchase> sortByTitleAscending(Map<Integer, Purchase> purchases) throws ApplicationException {

        Map<Integer, Book> books = CustomerReader.readBooks();
        List<Entry<Integer, Purchase>> list = new LinkedList<Map.Entry<Integer, Purchase>>(purchases.entrySet());

        list.sort((Map.Entry<Integer, Purchase> purchase1, Map.Entry<Integer, Purchase> purchase2) -> (books.get(purchase1.getValue().getBook_ID()).getTitle()).compareTo(books.get(purchase2.getValue().getBook_ID()).getTitle()));

        Map<Integer, Purchase> sortedBooks = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            sortedBooks.put(entry.getKey(), entry.getValue());
        }
        return sortedBooks;
    }

    /**
     * Sort HashMap of Purchases by Book Title
     * 
     * @param purchases
     *            an unsorted HashMap of purchases
     * @return
     *         HashMap of sorted purchases sorted by Title
     * @throws ApplicationException
     *             if not valid input
     */
    public Map<Integer, Purchase> sortByTitleDescending(Map<Integer, Purchase> purchases) throws ApplicationException {

        Map<Integer, Book> books = CustomerReader.readBooks();
        List<Entry<Integer, Purchase>> list = new LinkedList<Map.Entry<Integer, Purchase>>(purchases.entrySet());

        list.sort((Map.Entry<Integer, Purchase> purchase1, Map.Entry<Integer, Purchase> purchase2) -> (books.get(purchase1.getValue().getBook_ID()).getTitle()).compareTo(books.get(purchase2.getValue().getBook_ID()).getTitle()));
        Collections.reverse(list);
        Map<Integer, Purchase> sortedBooks = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            sortedBooks.put(entry.getKey(), entry.getValue());
        }
        List<Entry<Integer, Purchase>> reversedList = new LinkedList<Map.Entry<Integer, Purchase>>(sortedBooks.entrySet());
        Collections.reverse(reversedList);
        Map<Integer, Purchase> reversedMap = new LinkedHashMap<Integer, Purchase>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Purchase> entry = list.get(i);
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        return reversedMap;
    }

    /**
     * Calculate total price of all books in a hashMap
     * 
     * @param purchases
     *            a HashMap of purchases
     * @return total
     *         value of all books as a double
     * @throws ApplicationException
     *             if not valid input
     */
    public double addTotalPrice(Map<Integer, Purchase> purchases) throws ApplicationException {

        List<Entry<Integer, Purchase>> list = new LinkedList<Map.Entry<Integer, Purchase>>(purchases.entrySet());
        double total = 0.0;

        for (int i = 0; i < list.size(); i++) {
            Double cost = list.get(i).getValue().getPrice();
            total = total + cost;
        }
        return total;
    }
}
