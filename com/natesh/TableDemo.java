package com.natesh;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TableDemo extends JFrame {
	private JTable table ; 
	private JScrollPane scrollPane ; 
	private JButton btnDelete ; 
	
	public TableDemo() {
		setTitle("table") ; 
		setTitle("Add Student");
		setSize(300 , 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new JTable(StudentDAO.getTableModel()) ; 
		btnDelete = new JButton("Delete" ) ; 
		scrollPane = new JScrollPane(table) ; 

		add(btnDelete , BorderLayout.SOUTH ) ; 
		add(scrollPane) ; 
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int sel = table.getSelectedRow() ; 
				String usn = (String) table.getValueAt( sel, 1) ; 
				System.out.println("USN selected = " + usn);
				if( StudentDAO.deleteStudent(usn)) {
					table.setModel(StudentDAO.getTableModel()) ; 
				}
			}
		} );
			
		setVisible(true);
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TableDemo() ; 
			}
		});
	}

}
