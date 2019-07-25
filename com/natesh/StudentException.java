package com.natesh;

import java.sql.SQLException;

public class StudentException  extends RuntimeException{
	String msg ; 
	public StudentException(String msg) {
		this.msg = msg ; 
	}

}
