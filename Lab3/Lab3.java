/**
 * Project: a01029917Comp2613Lab02
 * File: Lab2.java
 * Date: Apr 17, 2018
 * Time: 5:33:18 PM
 */

package a01029917;

import java.time.Instant;

import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.io.CustomerReader;
import a01029917.io.CustomerReport;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab3 {

    private static Instant start;
    private static Instant end;

    /**
     * Main method. Creates new CustomerReader object. Prints report. Inserts String to split and prints formatted string.
     * Mark Chan will fail because his email is mChan.
     * 
     * @param args
     *            The main method args
     */
    public static void main(String[] args) {
        System.out.println();
        startTime();
        if (args.length == 0) {
            System.out.println("Input data is missing. Expecting customer data.");
            System.exit(-1);
        }
        new Lab3().run(args[0]);
    }

    /**
     * Populate the customers and generate a report.
     */
    private void run(String customerData) {

        Customer[] customers;
        try {
            customers = CustomerReader.splitString(customerData);
            CustomerReport.printToConsole(customers);
        } catch (ApplicationException e) {
            System.out.println(e.toString());
        } finally {
            endTime();
            calculateDuration();
            System.exit(-1);
        }
    }

    /**
     * Calculates start time and prints to console
     */
    public static void startTime() {
        start = Instant.now();
        String startTime = start.toString();
        System.out.println(startTime);
    }

    /**
     * Calculates end time and prints to console
     */
    public static void endTime() {
        end = Instant.now();
        String endTime = end.toString();
        System.out.println(endTime);
    }

    /**
     * Subtracts start time in milliseconds from end time in milliseconds to find programs run time duration in milliseconds.
     * Prints results to console.
     */
    public static void calculateDuration() {
        String durationString;
        long startMilliseconds = start.toEpochMilli();
        long endMilliseconds = end.toEpochMilli();
        long duration = (endMilliseconds - startMilliseconds);
        durationString = "Duration: " + new Long(duration).toString() + " ms";
        System.out.println(durationString);
    }
}
