/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.data.purchases;

/**
 * Create an object of Purchase class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Purchase {

    private int purchase_ID;
    private long customer_ID;
    private int book_ID;
    private double price;

    /**
     * Create an object of Builder class
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class Builder {
        private final int purchase_ID;
        private long customer_ID;
        private int book_ID;
        private double price;

        /**
         * @param purchase_ID
         *            The Purchase ID number
         */

        public Builder(int purchase_ID) {
            this.purchase_ID = purchase_ID;
        }

        /**
         * @param customer_ID
         *            The Customer ID number of the purchasing Customer
         * @return
         *         customer_ID as a long
         */
        public Builder customer_ID(long customer_ID) {
            this.customer_ID = customer_ID;
            return this;
        }

        /**
         * @param book_ID
         *            The Book ID number of the purchased Book
         * @return
         *         book_ID as an int
         */
        public Builder book_ID(int book_ID) {
            this.book_ID = book_ID;
            return this;
        }

        /**
         * @param price
         *            The Purchase price of the Book
         * @return
         *         price as a double
         */
        public Builder price(double price) {
            this.price = price;
            return this;
        }

        /**
         * @return
         *         Purchase object
         */
        public Purchase build() {
            return new Purchase(this);
        }

    }

    /**
     * default constructor
     */
    public Purchase() {
    }

    /**
     * @param builder
     *            The Purchase input
     */
    public Purchase(Builder builder) {
        purchase_ID = builder.purchase_ID;
        customer_ID = builder.customer_ID;
        book_ID = builder.book_ID;
        price = builder.price;
    }

    /**
     * @return the purchase_ID as an int
     */
    public int getPurchase_ID() {
        return purchase_ID;
    }

    /**
     * @param purchase_ID
     *            the purchase_ID to set
     */
    public void setPurchase_ID(int purchase_ID) {
        this.purchase_ID = purchase_ID;
    }

    /**
     * @return the customer_ID as a long
     */
    public long getCustomer_ID() {
        return customer_ID;
    }

    /**
     * @param customer_ID
     *            the customer_ID to set
     */
    public void setCustomer_ID(long customer_ID) {
        this.customer_ID = customer_ID;
    }

    /**
     * @return the book_ID as an int
     */
    public int getBook_ID() {
        return book_ID;
    }

    /**
     * @param book_ID
     *            the book_ID to set
     */
    public void setBook_ID(int book_ID) {
        this.book_ID = book_ID;
    }

    /**
     * @return the price as a double
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Purchase [purchase_ID=" + purchase_ID + ", customer_ID=" + customer_ID + ", book_ID=" + book_ID + ", price=" + price + "]";
    }

}
