/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.ui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.Books2;
import a01029917.db.BooksDao;
import a01029917.db.CustomerDao;
import a01029917.db.Database;
import a01029917.db.PurchasesDao;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private static final Logger LOG = LogManager.getLogger();

    static JCheckBoxMenuItem chckbxmntmByJoinDate;
    static JCheckBoxMenuItem chckbxmntmByAuthor;
    static JCheckBoxMenuItem authorsDescending;
    static JCheckBoxMenuItem sortByLastName;
    static JCheckBoxMenuItem lastNameDescending;
    static JCheckBoxMenuItem sortByTitle;
    static JCheckBoxMenuItem descendingPurchase;
    static JMenuItem mnFilterByCustomer;

    public Database database;
    public static Long object;

    /**
     * Create the frame.
     * 
     * @param customerDao
     *            The CustomerDao to read
     * @param booksDao
     *            The booksDao to read
     * @param purchasesDao
     *            The purchasesDao to read
     * @throws Exception
     *             if input invalid
     * @throws SQLException
     *             if SQL data invalid
     */
    @SuppressWarnings("deprecation")
    public MainFrame(CustomerDao customerDao, BooksDao booksDao, PurchasesDao purchasesDao) throws SQLException, Exception {
        getContentPane().setFont(new Font("Dialog", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        mnFile.setBackground(SystemColor.windowBorder);
        mnFile.setMnemonic('F');
        menuBar.add(mnFile);

        JMenuItem mntmCustomer = new JMenuItem("Drop");
        mntmCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(getContentPane(), "Do you want to delete all tables in Books2?", "Drop Tables", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        customerDao.drop();
                        LOG.info(CustomerDao.TABLE_NAME + " Table Dropped");
                        booksDao.drop();
                        LOG.info(BooksDao.TABLE_NAME + " Table Dropped");
                        purchasesDao.drop();
                        LOG.info(PurchasesDao.TABLE_NAME + " Table Dropped");
                    } catch (SQLException e1) {
                        LOG.error(e1.getMessage());
                    }
                }
            }
        });

        mntmCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));

        mnFile.add(mntmCustomer);

        JMenuItem mntmExit = new JMenuItem("Quit");
        mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));
        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("Program Quit By User");
                LOG.info(Books2.calculateEndTimeAndDuration());
                System.exit(0);
            }
        });
        mnFile.add(mntmExit);

        JMenu mnNewMenu = new JMenu("Books");
        mnNewMenu.setBackground(SystemColor.windowBorder);

        mnNewMenu.setMnemonic('B');
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Count");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bookCount;
                try {
                    bookCount = booksDao.countAllbooks();
                    JOptionPane.showMessageDialog(getContentPane(), "Total Books = " + bookCount, "Book Count", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "No Database Available", "Book Count", JOptionPane.INFORMATION_MESSAGE);
                    LOG.error(e1.getMessage());
                }
            }
        });

        mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
        mnNewMenu.add(mntmNewMenuItem);

        chckbxmntmByAuthor = new JCheckBoxMenuItem("By Author");
        chckbxmntmByAuthor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
        mnNewMenu.add(chckbxmntmByAuthor);

        authorsDescending = new JCheckBoxMenuItem("Descending");
        authorsDescending.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        mnNewMenu.add(authorsDescending);

        JMenuItem mntmList = new JMenuItem("List");
        mntmList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        mntmList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BooksDialog dialog = new BooksDialog(booksDao);
                dialog.setVisible(true);
            }
        });
        mnNewMenu.add(mntmList);

        JMenu mnNewMenu_1 = new JMenu("Customers");
        mnNewMenu_1.setMnemonic('C');
        mnNewMenu_1.setBackground(SystemColor.windowBorder);
        menuBar.add(mnNewMenu_1);

        JMenuItem mnNewMenu_2 = new JMenuItem("Count");
        mnNewMenu_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
        mnNewMenu_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int customerCount;
                try {
                    customerCount = customerDao.countAllCustomers();
                    JOptionPane.showMessageDialog(getContentPane(), "Total Customers = " + customerCount, "Customer Count", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "No Database Available", "Customer Count", JOptionPane.INFORMATION_MESSAGE);
                    LOG.error(e1.getMessage());
                }
            }
        });
        mnNewMenu_1.add(mnNewMenu_2);

        JMenuItem mnNewMenu_4 = new JMenuItem("List");
        mnNewMenu_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        mnNewMenu_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerDialog dialog = new CustomerDialog(customerDao);
                // dialog.setCustomer(customerDao);
                dialog.setVisible(true);
            }
        });
        chckbxmntmByJoinDate = new JCheckBoxMenuItem("By Join Date");
        chckbxmntmByJoinDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.ALT_MASK));
        mnNewMenu_1.add(chckbxmntmByJoinDate);
        mnNewMenu_1.add(mnNewMenu_4);

        JMenu mnPurchases = new JMenu("Purchases");
        mnPurchases.setFont(new Font("Dialog", Font.PLAIN, 12));
        mnPurchases.setBackground(SystemColor.windowBorder);
        mnPurchases.setMnemonic('P');
        menuBar.add(mnPurchases);

        JMenuItem mnNewMenu_5 = new JMenuItem("Total");
        mnNewMenu_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
        mnNewMenu_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPurchasePrice;
                try {
                    totalPurchasePrice = purchasesDao.totalSumAllPurchases();
                    String stringTotal = String.format("$%.2f", totalPurchasePrice);
                    JOptionPane.showMessageDialog(getContentPane(), "Total Of Purchases = " + stringTotal, "Purchase Total", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "No Database Available", "Purchase Total", JOptionPane.INFORMATION_MESSAGE);
                    LOG.error(e1.getMessage());
                }
            }
        });
        mnPurchases.add(mnNewMenu_5);

        sortByLastName = new JCheckBoxMenuItem("By Last Name");
        sortByLastName.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
        mnPurchases.add(sortByLastName);

        sortByTitle = new JCheckBoxMenuItem("By Title");
        sortByTitle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK));
        mnPurchases.add(sortByTitle);

        descendingPurchase = new JCheckBoxMenuItem("Descending");
        descendingPurchase.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        mnPurchases.add(descendingPurchase);

        mnFilterByCustomer = new JMenuItem("Filter by Customer ID");
        mnFilterByCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Long> possibilities = new ArrayList<>();
                try {
                    for (Long customerId : customerDao.getIds()) {
                        possibilities.add(customerId);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "No Database Available", "Customer Search", JOptionPane.INFORMATION_MESSAGE);
                    LOG.error(e1.getMessage());
                }
                try {
                    // object = (Long) JOptionPane.showInputDialog(getContentPane(), "Enter Customer ID Number To Search By:", "Select Customer", JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(),
                    // possibilities.get(0));

                    String string = (String) JOptionPane.showInputDialog(getContentPane(), "Enter Customer ID Number To Search By:", "Select Customer", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (string.equals("") || string.equals(null)) {
                        JOptionPane.showMessageDialog(getContentPane(), "Not A Valid Input", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {
                        object = Long.parseLong(string);
                    }
                    if (purchasesDao.getCustomerIds().contains(object)) {
                        PurchasesDialog dialog = new PurchasesDialog(purchasesDao, customerDao, booksDao);
                        dialog.setVisible(true);
                    } else if (!purchasesDao.getCustomerIds().contains(object) && customerDao.getIds().contains(object)) {
                        JOptionPane.showMessageDialog(getContentPane(), "Customer " + object + " has made no purchases.", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    } else if (!customerDao.getIds().contains(object)) {
                        JOptionPane.showMessageDialog(getContentPane(), "Customer " + object + " does not exist", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e1) {
                    LOG.error(e1.getMessage());
                }
            }
        });

        mnFilterByCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_MASK));
        mnPurchases.add(mnFilterByCustomer);

        JMenuItem mnList = new JMenuItem("List");
        mnList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        mnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchasesDialog dialog = new PurchasesDialog(purchasesDao, customerDao, booksDao);
                dialog.setVisible(true);
            }
        });
        mnPurchases.add(mnList);

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setMnemonic('H');
        mnHelp.setBackground(SystemColor.windowBorder);
        menuBar.add(mnHelp);

        JMenuItem mnAbout = new JMenuItem("About");
        mnAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        mnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getContentPane(), "Assignment 2\nBy Andrew Hewitson A01029917", "About Assignment 2", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mnHelp.add(mnAbout);
    }

    public static boolean joinDateSelected() {
        boolean jdSelected = false;
        if (chckbxmntmByJoinDate.isSelected()) {
            jdSelected = true;
        }
        return jdSelected;
    }

    public static boolean sortByAuthorjoinDateSelected() {
        boolean sbaSelected = false;
        if (chckbxmntmByAuthor.isSelected()) {
            sbaSelected = true;
        }
        return sbaSelected;
    }
}
