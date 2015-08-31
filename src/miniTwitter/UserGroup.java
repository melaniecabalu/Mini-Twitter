package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class UserGroup implements Component, Visitable{
	private String id;
	private List<Component> groupMembers;
	private List<Component> subgroups;
	private Component parentGroup;

	public UserGroup(String id){
		groupMembers = new ArrayList<Component>();
		subgroups = new ArrayList<Component>();
		this.id = id;
	}
	
	public UserGroup(String id, UserGroup parentGroup){
		groupMembers = new ArrayList<Component>();
		this.id = id;
		this.parentGroup = parentGroup;
	}
	
	public void acceptVisitor(Visitor v){
		v.atUserGroup(this);
	}
	
	public void setParent(Component parentGroup){
		this.parentGroup = parentGroup;
		this.parentGroup.addSubgroup(this);
	}
	
	public void addChild(Component user){
		groupMembers.add(user);
	}
	
	public void addSubgroup(Component group){
		subgroups.add(group);
	}
	
	public String getId(){
		return id;
	}

	public void accept(Visitor v) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public ArrayList<String> getFollowerIds() {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public boolean isFollowing(Component u) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public ArrayList<String> getNewsFeed() {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public ArrayList<String> getTweets() {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}


	@Override
	public String tweet() {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public void follow(Component u) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public void notify(String tweet) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public void attach(Component currentUser) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}

	@Override
	public void update(String tweet) {
	    throw new UnsupportedOperationException("Invalid operation for UserGroup object.");
	}
}
