package Server.Interfaces;

public class Message {
	
	private String userName;
	private String message;
	
	public Message(String userName, String message) {
				
		if(userName.equals("")) {
			this.userName = "no name";
			this.message = message;
		}
		else {
		
			this.userName = userName;
			this.message = message;
		}
	}
	
	
}
