/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data;

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

        public Builder(int purchase_ID) {
            this.purchase_ID = purchase_ID;
        }

        public Builder customer_ID(long customer_ID) {
            this.customer_ID = customer_ID;
            return this;
        }

        public Builder book_ID(int book_ID) {
            this.book_ID = book_ID;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Purchase build() {
            return new Purchase(this);
        }

    }

    public Purchase() {
    }

    public Purchase(Builder builder) {
        purchase_ID = builder.purchase_ID;
        customer_ID = builder.customer_ID;
        book_ID = builder.book_ID;
        price = builder.price;
    }

    /**
     * @return the purchase_ID
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
     * @return the customer_ID
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
     * @return the book_ID
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
     * @return the price
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
