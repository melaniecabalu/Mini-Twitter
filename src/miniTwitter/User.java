package miniTwitter;

import java.util.ArrayList;

public class User extends Subject implements Observer, Component, Visitable{
	private String id;
	private UserGroup parentGroup;
	private ArrayList<User> followers;
	private ArrayList<User> followings;
	private ArrayList<String> newsFeed;
	private ArrayList<String> tweets;
	private boolean followingFlag;
	private String tweet;
	
	public User(String id){
		this.id = id;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		tweets = new ArrayList<String>();
	}
	
	public String getId(){
		return id;
	}
	
	public void setParent(UserGroup parentGroup){
		this.parentGroup = parentGroup;
		parentGroup.addChild(this);
	}
	
	public UserGroup getParent(){
		return parentGroup;
	}
	
	public ArrayList<String> getFollowerIds(){
		ArrayList<String> f = new ArrayList<String>();
		
		if (!followings.isEmpty()){
			for (int i = 0; i < followings.size(); i++){
				f.add(followings.get(i).getId());
			}
		}
		return f;
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
	
	public ArrayList<String> getNewsFeed(){
		return newsFeed;
	}
	
	public ArrayList<String> getTweets(){
		return tweets;
	}
	
	//WHEN USER IS THE OBSERVER
	public void update(String tweet) {
		newsFeed.add(tweet);
	}
	
	public String tweet(){
		return tweet;
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
		//Add tweet to user's own news feed
		newsFeed.add(tweet);
		
		//Must be lowercase to compare to positive words
		this.tweet = tweet.toLowerCase();
					
		//Update followers' news feeds
		for (int i= 0; i < followers.size(); i++){		
			//Call follower's update method
			followers.get(i).update(tweet);
		}
	}
	
	public void accept(Visitor v){
		v.atUser(this);
	}
}
