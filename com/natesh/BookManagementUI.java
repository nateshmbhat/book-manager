package com.natesh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookManagementUI {
    private JPanel mainPanel;
    private JPanel LeftTabPanel;
    private JButton issuedBooksPanelButton;
    private JButton booksPanelButton;
    private JButton studentsPanelButton;
    private JPanel RightMainPanel;
    private JScrollPane issuesCard;
    private JScrollPane booksCard;
    private JScrollPane studentsCard;
    private JTable issueTable;
    private JButton issueDeleteButton;
    private JTextField issueUsnTextField;
    private JTextField issueISBNTextField;
    private JButton issueSubmitButton;
    private JTable booksCardTable;
    private JButton bookDeleteButton;
    private JButton bookSubmitButton;
    private JTextField bookTitleField;
    private JTextField bookIsbnField;
    private JTextField bookCategoryField;
    private JTextField bookAuthornameField;
    private JLabel bookAuthorEmaiLabel;
    private JTextField bookAuthoremailField;
    private JTextField studentUSNField;
    private JTextField studentNameField;
    private JButton studentSubmitButton;
    private JButton studentDeleteButton;
    private JTable studentTable;
    private JButton bookSearchButton;
    private JButton issuedBooksSeachButton;
    private JButton issueShowTodaysDueBookButton;
    private JButton issueResetButton;
    private JButton booksResetButton;

    public BookManagementUI() {

        issueTable.setModel(IssueBookDAO.getTableModel());
        booksCardTable.setModel(BookDAO.listAll());
        studentTable.setModel(StudentDAO.getTableModel());

        studentSubmitButton.addActionListener(actionEvent -> {
            try {
                StudentDAO.create(new Student(studentNameField.getText(), studentUSNField.getText()));
                studentTable.setModel(StudentDAO.getTableModel());

            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });

        bookSubmitButton.addActionListener(actionEvent -> {
            try {
                BookDAO.create(new Book(bookTitleField.getText(), bookIsbnField.getText(), bookCategoryField.getText()),
                        new Author(bookAuthornameField.getText(), bookAuthoremailField.getText(), bookIsbnField.getText())
                );
                booksCardTable.setModel(BookDAO.listAll());
            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });

        issueSubmitButton.addActionListener(actionEvent -> {
            try {
                IssueBookDAO.issue(issueUsnTextField.getText(), issueISBNTextField.getText());
                issueTable.setModel(IssueBookDAO.getTableModel());

            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });

        issueDeleteButton.addActionListener(actionEvent -> {
            if (issueTable.getSelectedRow() < 0) return;
            int id = Integer.parseInt((String) issueTable.getValueAt(issueTable.getSelectedRow(), 0));
            try {

                IssueBookDAO.delete(id);
                issueTable.setModel(IssueBookDAO.getTableModel());

            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });


        bookDeleteButton.addActionListener(actionEvent -> {
            if (booksCardTable.getSelectedRow() < 0) return;
            try {

                String isbn = (String) booksCardTable.getValueAt(booksCardTable.getSelectedRow(), 0);
                BookDAO.delete(isbn);
                booksCardTable.setModel(BookDAO.listAll());
            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });

        studentDeleteButton.addActionListener(actionEvent -> {
            String usn;
            if (studentTable.getSelectedRow() < 0) return;
            int selected = studentTable.getSelectedRow();
            usn = (String) studentTable.getValueAt(selected, 0);
            try {

                StudentDAO.deleteStudent(usn);
                studentTable.setModel(StudentDAO.getTableModel());
            } catch (BookManagementException e) {
                Utility.showErrorMessage(e.getMessage(), mainPanel);
            }
        });

        issuedBooksPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    CardLayout cl = (CardLayout) RightMainPanel.getLayout();
                    cl.show(RightMainPanel, "issuesCard");

                } catch (BookManagementException e) {
                    Utility.showErrorMessage(e.getMessage(), mainPanel);
                }
            }
        });


        booksPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                    CardLayout cl = (CardLayout) RightMainPanel.getLayout();
                    cl.show(RightMainPanel, "booksCard");
                } catch (BookManagementException e) {
                    Utility.showErrorMessage(e.getMessage(), mainPanel);
                }
            }
        });


        studentsPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    CardLayout cl = (CardLayout) RightMainPanel.getLayout() ;
                    cl.show(RightMainPanel, "studentsCard");
                } catch (BookManagementException e) {
                    Utility.showErrorMessage(e.getMessage(), mainPanel) ;
                }
            }
        });

        issueShowTodaysDueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try{
                    issueTable.setModel(IssueBookDAO.listBooksToBeReturedToday()) ;
                }  catch (BookManagementException e) {
                    Utility.showErrorMessage(e.getMessage(), mainPanel);
                }

            }
        });
        issuedBooksSeachButton.addActionListener(actionEvent -> {
           if(!issueUsnTextField.getText().trim().isEmpty()) {
                issueTable.setModel(IssueBookDAO.listIssuedUsn(issueUsnTextField.getText().trim()));
           }
           else if(!issueISBNTextField.getText().trim().isEmpty()) {
               issueTable.setModel(IssueBookDAO.listIssuedISBN(issueISBNTextField.getText().trim()));
           }
        });
        issueResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                issueTable.setModel(IssueBookDAO.getTableModel());
            }
        });
        booksResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                booksCardTable.setModel(BookDAO.listAll());
            }
        });
        bookSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String title = bookTitleField.getText().trim() ;
                String isbn = bookIsbnField.getText().trim() ;
                String cat = bookCategoryField.getText().trim() ;
                String authorname = bookAuthornameField.getText().trim() ;
                String authoremail= bookAuthoremailField.getText().trim() ;

                DefaultTableModel target  ;
                if(!title.isEmpty()){
                    target = BookDAO.SearchByTitle(title) ;
                }
                else if(!isbn.isEmpty()){
                    target = BookDAO.SearchByISBN(isbn) ;
                }
                else if(!cat.isEmpty()) target = BookDAO.SearchByCategory(cat) ;
                else if(!authorname.isEmpty()) target = BookDAO.SearchByAuthor(authorname) ;
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Book Management");
        try {
            frame.setContentPane(new BookManagementUI().mainPanel);
        } catch (BookManagementException e) {
            Utility.showErrorMessage(e.getMessage(), frame);
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.pack();
    }
}
