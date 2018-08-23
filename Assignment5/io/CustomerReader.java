/**
 * Project: a01029917Comp2613Lab05
 * 
 * File: Lab05.java
 * Date: May 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import a01029917.Lab05;
import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.Validator;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    public static final String DELIMITER1 = ":";
    public static final String DELIMITER2 = "\\|";
    public static final String IN_FILENAME = "customers.txt";

    /**
     * private constructor to prevent instantiation
     */
    public CustomerReader() {
    }

    /**
     * Read the customer input data.
     * 
     * @return An array of customers.
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static ArrayList<String> read() throws ApplicationException {

        ArrayList<String> customerArrayList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(IN_FILENAME);
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.readLine();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                customerArrayList.add(line);
            }
        } catch (IOException e) {
            Lab05.LOG.error(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Lab05.LOG.error(e.getMessage());
            }
        }
        return customerArrayList;
    }

    /**
     * Splits the input text and put it into a HashMap of customer values
     * 
     * @return
     *         the customers in a HashMap
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static Map<Long, Customer> splitString() throws ApplicationException {
        ArrayList<String> stringToSplit = read();
        Map<Long, Customer> customers = new HashMap<>();

        for (String splitIndex : stringToSplit) {
            String[] row = splitIndex.split(DELIMITER1);

            for (int x = 0; x < row.length; x++) {
                String tempCustomer = row[x];
                String[] temp = tempCustomer.split(DELIMITER2);

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
        }
        return customers;
    }
}
