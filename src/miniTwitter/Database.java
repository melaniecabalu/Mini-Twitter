package miniTwitter;

import java.util.HashMap;
import java.util.Map;

public class Database implements Visitable{
	private Map<String, User> users;
	private Map<String, UserGroup> groups;
	private UserGroup root;
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
		root = new UserGroup("Root");
		containsFlag = false;
		
		//Add root to the database
		groups.put("Root", root);
	}
	
	public void addUser(String id, User u, UserGroup parentGroup){
		//Add the user to database user array
		users.put(id, u);
		
		//Add user to the parent group	
		parentGroup.addChild(getUser(id));
				
		//Set the user's parent group
		getUser(id).setParent(parentGroup);
		
		userTotal++;
	}
	
	public void addGroup(String id, UserGroup g, UserGroup parentGroup){
		//Add the user to database
		groups.put(id, g);
		
		//Add the subgroup to the parent
		parentGroup.addSubgroup(getGroup(id));

		//Set the group BLAH FIX THIS WORDING IT SUCKS
		getGroup(id).setParent(parentGroup);
		
		groupTotal++;
	}
	
	public User getUser(String s){
		return users.get(s);
	}
	
	public UserGroup getGroup(String s){
		return groups.get(s);
	}
	
	public Map<String, UserGroup> getGroup(){
		return groups;
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
