/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */
package a01029917.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.data.Customer;
import a01029917.data.util.CustomerDao;
import net.miginfocom.swing.MigLayout;

/**
 * CustomerDialog class extends JDialog. It creates a Dialog UI that displays the Customer
 * information.
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class CustomerDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField iDtextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField streetTextField;
    private JTextField cityTextField;
    private JTextField postalCodeTextField;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JTextField joinedDateTextField;

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Create the dialog.
     * 
     * @param db
     * 
     * @param mainFrame
     */
    public CustomerDialog(CustomerDao db) {
        setBounds(100, 100, 450, 404);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new MigLayout("", "[61.00][grow]", "[][][][][][][][][]"));
        {
            JLabel label = new JLabel("ID");
            contentPanel.add(label, "cell 0 0,alignx trailing");
        }
        {
            iDtextField = new JTextField();
            contentPanel.add(iDtextField, "cell 1 0,growx");
            iDtextField.setColumns(10);
        }
        {
            JLabel lblNewLabel = new JLabel("First Name");
            contentPanel.add(lblNewLabel, "cell 0 1,growx");
        }
        {
            firstNameTextField = new JTextField();
            contentPanel.add(firstNameTextField, "cell 1 1,growx");
            firstNameTextField.setColumns(10);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Last Name");
            contentPanel.add(lblNewLabel_1, "flowy,cell 0 2,alignx trailing");
        }
        {
            lastNameTextField = new JTextField();
            contentPanel.add(lastNameTextField, "cell 1 2,growx");
            lastNameTextField.setColumns(10);
        }
        {
            JLabel lblNewLabel_2 = new JLabel("Street");
            contentPanel.add(lblNewLabel_2, "cell 0 3,alignx trailing");
        }
        {
            streetTextField = new JTextField();
            contentPanel.add(streetTextField, "cell 1 3,growx");
            streetTextField.setColumns(10);
        }
        {
            JLabel lblNewLabel_3 = new JLabel("City");
            contentPanel.add(lblNewLabel_3, "cell 0 4,alignx trailing");
        }
        {
            cityTextField = new JTextField();
            contentPanel.add(cityTextField, "cell 1 4,growx");
            cityTextField.setColumns(10);
        }
        {
            JLabel lblNewLabel_4 = new JLabel("Postal Code");
            contentPanel.add(lblNewLabel_4, "cell 0 5,alignx trailing");
        }
        {
            postalCodeTextField = new JTextField();
            contentPanel.add(postalCodeTextField, "cell 1 5,growx");
            postalCodeTextField.setColumns(10);
        }
        {
            JLabel lblNewLabel_5 = new JLabel("Phone");
            contentPanel.add(lblNewLabel_5, "cell 0 6,alignx trailing");
        }
        {
            phoneTextField = new JTextField();
            contentPanel.add(phoneTextField, "cell 1 6,growx");
            phoneTextField.setColumns(10);
        }
        {
            JLabel lblEmail = new JLabel("Email");
            contentPanel.add(lblEmail, "cell 0 7,alignx trailing");
        }
        {
            emailTextField = new JTextField();
            contentPanel.add(emailTextField, "cell 1 7,growx");
            emailTextField.setColumns(10);
        }
        {
            JLabel lblJoinedDate = new JLabel("Joined Date");
            contentPanel.add(lblJoinedDate, "cell 0 8,alignx trailing");
        }
        {
            joinedDateTextField = new JTextField();
            contentPanel.add(joinedDateTextField, "cell 1 8,growx,aligny center");
            joinedDateTextField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LOG.info("Customer Dialog Closed");
                        setVisible(false);
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LOG.info("Customer Dialog Closed");
                        setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
            setCustomer(db);
        }
    }

    /**
     * Sets Customer information from CustomerDao
     * 
     * @param db
     *            customerDao to set as Customer
     */
    void setCustomer(CustomerDao db) {
        Customer customer = getCustomer(db);
        iDtextField.setText(Long.toString(customer.getIdentifier()));
        firstNameTextField.setText(customer.getFirstName());
        lastNameTextField.setText(customer.getLastName());
        streetTextField.setText(customer.getStreetName());
        cityTextField.setText(customer.getCity());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhoneNumber());
        emailTextField.setText(customer.getEmailAddress());
        joinedDateTextField.setText(customer.getJoinDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    /**
     * Returns a random customer to display
     * 
     * @param db
     *            the CustomerDao to display method from
     * @return
     *         customer as a Customer
     */
    private Customer getCustomer(CustomerDao db) {

        try {

            Random rand = new Random();
            int id = rand.nextInt(db.getIds().size()) + 1;
            Customer customer = db.getCustomer((long) id);
            return customer;
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}