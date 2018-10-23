package client;

import java.io.IOException;

import Resources.Packet;

public class RetriveMessageThread implements Runnable {

	@Override
	public void run() {
		
			while(true) {
		
					Packet serverMessage = null;
					try {
						serverMessage = (Packet) ChatClient.in.readObject();

					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					
						System.out.println("\n" + serverMessage.getUsername().concat(": ") + serverMessage.getData() + "\n");
				}
	}

}
