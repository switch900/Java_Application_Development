/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.db;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.ApplicationException;
import a01029917.data.purchases.Purchase;
import a01029917.io.CustomerReader;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

public class PurchasesDao extends Dao {

    public static final String TABLE_NAME = DbConstants.TABLE_ROOT + "Assignment2_Purchases";
    public static final String TABLE_NAME_BOOKS = DbConstants.TABLE_ROOT + "Assignment2_Books";
    public static final String TABLE_NAME_CUSTOMERS = DbConstants.TABLE_ROOT + "Assignment2_Customers";
    private static final Logger LOG = LogManager.getLogger();
    public static final String IN_PURCHASEFILENAME = "purchases.csv";

    public PurchasesDao(Database database) throws ApplicationException {
        super(database, TABLE_NAME);
        load();
    }

    /**
     * 
     * @throws ApplicationException
     *             if invalid Application
     */
    public void load() throws ApplicationException {
        File purchasesDataFile = new File(IN_PURCHASEFILENAME);
        try {
            if (Database.tableExists(PurchasesDao.TABLE_NAME)) {
                drop();
            }

            create();

            LOG.debug("Inserting the purchases");

            if (!purchasesDataFile.exists()) {
                throw new ApplicationException(String.format("Required '%s' is missing.", IN_PURCHASEFILENAME));
            }
            CustomerReader.readPurchases(purchasesDataFile, this);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
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
                + "%s BIGINT, " // CUSTOMER_ID
                + "%s BIGINT, " // BOOK_ID
                + "%s FLOAT, " // PRICE
                + "PRIMARY KEY (%s))", // ID
                TABLE_NAME, // 1

                Fields.PURCHASE_ID.name, // 2
                Fields.CUSTOMER_ID.name, // 3
                Fields.BOOK_ID.name, // 4
                Fields.PRICE, Fields.PURCHASE_ID.name); // 1
        super.create(sql);
    }

    /*
     * Adds a new purchase to a SQL Table
     * 
     * @param purchase
     * A purchase to add to the table
     */
    public void add(Purchase purchase) throws SQLException {
        String sqlString = String.format("Insert into %s values(?,?,?,?)", TABLE_NAME);
        boolean result = execute(sqlString, //
                purchase.getPurchase_ID(), // 2
                purchase.getCustomer_ID(), // 3
                purchase.getBook_ID(), // 4
                purchase.getPrice()); // 5
        LOG.debug(String.format("Adding %s was %s", purchase, result ? "Unsuccessful" : "Successful"));
    }

    /**
     * Get a purchase from the SQL table according their purchaseId
     * 
     * @param purchaseId
     *            the purchase ID number to search by
     * @return
     *         purchase who matches purchaseId
     * @throws SQLException
     *             If not valid SQL table found
     * @throws Exception
     *             If invalid information is entered
     */
    public Purchase getPurchase(int purchaseId) throws SQLException, Exception {
        String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Fields.PURCHASE_ID.name, purchaseId);
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
                Purchase purchases = new Purchase.Builder(resultSet.getInt(Fields.PURCHASE_ID.name)).customer_ID(resultSet.getLong(Fields.CUSTOMER_ID.name)).book_ID(resultSet.getInt(Fields.BOOK_ID.name)).price(resultSet.getDouble(Fields.PRICE.name)).build();
                return purchases;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    public Purchase join(int tempIds) throws SQLException, Exception {
        String sqlString = String.format("%s%s", "select * from A01029917_Assignment2_Purchases INNER JOIN A01029917_Assignment2_Books ON A01029917_Assignment2_Purchases.book_ID = A01029917_Assignment2_Books.book_ID WHERE customer_ID = ", tempIds);
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
                Purchase purchases = new Purchase.Builder(resultSet.getInt(Fields.PURCHASE_ID.name)).customer_ID(resultSet.getLong(Fields.CUSTOMER_ID.name)).book_ID(resultSet.getInt(Fields.BOOK_ID.name)).price(resultSet.getDouble(Fields.PRICE.name)).build();
                return purchases;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    public Purchase getPurchaseName(long customerId) throws SQLException, Exception {
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
                Purchase purchases = new Purchase.Builder(resultSet.getInt(Fields.PURCHASE_ID.name)).customer_ID(resultSet.getLong(Fields.CUSTOMER_ID.name)).book_ID(resultSet.getInt(Fields.BOOK_ID.name)).price(resultSet.getDouble(Fields.PRICE.name)).build();
                return purchases;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    public Purchase getPurchaseTitle(int bookId) throws SQLException, Exception {
        String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Fields.BOOK_ID.name, bookId);
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
                Purchase purchases = new Purchase.Builder(resultSet.getInt(Fields.PURCHASE_ID.name)).customer_ID(resultSet.getLong(Fields.CUSTOMER_ID.name)).book_ID(resultSet.getInt(Fields.BOOK_ID.name)).price(resultSet.getDouble(Fields.PRICE.name)).build();
                return purchases;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    /**
     * Retrieve all the purchase IDs from the database
     * 
     * @return the list of purchase IDs
     * @throws SQLException
     *             if SQL data invalid
     */
    public List<Integer> getIds() throws SQLException {
        List<Integer> ids = new ArrayList<>();

        String selectString = String.format("SELECT %s FROM %s", Fields.PURCHASE_ID.name, TABLE_NAME);
        LOG.debug(selectString);

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectString);

            while (resultSet.next()) {
                ids.add((int) resultSet.getLong(Fields.PURCHASE_ID.name));
            }

        } finally {
            close(statement);
        }

        LOG.debug(String.format("Loaded %d purchase IDs from the database", ids.size()));

        return ids;
    }

    /**
     * Retrieve all the purchase IDs from the database
     * 
     * @return the list of purchase IDs
     * @throws SQLException
     *             if SQL data invalid
     */
    public List<Integer> getBookIds() throws SQLException {
        List<Integer> ids = new ArrayList<>();

        String selectString = String.format("SELECT %s FROM %s", Fields.BOOK_ID.name, TABLE_NAME);
        LOG.debug(selectString);

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectString);

            while (resultSet.next()) {
                ids.add(resultSet.getInt(Fields.BOOK_ID.name));
            }

        } finally {
            close(statement);
        }

        LOG.debug(String.format("Loaded %d book IDs from the database", ids.size()));

        return ids;
    }

    public List<Long> getCustomerIds() throws SQLException {
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
     * @param purchase
     *            The Purchase to be updated
     * @throws SQLException
     *             If SQL data is invalid
     */
    public void update(Purchase purchase) throws SQLException {
        String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=? WHERE %s=?", TABLE_NAME, //
                Fields.CUSTOMER_ID, Fields.BOOK_ID, Fields.PURCHASE_ID);

        LOG.debug("Update Statement: " + sqlString);

        boolean result = execute(sqlString, purchase.getPurchase_ID(), sqlString, purchase.getCustomer_ID(), purchase.getBook_ID(), purchase.getPrice());
        LOG.debug(String.format("Updating %s was %s", purchase, result ? "successful" : "unsuccessful"));
    }

    /**
     * Delete a purchase from the table
     * 
     * @param purchase
     *            A Purchase to delete
     * @throws SQLException
     *             If SQL Data is invalid
     */
    public void delete(Purchase purchase) throws SQLException {
        Connection connection;
        Statement statement = null;
        try {
            connection = Database.getConnection();
            statement = connection.createStatement();

            String sql = String.format("DELETE from %s WHERE %s='%s'", TABLE_NAME, Fields.PURCHASE_ID.name, purchase.getPurchase_ID());
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

        PURCHASE_ID("purchase_ID", 40), //
        CUSTOMER_ID("customer_ID", 40), //
        BOOK_ID("book_ID", 40), //
        PRICE("PRICE", 40); //

        String name;
        int length;

        private Fields(String name, int length) {
            this.name = name;
            this.length = length;
        }
    }

    /**
     * Count all purchases in database.
     * 
     * @return
     *         amount of purchases as an int
     * @throws Exception
     *             if data is invalid
     */
    public double totalSumAllPurchases() throws Exception {
        Statement statement = null;
        double totalPrice = 0;
        try {
            Connection connection = Database.getConnection();
            statement = connection.createStatement();
            String sqlString = String.format("SELECT SUM(PRICE) AS total FROM %s", TABLE_NAME);
            ResultSet resultSet = statement.executeQuery(sqlString);
            if (resultSet.next()) {
                totalPrice = resultSet.getFloat("total");
            }
        } finally {
            close(statement);
        }
        return totalPrice;
    }

    /**
     * Count all purchases in database
     * 
     * @return
     *         amount of purchases as an int
     * @throws Exception
     *             if data is invalid
     */
    public int countAllPurchases() throws Exception {
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
