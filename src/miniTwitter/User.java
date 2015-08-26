package miniTwitter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class User {
	private String id;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> newsFeed;
	private ArrayList<String> tweets;
	
	public User(){
	}
		
	public void acceptVisitor(Visitor v){
		v.atUser(this);
	}
	
	public String getId(){
		return id;
	}
	
	public ArrayList<String> newsFeed(){
		return newsFeed;
	}
	
	public void update(){
		
	}
}
