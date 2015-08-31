package miniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class UserView extends JFrame{

	private JPanel contentPane;
	private String userId;
	private JTextField userIdTextField;
	private JButton followUserButton;
	private Database userDatabase;
	private JLabel currentlyFollowingLabel;
	private List<String> followingIdList;
	private DefaultListModel<String> followingListModel;
	private JList<String> followingList;
	private JLabel newsFeedLabel;
	private List<String> newsFeed;
	private DefaultListModel<String> newsFeedListModel;
	private JList<String> newsFeedList;
	private Component currentUser;
	private JTextField tweetField;	
	private JButton postTweetButton;
	private JScrollPane followingScrollPane;
	private JScrollPane newsFeedScrollPane;

	@SuppressWarnings("unchecked")
	public UserView(String s) {		
		//Get access to Mini Twitter database
		userDatabase = Database.getInstance();

		//Create DefaultListModel for the Currently Following List
		followingListModel = new DefaultListModel<String>();
		
		//Create ArrayList to store ids that user is following
		followingIdList = new ArrayList<String>();

		//Create a Default List Model for the News Feed List
		newsFeedListModel = new DefaultListModel<String>();
		
		//Declare ArrayList to store the tweets of all the users that the current user is following
		newsFeed = new ArrayList<String>();
				
		//Set the user view for the current user
		currentUser = userDatabase.getUser(s);
		
		//Specify what happens when the close button is clicked
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Set size and bounds of the UserView
		setBounds(100, 100, 450, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Create userIdTextField
		userIdTextField = new JTextField();
		userIdTextField.setBounds(10, 11, 187, 20);
		userIdTextField.setColumns(20);
		
		//Create Follow User Button
		followUserButton = new JButton("Follow User");
		followUserButton.setBounds(207, 10, 217, 23);
		
		//Add an action listener to Follow User button
		followUserButton.addActionListener(new FollowUserButtonListener());
		
		//Create Currently Following Label
		currentlyFollowingLabel = new JLabel("Currently Following:");
		currentlyFollowingLabel.setBounds(20, 44, 177, 14);
		
		//Initialize the Following List
		followingList = new JList<String>(followingListModel);
		followingList.setBounds(10, 65, 414, 143);
		followingIdList = (ArrayList<String>) currentUser.getFollowerIds().clone();

		//Populate the Following List Model with previously followed users
		for (int i = 0; i < followingIdList.size(); i++){
			followingListModel.addElement(followingIdList.get(i));
		}
	
		//Create tweetField
		tweetField = new JTextField();
		tweetField.setColumns(20);
		tweetField.setBounds(10, 232, 272, 20);
		
		//Create Post Tweet Button
		postTweetButton = new JButton("Post tweet");
		postTweetButton.setBounds(292, 231, 113, 23);
		
		//Add an action listener to the TweetButton
		postTweetButton.addActionListener(new PostTweetButtonListener());
	
		//Create newsFeedLabel
		newsFeedLabel = new JLabel("News Feed:");
		newsFeedLabel.setBounds(20, 263, 211, 14);
		
		//Initialize the News Feed List
		newsFeedList = new JList<String>(newsFeedListModel);
		newsFeedList.setBounds(20, 287, 385, 114);
		newsFeed = (ArrayList<String>) currentUser.getNewsFeed().clone();
		
		//Populate the News Feed Model with previous tweets
		for (int i = 0; i < newsFeed.size(); i++){
			newsFeedListModel.addElement(newsFeed.get(i));
		}

		//Add to content pane
		contentPane.add(userIdTextField);
		contentPane.add(followUserButton);
		contentPane.add(currentlyFollowingLabel);
		contentPane.add(followingList);
		contentPane.add(tweetField);
		contentPane.add(postTweetButton);
		contentPane.add(newsFeedLabel);
		contentPane.add(newsFeedList);
		
		//Create JScrollPane for the Currently Following List
		followingScrollPane = new JScrollPane(followingList);
		followingScrollPane.setBounds(10, 64, 414, 144);
		contentPane.add(followingScrollPane);
		
		//Create JScrollPane for the News Feed
		newsFeedScrollPane = new JScrollPane(newsFeedList);
		newsFeedScrollPane.setBounds(20, 287, 385, 114);
		contentPane.add(newsFeedScrollPane);
	}
	
	//Method to return the current user's id
	public String getUserId(){
		return currentUser.getId();		
	}
	
	private class FollowUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			//Set the user ID of the user to follow
			userId = userIdTextField.getText();
			
			//Retrieve the user associated with the user ID
			Component followedUser = userDatabase.getUser(userId);
			
			//If the user does not exist
			if (userDatabase.containsUser(userId) == false){
				showMessage("ERROR: that user ID does not exist.");
			}
			//If current user is already following the user
			else if (currentUser.isFollowing(followedUser)){
				showMessage("ERROR: you are already following " + userId + ".");
			}
			//If current user tries to follow himself/herself
			else if (userId.equals(currentUser.getId())){
				showMessage("ERROR: you cannot follow yourself.");
			}
			else{
				//Follow the specified user
				currentUser.follow(followedUser);
			
				//Attach the current user to the followers list of the specified user
				followedUser.attach(currentUser);
			
				//Display confirmation of the following
				showMessage("You are now following " + followedUser.getId() + ".");
				
				//Display the specified user in current user's following list view
				followingListModel.addElement(followedUser.getId());
			}
		}
	}
	
	private class PostTweetButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){				
			//Current user sends tweet to all followers
			currentUser.notify("-   " + currentUser.getId() + ": " + tweetField.getText());
			
			//Add tweet to current user's news feed view
			newsFeedListModel.addElement("-   " + currentUser.getId() + ": " + tweetField.getText());
			
			//Increment message total
			MessagesTotalVisitor messageTotalVis = MessagesTotalVisitor.getInstance();
			currentUser.accept(messageTotalVis);
				
			//Check for positive words
			PositivePercentageVisitor posPercentageVis = PositivePercentageVisitor.getInstance();
			currentUser.accept(posPercentageVis);
		};
	}	

	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
}
