/**
 * Project: a01029917Comp2613Lab04
 * File: Validator.java
 * Date: May 1, 2018
 * Time: 5:35:27 PM
 */

package a01029917.data.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Validator {

    private static final String YYYYMMDD_PATTERN = "(20\\d{2})(\\d{2})(\\d{2})";
    static int EXPECTED_AMOUNT_ELEMENTS = 9;
    static int OLDEST_YEAR = 1000;

    /**
     * default constructor
     */
    public Validator() {
    }

    /**
     * Validates that email address is a good email and that the inserted value is not null or blank
     * 
     * @param emailAddress
     *            the emailAddress as a String to Validate
     * @return email as String if valid
     * @throws ApplicationException
     */
    public static String validateEmail(final String emailAddress) throws ApplicationException {
        String goodEmailAddress = emailAddress;
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (emailAddress.equals(null) || (isWhiteSpace(emailAddress))) {
            goodEmailAddress = "N/A";
            throw new ApplicationException(String.format("%s %s", "Invalid email:", emailAddress));
        } else {
            Matcher matcher = pattern.matcher(emailAddress);
            {
                if (!matcher.matches()) {
                    goodEmailAddress = "N/A";
                    throw new ApplicationException(String.format("%s %s", "Invalid email:", emailAddress));
                }
            }
        }
        return goodEmailAddress;
    }

    /**
     * Verifies that the join date is a real and good date
     * 
     * @param joinDate
     *            The join date as a String to validate
     * @return joinDate as a LocalDate if valid
     * @throws ApplicationException
     */
    public static LocalDate validDate(final String joinDate) throws ApplicationException {
        LocalDate localDate = null;
        if (!joinDate.matches(YYYYMMDD_PATTERN)) {
            throw new ApplicationException(String.format("Invalid joined date: %s", joinDate));
        }
        try {
            localDate = LocalDate.parse(joinDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            throw new ApplicationException(String.format("Invalid joined date: %s", joinDate));
        }
        return localDate;
    }

    /**
     * Validates that all items in the Array contain the correct amount of elements
     * 
     * @param customers
     *            The Array of customer's information to validate
     * @return a boolean
     *         True if there are the correct amount of elements in the array of customer's information.
     * @throws ApplicationException
     *             If the amount of elements is less than expected
     */
    public static boolean isExpectedAmountOfElements(String[] customers) throws ApplicationException {
        boolean success = true;
        if (customers.length < EXPECTED_AMOUNT_ELEMENTS) {
            throw new ApplicationException(String.format("%s %s %s %s", "Expected " + EXPECTED_AMOUNT_ELEMENTS + " elements but got", customers.length, ": ", Arrays.toString(customers)));
        }
        return success;
    }

    /**
     * Validates that inserted value is not blank
     * 
     * @param val
     * @return boolean true if value is not blank
     */
    private static boolean isWhiteSpace(String val) {
        boolean success = true;
        String trimmedString = val.trim();
        int length = trimmedString.length();

        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(trimmedString.charAt(i))) {
                    success = false;
                }
            }
        }
        return success;
    }
}
