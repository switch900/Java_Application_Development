/**
 * Project: a01029917Comp2613Lab08
 * 
 * File: Lab08.java
 * Date: Jun 1, 2018
 * Time: 10:33:18 PM
 */

package a01029917.Assign8.data.util;

import java.util.Comparator;

import a01029917.Assign8.data.Runner;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

public class CompareByResults {

    /**
     * default constructor
     */
    public CompareByResults() {
    }

    /**
     * Subclass compares Runner's results for sorting
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class CompareByRaceResults implements Comparator<Runner> {

        @Override
        public int compare(Runner runner1, Runner runner2) {
            return Double.compare(runner1.getResult(), runner2.getResult());
        }
    }
}
