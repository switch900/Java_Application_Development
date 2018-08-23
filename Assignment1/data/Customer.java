/**
 * Project: a01029917Comp2613Assignment1
 * 
 * File: Books.java
 * Date: May 21, 2018
 * Time: 10:45:00 AM
 */

package a01029917.data;

import java.time.LocalDate;

/**
 * Creates object of Customer class
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
public class Customer {

    public static final int ATTRIBUTE_COUNT = 9;

    private long identifier;
    private String firstName;
    private String lastName;
    private String streetName;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate joinDate;

    /**
     * Creates object of Builder class
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class Builder {

        private final long identifier;
        private final String phoneNumber;

        private String firstName;
        private String lastName;
        private String streetName;
        private String city;
        private String postalCode;
        private String emailAddress;
        private LocalDate joinDate;

        /**
         * @param identifier
         *            the identifier to set
         * @param phoneNumber
         *            the phone number to set
         */
        public Builder(long identifier, String phoneNumber) {
            this.identifier = identifier;
            this.phoneNumber = phoneNumber;
        }

        /**
         * @param firstName
         *            the firstName to set
         * @return
         *         the firstName as a String
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @param lastName
         *            the lastName to set
         * @return
         *         the lastName as a String
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @param streetName
         *            the streetName to set
         * @return
         *         the streetName as a String
         */
        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        /**
         * @param city
         *            the city to set
         * @return
         *         the city as a String
         */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /**
         * @param postalCode
         *            the postalCode to set
         * @return
         *         the postalCode as a String
         */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * @param emailAddress
         *            the emailAddress to set
         * @return
         *         the emailAddress as a String
         */
        public Builder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        /**
         * @param joinDate
         *            the joinDate to set
         * @return
         *         the joinDate as a LocalDate
         */
        public Builder joinDate(LocalDate joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        /**
         * 
         * @return
         *         new customer object
         */
        public Customer build() {
            return new Customer(this);
        }
    }

    /**
     * default constructor
     */
    public Customer() {
    }

    /**
     * 
     * @param builder
     *            for customer
     */
    public Customer(Builder builder) {
        identifier = builder.identifier;
        firstName = builder.firstName;
        lastName = builder.lastName;
        streetName = builder.streetName;
        city = builder.city;
        postalCode = builder.postalCode;
        phoneNumber = builder.phoneNumber;
        emailAddress = builder.emailAddress;
        joinDate = builder.joinDate;
    }

    /**
     * @return the identifier as a STring
     */
    public long getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the firstName as a String
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
     * @return the lastName as a String
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
     * @return the streetName as a String
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName
     *            the streetName to set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * @return the city as a String
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the postalCode as a String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the phoneNumber as a String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the emailAddress as a String
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    /**
     * @return the joinDate as a LocalDate
     */
    public LocalDate getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate
     *            the joinDate to set
     */
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Customer [identifier=" + identifier + ", firstName=" + firstName + ", lastName=" + lastName + ", streetName=" + streetName + ", city=" + city + ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + ", emailAddress=" + emailAddress + ", joinDate=" + joinDate + "]";
    }
}
