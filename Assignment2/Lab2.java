/**
 * Project: a01029917Comp2613Lab02
 * File: Lab2.java
 * Date: Apr 17, 2018
 * Time: 5:33:18 PM
 */

package a01029917;

import a01029917.data.Customer;
import a01029917.io.CustomerReader;
import a01029917.io.CustomerReport;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Lab2 {

    /**
     * Main method. Creates new CustomerReader object. Prints report. Inserts String to split and prints formatted string.
     * Mark Chan will fail because his email is mChan.
     * 
     * @param args
     *            The main method args
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Input data is missing. Expecting customer data.");
            System.exit(-1);
        }

        new Lab2().run(args[0]);
    }
    // CustomerReader test = new CustomerReader();
    // CustomerReport.printToConsole();
    // test.splitString(
    // "1|Fred|Fish|5707 Sidley St|Burnaby|V5J 5E6|(604) 433-5004|fredfish@imperial.net|20080322:2|Laurie|Nash|2816 Commercial Dr|Vancouver|V5N
    // 4C6|(604) 876-0182|laurieeenash@modern.com|20140828:3|Conrad|Washington|13479 King George Blvd|Surrey|V3T 2T8|(604) 588-4988|cwash@daytona.net|20110712:4|Jeanette|Price|21000
    // WestminsterHwy|Richmond|V6V2S9|(604) 276-2552|priceizrite@pacific.com|20151003:5|Mark|Chan|3766 E 1st Ave|Burnaby|V5C 3V9|(604) 293-1022|mChan|20100401");
    // }

    /**
     * Populate the customers and generate a report.
     */
    private void run(String customerData) {
        Customer[] customers = CustomerReader.splitString(customerData);
        CustomerReport.printToConsole(customers);
    }

}
