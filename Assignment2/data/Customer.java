/**
 * Project: a01029917Comp2613Lab02
 * File: Customer.java
 * Date: Apr 17, 2018
 * Time: 5:33:51 PM
 */

package a01029917.data;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Customer {

    private long identifier;
    private String firstName;
    private String lastName;
    private String streetName;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String emailAddress;
    private String joinDate;

    /**
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
        private String joinDate;

        /**
         * @param identifier
         *            the identifier to set
         * @param phoneNumber
         *            the phonenumber to set
         */
        public Builder(long identifier, String phoneNumber) {
            this.identifier = identifier;
            this.phoneNumber = phoneNumber;
        }

        /**
         * @param val
         *            the firstName to set
         * @return
         *         the firstName as a String
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @param val
         *            the lastName to set
         * @return
         *         the lastName as a String
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @param val
         *            the streetName to set
         * @return
         *         the streetName as a String
         */
        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        /**
         * @param val
         *            the city to set
         * @return
         *         the city as a String
         */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /**
         * @param val
         *            the postalCode to set
         * @return
         *         the postalCode as a String
         */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Checks to see if Email is Valid and sets
         * 
         * @param val
         *            the emailAddress to set if valid email
         * @return
         *         the emailAddress as a String
         */
        public Builder emailAddress(String emailAddress) {
            // if (Validator.validateEmail(val)) {
            // emailAddress = val;
            // } else {
            // emailAddress = "N/A";
            // }
            this.emailAddress = emailAddress;
            return this;
        }

        /**
         * @param val
         *            the joinDate to set
         * @return
         *         the joinDate as a String
         */
        public Builder joinDate(String joinDate) {
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
     * @return the identifier
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
     * @return the firstName
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
     * @return the lastName
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
     * @return the streetName
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
     * @return the city
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
     * @return the postalCode
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
     * @return the phoneNumber
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
     * @return the emailAddress
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
     * @return the joinDate
     */
    public String getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate
     *            the joinDate to set
     */
    public void setJoinDate(String joinDate) {
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
