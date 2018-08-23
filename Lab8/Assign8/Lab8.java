/**
 * Project: a01029917Comp2613Lab08
 * 
 * File: Lab08.java
 * Date: Jun 1, 2018
 * Time: 10:33:18 PM
 */
package a01029917.Assign8;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import a01029917.Assign8.data.Runner;
import a01029917.Assign8.data.util.ApplicationException;
import a01029917.Assign8.io.ResultsReport;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab8 {

    public static Instant STARTTIME;

    public static Runner runner;

    /**
     * The Main method for lab. It marks the StartTime and then initiates the test method.
     * 
     * @param args
     *            args
     * @throws InterruptedException
     *             if program is interrupted
     * @throws ApplicationException
     *             if input is invalid
     */
    public static void main(String[] args) throws InterruptedException, ApplicationException {
        STARTTIME = Instant.now();
        test();
    }

    /**
     * The Test method for Lab8. It creates an ArrayList of runners. Populates the list with
     * runners for testing. Then each of the runners is set to start as new thread. When all the
     * Runners have completed their task the the results are printed using ResultsReport class.
     * 
     * @throws InterruptedException
     *             if program is interrupted
     * @throws ApplicationException
     *             if input is invalid
     */
    public static void test() throws InterruptedException, ApplicationException {

        ArrayList<Runner> runners = new ArrayList<>();

        runners.add(new Runner(6, 2612, "JAM", "BOLT", "Usain", 0.155));
        runners.add(new Runner(4, 3069, "USA", "GATLIN", "Justin", .152));
        runners.add(new Runner(7, 2196, "CAN", "DE GRASSE", "Andre", .141));
        runners.add(new Runner(9, 2611, "JAM", "BLAKE", "Yohan", .145));
        runners.add(new Runner(3, 2909, "RSA", "SIMBINE", "Akani", .128));
        runners.add(new Runner(8, 2245, "CIV", "MEITE", "Ben Youssef", .156));
        runners.add(new Runner(5, 2434, "FRA", "VICAUT", "Jimmy", .14));
        runners.add(new Runner(2, 3054, "USA", "BROMELL", "Trayvan", .135));

        for (Runner racers : runners) {
            racers.start();
        }

        for (Runner racers : runners) {
            racers.join();
        }

        ResultsReport.printToConsole(runners);
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
}
