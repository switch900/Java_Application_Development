/**
 * Project: a01029917Comp2613Lab02
 * File: Validator.java
 * Date: Apr 17, 2018
 * Time: 5:35:27 PM
 */

package a01029917.data.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Validator {

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
     * Verifies that the join date is a real date
     * 
     * @param joinDate
     *            The join date as a String to validate
     * @return joinDate as a formatted String if valid
     * @throws ApplicationException
     */
    public static String validDate(final String joinDate) throws ApplicationException {
        LocalDate formattedDate;
        String joinDateRegex = "(?<!\\d)\\d{8}+(?!\\d)";
        Pattern pattern = Pattern.compile(joinDateRegex);
        Matcher matcher = pattern.matcher(joinDate);
        {
            if (!matcher.matches()) {
                throw new ApplicationException(String.format("%s %s", "Invalid value for date (date must consist of 8 integers): ", joinDate));
            } else {

                String year = joinDate.substring(0, 4);
                int intYear = Integer.parseInt(year);

                String month = joinDate.substring(4, 6);
                int intMonth = Integer.parseInt(month);

                String day = joinDate.substring(6, 8);
                int intDay = Integer.parseInt(day);

                if (joinDate.equals(null) || (isWhiteSpace(joinDate))) {
                    throw new ApplicationException(String.format("%s %s", "Invalid value for date: ", joinDate));
                } else if (intYear > LocalDate.now().getYear() || intYear < OLDEST_YEAR) {
                    throw new ApplicationException(String.format("%s %s", "Invalid value for Year (valid valid values " + OLDEST_YEAR + " - Present Year): ", intYear));
                } else if (intMonth < 1 || intMonth > 12) {
                    throw new ApplicationException(String.format("%s %s", "Invalid value for Month (valid values 1-12): ", intMonth));
                } else if (intDay < 1 || intDay > 31) {
                    throw new ApplicationException(String.format("%s %s", "Invalid value for DayOfMonth (valid values 1-28/31): ", intDay));
                }
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        try {
            formattedDate = LocalDate.parse(joinDate, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new ApplicationException(String.format("%s %s", "Invalid value for date: ", joinDate));
        }
        return formattedDate.format(formatter);
    }

    /**
     * Validates that all items in the Array contain the correct amount of elements
     * 
     * @param customers
     *            The Array of customers to validate
     * @return a boolean
     *         True if there are the correct amount of elements in all items in the Array.
     * @throws ApplicationException
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
