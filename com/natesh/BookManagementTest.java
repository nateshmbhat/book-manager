package com.natesh;

import java.util.Scanner;

public class BookManagementTest {
	public static void main(String[] args) {
		while(true) {
			Scanner scan = new Scanner(System.in);  
			System.out.println("1.Add a book\n"
					+ "2. Search book based on title\n"
					+ "3. Search book based on category\n"
					+"4. Search book based on author\n"
					+"5. List all books\n"
					+"6. Issue book to student\n"
					+"7. List books issued to student based on usn\n"
					+"8. List books to be returned today\n"
					+ "9. Exit\n\nEnter choice : "); 
			int ch = scan.nextInt() ; 
			scan.nextLine().trim() ; 
			switch(ch) {
			case 1 :
				System.out.println("Enter book isbn , title , category : ");
				String isbn = scan.next() , title= scan.next() , cat = scan.nextLine().trim() ; 
				System.out.println("Enter author name and email : ");
				String name = scan.next() , email = scan.nextLine().trim() ; 
				
				if(BookDAO.create(new Book(title , isbn , cat), new Author(name , email , isbn))) {
					System.out.println("\nBook Created Successfully ! ");
				}
				else {
					System.out.println("\nNot able to create book !");
				}
				break ; 
			
			case 2 : 
				System.out.println("Enter title : "); 
				title = scan.nextLine().trim() ; 
				BookDAO.SearchByTitle(title);
				break ; 
			case 3 : 
				System.out.println("Enter category : "); 
				cat = scan.nextLine().trim() ; 
				BookDAO.SearchByCategory(cat);
				break ;
			case 4 : 
				System.out.println("Enter author name : "); 
				name = scan.next() ; 
				BookDAO.SearchByAuthor(name);
				break ; 
			
			case 5 : 
					BookDAO.listAll();
					break ; 
			case 6 : 
					System.out.println("Enter usn of the student and the isbn of book : ");
					String usn = scan.next() ;
					isbn = scan.nextLine().trim() ; 
					IssueBookDAO.issue(usn, isbn) ; 
					break ;
			case 7 : 
				System.out.println("Enter usn : "); usn = scan.nextLine().trim() ; 
				IssueBookDAO.listIssuedUsn(usn);
				break ;
			case 8 :
				IssueBookDAO.listBooksToBeReturedToday();
				break ; 
			case 9 :
				System.exit(0);
			}
		}
	}
}
