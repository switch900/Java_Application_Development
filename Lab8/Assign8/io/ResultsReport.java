/**
 * Project: a01029917Comp2613Lab08
 * 
 * File: Lab08.java
 * Date: Jun 1, 2018
 * Time: 10:33:18 PM
 */
package a01029917.Assign8.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import a01029917.Assign8.Lab8;
import a01029917.Assign8.data.Runner;
import a01029917.Assign8.data.util.ApplicationException;
import a01029917.Assign8.data.util.CompareByResults;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class ResultsReport {

    public static final String LINE = new String(new char[61]).replace("\0", "=");
    public static final String HEADER_FORMAT = "%3s %5s %4s %7s %9s %11s %8s %6s %n";
    public static final String RESULT_FORMAT = "%4d %5d %4d %7s %9s %11s %8.3f %6.3f %n";

    private static final String OUT_FILENAME = "results.txt";

    /**
     * Sorts an ArrayList of runners who raced by results from first to last and prints results.
     * The time the program begins and ends is documented as well as the amount of ms the program
     * ran for.
     * 
     * @param runner
     *            An ArrayList of Runner
     * @throws ApplicationException
     *             If input is incorrect throws ApplicationException
     */

    public static void printToConsole(ArrayList<Runner> runner) throws ApplicationException {
        PrintStream out = null;
        File filename = new File(OUT_FILENAME);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filename);

            out = new PrintStream(fileOut);
            int finishPlace = 1;
            System.out.println(Lab8.STARTTIME);
            out.println(Lab8.STARTTIME);

            System.out.println(LINE);
            out.println(LINE);
            System.out.printf(HEADER_FORMAT, "Rank", "Lane", "Bib#", "Country", "Last Name", "First Name", "Reaction", "Result");
            out.printf(HEADER_FORMAT, "Rank", "Lane", "Bib#", "Country", "Last Name", "First Name", "Reaction", "Result");

            Collections.sort(runner, new CompareByResults.CompareByRaceResults());

            for (Runner raceResults : runner) {
                System.out.printf(RESULT_FORMAT, finishPlace, raceResults.getBibNumber(), raceResults.getLane(), raceResults.getCountry(), raceResults.getLastName(), raceResults.getFirstName(), raceResults.getReaction(), raceResults.getResult());
                finishPlace++;
                out.printf(RESULT_FORMAT, finishPlace, raceResults.getBibNumber(), raceResults.getLane(), raceResults.getCountry(), raceResults.getLastName(), raceResults.getFirstName(), raceResults.getReaction(), raceResults.getResult());
                finishPlace++;
            }

        } catch (FileNotFoundException e) {
            e.getMessage();
        } finally {

            System.out.println(LINE);
            System.out.println(Lab8.calculateEndTimeAndDuration());
            out.println(LINE);
            out.println(Lab8.calculateEndTimeAndDuration());
            out.close();
        }
    }
}
