/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import a01029917.data.customer.Customer;
import a01029917.data.customer.SortCustomers;
import a01029917.db.CustomerDao;
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
    private static MainFrame mainFrame;
    private static final Logger LOG = LogManager.getLogger();
    private JTable table_1;
    private DefaultTableModel dtm;
    private JScrollPane scrollPane;
    private JButton okButton;
    private JButton cancelButton;

    List<Customer> customerList = new ArrayList<>();

    /**
     * Create the dialog.
     * 
     * @param customerDao
     *            The CustomerDao to read
     */
    public CustomerDialog(CustomerDao customerDao) {
        setBounds(100, 100, 1018, 403);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.WEST);
        contentPanel.setLayout(new MigLayout("", "[968.00]", "[247.00,grow][]"));
        {

            table_1 = new JTable();

            table_1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {

                        int customerRow = table_1.getSelectedRow();

                        Customer customer;
                        try {
                            customer = customerDao.getCustomer(customerDao.getIds().get(customerRow));
                            InputDialog dialog = new InputDialog(mainFrame, customer);
                            dialog.setVisible(true);
                            customerDao.update(customer);

                        } catch (SQLException e1) {

                            LOG.error(e1.getMessage());
                        } catch (Exception e1) {

                            LOG.error(e1.getMessage());
                        }
                    }
                }
            });
            table_1.setUpdateSelectionOnSort(false);

            dtm = new DefaultTableModel(0, 0);
            String header[] = new String[] { "ID", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone", "Email", "Joined Date" };

            dtm.setColumnIdentifiers(header);
            table_1.setModel(dtm);

            try {
                for (int count = 1; count <= customerDao.countAllCustomers(); count++) {
                    dtm.addRow(new Object[] { "data", "data", "data", "data", "data", "data", "data", "data", "data" });
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
            table_1.getColumnModel().getColumn(0).setMinWidth(25);
            table_1.getColumnModel().getColumn(1).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(1).setMinWidth(25);
            table_1.getColumnModel().getColumn(2).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(2).setMinWidth(25);
            table_1.getColumnModel().getColumn(3).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(3).setMinWidth(25);
            table_1.getColumnModel().getColumn(4).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(4).setMinWidth(25);
            table_1.getColumnModel().getColumn(5).setMinWidth(25);
            table_1.getColumnModel().getColumn(6).setMinWidth(25);
            table_1.getColumnModel().getColumn(7).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(7).setMinWidth(25);

            try {
                int index = 0;

                if (MainFrame.joinDateSelected()) {
                    customerList = getCustomerSortedByJoinDate(customerDao);
                } else {
                    customerList = getCustomer(customerDao);
                }

                for (Customer customer1 : customerList) {
                    table_1.setValueAt(customer1.getIdentifier(), index, 0);
                    table_1.setValueAt(customer1.getFirstName(), index, 1);
                    table_1.setValueAt(customer1.getLastName(), index, 2);
                    table_1.setValueAt(customer1.getStreetName(), index, 3);
                    table_1.setValueAt(customer1.getCity(), index, 4);
                    table_1.setValueAt(customer1.getPostalCode(), index, 5);
                    table_1.setValueAt(customer1.getPhoneNumber(), index, 6);
                    table_1.setValueAt(customer1.getEmailAddress(), index, 7);
                    table_1.setValueAt(customer1.getJoinDate(), index, 8);
                    index++;
                }

            } catch (Exception e) {

                LOG.error(e.getMessage());
            }
            scrollPane = new JScrollPane(table_1);

            scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
            contentPanel.add(scrollPane, "cell 0 0,growx");
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton = new JButton("REFRESH");
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LOG.info("Customer Dialog Closed");
                        setVisible(false);
                        CustomerDialog newDialog = new CustomerDialog(customerDao);
                        newDialog.setVisible(true);
                    }
                });
                okButton.setActionCommand("REFRESH");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LOG.info("Customer Dialog Closed");
                        setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                getRootPane().setDefaultButton(cancelButton);
            }
            setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { getContentPane(), table_1, contentPanel, scrollPane, buttonPane, okButton, cancelButton }));
            // setCustomer(customerDao);
        }
    }

    // /**
    // * Sets Customer information from CustomerDao
    // *
    // * @param db
    // * customerDao to set as Customer
    // */
    // void setCustomer(CustomerDao db) {
    // // Customer customer = getCustomer(db);
    //
    // }

    /**
     * 
     * @param db
     * @return
     */
    private ArrayList<Customer> getCustomer(CustomerDao db) {

        ArrayList<Customer> customerList = new ArrayList<>();
        try {
            for (Long tempIds : db.getIds()) {
                Customer customer = db.getCustomer(tempIds);
                customerList.add(customer);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return customerList;
    }

    /**
     * 
     * @param db
     * @return
     */
    private ArrayList<Customer> getCustomerSortedByJoinDate(CustomerDao db) {

        ArrayList<Customer> customerList = getCustomer(db);
        Collections.sort(customerList, new SortCustomers.CompareByJoinedDate());
        return customerList;
    }
}
