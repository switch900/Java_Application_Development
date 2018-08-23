/**
 * Project: a01029917Comp2613Lab04
 * 
 * File: Lab2.java
 * Date: May 1, 2018
 * Time: 5:33:18 PM
 */

package a01029917;

import java.time.Instant;
import java.util.Map;

import a01029917.data.Customer;
import a01029917.data.util.ApplicationException;
import a01029917.io.CustomerReader;
import a01029917.io.CustomerReport;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab4 {

    private static Instant start;
    private static Instant end;

    /**
     * Main method. If there are input args it passes them to the run method. If there are no args
     * then prints an error message Prints start time stamp at beginning of program.
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
        new Lab4().run(args[0]);
    }

    /**
     * Passes input args to CustomerReader to be split into elements as a HashMap.
     * If the elements are good then they are returned and passed to CustomerReport class
     * to print a report. If the args are not good the program prints a error
     * using ApplicationException class. Print end time stamp at end of program runtime as well
     * as program runtime duration in ms and finally exits.
     * 
     * @param customerData
     *            customerData as a String
     */
    private void run(String customerData) {

        try {
            Map<Long, Customer> customers = CustomerReader.splitString(customerData);
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
