package miniTwitter;

public class GetId implements Visitor{

	@Override
	public void atUser(User u) {
		u.getId();
	}

	@Override
	public void atUserGroup(UserGroup g) {
		g.getId();
	}

}
