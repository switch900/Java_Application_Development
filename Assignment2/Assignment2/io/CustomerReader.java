/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.ApplicationException;
import a01029917.data.books.Book;
import a01029917.data.customer.Customer;
import a01029917.data.purchases.Purchase;
import a01029917.data.util.Validator;
import a01029917.db.BooksDao;
import a01029917.db.CustomerDao;
import a01029917.db.PurchasesDao;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class CustomerReader {

    public static final String DELIMITER1 = ",";
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
     * @param customerDao
     *            the CustomerDao to read from
     * @param customerDataFile
     *            the File to read
     * 
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     */
    public static void readCustomers(File customerDataFile, CustomerDao customerDao) throws ApplicationException {

        BufferedReader customerReader = null;
        String line = null;
        try {

            File file = new File(IN_FILENAME);
            customerReader = new BufferedReader(new FileReader(file));
            customerReader.readLine();
            while ((line = customerReader.readLine()) != null) {
                Customer customer = readCustomerString(line);
                try {
                    customerDao.add(customer);
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
     * Read the customer input data.
     * 
     * @param purchasesDao
     *            the PurchasesDao to read from
     * @param purchaseDataFile
     *            the File to read
     * 
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     * @throws SQLException
     *             if SQL data in invalid
     */
    public static void readPurchases(File purchaseDataFile, PurchasesDao purchasesDao) throws ApplicationException, SQLException {

        int purchase_ID = 0;
        int customer_ID = 0;
        int book_ID = 0;
        float price = 0;

        File file = new File(IN_PURCHASEFILENAME);
        FileReader in;
        Iterable<CSVRecord> records;
        try {
            in = new FileReader(file);
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        LOG_READER.debug("Reading" + file.getAbsolutePath());
        for (CSVRecord record : records) {
            purchase_ID = Integer.parseInt(record.get("id"));
            customer_ID = Integer.parseInt(record.get("customer_id"));
            book_ID = Integer.parseInt(record.get("book_id"));
            price = Float.parseFloat(record.get("price"));
            Purchase purchase = new Purchase.Builder(purchase_ID).customer_ID(customer_ID).book_ID(book_ID).price(price).build();
            purchasesDao.add(purchase);
        }
    }

    /**
     * Read the customer input data.
     * 
     * @param bookDataFile
     *            The File of Books to read
     * @param booksDao
     *            The BooksDao to use
     * @throws ApplicationException
     *             if input data is incorrect throws ApplicationException
     * @throws SQLException
     *             if SQL Data invalid
     */
    public static void readBooks(File bookDataFile, BooksDao booksDao) throws ApplicationException, SQLException {

        int bookID = 0;
        String iSBN = null;
        String authors = null;
        int year = 0;
        String title = null;
        float ratings = 0;
        int ratingsCount = 0;
        String imageURL = null;

        File file = new File(IN_BOOKFILENAME);
        FileReader in;
        Iterable<CSVRecord> records;
        try {
            in = new FileReader(file);
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        LOG_READER.debug("Reading" + file.getAbsolutePath());
        for (CSVRecord record : records) {
            bookID = Integer.parseInt(record.get("book_id"));
            iSBN = record.get("isbn");
            authors = record.get("authors");
            year = Integer.parseInt(record.get("original_publication_year"));
            title = record.get("original_title");
            ratings = Float.parseFloat(record.get("average_rating"));
            ratingsCount = Integer.parseInt(record.get("ratings_count"));
            imageURL = record.get("image_url");
            Book books = new Book.Builder(bookID, iSBN).authors(authors).title(title).year(year).ratings(ratings).ratingsCount(ratingsCount).imageURL(imageURL).build();
            booksDao.add(books);
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

    // /**
    // * Read the purchases input data and return a hashMap of purchases that match customerID number.
    // *
    // * @param customerIDString
    // * customerID number as a String from input arg
    // * @return
    // * A HashMap of purchases which contain customerID number.
    // *
    // * @throws ApplicationException
    // * if input data is incorrect throws ApplicationException
    // */
    // public static Map<Integer, Purchase> readPurchasesByCustomerID(String customerIDString) throws ApplicationException {
    //
    // File file = new File(IN_PURCHASEFILENAME);
    // FileReader in;
    // Iterable<CSVRecord> records;
    // try {
    // in = new FileReader(file);
    // records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
    // } catch (IOException e) {
    // throw new ApplicationException(e);
    // }
    //
    // Map<Integer, Purchase> purchases = new HashMap<>();
    //
    // LOG_READER.debug("Reading" + file.getAbsolutePath());
    // for (CSVRecord record : records) {
    // int purchase_ID = Integer.parseInt(record.get("id"));
    // long customer_ID = Long.parseLong(record.get("customer_id"));
    // int book_ID = Integer.parseInt(record.get("book_id"));
    // double price = Double.parseDouble(record.get("price"));
    //
    // Long stringLong = Long.parseLong(customerIDString);
    //
    // if (stringLong == customer_ID) {
    // purchases.put(purchase_ID, new Purchase.Builder(purchase_ID).customer_ID(customer_ID).book_ID(book_ID).price(price).build());
    // }
    // }
    // return purchases;
    // }
    //
    // /**
    // * Read the customer input data.
    // *
    // * @return
    // * A HashMap of books.
    // *
    // * @throws ApplicationException
    // * if input data is incorrect throws ApplicationException
    // */
    // public static Map<Integer, Book> readBooks() throws ApplicationException {
    //
    // File file = new File(IN_BOOKFILENAME);
    // FileReader in;
    // Iterable<CSVRecord> records;
    // try {
    // in = new FileReader(file);
    // records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
    // } catch (IOException e) {
    // throw new ApplicationException(e);
    // }
    //
    // Map<Integer, Book> books = new HashMap<>();
    //
    // LOG_READER.debug("Reading" + file.getAbsolutePath());
    // for (CSVRecord record : records) {
    // int bookID = Integer.parseInt(record.get("book_id"));
    // String iSBN = record.get("isbn");
    // String authors = record.get("authors");
    // int year = Integer.parseInt(record.get("original_publication_year"));
    // String title = record.get("original_title");
    // float ratings = Float.parseFloat(record.get("average_rating"));
    // int ratingsCount = Integer.parseInt(record.get("ratings_count"));
    // String imageURL = record.get("image_url");
    //
    // books.put(bookID, new Book.Builder(bookID, iSBN).authors(authors).title(title).year(year).ratings(ratings).ratingsCount(ratingsCount).imageURL(imageURL).build());
    // }
    // return books;
    // }
}
