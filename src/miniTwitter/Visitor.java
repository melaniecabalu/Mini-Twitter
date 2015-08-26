package miniTwitter;

public interface Visitor {
	public void atUser(User u);
	public void atAdminControlPanel(AdminControlPanel a);
	public void visit (UserGroup g);
}
