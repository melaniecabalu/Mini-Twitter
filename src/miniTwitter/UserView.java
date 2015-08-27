package miniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserView extends JFrame {

	private JPanel contentPane;
	private String userId;
	private JTextField userIdTextField;
	private JButton followUserButton;
	private Database userDatabase;
	private JTextField textField;
	private User currentUser;
	private ArrayList<String> followingIdList;
	private JList followingList;
	private JLabel currentlyFollowingLabel;
	private DefaultListModel<String> listModel;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public UserView(String s) {
		listModel = new DefaultListModel<String>();
		followingIdList = new ArrayList<String>();
		
		userDatabase = Database.getInstance();
		
		currentUser = userDatabase.getUser(s);

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
		
		//set Currently Following Label
		currentlyFollowingLabel = new JLabel("Currently Following:");
		currentlyFollowingLabel.setBounds(20, 44, 177, 14);
		
		followingList = new JList<String>(listModel);
		followingList.setBounds(19, 65, 386, 143);
		
		//Populate the Current Following list with previously followed users
		followingIdList = (ArrayList<String>) currentUser.getFollowerIds().clone();

		for (int i = 0; i < followingIdList.size(); i++){
			listModel.addElement(followingIdList.get(i));
		}
				
		//Add to content pane
		contentPane.add(followUserButton);
		contentPane.add(currentlyFollowingLabel);
		contentPane.add(followingList);

	}
	
	//Follow user: add user to userId's follower list
	private class FollowUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			userId = userIdTextField.getText();
			
			User followedUser = userDatabase.getUser(userId);
			
			if (userDatabase.containsUser(userId) == false){
				JOptionPane.showMessageDialog(null,"ERROR: that user ID does not exist.");
			}
			else if (currentUser.isFollowing(followedUser)){
				JOptionPane.showMessageDialog(null,"ERROR: you are already following " + userId + ".");
			}
			else if (userId.equals(currentUser.getId())){
				JOptionPane.showMessageDialog(null,"ERROR: you cannot follow yourself.");
			}
			else{
				//following
				currentUser.follow(followedUser);
			
				//attach to the followers of user
				followedUser.attach(currentUser);
			
				JOptionPane.showMessageDialog(null,"You are now following " + followedUser.getId() + ".");
				listModel.addElement(followedUser.getId());
			}
		}
	}
}
