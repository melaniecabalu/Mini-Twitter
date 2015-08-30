package miniTwitter;

public interface Component {
	void accept(Visitor v);

	void setParent(String parent);
}
