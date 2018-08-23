/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917.data.util;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String YYYYMMDD_PATTERN = "(20\\d{2})(\\d{2})(\\d{2})";

    static int EXPECTED_AMOUNT_ELEMENTS = 9;
    static int OLDEST_YEAR = 1000;

    /**
     * default constructor
     */
    public Validator() {
    }

    public static boolean validateEmail(final String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean validateJoinedDate(String yyyymmdd) {
        return yyyymmdd.matches(YYYYMMDD_PATTERN);
    }
}