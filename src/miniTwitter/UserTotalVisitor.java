package miniTwitter;

public class UserTotalVisitor implements Visitor{
	private int size;
	
	public int size(){
		return size;
	}
	
	public void atDatabase(Database d){
		size = d.getUserTotal();
	}

	@Override
	public void atUser(User u) {
		
	}

	@Override
	public void atUserGroup(UserGroup g) {
		
	}
	
}
