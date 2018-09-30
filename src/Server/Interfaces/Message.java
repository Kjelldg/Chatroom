package Server.Interfaces;

public class Message {
	
	private String userName;
	private String message;
	
	
	
	public Message() {
	}

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

	public String getUserName() {
		return userName;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	
}
