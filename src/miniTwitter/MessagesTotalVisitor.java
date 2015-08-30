package miniTwitter;

public class MessagesTotalVisitor implements Visitor{
	public static MessagesTotalVisitor instance;
	private int size;
	
	private MessagesTotalVisitor(){}
	
	public static MessagesTotalVisitor getInstance(){
		if (instance == null)
			instance = new MessagesTotalVisitor();
		return instance;	
	}
	
	public int size(){
		return size;
	}
	
	public void atDatabase(Database d) {
		
	}

	@Override
	public void atUser(User u) {
		size++;
	}

	@Override
	public void atUserGroup(UserGroup g) {
		
	}

}
