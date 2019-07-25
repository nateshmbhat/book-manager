package com.natesh;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BookDAO {
    public static boolean create(Book book, Author author) {
        String sql = "insert into book values (? , ? , ? )";
        String authorsql = "insert into author values (? , ? , ? )";
        Connection con = DBConnectionManager.getConnection();
        int rows = 0;
        int authorrows = 0;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, book.isbn);
            ps.setString(2, book.title);
            ps.setString(3, book.category);
            rows = ps.executeUpdate();

            ps = con.prepareStatement(authorsql);
            ps.setString(1, author.name);
            ps.setString(2, author.email);
            ps.setString(3, author.isbn);
            authorrows = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BookManagementException(e.getMessage());
        }
        return rows > 0 || authorrows > 0;
    }

    public static boolean delete(String isbn) {
        String sql = "delete from book where isbn=?";
        String authorsql = "delete from author where bookisbn=?";
        Connection con = DBConnectionManager.getConnection();
        int rows = 0;
        int authorrows = 0;
        PreparedStatement ps;
        try {

            ps = con.prepareStatement(authorsql);
            ps.setString(1, isbn);
            authorrows = ps.executeUpdate();

            ps = con.prepareStatement(sql);
            ps.setString(1, isbn);
            rows = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BookManagementException(e.getMessage());
        }
        return rows > 0 || authorrows > 0;
    }


    private static DefaultTableModel searchByField(String field, String val) {
        String sql = "select isbn,title,category from book where " + field + " like '%" + val + "%'";
        Connection con = DBConnectionManager.getConnection();
        int rows = 0;
        PreparedStatement ps;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("Book ISBN");
        colNames.add("Title");
        colNames.add("Category");
        try {
            ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;
            int i = 0;
            while (rs != null && rs.next()) {
                found = true;
                Vector<String> v = new Vector<>();
                v.add(rs.getString("isbn"));
                v.add(rs.getString("title"));
                v.add(rs.getString("category"));
                data.add(v);
                System.out.println("Book " + i++ + " : ISBN = " + rs.getString("isbn") + " , Title = " + rs.getString("title")
                        + " , Category = " + rs.getString("category")
                );
            }
            if (!found) {
                System.out.println("\nNo Result Found !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BookManagementException(e.getMessage());
        }
        return new DefaultTableModel(data, colNames);
    }

    public static DefaultTableModel SearchByTitle(String val) {
        return searchByField("title", val);
    }

    public static DefaultTableModel SearchByISBN(String val) {
        return searchByField("isbn", val);
    }

    public static DefaultTableModel SearchByCategory(String val) {
        return searchByField("category", val);
    }

    public static DefaultTableModel listAll() {
        System.out.println("\nListing All Books : ");
        String sql = "select b.isbn ,title,category ,name,email from book  as b , author as a where a.bookisbn=b.isbn";
        Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps;

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("ISBN");
        colNames.add("Title");
        colNames.add("Category");
        colNames.add("Author Name");
        colNames.add("Author Email");

        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i = 1;
            while (rs != null && rs.next()) {
                Vector<String> v = new Vector<>();
                v.add(rs.getString("isbn"));
                v.add(rs.getString("title"));
                v.add(rs.getString("category"));
                v.add(rs.getString("name"));
                v.add(rs.getString("email"));
                data.add(v);
                System.out.println("Book " + i++ + " : ISBN = " + rs.getString("isbn") + " , Title = " + rs.getString("title")
                        + " , Category = " + rs.getString("category")
                        + "\nAuthor : name = " + rs.getString("name") + " , email = " + rs.getString("email")
                        + "\n"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();

            throw new BookManagementException(e.getMessage());

        }
        return new DefaultTableModel(data, colNames);
    }

    public static DefaultTableModel SearchByAuthorEmail(String email) {
        String sql = "select isbn,title,category from book where isbn in( select bookisbn from author where email like '%" + email + "%')";
        Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("ISBN");
        colNames.add("Title");
        colNames.add("Category");

        try {
            ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;
            int i = 1;
            while (rs != null && rs.next()) {
                found = true;
                Vector<String> v = new Vector<>();
                v.add(rs.getString("isbn"));
                v.add(rs.getString("title"));
                v.add(rs.getString("category"));
                data.add(v);

                System.out.println("Book " + i++ + " : ISBN = " + rs.getString("isbn") + " , Title = " + rs.getString("title")
                        + " , Category = " + rs.getString("category")
                );
            }
            if (!found) {
                System.out.println("\nNo Result Found !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BookManagementException(e.getMessage());
        }
        return new DefaultTableModel(data, colNames);
    }

    public static DefaultTableModel SearchByAuthorName(String name) {
        String sql = "select isbn,title,category from book where isbn in( select bookisbn from author where name like '%" + name + "%')";
        Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("ISBN");
        colNames.add("Title");
        colNames.add("Category");

        try {
            ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;
            int i = 1;
            while (rs != null && rs.next()) {
                found = true;
                Vector<String> v = new Vector<>();
                v.add(rs.getString("isbn"));
                v.add(rs.getString("title"));
                v.add(rs.getString("category"));
                data.add(v);

                System.out.println("Book " + i++ + " : ISBN = " + rs.getString("isbn") + " , Title = " + rs.getString("title")
                        + " , Category = " + rs.getString("category")
                );
            }
            if (!found) {
                System.out.println("\nNo Result Found !");
            }

        } catch (SQLException e) {
            e.printStackTrace();

            throw new BookManagementException(e.getMessage());
        }
        return new DefaultTableModel(data, colNames);
    }
}
