/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01029917.db.BooksDao;
import a01029917.db.CustomerDao;
import a01029917.db.Database;
import a01029917.db.PurchasesDao;
import a01029917.ui.MainFrame;

/**
 * Main class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Books2 {

    public static final Instant STARTTIME = Instant.now();
    public static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
    private static final String PLAYERS_DATA_FILENAME = "customers.dat";
    private static final String DB_PROPERTIES_FILENAME = "db.properties";

    static {
        configureLogging();
    }

    /**
     * new Log file
     */
    public static final Logger LOG = LogManager.getLogger(Books2.class);

    /**
     * Main method. Logs message and program start time then goes to the run method.
     * 
     * @param args
     *            The main method args
     * @throws ApplicationException
     *             If args are not valid
     * 
     */
    public static void main(String[] args) throws ApplicationException {

        LOG.info(String.format("%s%n%s", "Program Started", STARTTIME));

        File file = new File(PLAYERS_DATA_FILENAME);
        if (!file.exists()) {
            System.out.format("Required '%s' is missing.", PLAYERS_DATA_FILENAME);
            System.exit(-1);
        }
        new Books2().run();

    }

    /**
     * Connects to Databases. Loads Customers, Purchases and Books. Sets Nimbus Look and Feel.
     * Creates new Mainframe
     * 
     */
    private void run() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            LOG.info(info.getName() + " LookAndFeel set.");
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                    Database db = connect();

                    CustomerDao customerDao = loadCustomers(db);
                    PurchasesDao purchaseDao = loadPurchases(db);
                    BooksDao booksDao = loadBooks(db);

                    MainFrame frame = new MainFrame(customerDao, booksDao, purchaseDao);
                    frame.setVisible(true);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }
        });
    }

    /**
     * Finds program end time in ms and calculates duration that program ran for in ms.
     * 
     * @return
     *         calculateEndTimeAndDuration as a String
     */
    public static String calculateEndTimeAndDuration() {
        Instant endTime = Instant.now();
        return String.format("%s%nDuration: %d ms", endTime, Duration.between(STARTTIME, endTime).toMillis());
    }

    /**
     * Configures LOG4J logging file
     */
    public static void configureLogging() {

        ConfigurationSource source;
        try {
            source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
            Configurator.initialize(null, source);
            Configurator.setRootLevel(Level.DEBUG);
        } catch (IOException e) {
            LOG.error(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
        }
    }

    /**
     * Creates new CustomerDao from Database
     * 
     * @param db
     *            as Database
     * @return
     *         customerDao as CustomerDao
     * @throws ApplicationException
     *             if input invalid
     */
    private CustomerDao loadCustomers(Database db) throws ApplicationException {
        CustomerDao customerDao = new CustomerDao(db);
        return customerDao;
    }

    /**
     * Creates new PurchasesDao from Database
     * 
     * @param db
     *            as Database
     * @return
     *         purchasesDao as PurchasesDao
     * @throws ApplicationException
     *             if input invalid
     */
    private PurchasesDao loadPurchases(Database db) throws ApplicationException {
        PurchasesDao purchasesDao = new PurchasesDao(db);
        return purchasesDao;
    }

    /**
     * Creates new BooksDao from Database
     * 
     * @param db
     *            as Database
     * @return
     *         booksDao as BooksDao
     * @throws ApplicationException
     *             if input invalid
     */
    private BooksDao loadBooks(Database db) throws ApplicationException, IOException {
        BooksDao booksDao = new BooksDao(db);
        return booksDao;
    }

    /**
     * Connects to Database
     * 
     * @return
     *         db as Database
     * @throws IOException
     *             if IO not valid
     * @throws SQLException
     *             if SQL data not valid
     * @throws ApplicationException
     *             if Application information not valid
     */
    private Database connect() throws IOException, SQLException, ApplicationException {
        Properties dbProperties = new Properties();
        dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
        Database db = new Database(dbProperties);
        return db;
    }
}