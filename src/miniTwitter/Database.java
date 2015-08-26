package miniTwitter;

import java.util.HashMap;
import java.util.Map;

public class Database {
	private Map<String, User> users;
	private Map<String, UserGroup> groups;
	private static Database instance; 		//Ensure only one instance of Database
	private int messagesTotal;
	private boolean containsFlag;
	
	public static Database getInstance(){
		if (instance == null)
			instance = new Database();
		return instance;
	}
	
	private Database(){
		int messagesTotal = 0;
		users = new HashMap<String, User>();
		groups = new HashMap<String, UserGroup>();
		containsFlag = false;
	}
	
	public void addUser(String s, User u){
		users.put(s, u);
	}
	
	public void addGroup(String s, UserGroup g){
		groups.put(s, g);
	}
	
	public User getUser(String s){
		return users.get(s);
	}
	
	//testing?
	public Map<String, User> getUsers(){
		return users;
	}
	
	public void incrementMessages(){
		messagesTotal++;
	}
	
	public int getMessagesTotal(){
		return messagesTotal;
	}
	
	public boolean containsUser(String s){
		//ISSUE - USER AND GROUP CANNOT HAVE THE SAME NAME
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
		//ISSUE - USER AND GROUP CANNOT HAVE THE SAME NAME
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

}
