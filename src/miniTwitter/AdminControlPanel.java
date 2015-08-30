package miniTwitter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;

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
	private JScrollPane scrollPane;
	
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

			if (userId.isEmpty() == true){
				showMessage("ERROR: Please enter a user ID.");
			}
			else if (database.containsUser(userId)){
				showMessage("ERROR: " + userIdTextField.getText() + " already exists as a user.");
			}
			else if (selectedNode == null){
				addUser(userId, root);
			}
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
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			groupId = groupIdTextField.getText();
			
			if (groupId.isEmpty() == true){
				showMessage("ERROR: please enter a group ID.");
			}
			else if (database.containsGroup(groupId)){
				showMessage("ERROR: " + groupId + " already exists as a group.");

			}
			else if (selectedNode == null){
				addGroup(groupId, root);
			}
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
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

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
		
		ImageIcon imageIcon = new ImageIcon(AdminControlPanel.class.getResource("/folder_closed.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		
		//Set folder icon to non-leaf nodes
		renderer.setOpenIcon(imageIcon);
        tree = new JTree(root);
        tree.setBounds(10, 6, 152, 295);
        contentPane.add(tree);
        selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        model = tree.getModel();
        tree.setCellRenderer(renderer);   
        
        JScrollPane scrollPane_1 = new JScrollPane(tree);
        scrollPane_1.setBounds(10, 6, 152, 295);
        contentPane.add(scrollPane_1);
   	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void expandTree(){
		((DefaultTreeModel) model).reload();

		for(int i=0;i<tree.getRowCount();i++){
		    tree.expandRow(i);
		}
	}
	
	public void addGroup(String id, DefaultMutableTreeNode node){
		UserGroup g = new UserGroup(id);
		
		//Add group to the database		
		database.addGroup(id, g, database.getGroup(node.toString()));
		
		//Add group to the node
		node.add(new DefaultMutableTreeNode(id));				
		showMessage(id + " has been added as a group.");
	}
	
	public void addUser(String id, DefaultMutableTreeNode node){
		User u = new User(id);	
		
		//Add user to the database 
		database.addUser(id, u, database.getGroup(node.toString()));
		
		//Add user to the node
		node.add(new DefaultMutableTreeNode(id));
		showMessage(id + " has been added as a user.");
	}
}
