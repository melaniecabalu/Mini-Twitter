package miniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserView extends JFrame {

	private JPanel contentPane;
	private User targetUser;
	private String userId;
	private JTextField userIdTextField;
	private JButton followUserButton;
	private Database userDatabase;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public UserView(String s) {
		userDatabase = Database.getInstance();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userIdTextField = new JTextField();
		userIdTextField.setBounds(10, 11, 187, 20);
		contentPane.add(userIdTextField);
		userIdTextField.setColumns(20);
		
		followUserButton = new JButton("Follow User");
		followUserButton.setBounds(207, 10, 217, 23);
		
		//Add an action listener to Open User View button
		followUserButton.addActionListener(new FollowUserButtonListener());
		
		contentPane.add(followUserButton);
	}
	
	//Follow user: add user to userId's follower list
	private class FollowUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			userId = userIdTextField.getText();
			// = userDatabase.getUser(userId);
			
			
			
		}
	}
}
