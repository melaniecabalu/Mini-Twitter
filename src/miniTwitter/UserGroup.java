package miniTwitter;

import java.util.ArrayList;

public class UserGroup implements Component{
	private String id;
	private ArrayList<User> groupMembers;
	private String parentGroup;

	public UserGroup(){
	}
	
	public UserGroup(String id){
		this.id = id;
	}
	
	public UserGroup(String id, String parent){
		this.id = id;
		parentGroup = parent;
	}
	
	public void acceptVisitor(Visitor v){
		v.atUserGroup(this);
	}
	
	public void setParent(String s){
		this.parentGroup = s;
	}
	
	public String getId(){
		return id;
	}

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		
	}
}
