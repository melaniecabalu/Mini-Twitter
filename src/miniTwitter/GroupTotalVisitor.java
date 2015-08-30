package miniTwitter;

public class GroupTotalVisitor implements Visitor{
	private int size;
	
	public int size(){
		return size;
	}
	
	@Override
	public void atDatabase(Database d){
		size = d.getGroupTotal();
	}
	
	@Override
	public void atUser(User u) {
		
	}

	@Override
	public void atUserGroup(UserGroup g) {
		
	}

}
