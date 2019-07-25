package com.natesh;
import java.sql.* ; 

public class DBConnectionManager {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver") ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static Connection getConnection() {
		Connection con = null ; 
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager" , "root" , "password") ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return con ; 
	}
}
