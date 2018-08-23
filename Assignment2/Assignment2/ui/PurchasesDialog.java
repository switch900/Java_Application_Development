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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import a01029917.data.books.Book;
import a01029917.data.customer.Customer;
import a01029917.data.purchases.Purchase;
import a01029917.db.BooksDao;
import a01029917.db.CustomerDao;
import a01029917.db.PurchasesDao;
import net.miginfocom.swing.MigLayout;

/**
 * PurchaseDialog class extends JDialog. It creates a Dialog UI that displays the Purchase
 * information.
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class PurchasesDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();

    private static final Logger LOG = LogManager.getLogger();
    private JTable table_1;
    private DefaultTableModel dtm;

    List<Purchase> purchaseList = new ArrayList<>();
    List<Customer> customerList = new ArrayList<>();
    List<Book> booksList = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    /**
     * Create the dialog.
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     */
    public PurchasesDialog(PurchasesDao purchasesDao, CustomerDao customerDao, BooksDao booksDao) {

        setBounds(100, 100, 1018, 403);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.WEST);
        contentPanel.setLayout(new MigLayout("", "[968.00,grow]", "[247.00,grow][grow][]"));
        {

            table_1 = new JTable();
            dtm = new DefaultTableModel(0, 0);
            String header[] = new String[] { "Purchase ID", "Customer Name", "Book Title", "Price" };

            dtm.setColumnIdentifiers(header);
            table_1.setModel(dtm);

            table_1.getColumnModel().getColumn(0).setMinWidth(25);
            table_1.getColumnModel().getColumn(1).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(1).setMinWidth(25);
            table_1.getColumnModel().getColumn(2).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(2).setMinWidth(25);
            table_1.getColumnModel().getColumn(3).setPreferredWidth(125);
            table_1.getColumnModel().getColumn(3).setMinWidth(25);
        }
        try {

            int index = 0;
            if (MainFrame.mnFilterByCustomer.isArmed()) {
                items = getItemByCustomerId(customerDao, booksDao, purchasesDao);
            } else if (MainFrame.descendingPurchase.isSelected() && MainFrame.sortByLastName.isSelected()) {
                items = getPurchasesSortedByLastNameDescending(customerDao, booksDao, purchasesDao);
            } else if (MainFrame.sortByLastName.isSelected()) {
                items = getPurchasesSortedByLastName(customerDao, booksDao, purchasesDao);
            } else if (MainFrame.descendingPurchase.isSelected() && MainFrame.sortByTitle.isSelected()) {
                items = getPurchasesSortedByTitleDescending(customerDao, booksDao, purchasesDao);
            } else if (MainFrame.sortByTitle.isSelected()) {
                items = getPurchasesSortedByTitle(customerDao, booksDao, purchasesDao);
            } else {
                items = getItem(customerDao, booksDao, purchasesDao);
            }

            try {
                for (int count = 1; count <= items.size(); count++) {
                    dtm.addRow(new Object[] { "data", "data", "data", "data" });
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

            for (int x = 0; x < items.size(); x++) {
                String stringFormatPrice = String.format("$%.2f", items.get(x).price);
                table_1.setValueAt(items.get(x).purchaseId, index, 0);
                table_1.setValueAt(items.get(x).firstName + " " + items.get(x).lastName, index, 1);
                table_1.setValueAt(items.get(x).title, index, 2);
                table_1.setValueAt(stringFormatPrice, index, 3);
                index++;
            }
        } catch (

        Exception e) {

            LOG.error(e.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPanel.add(scrollPane, "cell 0 0,growx");
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel, "cell 0 1,grow");
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            {
                JButton button = new JButton("OK");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                });
                button.setActionCommand("OK");
                panel.add(button);
            }
            {
                JButton button = new JButton("Cancel");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                });
                button.setActionCommand("Cancel");
                panel.add(button);
            }
        }
    }

    {
    }

    /**
     * Return an ArrayList of Items representing the information from the Purchase class.
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     *         An ArrayList of Items
     */
    private ArrayList<Item> getItem(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {

        ArrayList<Item> itemList = new ArrayList<>();
        List<Integer> purchaseIds = new ArrayList<>();
        try {
            purchaseIds = purchasesDao.getIds();

        } catch (SQLException e1) {

            LOG.error(e1.getMessage());
        }
        try {
            int purchaseId;
            double price;
            String firstName;
            String lastName;
            String title;

            for (int ids = 0; ids < purchaseIds.size(); ids++) {
                purchaseList.add(purchasesDao.getPurchase(purchaseIds.get(ids)));
                customerList.add(customerDao.getCustomer(purchasesDao.getPurchase(purchaseIds.get(ids)).getCustomer_ID()));
                booksList.add(booksDao.getBook(purchasesDao.getPurchase(purchaseIds.get(ids)).getBook_ID()));
            }

            for (int x = 0; x < purchaseList.size(); x++) {

                purchaseId = purchaseList.get(x).getPurchase_ID();
                price = purchaseList.get(x).getPrice();
                firstName = customerList.get(purchaseId - 1).getFirstName();
                lastName = customerList.get(purchaseId - 1).getLastName();
                title = booksList.get(purchaseId - 1).getTitle();

                Item item = new Item(purchaseId, firstName, lastName, title, price);
                itemList.add(item);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return itemList;
    }

    /**
     * 
     * Return an ArrayList of Items based on a specific customerId number
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     *         An ArrayList of Items
     */
    private ArrayList<Item> getItemByCustomerId(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {

        ArrayList<Item> itemList = new ArrayList<>();
        List<Integer> purchaseIds = new ArrayList<>();
        try {
            purchaseIds = purchasesDao.getIds();

        } catch (SQLException e1) {

            LOG.error(e1.getMessage());
        }
        try {
            int purchaseId;
            double price;
            String firstName;
            String lastName;
            String title;

            for (int ids = 0; ids < purchaseIds.size() + 0; ids++) {

                purchaseList.add(purchasesDao.getPurchase(purchaseIds.get(ids)));
                customerList.add(customerDao.getCustomer(purchasesDao.getPurchase(purchaseIds.get(ids)).getCustomer_ID()));
                booksList.add(booksDao.getBook(purchasesDao.getPurchase(purchaseIds.get(ids)).getBook_ID()));

            }

            for (int x = 0; x < purchaseList.size(); x++) {

                purchaseId = purchaseList.get(x).getPurchase_ID();
                price = purchaseList.get(x).getPrice();
                firstName = customerList.get(purchaseId - 1).getFirstName();
                lastName = customerList.get(purchaseId - 1).getLastName();
                title = booksList.get(purchaseId - 1).getTitle();

                Long customerId = MainFrame.object;
                if (purchaseList.get(x).getCustomer_ID() == customerId) {
                    Item item = new Item(purchaseId, firstName, lastName, title, price);
                    itemList.add(item);
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return itemList;
    }

    /**
     * Return an ArrayList of Items sorted by the Book title.
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     *         An ArrayList of Items
     */
    private ArrayList<Item> getPurchasesSortedByTitle(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {
        ArrayList<Item> itemList = getItem(customerDao, booksDao, purchasesDao);
        itemList.sort(new CompareByTitle());
        return itemList;
    }

    /**
     * Return an ArrayList of Items sorte by the Book title in a descending order
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     */
    private ArrayList<Item> getPurchasesSortedByTitleDescending(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {
        ArrayList<Item> reversedItems = new ArrayList<>();
        reversedItems = getPurchasesSortedByTitle(customerDao, booksDao, purchasesDao);
        Collections.reverse(reversedItems);
        return reversedItems;
    }

    /**
     * Return an ArrayList of Items sorted by Book Author's lastName in a descending order.
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     *         ArrayList of Items
     */
    private ArrayList<Item> getPurchasesSortedByLastNameDescending(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {
        ArrayList<Item> reversedItems = new ArrayList<>();
        reversedItems = getPurchasesSortedByLastName(customerDao, booksDao, purchasesDao);
        Collections.reverse(reversedItems);
        return reversedItems;
    }

    /**
     * Return an ArrayList of Items sorted by Book Author's lastName in a ascending order.
     * 
     * @param purchasesDao
     *            The PurchasesDao to read
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The BooksDao to read
     * @return
     *         ArrayList of Items
     */
    private ArrayList<Item> getPurchasesSortedByLastName(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) {
        ArrayList<Item> itemList = getItem(customerDao, booksDao, purchasesDao);
        itemList.sort(new CompareByFirstName());
        itemList.sort(new CompareByLastName());
        return itemList;
    }

    /**
     * Compare Items by Author's Last Name
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class CompareByLastName implements Comparator<Item> {
        @Override
        public int compare(Item item1, Item item2) {
            return item1.lastName.compareTo(item2.lastName);
        }
    }

    /**
     * Compare Items by Author's First Name
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class CompareByFirstName implements Comparator<Item> {
        @Override
        public int compare(Item item1, Item item2) {
            return item1.firstName.compareTo(item2.firstName);
        }
    }

    /**
     * Compare Items by Book's Title.
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    public static class CompareByTitle implements Comparator<Item> {
        @Override
        public int compare(Item item1, Item item2) {
            return item1.title.compareTo(item2.title);
        }
    }

    /**
     * Inner class of Item which are representations based on the id Numbers provided by the
     * Purchase Class
     * 
     * @author Andrew Hewitson, A01029917
     *
     */
    private static class Item {
        private int purchaseId;
        private String firstName;
        private String lastName;
        private String title;
        private double price;

        /**
         * @param firstName
         *            The First Name of the Author
         * @param lastName
         *            The Last Name of the Author
         * @param title
         *            The Book's Title
         * @param price
         *            The Book's Price
         */
        public Item(int purchaseId, String firstName, String lastName, String title, double price) {
            this.purchaseId = purchaseId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.title = title;
            // this.title = Common.truncateIfRequired(title, 80);
            this.price = price;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Item [firstName=" + firstName + ", lastName=" + lastName + ", title=" + title + ", price=" + price + "]";
        }
    }
}
