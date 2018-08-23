/**
 * Project: a01029917Comp2613Lab04
 * File: CustomerReader.java
 * Date: May 1, 2018
 * Time: 5:34:13 PM
 */

package a01029917.io;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.Validator;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    /**
     * default constructor
     */
    public CustomerReader() {
    }

    /**
     * Takes value to split. Creates a HashMap of customers where the id is the key.
     * 
     * @param stringToSplit
     *            string to split as a String
     * @return
     *         customers as a HashMap
     * @throws ApplicationException
     * @throws NumberFormatException
     */
    public static Map<Long, Customer> splitString(String stringToSplit) throws ApplicationException {

        String delimiter1 = ":";
        String[] row = stringToSplit.split(delimiter1);

        Map<Long, Customer> customers = new HashMap<>();

        for (int x = 0; x < row.length; x++) {
            String tempCustomer = row[x];
            String delimiter2 = "\\|";
            String[] temp = tempCustomer.split(delimiter2);
            int index = 0;

            if (Validator.isExpectedAmountOfElements(temp)) {
                long id = Integer.parseInt(temp[index++]);
                String firstName = temp[index++];
                String lastName = temp[index++];
                String street = temp[index++];
                String city = temp[index++];
                String postalCode = temp[index++];
                String phone = temp[index++];
                String emailAddress = Validator.validateEmail(temp[index++]);
                LocalDate joinDate = Validator.validDate(temp[index++]);

                customers.put(id, new Customer.Builder(id, phone).firstName(firstName).lastName(lastName).streetName(street).city(city).postalCode(postalCode).emailAddress(emailAddress).joinDate(joinDate).build());
            }
        }
        return customers;
    }
}
