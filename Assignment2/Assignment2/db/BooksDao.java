/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.ApplicationException;
import a01029917.data.books.Book;
import a01029917.io.CustomerReader;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

public class BooksDao extends Dao {

    public static final String TABLE_NAME = DbConstants.TABLE_ROOT + "Assignment2_Books";
    private static final Logger LOG = LogManager.getLogger();
    public static final String IN_BOOKFILENAME = "books500.csv";

    public BooksDao(Database database) throws ApplicationException, IOException {
        super(database, TABLE_NAME);
        load();
    }

    /**
     * 
     * @throws ApplicationException
     *             if invalid Application
     * @throws IOException
     *             if input data invalid
     */
    public void load() throws ApplicationException, IOException {
        File booksDataFile = new File(IN_BOOKFILENAME);
        try {
            if (Database.tableExists(BooksDao.TABLE_NAME)) {
                drop();
            }

            create();

            LOG.debug("Inserting the books");

            if (!booksDataFile.exists()) {
                throw new ApplicationException(String.format("Required '%s' is missing.", IN_BOOKFILENAME));
            }
            CustomerReader.readBooks(booksDataFile, this);
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
                + "%s BIGINT, " // BOOK_ID
                + "%s VARCHAR(%d), " // ISBN
                + "%s VARCHAR(%d), " // AUTHORS
                + "%s BIGINT, " // YEAR
                + "%s VARCHAR(%d), " // TITLE
                + "%s FLOAT, " // RATINGS
                + "%s BIGINT, " // RATINGS_COUNT
                + "%s VARCHAR(%d), " // IMAGE_URL
                + "PRIMARY KEY (%s))", // ID
                TABLE_NAME, // 1

                Fields.BOOK_ID.name, // 2
                Fields.ISBN.name, Fields.ISBN.length, // 3
                Fields.AUTHORS.name, Fields.AUTHORS.length, // 4
                Fields.ORIGINAL_PUBLICATION_YEAR.name, //
                Fields.ORIGINAL_TITLE.name, Fields.ORIGINAL_TITLE.length, //
                Fields.AVERAGE_RATING.name, //
                Fields.RATINGS_COUNT.name, //
                Fields.IMAGE_URL.name, Fields.IMAGE_URL.length, //
                Fields.BOOK_ID.name); // 1
        super.create(sql);
    }

    /*
     * Adds a new book to a SQL Table
     * 
     * @param book
     * A book to add to the table
     */
    public void add(Book book) throws SQLException {
        String sqlString = String.format("Insert into %s values(?,?,?,?,?,?,?,?)", TABLE_NAME);
        boolean result = execute(sqlString, //
                book.getBookID(), //
                book.getiSBN(), //
                book.getAuthors(), //
                book.getYear(), //
                book.getTitle(), //
                book.getRatings(), //
                book.getRatingsCount(), //
                book.getImageURL());
        LOG.debug(String.format("Adding %s was %s", book, result ? "Unsuccessful" : "Successful"));
    }

    /**
     * Get a book from the SQL table according their bookId
     * 
     * @param bookId
     *            the book ID number to search by
     * @return
     *         book that matches bookId
     * @throws SQLException
     *             If not valid SQL table found
     * @throws Exception
     *             If invalid information is entered
     */
    public Book getBook(int bookId) throws SQLException, Exception {
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
                Book books = new Book.Builder(resultSet.getInt(Fields.BOOK_ID.name), resultSet.getString(Fields.ISBN.name)).//
                        authors(resultSet.getString(Fields.AUTHORS.name)).title(resultSet.getString(Fields.ORIGINAL_TITLE.name)).//
                        year(resultSet.getInt(Fields.ORIGINAL_PUBLICATION_YEAR.name)).ratings(resultSet.getFloat(Fields.AVERAGE_RATING.name)).//
                        ratingsCount(resultSet.getInt(Fields.RATINGS_COUNT.name)).imageURL(resultSet.getString(Fields.IMAGE_URL.name)).build();
                return books;
            }
        } finally {
            close(statement);
        }

        return null;
    }

    /**
     * Count all books in database
     * 
     * @return
     *         amount of books as an int
     * @throws Exception
     *             if data is invalid
     */
    public int countAllbooks() throws Exception {
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

    /**
     * Retrieve all the book IDs from the database
     * 
     * @return
     *         the list of book IDs as a List
     * @throws SQLException
     *             if SQL data invalid
     */
    public List<Integer> getIds() throws SQLException {
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
                ids.add((int) resultSet.getLong(Fields.BOOK_ID.name));
            }

        } finally {
            close(statement);
        }

        LOG.debug(String.format("Loaded %d book IDs from the database", ids.size()));

        return ids;
    }

    /**
     * Update information in the table
     * 
     * @param book
     *            The book to be updated
     * @throws SQLException
     *             If SQL data is invalid
     */
    public void update(Book book) throws SQLException {
        String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?,  WHERE %s=?", TABLE_NAME, //
                Fields.ISBN.name, Fields.AUTHORS.name, Fields.ORIGINAL_PUBLICATION_YEAR.name, Fields.ORIGINAL_TITLE.name, Fields.AVERAGE_RATING.name, Fields.RATINGS_COUNT.name, Fields.IMAGE_URL.name, Fields.BOOK_ID);

        LOG.debug("Update Statement: " + sqlString);

        boolean result = execute(sqlString, book.getBookID(), sqlString, book.getiSBN(), book.getAuthors(), book.getYear(), book.getTitle(), book.getRatings(), book.getRatingsCount(), book.getImageURL());
        LOG.debug(String.format("Updating %s was %s", book, result ? "successful" : "unsuccessful"));
    }

    /**
     * Delete a book from the table
     * 
     * @param book
     *            A book to delete
     * @throws SQLException
     *             If SQL Data is invalid
     */
    public void delete(Book book) throws SQLException {
        Connection connection;
        Statement statement = null;
        try {
            connection = Database.getConnection();
            statement = connection.createStatement();

            String sql = String.format("DELETE from %s WHERE %s='%s'", TABLE_NAME, Fields.BOOK_ID.name, book.getBookID());
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

        BOOK_ID("book_ID", 40), //
        ISBN("isbn", 40), //
        AUTHORS("authors", 80), //
        ORIGINAL_PUBLICATION_YEAR("original_publication_year", 40), //
        ORIGINAL_TITLE("original_title", 120), //
        AVERAGE_RATING("average_rating", 40), //
        RATINGS_COUNT("ratings_count", 40), //
        IMAGE_URL("image_url", 120); //

        String name;
        int length;

        private Fields(String name, int length) {
            this.name = name;
            this.length = length;
        }
    }

}
