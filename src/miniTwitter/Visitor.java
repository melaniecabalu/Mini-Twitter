package miniTwitter;

public interface Visitor {
	public void atDatabase(Database d);
	public void atUser(User u);
	public void atUserGroup (UserGroup g);
}
