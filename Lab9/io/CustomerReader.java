/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.CustomerDao;
import a01029917.data.util.Validator;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    public static final String DELIMITER1 = ":";
    public static final String DELIMITER2 = "\\|";
    public static final String IN_FILENAME = "customers.txt";

    public static final Logger LOG_READER = LogManager.getLogger(CustomerReader.class);

    /**
     * private constructor to prevent instantiation
     */
    public CustomerReader() {
    }

    /**
     * Read the customer input data.
     * 
     * @param customerDao
     *            the CustomerDao to read from
     * @param customerDataFile
     *            the File to read
     * 
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static void read(File customerDataFile, CustomerDao dao) throws ApplicationException {

        BufferedReader customerReader = null;
        String line = null;
        try {

            File file = new File(IN_FILENAME);
            customerReader = new BufferedReader(new FileReader(file));
            customerReader.readLine();
            while ((line = customerReader.readLine()) != null) {
                Customer customer = readCustomerString(line);
                try {
                    dao.add(customer);
                } catch (SQLException e) {
                    throw new ApplicationException(e);
                }
            }
        } catch (IOException e) {
            throw new ApplicationException(e.getMessage());
        } finally {
            try {
                if (customerReader != null) {
                    customerReader.close();
                }
            } catch (IOException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    /**
     * Splits the input text and creates Customer
     * 
     * @return
     *         customer as a Customer
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    private static Customer readCustomerString(String data) throws ApplicationException {
        String[] elements = data.split(DELIMITER2);
        if (elements.length != Customer.ATTRIBUTE_COUNT) {
            throw new ApplicationException(String.format("Expected %d but got %d: %s", Customer.ATTRIBUTE_COUNT, elements.length, Arrays.toString(elements)));
        }

        int index = 0;
        long id = Integer.parseInt(elements[index++]);
        String firstName = elements[index++];
        String lastName = elements[index++];
        String street = elements[index++];
        String city = elements[index++];
        String postalCode = elements[index++];
        String phone = elements[index++];
        // should the email validation be performed here or in the customer class? I'm leaning towards putting it here.
        String emailAddress = elements[index++];
        if (!Validator.validateEmail(emailAddress)) {
            throw new ApplicationException(String.format("Invalid email: %s", emailAddress));
        }
        String yyyymmdd = elements[index];
        if (!Validator.validateJoinedDate(yyyymmdd)) {
            throw new ApplicationException(String.format("Invalid joined date: %s for customer %d", yyyymmdd, id));
        }
        int year = Integer.parseInt(yyyymmdd.substring(0, 4));
        int month = Integer.parseInt(yyyymmdd.substring(4, 6));
        int day = Integer.parseInt(yyyymmdd.substring(6, 8));

        return new Customer.Builder(id, phone).//
                firstName(firstName).//
                lastName(lastName).//
                streetName(street).//
                city(city).//
                postalCode(postalCode).//
                emailAddress(emailAddress).//
                joinDate(year, month, day).build();
    }

}
