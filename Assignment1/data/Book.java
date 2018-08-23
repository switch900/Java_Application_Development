/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */
package a01029917.data;

/**
 * Create object of Book class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Book {

    private int bookID;
    private String iSBN;
    private String authors;
    private String title;
    private int year;
    private double ratings;
    private int ratingsCount;
    private String imageURL;

    /**
     * Create Object of Builder Class
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class Builder {

        private final int bookID;
        private final String iSBN;
        private String authors;
        private String title;
        private int year;
        private double ratings;
        private int ratingsCount;
        private String imageURL;

        /**
         * @param bookID
         *            the ID number or a book
         * @param iSBN
         *            the ISBN number of a book
         */
        public Builder(int bookID, String iSBN) {
            this.bookID = bookID;
            this.iSBN = iSBN;
        }

        /**
         * @param authors
         *            a book author
         * @return
         *         bookAuthor as a String
         */
        public Builder authors(String authors) {
            this.authors = authors;
            return this;
        }

        /**
         * @param title
         *            a book's title
         * @return
         *         title as a String
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * @param year
         *            the year a book was published
         * @return
         *         year as an int
         */
        public Builder year(int year) {
            this.year = year;
            return this;
        }

        /**
         * @param ratings
         *            the book's rating
         * @return
         *         ratings as a double
         */
        public Builder ratings(double ratings) {
            this.ratings = ratings;
            return this;
        }

        /**
         * @param ratingsCount
         *            the total amount of ratings
         * @return
         *         ratingsCount as an int
         */
        public Builder ratingsCount(int ratingsCount) {
            this.ratingsCount = ratingsCount;
            return this;
        }

        /**
         * @param imageURL
         *            the image URL for the book
         * @return
         *         imageURL as a String
         */
        public Builder imageURL(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        /**
         * @return
         *         a new Book
         */
        public Book build() {
            return new Book(this);
        }
    }

    /**
     * Default constructor
     */
    public Book() {

    }

    /**
     * Constructor for Book Class
     * 
     * @param builder
     *            values for Book from Builder Class
     */
    public Book(Builder builder) {
        bookID = builder.bookID;
        iSBN = builder.iSBN;
        authors = builder.authors;
        title = builder.title;
        year = builder.year;
        ratings = builder.ratings;
        ratingsCount = builder.ratingsCount;
        imageURL = builder.imageURL;

    }

    /**
     * @return the bookID as an int
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * @param bookID
     *            the bookID to set
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    /**
     * @return the iSBN as a String
     */
    public String getiSBN() {
        return iSBN;
    }

    /**
     * @param iSBN
     *            the iSBN to set
     */
    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    /**
     * @return the authors as a String
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * @param authors
     *            the authors to set
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * @return the title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the year as an int
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the ratings as a double
     */
    public double getRatings() {
        return ratings;
    }

    /**
     * @param ratings
     *            the ratings to set
     */
    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the ratingsCount as an int
     */
    public int getRatingsCount() {
        return ratingsCount;
    }

    /**
     * @param ratingsCount
     *            the ratingsCount to set
     */
    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    /**
     * @return the imageURL as a String
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @param imageURL
     *            the imageURL to set
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Book [bookID=" + bookID + ", iSBN=" + iSBN + ", authors=" + authors + ", title=" + title + ", year=" + year + ", ratings=" + ratings + ", ratingsCount=" + ratingsCount + ", imageURL=" + imageURL + "]";
    }

}
