/**
 * Project: a01029917Comp2613Lab08
 * 
 * File: Lab08.java
 * Date: Jun 1, 2018
 * Time: 10:33:18 PM
 */
package a01029917.Assign8.data.util;

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
