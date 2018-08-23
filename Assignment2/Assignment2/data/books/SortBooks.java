/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data.books;

import java.util.Comparator;

/**
 * Sorts Books
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

public class SortBooks {

    public SortBooks() {
    }

    public static class CompareByAuthors implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getAuthors().compareTo(book2.getAuthors());
        }
    }
}
