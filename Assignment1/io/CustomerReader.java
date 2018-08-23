/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.data.Book;
import a01029917.data.Customer;
import a01029917.data.Purchase;
import a01029917.data.util.ApplicationException;
import a01029917.data.util.Validator;

/**
 * Reads input files
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    public static final String DELIMITER1 = ":";
    public static final String DELIMITER2 = "\\|";
    public static final String IN_FILENAME = "customers.dat";
    public static final String IN_BOOKFILENAME = "books500.csv";
    public static final String IN_PURCHASEFILENAME = "purchases.csv";

    public static final Logger LOG_READER = LogManager.getLogger(CustomerReader.class);

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
    public static ArrayList<String> readCustomers() throws ApplicationException {

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
            LOG_READER.error(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                LOG_READER.error(e.getMessage());
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
    public static Map<Long, Customer> customerSplitString() throws ApplicationException {
        ArrayList<String> stringToSplit = readCustomers();
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

    /**
     * Read the purchases input data.
     * 
     * @return
     *         the purchases in a HashMap
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static Map<Integer, Purchase> readPurchases() throws ApplicationException {

        File file = new File(IN_PURCHASEFILENAME);
        FileReader in;
        Iterable<CSVRecord> records;
        try {
            in = new FileReader(file);
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        Map<Integer, Purchase> purchases = new HashMap<>();

        LOG_READER.debug("Reading" + file.getAbsolutePath());
        for (CSVRecord record : records) {
            int purchase_ID = Integer.parseInt(record.get("id"));
            long customer_ID = Long.parseLong(record.get("customer_id"));
            int book_ID = Integer.parseInt(record.get("book_id"));
            double price = Double.parseDouble(record.get("price"));

            purchases.put(purchase_ID, new Purchase.Builder(purchase_ID).customer_ID(customer_ID).book_ID(book_ID).price(price).build());
        }
        return purchases;
    }

    /**
     * Read the purchases input data and return a hashMap of purchases that match customerID number.
     * 
     * @param customerIDString
     *            customerID number as a String from input arg
     * @return
     *         A HashMap of purchases which contain customerID number.
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static Map<Integer, Purchase> readPurchasesByCustomerID(String customerIDString) throws ApplicationException {

        File file = new File(IN_PURCHASEFILENAME);
        FileReader in;
        Iterable<CSVRecord> records;
        try {
            in = new FileReader(file);
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        Map<Integer, Purchase> purchases = new HashMap<>();

        LOG_READER.debug("Reading" + file.getAbsolutePath());
        for (CSVRecord record : records) {
            int purchase_ID = Integer.parseInt(record.get("id"));
            long customer_ID = Long.parseLong(record.get("customer_id"));
            int book_ID = Integer.parseInt(record.get("book_id"));
            double price = Double.parseDouble(record.get("price"));

            Long stringLong = Long.parseLong(customerIDString);

            if (stringLong == customer_ID) {
                purchases.put(purchase_ID, new Purchase.Builder(purchase_ID).customer_ID(customer_ID).book_ID(book_ID).price(price).build());
            }
        }
        return purchases;
    }

    /**
     * Read the customer input data.
     * 
     * @return
     *         A HashMap of books.
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static Map<Integer, Book> readBooks() throws ApplicationException {

        File file = new File(IN_BOOKFILENAME);
        FileReader in;
        Iterable<CSVRecord> records;
        try {
            in = new FileReader(file);
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        Map<Integer, Book> books = new HashMap<>();

        LOG_READER.debug("Reading" + file.getAbsolutePath());
        for (CSVRecord record : records) {
            int bookID = Integer.parseInt(record.get("book_id"));
            String iSBN = record.get("isbn");
            String authors = record.get("authors");
            int year = Integer.parseInt(record.get("original_publication_year"));
            String title = record.get("original_title");
            float ratings = Float.parseFloat(record.get("average_rating"));
            int ratingsCount = Integer.parseInt(record.get("ratings_count"));
            String imageURL = record.get("image_url");

            books.put(bookID, new Book.Builder(bookID, iSBN).authors(authors).title(title).year(year).ratings(ratings).ratingsCount(ratingsCount).imageURL(imageURL).build());
        }
        return books;
    }
}
