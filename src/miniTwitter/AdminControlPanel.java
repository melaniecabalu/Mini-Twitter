package miniTwitter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame{
	private static AdminControlPanel instance; 	//Ensure only one instance of AdminControlPanel
	private JPanel contentPane;
	private JTree tree;
	private String userId;
	private String groupId;
	private JTextField userIdTextField;
	private JTextField groupIdTextField;
	private JButton addUserButton;		
	private JButton addGroupButton;		
	private JButton openUserViewButton;		
	private JButton showUserTotalButton;		
	private JButton showGroupTotalButton;		
	private JButton showMessagesTotalButton;		
	private JButton showPositivePercentageButton;	
	private Database database;
	private TreeModel model;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode selectedNode;	
	
	//Ensures one instance of AdminControlPanel
	public static AdminControlPanel getInstance(){
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}

	private AdminControlPanel() {	
		root = new DefaultMutableTreeNode("Root");
		database = Database.getInstance();
		
		//Set bounds and size of the window 
		setBounds(100, 100, 656, 350);
		
		//Specify what happens when the close button is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add other components to the window
		buildPanel();		
	}
	
	public void buildPanel(){		
		//Create user ID field 20 characters wide
		userIdTextField = new JTextField(20);
		userIdTextField.setBounds(185, 6, 278, 20);
				
		//Create group ID field 20 characters wide
		groupIdTextField = new JTextField(20);
		groupIdTextField.setBounds(185, 37, 278, 20);

		//Create Add User button
		addUserButton = new JButton("Add User");
		addUserButton.setBounds(473, 5, 159, 23);
		
		//Add an action listener to Add User button
		addUserButton.addActionListener(new AddUserButtonListener());
		
		//Create Add Group button
		addGroupButton = new JButton("Add Group");
		addGroupButton.setBounds(473, 39, 159, 23);		
		
		//Add an action listener to Add Group button
		addGroupButton.addActionListener(new AddGroupButtonListener());
		
		//Create Show User Total button
		showUserTotalButton = new JButton("Show User Total");
		showUserTotalButton.setBounds(185, 250, 217, 23);
		
		//Add an action listener to Show User Total button
		showUserTotalButton.addActionListener(new ShowUserTotalButtonListener());
		
		//Create Show Group Total Button
		showGroupTotalButton = new JButton("Show Group Total");
		showGroupTotalButton.setBounds(415, 250, 217, 23);
		
		//Add an action listener to Show Group Total button
		showGroupTotalButton.addActionListener(new ShowGroupTotalButtonListener());
		
		//Create Show Messages Total Button
		showMessagesTotalButton = new JButton("Show Messages Total");
		showMessagesTotalButton.setBounds(185, 278, 217, 23);
		
		//Add an action listener to Show Messages Total button
		showMessagesTotalButton.addActionListener(new ShowMessagesTotalButtonListener());
		
		//Create Show Positive Percentage button
        showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.setBounds(415, 278, 217, 23);
        
		//Add an action listener to Open User View button
        showPositivePercentageButton.addActionListener(new ShowPositivePercentageButtonListener());
		
		//Create Open User View button
		openUserViewButton = new JButton("Open User View");
		openUserViewButton.setBounds(185, 68, 447, 23);

		//Add an action listener to Open User View button
		openUserViewButton.addActionListener(new OpenUserViewButtonListener());

		//Create a JPanel object and let the contentPane field reference it
		//A JPanel object is used to hold other components
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Add components to contentPane 
		contentPane.add(userIdTextField);
		contentPane.add(addUserButton);
		contentPane.add(groupIdTextField);
		contentPane.add(addGroupButton);
		contentPane.add(openUserViewButton);
		contentPane.add(showUserTotalButton);
		contentPane.add(showGroupTotalButton);
		contentPane.add(showMessagesTotalButton);
        contentPane.add(showPositivePercentageButton);

		//Build the JTree
		buildTree();
	}
	
	private class AddUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			userId = userIdTextField.getText();
			User u = new User(userId);
			
			if (database.containsUser(userId)){
				showMessage("ERROR: " + userIdTextField.getText() + " already exists as a user.");
			}
			else if (selectedNode == null){
				//Add user to database
				database.addUser(userId, "Root", u);
				
				//Add user to the root of the JTree
				root.add(new DefaultMutableTreeNode(userId));
				
				showMessage(userId + " has been added as a user.");
			}
			else{				
				//Add user to the database
				database.addUser(userId, selectedNode.toString(), u);
				
				//Add user to the selected group
				selectedNode.add(new DefaultMutableTreeNode(userId));

				showMessage(userIdTextField.getText() + " has been added as a user.");
			}
			
			//Reload the JTree
			expandTree();
			expandAllNodes(tree, 0, tree.getRowCount());

		}
	}

	private class AddGroupButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){			
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			groupId = groupIdTextField.getText();
			UserGroup g = new UserGroup(groupId);
			
			if (database.containsGroup(groupId)){
				showMessage("ERROR: " + groupIdTextField.getText() + " already exists as a group.");
			}
			else if (selectedNode == null){
				//Add group to the database
				database.addGroup(groupId, "Root", g);
				
				//Add group to the root
				root.add(new DefaultMutableTreeNode(groupId));
				expandAllNodes(tree, 0, tree.getRowCount());
				
				showMessage(groupId + " has been added as a group.");
			}
			else{
				database.addGroup(groupId, selectedNode.toString(), g);
				showMessage(groupIdTextField.getText() + " has been added as a group. Please add a user to your group.");
				selectedNode.add(new DefaultMutableTreeNode(groupId));   			
			}
			
			expandTree();
			expandAllNodes(tree, 0, tree.getRowCount());
		}
	}
	
	
	private class ShowUserTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			UserTotalVisitor userTotal = new UserTotalVisitor();
			database.accept(userTotal);
			showMessage("Total number of users: " + userTotal.size());
		}
	}
	
	private class ShowGroupTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			GroupTotalVisitor groupTotal = new GroupTotalVisitor();
			database.accept(groupTotal);
			showMessage("Total number of groups: " + groupTotal.size());
		}
	}
	
	private class OpenUserViewButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){			
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (selectedNode == null){
    			showMessage("ERROR: Please select a user.");
            }
            else{
    			UserView userView = new UserView(selectedNode.toString());
    			userView.setVisible(true);								       //Display user view for selected user
            }
		}
	}
	
	private class ShowMessagesTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			MessagesTotalVisitor messagesTotalVis = MessagesTotalVisitor.getInstance();
			//showMessage("Total number of messages: " + database.getMessagesTotal());
			showMessage("Total number of messages: " + messagesTotalVis.size());
		}
	}
	
	private class ShowPositivePercentageButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			PositivePercentageVisitor posPercentageVis = PositivePercentageVisitor.getInstance();
			showMessage("Percentage of positive words: " + posPercentageVis.percentage() + "%");
		}
	}
	
	public void buildTree(){
		tree = new JTree(root);
		tree.setBounds(10, 6, 152, 295);
		contentPane.add(tree);
		selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		model = tree.getModel();
		
		ImageIcon imageIcon = new ImageIcon(AdminControlPanel.class.getResource("/folder_closed.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		
		//Set folder icon to non-leaf nodes
		renderer.setOpenIcon(imageIcon);
        tree.setCellRenderer(renderer);
	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void expandTree(){
		for(int i=0;i<tree.getRowCount();i++){
		    tree.expandRow(i);
		}
		((DefaultTreeModel) model).reload();
	}
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
}
