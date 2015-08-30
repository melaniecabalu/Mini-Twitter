package miniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class UserView extends JFrame{

	private JPanel contentPane;
	private String userId;
	private JTextField userIdTextField;
	private JButton followUserButton;
	private Database userDatabase;
	
	private ArrayList<String> followingIdList;
	private DefaultListModel<String> followingListModel;
	private JList<String> followingList;

	private ArrayList<String> newsFeed;
	private DefaultListModel<String> newsFeedListModel;
	private JList<String> newsFeedList;
	
	private User currentUser;
	private JLabel currentlyFollowingLabel;
	private JTextField tweetField;	
	private JButton postTweetButton;
	private JLabel newsFeedLabel;
	
	private JScrollPane followingScrollPane;
	private JScrollPane newsFeedScrollPane;
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public UserView(String s) {		
		//Get access to Mini Twitter database
		userDatabase = Database.getInstance();

		followingListModel = new DefaultListModel<String>();
		followingIdList = new ArrayList<String>();

		newsFeedListModel = new DefaultListModel<String>();
		newsFeed = new ArrayList<String>();
				
		//Set the user view for the current user
		currentUser = userDatabase.getUser(s);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 451);
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
		
		//Add an action listener to Follow User button
		followUserButton.addActionListener(new FollowUserButtonListener());
		
		//set Currently Following Label
		currentlyFollowingLabel = new JLabel("Currently Following:");
		currentlyFollowingLabel.setBounds(20, 44, 177, 14);
		
		//Initialize the Following List
		followingList = new JList<String>(followingListModel);
		followingList.setBounds(10, 65, 414, 143);
		
		//Get all the IDs of followed users and store in the Following List
		followingIdList = (ArrayList<String>) currentUser.getFollowerIds().clone();

		//Populate the Following List Model with previously followed users
		for (int i = 0; i < followingIdList.size(); i++){
			followingListModel.addElement(followingIdList.get(i));
		}
	
		tweetField = new JTextField();
		tweetField.setColumns(20);
		tweetField.setBounds(10, 232, 272, 20);
		
		postTweetButton = new JButton("Post tweet");
		postTweetButton.setBounds(292, 231, 113, 23);
		postTweetButton.addActionListener(new PostTweetButtonListener());
	
		newsFeedLabel = new JLabel("News Feed:");
		newsFeedLabel.setBounds(20, 263, 211, 14);
		
		//Initialize the News Feed List
		newsFeedList = new JList<String>(newsFeedListModel);
		newsFeedList.setBounds(20, 287, 385, 114);

		//Store news feed of current user in the User View
		newsFeed = (ArrayList<String>) currentUser.getNewsFeed().clone();
		
		//Populate the News Feed Model with previous tweets
		for (int i = 0; i < newsFeed.size(); i++){
			newsFeedListModel.addElement(newsFeed.get(i));
		}

		//Add to content pane
		contentPane.add(followUserButton);
		contentPane.add(currentlyFollowingLabel);
		contentPane.add(followingList);
		contentPane.add(tweetField);
		contentPane.add(postTweetButton);
		contentPane.add(newsFeedLabel);
		contentPane.add(newsFeedList);
		
		followingScrollPane = new JScrollPane(followingList);
		followingScrollPane.setBounds(10, 64, 414, 144);
		contentPane.add(followingScrollPane);
		
		newsFeedScrollPane = new JScrollPane(newsFeedList);
		newsFeedScrollPane.setBounds(20, 287, 385, 114);
		contentPane.add(newsFeedScrollPane);
	}
	
	public String getUserId(){
		return currentUser.getId();
	}
	
	//Follow user: add user to userId's follower list
	private class FollowUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			//Set the user ID of the user to follow
			userId = userIdTextField.getText();
			
			//Retrieve the user associated with the user ID
			User followedUser = userDatabase.getUser(userId);
			
			if (userDatabase.containsUser(userId) == false){
				showMessage("ERROR: that user ID does not exist.");
			}
			else if (currentUser.isFollowing(followedUser)){
				showMessage("ERROR: you are already following " + userId + ".");
			}
			else if (userId.equals(currentUser.getId())){
				showMessage("ERROR: you cannot follow yourself.");
			}
			else{
				//Follow the specified user
				currentUser.follow(followedUser);
			
				//Current user is attached to the followers list of the specified user
				followedUser.attach(currentUser);
			
				showMessage("You are now following " + followedUser.getId() + ".");
				
				//Display the specified user in current user's Following List
				followingListModel.addElement(followedUser.getId());
				
			}
		}
	}
	
	public DefaultListModel<String> getNewsFeedModel(){
		return newsFeedListModel;
	}
	
	private class PostTweetButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){				
			//Current user sends tweet to all followers
			currentUser.notify("-   " + currentUser.getId() + ": " + tweetField.getText());
			
			//Add tweet to current user's news feed
			newsFeedListModel.addElement("-   " + currentUser.getId() + ": " + tweetField.getText());
						
			MessagesTotalVisitor messageTotalVis = MessagesTotalVisitor.getInstance();
			currentUser.accept(messageTotalVis);
				
			PositivePercentageVisitor posPercentageVis = PositivePercentageVisitor.getInstance();
			currentUser.accept(posPercentageVis);
		};
	}	

	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
}
