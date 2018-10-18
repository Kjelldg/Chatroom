package client;

import java.io.IOException;

import Resources.Packet;

public class RetriveMessageThread implements Runnable {

	@Override
	public void run() {
		
	
		 	
			try{
					Packet serverMessage = (Packet) ChatClient.in.readObject();
					
					if (serverMessage != null && serverMessage.getFlag() == Packet.TEXT) {
	
						System.out.println(serverMessage.getUsername() + ": " + serverMessage.getData() + "\n");
						// TODO: save to file logic goes here..
					}
				
			} catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
			}
		
	}

}
