/**
 * Project: a01029917Comp2613Lab02
 * File: Validator.java
 * Date: Apr 17, 2018
 * Time: 5:35:27 PM
 */

package a01029917.data.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Validator {

    /**
     * default constructor
     */
    public Validator() {
    }

    /**
     * Validates that email address is an RFC 5322 Official Standard formatted email and that the inserted value is not null or blank
     * 
     * @param val
     *            the value to validate as a String
     * @return boolean true if email is valid
     */
    public static boolean validateEmail(final String val) {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        boolean success = true;

        if (val.equals(null) || (isWhiteSpace(val))) {
            success = false;
        } else {
            Matcher matcher = pattern.matcher(val);
            {
                if (!matcher.matches()) {
                    success = false;
                }
            }
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
