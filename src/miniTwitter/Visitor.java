package miniTwitter;

public interface Visitor {
	public void atUser(User u);
	public void atUserGroup (UserGroup g);
}
