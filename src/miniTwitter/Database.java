package miniTwitter;

import java.util.HashMap;
import java.util.Map;

public class Database implements Visitable{
	private Map<String, User> users;
	private Map<String, UserGroup> groups;
	private static Database instance; 		//Ensure only one instance of Database
	private int userTotal;
	private int groupTotal;
	private boolean containsFlag;
	
	public static Database getInstance(){
		if (instance == null)
			instance = new Database();
		return instance;
	}
	
	private Database(){
		users = new HashMap<String, User>();
		groups = new HashMap<String, UserGroup>();
		containsFlag = false;
	}
	
	public void addUser(String id, String parent, User u){
		users.put(id, u);
		getUser(id).setParent(parent);
		userTotal++;
	}
	
	public void addGroup(String id, String parent, UserGroup g){
		groups.put(id, g);
		getGroup(id).setParent(parent);
		groupTotal++;
	}
	
	public User getUser(String s){
		return users.get(s);
	}
	
	public UserGroup getGroup(String s){
		return groups.get(s);
	}
	
	public int getUserTotal(){
		return userTotal;
	}
	
	public int getGroupTotal(){
		return groupTotal;
	}
	
	//testing?
	public Map<String, User> getUsers(){
		return users;
	}
	
	public boolean containsUser(String s){
		//reset containsFlag
		containsFlag = false;
		
		if (users.containsKey(s)){
			containsFlag = true;
			return containsFlag;
		}
		else{
			return containsFlag;
		}
	}
	
	public boolean containsGroup(String s){
		//reset containsFlag
		containsFlag = false;
		
		if (groups.containsKey(s)){
			containsFlag = true;
			return containsFlag;
		}
		else{
			return containsFlag;
		}
	}

	@Override
	public void accept(Visitor v) {
		v.atDatabase(this);
		
	}
}
