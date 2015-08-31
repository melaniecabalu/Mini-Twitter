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
	
	//Return instance of the database
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
	
	//Method to add user to the database
	public void addUser(String id, User u, UserGroup parentGroup){
		//Store user in the User map
		users.put(id, u);
		
		//Add user to the parent UserGroup object
		parentGroup.addChild(getUser(id));
				
		//Set the user's parent UserGroup
		getUser(id).setParent(parentGroup);
		
		//Increment user total
		userTotal++;
	}
	
	//Method to add UserGroup to the database
	public void addGroup(String id, UserGroup g, UserGroup parentGroup){
		//Put group in the UserGroup map
		groups.put(id, g);
		
		//Add the subgroup to the parent UserGroup object
		parentGroup.addSubgroup(getGroup(id));

		//Set the subgroup's parent UserGroup
		getGroup(id).setParent(parentGroup);
		
		//Increment group total
		groupTotal++;
	}
	
	//Method to return user with specified id 
	public User getUser(String id){
		return users.get(id);
	}
	
	//Method to return UserGroup with specified id
	public UserGroup getGroup(String id){
		return groups.get(id);
	}
	
	//Method to return total number of Users in the database
	public int getUserTotal(){
		return userTotal;
	}
	
	//Method to return total number of UserGroups in the database
	public int getGroupTotal(){
		return groupTotal;
	}
	
	//Method to determine if database contains a User with specified id
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
	
	//Method to determine if database contains a UserGroup with specified id
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

	public void accept(Visitor v) {
		v.atDatabase(this);
	}
}
