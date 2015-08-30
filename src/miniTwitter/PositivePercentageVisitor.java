package miniTwitter;

public class PositivePercentageVisitor implements Visitor{
	public static PositivePercentageVisitor instance;
	private MessagesTotalVisitor messagesTotal = MessagesTotalVisitor.getInstance();
	private int posPercentage = 0;
	private double posWords = 0;
	private String[] words = {"happy", "good", "great", "excellent", "yay", "amazing", "wonderful", "love", "like"}; 

	private PositivePercentageVisitor(){}
	
	public static PositivePercentageVisitor getInstance(){
		if (instance == null)
			instance = new PositivePercentageVisitor();
		return instance;	
	}
		
	public int percentage(){
		return posPercentage;
	}
	
	public void atDatabase(Database d) {}

	public void atUser(User u) {
		for (int i = 0; i < words.length; i++){
			if (u.tweet().contains(words[i])){
				posWords++;
				break;
			}
		}
		if (posWords > 0)
			posPercentage =  (int) ((posWords / messagesTotal.size()) * 100);
	}

	public void atUserGroup(UserGroup g) {}

}
