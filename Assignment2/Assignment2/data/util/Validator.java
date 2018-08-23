/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
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

    /**
     * Validate email input
     * 
     * @param email
     *            The email Address to validate
     * @return
     *         True as boolean if valid
     */
    public static boolean validateEmail(final String email) {
        return email.matches(EMAIL_PATTERN);
    }

    /**
     * Validate joined date
     * 
     * @param yyyymmdd
     *            The joined date as a String of yyymmdd
     * @return
     *         True as a boolean if valid
     */
    public static boolean validateJoinedDate(String yyyymmdd) {
        return yyyymmdd.matches(YYYYMMDD_PATTERN);
    }
}