/**
 * Project: a01029917Comp2613Lab02
 * File: CustomerReader.java
 * Date: Apr 17, 2018
 * Time: 5:34:13 PM
 */

package a01029917.io;

import a01029917.data.Customer;
import a01029917.data.util.Validator;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    // static int index = 0;
    // public static Customer[] customerArray = new Customer[1];

    /**
     * 
     */
    public CustomerReader() {
    }

    /**
     * Takes value to split. Creates an array of customers and outputs array to console with printToConsole Method.
     * 
     * @param stringToSplit
     *            string to split as a String
     * @return
     */
    public static Customer[] splitString(String stringToSplit) {

        String delimiter1 = ":";
        String[] row = stringToSplit.split(delimiter1);
        Customer[] customers = new Customer[row.length];
        int i = 0;
        long id;
        String firstName;
        String lastName;
        String street;
        String city;
        String postalCode;
        String phone;
        String emailAddress;

        for (int x = 0; x < row.length; x++) {
            String tempCustomer = row[x];
            String delimiter2 = "\\|";
            String[] temp = tempCustomer.split(delimiter2);
            int index = 0;
            id = Integer.parseInt(temp[index++]);
            firstName = temp[index++];
            lastName = temp[index++];
            street = temp[index++];
            city = temp[index++];
            postalCode = temp[index++];
            phone = temp[index++];
            emailAddress = temp[index++];
            if (!Validator.validateEmail(emailAddress)) {
                emailAddress = "N/A";
            }
            Customer customer = new Customer.Builder(id, phone).firstName(firstName).lastName(lastName).streetName(street).city(city).postalCode(postalCode).emailAddress(emailAddress).build();
            customers[i++] = customer;
        }
        return customers;
    }
}
