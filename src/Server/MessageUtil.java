package Server;


import Resources.Packet;

import java.io.IOException;

// Used for sending messages from the message queue to clients
public class MessageUtil {


    private void send(ClientHandler client, Packet packet) {
        try {
        	
            client.out.writeObject(packet);
            client.out.flush();
        } catch(IOException e) {
            System.err.println("Could not send message to client.");
        }
    }
    
    public  void send(Packet packet) throws IOException {
        for (ClientHandler client : Server.clients) {
		    
        	//send(client, packet);
        	client.out.writeObject(packet);
        	client.out.flush();
         }
    }
}
