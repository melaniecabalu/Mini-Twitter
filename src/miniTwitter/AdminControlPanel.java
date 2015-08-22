package miniTwitter;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {
	private JPanel contentPane;
	private JTextField userIdTextField;
	private JTextField groupId;
	private JButton addUserButton;		
	private JButton addGroupButton;		
	private JButton openUserViewButton;		
	private JButton showUserTotalButton;		
	private JButton showGroupTotalButton;		
	private JButton showMessagesTotalButton;		
	private JButton showPositivePercentageButton;		
	private JTextField txtUseridtextfield;


	public AdminControlPanel() {
		//Set bounds and size of the window 
		setBounds(100, 100, 600, 350);
		
		//Specify what happens when the close button is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildPanel();
		
		/*
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		contentPane.setLayout(null);
		JTree tree = new JTree(root);
		tree.setBounds(10, 11, 113, 290);
		contentPane.add(tree);
		
		DefaultMutableTreeNode userNode = new DefaultMutableTreeNode("Melanie");

		root.add(userNode);*/
				
	}
	
	public void buildPanel(){
		//Create user ID field 20 characters wide
		userIdTextField = new JTextField(20);
		userIdTextField.setBounds(222, 23, 180, 20);

		//Create a JPanel object and let the contentPane field reference it
		//A JPanel object is used to hold other components
		contentPane = new JPanel();
		setContentPane(contentPane);

		//Add components to contentPane 
		contentPane.add(userIdTextField);
	}
}
