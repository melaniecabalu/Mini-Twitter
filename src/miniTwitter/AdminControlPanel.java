package miniTwitter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {
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
	private static AdminControlPanel instance; 	//Ensure only one instance of AdminControlPanel
	
	private int userTotal;
	private int groupTotal;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode selectedNode;
	
	
	public static AdminControlPanel getInstance(){
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}

	private AdminControlPanel() {	
		root = new DefaultMutableTreeNode("Root");

		userTotal = 0;
		groupTotal = 0;
		
		database = Database.getInstance();
		
		//Set bounds and size of the window 
		setBounds(100, 100, 600, 350);
		
		//Specify what happens when the close button is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildPanel();		
	}
	
	//NEEDED? I DERNO
	/*public void accept(Visitor vis){
		vis.atAdminControlPanel(this);
	}*/
	
	public void buildPanel(){		
		//Create user ID field 20 characters wide
		userIdTextField = new JTextField(20);
		userIdTextField.setBounds(243, 6, 166, 20);
		
		//Create group ID field 20 characters wide
		groupIdTextField = new JTextField(20);
		groupIdTextField.setBounds(243, 37, 166, 20);

		//Create Add User button
		addUserButton = new JButton("Add User");
		addUserButton.setBounds(415, 5, 159, 23);
		
		//Add an action listener to Add User button
		addUserButton.addActionListener(new AddUserButtonListener());
		
		//Create Add Group button
		addGroupButton = new JButton("Add Group");
		addGroupButton.setBounds(415, 34, 159, 23);		
		
		//Add an action listener to Add Group button
		addGroupButton.addActionListener(new AddGroupButtonListener());
		
		//Create Show User Total button
		showUserTotalButton = new JButton("Show User Total");
		showUserTotalButton.setBounds(243, 250, 159, 23);
		
		//Add an action listener to Show User Total button
		showUserTotalButton.addActionListener(new ShowUserTotalButtonListener());
		
		//Create Show Group Total Button
		showGroupTotalButton = new JButton("Show Group Total");
		showGroupTotalButton.setBounds(415, 250, 159, 23);
		
		//Add an action listener to Show Group Total button
		showGroupTotalButton.addActionListener(new ShowGroupTotalButtonListener());
		
		//Create Open User View button
		openUserViewButton = new JButton("Open User View");
		openUserViewButton.setBounds(243, 68, 331, 23);

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

		buildTree();
		selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
	
	private class AddUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){				
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            //IF SELECTED NODE IS NOT A GROUP NODE DONT LET THEM ADDDDDDD
			if (database.containsUser(userIdTextField.getText())){
				JOptionPane.showMessageDialog(null,"ERROR: " + userIdTextField.getText() + " already exists as a user.");	
			}
			else if (selectedNode == null){
				JOptionPane.showMessageDialog(null,"ERROR: please select either the root or a group in which to store user.");	
			}
			/*
			else if (selectedNode.toString().contains("Root") != true && selectedNode.isLeaf() == true){
				JOptionPane.showMessageDialog(null,"ERROR: please select either the root or a group in which to store user.");	
			}*/
			else{
				userId = userIdTextField.getText();
				User u = new User(userId);
				database.addUser(userId, selectedNode.toString(), u);
				userTotal++;
				JOptionPane.showMessageDialog(null,userIdTextField.getText() + " has been added as a user.");
							
				//Expand tree
				for(int i=0;i<tree.getRowCount();i++){
				    tree.expandRow(i);
				}
				
				selectedNode.add(new DefaultMutableTreeNode(userId));

				//Expand tree
				for(int i=0;i<tree.getRowCount();i++){
				    tree.expandRow(i);
				}
				
				TreeModel model = tree.getModel();
				((DefaultTreeModel) model).reload();
			}
		}
	}

	private class AddGroupButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){			
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (database.containsGroup(groupIdTextField.getText())){
				JOptionPane.showMessageDialog(null,"ERROR: " + groupIdTextField.getText() + " already exists as a group.");	
			}
			else if (selectedNode == null){
				JOptionPane.showMessageDialog(null,"ERROR: you must select where to store the group. Please select the root or a group in which to store your new group.");	
			}
			else{
				groupId = groupIdTextField.getText();
				UserGroup g = new UserGroup(groupId);
				database.addGroup(groupId, selectedNode.toString(), g);
				groupTotal++;
				JOptionPane.showMessageDialog(null, groupIdTextField.getText() + " has been added as a group.");
				
				selectedNode.add(new DefaultMutableTreeNode(groupId));
				
				//Expand tree
				for(int i=0;i<tree.getRowCount();i++){
				    tree.expandRow(i);
				}
				
				TreeModel model = tree.getModel();
				((DefaultTreeModel) model).reload();
			}
		}
	}
	
	private class ShowUserTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){						
			JOptionPane.showMessageDialog(null, "Total number of users: " + userTotal);
		}
	}
	
	private class ShowGroupTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){						
			JOptionPane.showMessageDialog(null, "Total number of groups: " + groupTotal);
		}
	}
	
	private class OpenUserViewButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){						
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null){
    			JOptionPane.showMessageDialog(null, "ERROR: Please select a user.");
            }
            else{
    			UserView userView = new UserView(selectedNode.toString());
    			userView.setVisible(true);								       //Display user view for selected user

            }
		}
	}
	
	public void buildTree(){
		tree = new JTree(root);
		tree.setBounds(10, 6, 152, 295);
		contentPane.add(tree);
	}
}
