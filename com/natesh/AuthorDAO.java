package com.natesh;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorDAO {
	public static boolean create(Author author) {
		String sql = "insert into author values (? , ? , ? )" ;
		Connection con = DBConnectionManager.getConnection() ; 
		int rows =0 ; 
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, author.name);
			ps.setString(2, author.email);
			ps.setString(3, author.isbn);

			rows =ps.executeUpdate() ; 

		} catch (SQLException e) {
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		} 
		return rows>0 ; 
	}

	public static boolean delete(String email){
		String sql = "delete from author where email=?" ;
		Connection con = DBConnectionManager.getConnection() ;
		int rows =0 ;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			rows =ps.executeUpdate() ;

		} catch (SQLException e) {
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		}
		return rows>0 ;
	}

	public static boolean deleteFromISBN(String isbn){
		String sql = "delete from author where bookisbn=?" ;
		Connection con = DBConnectionManager.getConnection() ;
		int rows =0 ;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, isbn);
			rows =ps.executeUpdate() ;

		} catch (SQLException e) {
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		}
		return rows>0 ;
	}
}
