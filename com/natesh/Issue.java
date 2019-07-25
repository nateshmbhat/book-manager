package com.natesh;

import java.sql.Date;

public class Issue {
	String id  ; 
	Date issueDate ; 
	Date returnDate ; 
	Student student ; 
	Book book ; 

	public Issue(String id, Date issueDate, Date returnDate, Student student, Book book) {
		super();
		this.id = id;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.student = student;
		this.book = book;
	}
}
