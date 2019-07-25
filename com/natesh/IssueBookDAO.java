package com.natesh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class IssueBookDAO {

    public static boolean delete(int id) {
        Connection con = DBConnectionManager.getConnection();
        String sql = "delete from BookIssue where id=?";
        int rows = 0;
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                rows = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }
        return rows > 0;
    }

     public static boolean delete(String isbn , String usn) {
        Connection con = DBConnectionManager.getConnection();
        String sql = "delete from BookIssue where isbn=? and usn=";
        int rows = 0;
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, isbn);
                ps.setString(2, usn);
                rows = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }
        return rows > 0;
    }


    public static DefaultTableModel getTableModel() {
        Connection con = DBConnectionManager.getConnection();
        String sql = "select * from BookIssue";
        int rows = 0;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();

        colNames.add("ID");
        colNames.add("USN");
        colNames.add("Issue Date");
        colNames.add("Return Date");
        colNames.add("Book ISBN");

        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs != null && rs.next()) {
                    Vector<String> v = new Vector<String>();
                    v.add(rs.getString("id"));
                    v.add(rs.getString("usn"));
                    v.add(rs.getString("issueDate"));
                    v.add(rs.getString("returnDate"));
                    v.add(rs.getString("isbn"));
                    data.add(v);
                }

            } catch (SQLException e) {
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }

        return new DefaultTableModel(data, colNames);
    }




    public static DefaultTableModel listIssuedISBN(String isbn) {
        Connection con = DBConnectionManager.getConnection();
        String sql = "select * from BookIssue where isbn=?";
        int rows = 0;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("Student USN");
        colNames.add("IssueDate");
        colNames.add("Return Date");
        colNames.add("ISBN");

        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, isbn);
                ResultSet rs = ps.executeQuery();

                while (rs != null && rs.next()) {
                    Vector<String> v = new Vector<String>();
                    v.add(rs.getString("usn"));
                    v.add(rs.getString("issueDate"));
                    v.add(rs.getString("returnDate"));
                    v.add(rs.getString("isbn"));
                    data.add(v);
                }

            } catch (SQLException e) {
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ;
            }
        }

        return new DefaultTableModel(data, colNames);
    }


    public static DefaultTableModel listIssuedUsn(String usn) {
        Connection con = DBConnectionManager.getConnection();
        String sql = "select bi.returnDate,s.name, b.title from student s , book b, BookIssue bi where s.usn=? and bi.usn=s.usn and bi.isbn=b.isbn";
        int rows = 0;
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("Book Title");
        colNames.add("Student Name");
        colNames.add("Return Date");

        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, usn);
                ResultSet rs = ps.executeQuery();

                while (rs != null && rs.next()) {
                    Vector<String> v = new Vector<String>();
                    v.add(rs.getString("title"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("returnDate"));
                    data.add(v);
                    System.out.println("Book title = " + rs.getString("title") + " , Student name = " + rs.getString("name") + " , Return Date = " + rs.getDate("returnDate"));
                }

            } catch (SQLException e) {
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }

        return new DefaultTableModel(data, colNames);
    }


    public static DefaultTableModel listBooksToBeReturedToday() {
        Connection con = DBConnectionManager.getConnection();
        Date date = Calendar.getInstance().getTime();
        DateFormat dformat = new SimpleDateFormat("YYYY-MM-dd");
        String sql = "select isbn , title , category from book where isbn in (select isbn from BookIssue where returnDate=?)";
        int rows = 0;

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> colNames = new Vector<String>();
        colNames.add("Book Title");
        colNames.add("ISBN");
        colNames.add("Return Date");

        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, dformat.format(date));

                ResultSet rs = ps.executeQuery();

                while (rs != null && rs.next()) {
                    Vector<String> v = new Vector<String>();
                    v.add(rs.getString("title"));
                    v.add(rs.getString("isbn"));
                    v.add(rs.getString("returnDate"));
                    data.add(v);
                    System.out.println("Book title = " + rs.getString("title") + " , Isbn = " + rs.getString("isbn") + " , Category = " + rs.getDate("returnDate"));
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }
        return new DefaultTableModel(data , colNames) ;
    }


    public static boolean issue(String usn, String isbn) {
        Connection con = DBConnectionManager.getConnection();
        String sql = "insert into BookIssue(usn,issueDate,returnDate,isbn) values( ? , ? , ? , ?)";
        int rows = 0;
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                Calendar cal = Calendar.getInstance();
                Date curdate = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date returnDate = cal.getTime();
                DateFormat dformat = new SimpleDateFormat("YYYY-MM-dd");

                ps.setString(1, usn);
                ps.setString(2, dformat.format(curdate));
                ps.setString(3, dformat.format(returnDate));
                ps.setString(4, isbn);

                rows = ps.executeUpdate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
            }
        }

        return rows > 0;
    }
}
