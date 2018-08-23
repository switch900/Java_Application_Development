/**
 * Project: a01029917Comp2613Lab05
 * 
 * File: Lab05.java
 * Date: May 9, 2018
 * Time: 5:33:18 PM
 */

package a01029917;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01029917.data.util.ApplicationException;
import a01029917.io.CustomerReader;
import a01029917.io.CustomerReport;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab05 {

    public static final Instant STARTTIME = Instant.now();
    public static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
    public static final String IN_FILE = "customers.txt";

    static {
        configureLogging();
    }

    /**
     * new Log file
     */
    public static final Logger LOG = LogManager.getLogger(Lab05.class);

    /**
     * Main method. Logs message and program start time then goes to the run method.
     * 
     * @param args
     *            The main method args
     */
    public static void main(String[] args) {

        LOG.info("Starting BasicLogging.");
        LOG.info(STARTTIME);
        new Lab05().run();
    }

    /**
     * CustomerReader splitString reads a .txt file and splits it up into values which
     * are added to a HashMap. It returns a Hashmap. CustomerReport writeToFile takes
     * a HashMap as a Parameter and formats it to print to a .txt file
     */
    public void run() {

        try {
            CustomerReport.writeToFile(CustomerReader.splitString());
        } catch (ApplicationException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        } finally {
            LOG.info(calculateEndTimeAndDuration());

            System.exit(-1);
        }
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
        } catch (IOException e) {
            System.out.println(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
        }
    }
}