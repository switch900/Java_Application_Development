/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data.util;

import java.time.format.DateTimeFormatter;

/**
 * Common class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Common {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * If the input string exceeds the length then truncate the string to the length - 3 characters and add "..."
     * 
     * @param title
     *            the title of the book to be truncated
     * @param length
     *            the length of the String to be truncated
     * @return a string
     *         the truncated title as a String
     */
    public static String truncateIfRequired(String title, int length) {
        if (title.length() > length) {
            title = title.substring(0, length - 3) + "...";
        }
        return title;
    }
}
