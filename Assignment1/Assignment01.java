/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */

package a01029917;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01029917.data.util.ApplicationException;
import a01029917.data.util.BookOptions;

/**
 * Main class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Assignment01 {

    public static final Instant STARTTIME = Instant.now();
    public static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
    public static final String IN_FILE = "customers.txt";

    static {
        configureLogging();
    }

    /**
     * new Log file
     */
    public static final Logger LOG = LogManager.getLogger(Assignment01.class);

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

        LOG.info(STARTTIME);
        LOG.info("Starting BasicLogging.");

        try {
            BookOptions.process(args);
            new BookOptions().run();

        } catch (ParseException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
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