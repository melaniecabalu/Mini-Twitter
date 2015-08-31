package miniTwitter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.JScrollPane;
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
	private JScrollPane treeScrollPane;
	
	//Ensures one instance of AdminControlPanel
	public static AdminControlPanel getInstance(){
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}

	private AdminControlPanel() {	
		//Create the root
		root = new DefaultMutableTreeNode("Root");
		
		//Get access to database
		database = Database.getInstance();
				
		//Set bounds and size of the Admin Control Panel
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
			//Declare variable for selected node
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			//Store userId
			userId = userIdTextField.getText();

			//If userId field is blank
			if (userId.isEmpty() == true){
				showMessage("ERROR: Please enter a user ID.");
			}
			//If user already exists
			else if (database.containsUser(userId)){
				showMessage("ERROR: " + userIdTextField.getText() + " already exists as a user.");
			}
			//If no group is selected
			else if (selectedNode == null){
				addUser(userId, root);
			}
			//If an attempt is made to store user within another user
			else if (database.containsGroup(selectedNode.toString()) == false && selectedNode.toString().equals("Root") == false){
				showMessage("ERROR: you must store your user in a group OR the root.");
			}
			else{				
				addUser(userId, selectedNode);
			}
			//Reload the JTree
			expandTree();
		}
	}

	private class AddGroupButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){			
			//Declare variable for selected node
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			//Store groupId
			groupId = groupIdTextField.getText();
			
			//If groupId field is left blank
			if (groupId.isEmpty() == true){
				showMessage("ERROR: please enter a group ID.");
			}
			//If group already exists
			else if (database.containsGroup(groupId)){
				showMessage("ERROR: " + groupId + " already exists as a group.");
			}
			//If no group is selected
			else if (selectedNode == null){
				addGroup(groupId, root);
			}
			//If an attempt is made to store group within a user
			else if (database.containsGroup(selectedNode.toString()) == false && selectedNode.toString().equals("Root") == false){
				showMessage("ERROR: you must add a group to either the root or another group.");
			}
			else{
				addGroup(groupId, selectedNode);
			}
			
			//Reload the JTree
			expandTree();
		}
	}
	
	private class OpenUserViewButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			//Declare variable for selected node
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			//If no user is selected or a group is selected
            if (selectedNode == null || database.containsGroup(selectedNode.toString())){
    			showMessage("ERROR: Please select a user.");
            }
            else{
    			UserView userView = new UserView(selectedNode.toString());
    			userView.setVisible(true);								       //Display user view for selected user
            }
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
	
	private class ShowMessagesTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			MessagesTotalVisitor messagesTotalVis = MessagesTotalVisitor.getInstance();
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
		//Create folder icon 
		ImageIcon imageIcon = new ImageIcon(AdminControlPanel.class.getResource("/folder_closed.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		
		//Set folder icon to non-leaf nodes
		renderer.setOpenIcon(imageIcon);
		
		//Create the tree
        tree = new JTree(root);
        
        //Set size and bounds of the tree
        tree.setBounds(10, 6, 152, 295);
        
        //Add tree to the contentPane
        contentPane.add(tree);
        
        //Create TreeModel from tree
        model = tree.getModel();
        
        //Sets DefaultTreeCellRenderer
        tree.setCellRenderer(renderer);   
        
        //Create the JScrollpane for tree
        treeScrollPane = new JScrollPane(tree);
        
        //Set size and bounds of the JScrollPane
        treeScrollPane.setBounds(10, 6, 152, 295);
        
        //Add JScrollPane to contentPane
        contentPane.add(treeScrollPane);
   	}
	
	//Method that creates dialog window
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	//Method that reloads and expands each node of the tree
	public void expandTree(){
		((DefaultTreeModel) model).reload();

		for(int i=0;i<tree.getRowCount();i++){
		    tree.expandRow(i);
		}
	}
	
	//Method to add UserGroup to the tree and database
	public void addGroup(String id, DefaultMutableTreeNode node){
		//Create new UserGroup object
		UserGroup g = new UserGroup(id);
		
		//Add group to the database		
		database.addGroup(id, g, database.getGroup(node.toString()));
		
		//Add group to the tree
		node.add(new DefaultMutableTreeNode(id));		
		
		//Display confirmation
		showMessage(id + " has been added as a group.");
	}
	
	//Method to add User to the tree and database
	public void addUser(String id, DefaultMutableTreeNode node){
		//Create new User object
		User u = new User(id);	
		
		//Add user to the database 
		database.addUser(id, u, database.getGroup(node.toString()));
		
		//Add user to the tree
		node.add(new DefaultMutableTreeNode(id));
		
		//Display confirmation
		showMessage(id + " has been added as a user.");
	}
}
