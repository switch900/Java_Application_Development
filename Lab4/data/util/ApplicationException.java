/**
 * Project: a01029917Comp2613Lab04
 * File: Customer.java
 * Date: May 1, 2018
 * Time: 5:33:51 PM
 */
package a01029917.data.util;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(message, cause, enableSuppression, writeableStackTrace);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.getMessage();
    }
}
