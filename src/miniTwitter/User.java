package miniTwitter;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class User extends Subject implements Observer{
	private String id;
	private String parentGroup;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> newsFeed;
	private ArrayList<String> tweets;
	private boolean followingFlag;
	
	public User(){
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		tweets = new ArrayList<String>();
	}
	
	public User(String id){
		this.id = id;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		tweets = new ArrayList<String>();
	}
	
	public User(String id, String parent){
		this.id = id;
		parentGroup = parent;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		tweets = new ArrayList<String>();
	}
		
	
	public void acceptVisitor(Visitor v){
		v.atUser(this);
	}
	
	public String getId(){
		return id;
	}
	
	public void setParent(String s){
		this.parentGroup = s;
	}
	
	public boolean isFollowing(User u){
		if (followings.contains(u)){
			followingFlag = true;
			return followingFlag;
		}
		else{
			followingFlag = false;
			return followingFlag;
		}
	}
	
	public ArrayList<String> newsFeed(){
		return newsFeed;
	}
	

	//WHEN USER IS THE OBSERVER
	public void update(String tweet) {
		newsFeed.add(tweet);
	}
	
	public void follow(User u){
		followings.add(u);
	}

	//WHEN USER IS THE SUBJECT
	@Override
	public void attach(User u) {
		followers.add(u);
	}
	
	//WHEN USER IS THE SUBJECT
	public void notify(String tweet){
		for (int i= 0; i <= followers.size(); i++){
			followers.get(i).update(tweet);
		}

	}

}
