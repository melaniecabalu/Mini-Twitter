package miniTwitter;

import java.util.ArrayList;

public interface Component{
	public void accept(Visitor v);
	
	public String getId();
	
	public void setParent(Component parentGroup);
		
	public ArrayList<String> getFollowerIds();
	
	public boolean isFollowing(Component u);
	
	public ArrayList<String> getNewsFeed();
	
	public ArrayList<String> getTweets();
	
	public String tweet();
	
	public void follow(Component u);

	public void notify(String tweet);

	public void addChild(Component user);
	
	public void addSubgroup(Component group);

	public void attach(Component currentUser);

	public void update(String tweet);
	
}
