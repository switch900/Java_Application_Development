/**
 * Project: a01029917Comp2613Lab08
 * 
 * File: Lab08.java
 * Date: Jun 1, 2018
 * Time: 10:33:18 PM
 */
package a01029917.Assign8.data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Runner extends Thread {

    public static final int RACE_LENGTH = 100;
    public static final int RACE_SEGMENT = 1;
    public static int RACE_START = 0;

    private int lane;
    private int bibNumber;
    private String country;
    private String lastName;
    private String firstName;
    private double reaction;
    private double result;
    public ArrayList<Runner> runners;

    /**
     * default constructor
     */
    public Runner() {
    }

    /**
     * @param lane
     *            the Lane the Runner runs in as an int
     * @param bibNumber
     *            the Runner's bib number as an int
     * @param country
     *            the Country the runner is from as a String
     * @param lastName
     *            the Runner's last name as a String
     * @param firstName
     *            the Runner's first name as a String
     * @param reaction
     *            the Runner's reaction time as a Double
     */
    public Runner(int lane, int bibNumber, String country, String lastName, String firstName, double reaction) {
        super();
        this.lane = lane;
        this.bibNumber = bibNumber;
        this.country = country;
        this.lastName = lastName;
        this.firstName = firstName;
        this.reaction = reaction;
    }

    /**
     * @return the lane
     *         as an int
     */
    public int getLane() {
        return lane;
    }

    /**
     * @param lane
     *            the lane to set
     */
    public void setLane(int lane) {
        this.lane = lane;
    }

    /**
     * @return the bibNumber
     *         as an int
     */
    public int getBibNumber() {
        return bibNumber;
    }

    /**
     * @param bibNumber
     *            the bibNumber to set
     */
    public void setBibNumber(int bibNumber) {
        this.bibNumber = bibNumber;
    }

    /**
     * @return the country
     *         as a String
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the lastName
     *         as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     *         as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the reaction
     *         as a double
     */
    public double getReaction() {
        return reaction;
    }

    /**
     * @param reaction
     *            the reaction to set
     */
    public void setReaction(double reaction) {
        this.reaction = reaction;
    }

    /**
     * @return the result
     *         as a double
     */
    public double getResult() {
        return result;
    }

    /**
     * @return the runners
     *         as an ArrayList of Runner
     */
    public ArrayList<Runner> getRunners() {
        return runners;
    }

    /**
     * @param runners
     *            the runners to set
     */
    public void setRunners(ArrayList<Runner> runners) {
        this.runners = runners;
    }

    /**
     * The run method loops through a set of loops dependant on the length of the race. Each time it goes up
     * by 1 the loop Sleeps for a period of time which based on a formular consisting of a random number
     * and the Runner's reaction time. The duration it takes to run the race is counted and the result is
     * tabulated which becomes the Runner's result in seconds.
     */
    @Override
    public void run() {
        Instant start = Instant.now();
        for (int x = RACE_START; x <= RACE_LENGTH; x++) {
            Random randomDelay = new Random();
            long time = (long) ((90L + randomDelay.nextInt(16)) + reaction);
            try {
                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Instant end = Instant.now();
        result = Duration.between(start, end).toMillis();
        result = result / 1000;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Runner [lane=" + lane + ", bibNumber=" + bibNumber + ", country=" + country + ", lastName=" + lastName + ", firstName=" + firstName + ", reaction=" + reaction + ", result=" + result + "]";
    }

}
