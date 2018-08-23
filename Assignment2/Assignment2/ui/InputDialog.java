/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import a01029917.data.customer.Customer;
import net.miginfocom.swing.MigLayout;

/**
 * InputDialog class extends JDialog. It creates a Dialog UI that displays the Customer
 * information of a specific customer.
 * 
 * @author Andrew Hewitson, A01029917
 *
 */
@SuppressWarnings("serial")
public class InputDialog extends JDialog {

    public static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final JPanel contentPanel = new JPanel();
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField postalCodeField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField joinedDateField;

    private Customer customer;

    /**
     * Create the dialog.
     * 
     * @param frame
     *            The JFrame to use
     * @param customer
     *            The Customer to read
     */
    public InputDialog(JFrame frame, Customer customer) {
        super(frame, true);

        this.customer = customer;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(frame);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));

        JLabel lblId = new JLabel("ID");
        contentPanel.add(lblId, "cell 0 0,alignx trailing");

        idField = new JTextField();
        idField.setEnabled(false);
        idField.setEditable(false);
        contentPanel.add(idField, "cell 1 0,growx");
        idField.setColumns(10);

        JLabel lblFirstName = new JLabel("First Name");
        contentPanel.add(lblFirstName, "cell 0 1,alignx trailing");

        firstNameField = new JTextField();
        contentPanel.add(firstNameField, "cell 1 1,growx");
        firstNameField.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name");
        contentPanel.add(lblLastName, "cell 0 2,alignx trailing");

        lastNameField = new JTextField();
        contentPanel.add(lastNameField, "cell 1 2,growx");
        lastNameField.setColumns(10);

        JLabel lblStreet = new JLabel("Street");
        contentPanel.add(lblStreet, "cell 0 3,alignx trailing");

        streetField = new JTextField();
        contentPanel.add(streetField, "cell 1 3,growx");
        streetField.setColumns(10);

        JLabel lblCity = new JLabel("City");
        contentPanel.add(lblCity, "cell 0 4,alignx trailing");

        cityField = new JTextField();
        contentPanel.add(cityField, "cell 1 4,growx");
        cityField.setColumns(10);

        JLabel lblPostalCode = new JLabel("Postal Code");
        contentPanel.add(lblPostalCode, "cell 0 5,alignx trailing");

        postalCodeField = new JTextField();
        contentPanel.add(postalCodeField, "cell 1 5,growx");
        postalCodeField.setColumns(10);

        JLabel lblPhone = new JLabel("Phone");
        contentPanel.add(lblPhone, "cell 0 6,alignx trailing");

        phoneField = new JTextField();
        contentPanel.add(phoneField, "cell 1 6,growx");
        phoneField.setColumns(10);

        JLabel lblEmail = new JLabel("Email");
        contentPanel.add(lblEmail, "cell 0 7,alignx trailing");

        emailField = new JTextField();
        contentPanel.add(emailField, "cell 1 7,growx");
        emailField.setColumns(10);

        JLabel lblJoinedDate = new JLabel("Joined Date");
        contentPanel.add(lblJoinedDate, "cell 0 8,alignx trailing");

        joinedDateField = new JTextField();
        contentPanel.add(joinedDateField, "cell 1 8,growx");
        joinedDateField.setColumns(10);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            JButton okButton = new JButton("SAVE");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateCustomer();
                    InputDialog.this.dispose();
                }
            });
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InputDialog.this.dispose();
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setCustomer(customer);
    }

    /**
     * @param customer
     *            The Customer to set
     */
    public void setCustomer(Customer customer) {
        idField.setText(Long.toString(customer.getIdentifier()));
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        streetField.setText(customer.getStreetName());
        cityField.setText(customer.getCity());
        postalCodeField.setText(customer.getPostalCode());
        phoneField.setText(customer.getPhoneNumber());
        emailField.setText(customer.getEmailAddress());
        joinedDateField.setText(customer.getJoinDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Update Customer Information
     */
    protected void updateCustomer() {
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setStreetName(streetField.getText());
        customer.setCity(cityField.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setPhoneNumber(phoneField.getText());
        customer.setEmailAddress(emailField.getText());
        String dateString = joinedDateField.getText(); // Tue Feb 22 1977
        LocalDate date = LocalDate.parse(dateString, SHORT_DATE_FORMAT);
        customer.setJoinDate(date);

    }

    /**
     * Get Customer
     * 
     * @return
     *         The Customer to get
     */
    public Customer getCustomer() {
        return customer;
    }
}
