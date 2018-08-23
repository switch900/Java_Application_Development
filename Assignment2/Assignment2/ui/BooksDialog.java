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

import a01029917.data.books.Book;
import a01029917.data.books.SortBooks.CompareByAuthors;
import a01029917.db.BooksDao;
import net.miginfocom.swing.MigLayout;

/**
 * BooksDialog class extends JDialog. It creates a Dialog UI that displays the Book
 * information.
 * 
 * @author Andrew Hewitson, A01029917
 *
 */

@SuppressWarnings("serial")
public class BooksDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();

    private static final Logger LOG = LogManager.getLogger();
    private JTable table_1;
    private DefaultTableModel dtm;

    List<Book> bookList = new ArrayList<>();

    /**
     * Create the dialog.
     * 
     * @param booksDao
     *            The BooksDao to read
     */
    public BooksDialog(BooksDao booksDao) {
        setBounds(100, 100, 1018, 403);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.WEST);
        contentPanel.setLayout(new MigLayout("", "[968.00]", "[247.00,grow][]"));
        {

            table_1 = new JTable();
            dtm = new DefaultTableModel(0, 0);
            String header[] = new String[] { "Book ID", "ISBN", "Author Name", "Title", "Year", "Rating Average", "Rating Count", "Image URL" };

            dtm.setColumnIdentifiers(header);
            table_1.setModel(dtm);

            try {
                for (int count = 1; count <= booksDao.countAllbooks(); count++) {
                    dtm.addRow(new Object[] { "data", "data", "data", "data", "data", "data", "data", "data" });
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
                if (MainFrame.authorsDescending.isSelected() && MainFrame.chckbxmntmByAuthor.isSelected()) {
                    bookList = getBookSortedByAuthorDescending(booksDao);
                } else if (MainFrame.chckbxmntmByAuthor.isSelected()) {
                    bookList = getBookSortedByAuthor(booksDao);

                } else {
                    bookList = getBook(booksDao);
                }

                for (Book book1 : bookList) {
                    table_1.setValueAt(book1.getBookID(), index, 0);
                    table_1.setValueAt(book1.getiSBN(), index, 1);
                    table_1.setValueAt(book1.getAuthors(), index, 2);
                    table_1.setValueAt(book1.getTitle(), index, 3);
                    table_1.setValueAt(book1.getYear(), index, 4);
                    table_1.setValueAt(book1.getRatings(), index, 5);
                    table_1.setValueAt(book1.getRatingsCount(), index, 6);
                    table_1.setValueAt(book1.getImageURL(), index, 7);

                    index++;
                }

            } catch (Exception e) {

                LOG.error(e.getMessage());
            }
            JScrollPane scrollPane = new JScrollPane(table_1);
            scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
            contentPanel.add(scrollPane, "cell 0 0,growx");
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
                        LOG.info("Book Dialog Closed");
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
                        LOG.info("Book Dialog Closed");
                        setVisible(false);
                    }

                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                getRootPane().setDefaultButton(cancelButton);

            }
        }
    }

    /**
     * 
     * @param db
     * @return
     */
    private ArrayList<Book> getBook(BooksDao db) {

        ArrayList<Book> bookList = new ArrayList<>();
        try {
            for (Integer tempIds : db.getIds()) {
                Book book = db.getBook(tempIds);
                bookList.add(book);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return bookList;
    }

    /**
     * 
     * @param db
     * @return
     */
    private ArrayList<Book> getBookSortedByAuthor(BooksDao db) {

        ArrayList<Book> bookList = getBook(db);
        Collections.sort(bookList, new CompareByAuthors());
        return bookList;
    }

    /**
     * 
     * @param db
     * @return
     */
    private ArrayList<Book> getBookSortedByAuthorDescending(BooksDao db) {
        ArrayList<Book> reversedBook = new ArrayList<>();
        reversedBook = getBookSortedByAuthor(db);
        Collections.reverse(reversedBook);
        return reversedBook;
    }
}
