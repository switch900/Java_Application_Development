/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a01029917.data.Book;

/**
 * Sorts Books
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

public class SortBooks {

    public SortBooks() {
    }

    /**
     * Sort HashMap of Books by Author
     * 
     * @param books
     *            an unsorted HashMap of books
     * @return HashMap of sorted books
     * @throws ApplicationException
     *             if not valid input
     */
    public Map<Integer, Book> sortAuthor(Map<Integer, Book> books) throws ApplicationException {

        List<Entry<Integer, Book>> list = new LinkedList<Map.Entry<Integer, Book>>(books.entrySet());

        list.sort((Map.Entry<Integer, Book> book1, Map.Entry<Integer, Book> book2) -> book1.getValue().getAuthors().compareTo(book2.getValue().getAuthors()));

        Map<Integer, Book> sortedustomers = new LinkedHashMap<Integer, Book>();

        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Integer, Book> entry = list.get(i);
            sortedustomers.put(entry.getKey(), entry.getValue());
        }
        return sortedustomers;
    }
}
