/**
 * Project: a01029917Comp2613Lab09
 * 
 * File: Lab09.java
 * Date: June 9, 2018
 * Time: 5:33:18 PM
 */
package a01029917.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01029917.Lab09;
import a01029917.data.util.CustomerDao;

/**
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Create the frame.
     * 
     * @throws Exception
     *             if input invalid
     * @throws SQLException
     *             if SQL data invalid
     */
    @SuppressWarnings("deprecation")
    public MainFrame(CustomerDao db) throws SQLException, Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        mnFile.setMnemonic('F');
        menuBar.add(mnFile);

        JMenuItem mntmCustomer = new JMenuItem("Customer");
        mntmCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
        mntmCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerDialog dialog = new CustomerDialog(db);
                dialog.setCustomer(db);
                dialog.setVisible(true);
            }
        });
        mntmCustomer.setMnemonic('C');
        mnFile.add(mntmCustomer);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.info(Lab09.calculateEndTimeAndDuration());
                System.exit(0);
            }
        });
        mntmExit.setMnemonic('E');
        mnFile.add(mntmExit);

        JMenu mnNewMenu = new JMenu("Help");
        mnNewMenu.setMnemonic('H');
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("About");
        mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        mntmNewMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getContentPane(), "Lab 9\nBy Andrew Hewitson A01029917", "About Lab 9", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mntmNewMenuItem.setMnemonic('A');
        mnNewMenu.add(mntmNewMenuItem);
    }
}
