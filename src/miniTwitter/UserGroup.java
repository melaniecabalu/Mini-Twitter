package miniTwitter;

import java.util.ArrayList;

public class UserGroup implements Component, Visitable{
	private String id;
	private ArrayList<User> groupMembers;
	private ArrayList<UserGroup> subgroups;
	private UserGroup parentGroup;

	public UserGroup(String id){
		groupMembers = new ArrayList<User>();
		subgroups = new ArrayList<UserGroup>();
		this.id = id;
	}
	
	public UserGroup(String id, UserGroup parentGroup){
		groupMembers = new ArrayList<User>();
		this.id = id;
		this.parentGroup = parentGroup;
	}
	
	public void acceptVisitor(Visitor v){
		v.atUserGroup(this);
	}
	
	public void setParent(UserGroup parentGroup){
		this.parentGroup = parentGroup;
		this.parentGroup.addSubgroup(this);
	}
	
	public void addChild(User user){
		groupMembers.add(user);
	}
	
	public void addSubgroup(UserGroup group){
		subgroups.add(group);
	}
	
	public String getId(){
		return id;
	}

	public void accept(Visitor v) {
		v.atUserGroup(this);
	}
}
