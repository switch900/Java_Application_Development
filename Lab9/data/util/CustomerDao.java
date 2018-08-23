/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */
package a01029917.data.util;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.data.Customer;
import a01029917.io.CustomerReader;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

public class CustomerDao extends Dao {

    public static final String TABLE_NAME = DbConstants.TABLE_ROOT + "Customers";
    private static final Logger LOG = LogManager.getLogger();
    private static final String CUSTOMERS_DATA_FILENAME = "customers.txt";

    public CustomerDao(Database database) throws ApplicationException {
        super(database, TABLE_NAME);
        load();
    }

    /**
     * 
     * @throws ApplicationException
     *             if invalid Application
     */
    public void load() throws ApplicationException {
        File customerDataFile = new File(CUSTOMERS_DATA_FILENAME);
        try {
            if (!Database.tableExists(CustomerDao.TABLE_NAME) || Database.dbTableDropRequested()) {
                if (Database.tableExists(CustomerDao.TABLE_NAME) && Database.dbTableDropRequested()) {
                    drop();
                }

                create();

                LOG.debug("Inserting the customers");

                if (!customerDataFile.exists()) {
                    throw new ApplicationException(String.format("Required '%s' is missing.", CUSTOMERS_DATA_FILENAME));
                }

                CustomerReader.read(customerDataFile, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    /**
     * Creates a new SQL Table
     */

    @Override
    public void create() throws SQLException {
        LOG.debug("Creating database table " + TABLE_NAME);
        String sql = String.format("CREATE TABLE %s(" // 1
                + "%s BIGINT, " // ID
                + "%s VARCHAR(%d), " // FIRST_NAME
                + "%s VARCHAR(%d), " // LAST_NAME
                + "%s VARCHAR(%d), " // STREET
                + "%s VARCHAR(%d), " // CITY
                + "%s VARCHAR(%d), " // POSTAL_CODE
                + "%s VARCHAR(%d), " // PHONE
                + "%s VARCHAR(%d), " // EMAIL_ADDRESS
                + "%s DATETIME, " // JOINED_DATE
                + "PRIMARY KEY (%s))", // ID
                TABLE_NAME, // 1

                Fields.CUSTOMER_ID.name, // 2
                Fields.FIRST_NAME.name, Fields.FIRST_NAME.length, // 3
                Fields.LAST_NAME.name, Fields.LAST_NAME.length, // 4
                Fields.STREET.name, Fields.STREET.length, // 7
                Fields.CITY.name, Fields.CITY.length, // 8
                Fields.POSTAL_CODE.name, Fields.POSTAL_CODE.length, // 9
                Fields.PHONE.name, Fields.PHONE.length, // 5
                Fields.EMAIL_ADDRESS.name, Fields.EMAIL_ADDRESS.length, // 10
                Fields.JOINDATE.name, // 6
                Fields.CUSTOMER_ID.name); // 1
        super.create(sql);
    }

    /*
     * Adds a new customer to a SQL Table
     * 
     * @param customer
     * A customer to add to the table
     */
    public void add(Customer customer) throws SQLException {
        String sqlString = String.format("Insert into %s values(?,?,?,?,?,?,?,?,?)", TABLE_NAME);
        boolean result = execute(sqlString, //
                customer.getIdentifier(), // 2
                customer.getFirstName(), // 3
                customer.getLastName(), // 4
                customer.getStreetName(), // 5
                customer.getCity(), // 6
                customer.getPostalCode(), // 7
                customer.getPhoneNumber(), // 8
                customer.getEmailAddress(), // 9
                toTimestamp(customer.getJoinDate()));
        LOG.debug(String.format("Adding %s was %s", customer, result ? "successful" : "Unsuccessful"));
    }

    /**
     * Get a customer from the SQL table according their customerId
     * 
     * @param customerId
     *            the customer ID number to search by
     * @return
     *         Customer who matches CustomerId
     * @throws SQLException
     *             If not valid SQL table found
     * @throws Exception
     *             If invalid information is entered
     */
    public Customer getCustomer(Long customerId) throws SQLException, Exception {
        String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Fields.CUSTOMER_ID.name, customerId);
        LOG.debug(sqlString);

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);

            int count = 0;
            while (resultSet.next()) {
                count++;
                if (count > 1) {
                    throw new ApplicationException(String.format("Expected one result, got %d", count));
                }

                Timestamp timestamp = resultSet.getTimestamp(Fields.JOINDATE.name);
                LocalDate date = timestamp.toLocalDateTime().toLocalDate();

                Customer customer = new Customer.Builder(resultSet.getInt(Fields.CUSTOMER_ID.name), resultSet.getString(Fields.PHONE.name)) //
                        .firstName(resultSet.getString(Fields.FIRST_NAME.name)) //
                        .lastName(resultSet.getString(Fields.LAST_NAME.name)) //
                        .streetName(resultSet.getString(Fields.STREET.name)) //
                        .city(resultSet.getString(Fields.CITY.name)) //
                        .postalCode(resultSet.getString(Fields.POSTAL_CODE.name)) //
                        .emailAddress(resultSet.getString(Fields.EMAIL_ADDRESS.name)) //
                        .joinDate(date).build();

                return customer;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    /**
     * Retrieve all the customer IDs from the database
     * 
     * @return the list of customer IDs
     * @throws SQLException
     *             if SQL data invalid
     */
    public List<Long> getIds() throws SQLException {
        List<Long> ids = new ArrayList<>();

        String selectString = String.format("SELECT %s FROM %s", Fields.CUSTOMER_ID.name, TABLE_NAME);
        LOG.debug(selectString);

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectString);

            while (resultSet.next()) {
                ids.add(resultSet.getLong(Fields.CUSTOMER_ID.name));
            }

        } finally {
            close(statement);
        }

        LOG.debug(String.format("Loaded %d customer IDs from the database", ids.size()));

        return ids;
    }

    /**
     * Update information in the table
     * 
     * @param customer
     *            The Customer to be updated
     * @throws SQLException
     *             If SQL data is invalid
     */
    public void update(Customer customer) throws SQLException {
        String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, WHERE  %s=?", TABLE_NAME, //
                Fields.FIRST_NAME.name, //
                Fields.LAST_NAME.name, //
                Fields.STREET.name, //
                Fields.CITY.name, //
                Fields.POSTAL_CODE.name, //
                Fields.PHONE.name, //
                Fields.EMAIL_ADDRESS.name, //
                Fields.JOINDATE.name, //
                Fields.CUSTOMER_ID.name); //

        LOG.debug("Update Statement: " + sqlString);

        boolean result = execute(sqlString, customer.getIdentifier(), sqlString, customer.getFirstName(), customer.getLastName(), customer.getStreetName(), customer.getCity(), customer.getPostalCode(), toTimestamp(customer.getJoinDate()));
        LOG.debug(String.format("Updating %s was %s", customer, result ? "successful" : "unsuccessful"));
    }

    /**
     * Delete a customer from the table
     * 
     * @param Customer
     *            A Customer to delete
     * @throws SQLException
     *             If SQL Data is invalid
     */
    public void delete(Customer Customer) throws SQLException {
        Connection connection;
        Statement statement = null;
        try {
            connection = Database.getConnection();
            statement = connection.createStatement();

            String sql = String.format("DELETE from %s WHERE %s='%s'", TABLE_NAME, Fields.CUSTOMER_ID.name, Customer.getIdentifier());
            LOG.debug(sql);
            int rowcount = statement.executeUpdate(sql);
            System.out.println(String.format("Deleted %d rows", rowcount));
        } finally {
            close(statement);
        }
    }

    /**
     * A subclass of enum of SQL data
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public enum Fields {

        CUSTOMER_ID("identifier", 40), //
        FIRST_NAME("firstName", 40), //
        LAST_NAME("lastName", 40), //
        STREET("streetName", 40), //
        CITY("city", 40), //
        POSTAL_CODE("postalCode", 40), //
        PHONE("phonenumber", 40), //
        EMAIL_ADDRESS("emailAddress", 40), //
        JOINDATE("joinDate", 40); //

        String name;
        int length;

        private Fields(String name, int length) {
            this.name = name;
            this.length = length;
        }
    }

    public int countAllCustomers() throws Exception {
        Statement statement = null;
        int count = 0;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            String sqlString = String.format("SELECT COUNT(*) AS total FROM %s", TABLE_NAME);
            ResultSet resultSet = statement.executeQuery(sqlString);
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } finally {
            close(statement);
        }
        return count;
    }
}
