package com.natesh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.* ;


public class FrontEndSwingInterfaceDemo extends JFrame {

		private JTextField txtFieldName  ;
		private JTextField txtFieldUSN;
		private JPanel mainPanel ;
		private JPanel editAndCreatePanel ;
		private JLabel nameLabel ;
		private JLabel usnLabel ;
		private JButton btnSubmit ;
		
		private void registerListeners() {
			btnSubmit.addActionListener(arg0 -> {

                if(txtFieldName.getText().trim().isEmpty() || txtFieldUSN.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "The fields should not be empty !" , "Error" , JOptionPane.ERROR_MESSAGE);
                    return ;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(StudentDAO.create(new Student(txtFieldName.getText() , txtFieldUSN.getText() ) )) {
                                txtFieldName.setText("");
                                txtFieldUSN.setText("");
                                System.out.println("Student created ! ");
                                JOptionPane.showMessageDialog(mainPanel, "Student Created Successfully" , "Success" , JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        catch(StudentException e) {
                            JOptionPane.showMessageDialog(mainPanel, "Duplicate entry not allowed !" , "Error" , JOptionPane.ERROR_MESSAGE);
                        }
                        catch(Exception e ) {
                            JOptionPane.showMessageDialog(mainPanel, e.getMessage()  , "Error" , JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }).start();
            }) ;
		}
		
		private FrontEndSwingInterfaceDemo() {
			setTitle("Add Student");
			setSize(300 , 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
			mainPanel = new JPanel() ;
			btnSubmit = new JButton("Submit") ; 
			txtFieldName = new JTextField(10) ; 
			txtFieldUSN = new JTextField(5) ; 
			nameLabel = new JLabel("Name") ; 
			usnLabel = new JLabel("USN") ; 
			
			mainPanel.add(nameLabel) ;
			mainPanel.add(txtFieldName) ;
			mainPanel.add(usnLabel) ;
			mainPanel.add(txtFieldUSN) ;
			mainPanel.add(btnSubmit) ;
			add(mainPanel) ;
			
			
			System.out.println("In constructor : "  + Thread.currentThread());
			setVisible(true);
			
			registerListeners();
		}
		
		public static void main(String[] args) {
			System.out.println("Created app : "  + Thread.currentThread());
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new FrontEndSwingInterfaceDemo() ;
				}
			});
		}

}
