package com.natesh ; 

import java.sql.* ;
import java.util.* ;

import javax.swing.table.DefaultTableModel; 

public class StudentDAO { //DATA ACCESS OBJECT = DAO

	public static boolean deleteStudent(String usn) {
		String sql = "delete from student where usn=?" ; 
		int rows= 0 ; 

		try(Connection con = DBConnectionManager.getConnection()){
			PreparedStatement ps =  con.prepareStatement(sql) ; 
			ps.setString(1, usn);
			rows = ps.executeUpdate() ; 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		}

		return rows>0 ; 
	}
	
	
	public static DefaultTableModel getTableModel() {
		String sql = "select name,usn from student" ; 
		Vector<String> colNames = new Vector<String>() ; 
		colNames.add("USN") ;
		colNames.add("Name") ;
		Vector<Vector<String>> data = new Vector<Vector<String>>() ;

		try(Connection con = DBConnectionManager.getConnection()){
			PreparedStatement ps =  con.prepareStatement(sql) ; 
			ResultSet rs = ps.executeQuery() ; 

			while(rs!=null && rs.next())
			{
				Vector<String> v = new Vector<String>() ;
				v.add(rs.getString("usn")) ;
				v.add(rs.getString("name")) ;
				data.add(v) ;
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		}

		return new DefaultTableModel(data, colNames) ; 
	}
	
	public List<Student> getAllStudents(){
		String sql = "select rollno,name from student" ; 
		List<Student> studs = new ArrayList<>() ; 

		try(Connection con = DBConnectionManager.getConnection()){
			PreparedStatement ps =  con.prepareStatement(sql) ; 
			ResultSet rs = ps.executeQuery() ; 

			while(rs!=null && rs.next())
			{
				studs.add(new Student(rs.getString("name") , rs.getString("usn"))) ; 
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ; throw new BookManagementException(e.getMessage()) ; 
		}
		return studs ; 
	}

	
	public static boolean create(Student student ) {
		String sql = "insert into student(name,usn) values(?,?)" ; 
		int rows = 0 ; 
		try(Connection con = DBConnectionManager.getConnection()){
			PreparedStatement ps =  con.prepareStatement(sql) ; 
			ps.setString(1 , student.name);
			ps.setString(2 , student.usn) ; 
			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace() ;
			throw new BookManagementException(e.getMessage()) ;
		}
	return rows > 0 ; 
	}

}
