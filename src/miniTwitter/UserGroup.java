package miniTwitter;

import java.util.ArrayList;

public class UserGroup {
	private String id;
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
}
