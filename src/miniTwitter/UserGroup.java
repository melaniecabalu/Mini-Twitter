package miniTwitter;

import java.util.ArrayList;

public class UserGroup {
	private String id;
	private ArrayList<User> members;
	private ArrayList<UserGroup> subgroups;
	
	public UserGroup(){
		subgroups = new ArrayList<UserGroup>();
	}
	
	public UserGroup(String id){
		this.id = id;
		subgroups = new ArrayList<UserGroup>();
	}
	
	public String getId(){
		return id;
	}
}
