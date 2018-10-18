package client;

import java.io.IOException;
import java.util.Scanner;

import Resources.Packet;

public class InputThread implements Runnable{
	
	private Scanner scanner;
	protected boolean shouldRun;
	
	

	public InputThread(Scanner scanner) {
		
		this.scanner = scanner;
		shouldRun = true;
	}


	@Override
	public void run() {
	
		//while(shouldRun) {
			
			System.out.println("Enter new message");
			// read user input
			String message = scanner.nextLine();
			// break loop if user prompts command.
			if (message.equalsIgnoreCase("/close")) {
				shouldRun = false;
			}
				// send message
			Packet pack = new Packet(Packet.TEXT, ChatClient.userName, message);
			try {
				ChatClient.out.writeObject(pack);
				Thread.sleep(300);
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
			
		//}
		
		}
	

}
