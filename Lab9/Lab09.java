/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01029917.data.util.ApplicationException;
import a01029917.data.util.CustomerDao;
import a01029917.data.util.Database;
import a01029917.ui.MainFrame;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab09 {

    public static final Instant STARTTIME = Instant.now();
    public static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
    public static final String DROP_OPTION = "-drop";
    private static final String PLAYERS_DATA_FILENAME = "customers.txt";
    private static final String DB_PROPERTIES_FILENAME = "db.properties";

    static {
        configureLogging();
    }

    /**
     * new Log file
     */
    public static final Logger LOG = LogManager.getLogger();

    /**
     * Main method. Implements Run method.
     * 
     * @param args
     *            The main method args
     * @throws IOException
     *             if Input/outpu not valid
     * @throws FileNotFoundException
     *             if Source file not found
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        LOG.info(String.format("%s%n%s", "Program Started", STARTTIME));

        if (args.length == 1 && args[0].equalsIgnoreCase(DROP_OPTION)) {
            Database.requestDbTableDrop();
        }
        File file = new File(PLAYERS_DATA_FILENAME);
        if (!file.exists()) {
            System.out.format("Required '%s' is missing.", PLAYERS_DATA_FILENAME);
            System.exit(-1);
        }
        new Lab09().run();
        LOG.info(calculateEndTimeAndDuration());
    }

    /**
     * Implements new MainFrame Method
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
                    MainFrame frame = new MainFrame(customerDao);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
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
        return String.format("%s%n%s%nDuration: %d ms", "Program Ended", endTime, Duration.between(STARTTIME, endTime).toMillis());
    }

    /**
     * Configures LOG4J logging file
     */
    public static void configureLogging() {

        ConfigurationSource source;
        try {
            source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
            Configurator.initialize(null, source);
        } catch (IOException e) {
            System.out.println(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
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
     * Connects to Databse
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