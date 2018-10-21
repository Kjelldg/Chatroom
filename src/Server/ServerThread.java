package Server;


import Resources.Packet;

import java.io.IOException;

// Used for sending messages from the message queue to clients
public class ServerThread extends Thread {


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

    @Override
    public void run() {
    	
    		while(true) {}
    	
    		/*int currentNoOfMessagesInQueue = 0;
    		while(true) {
    			int sizeNow = Server.messageQueue.size();
    			if(sizeNow > currentNoOfMessagesInQueue) {
    				
    				int diff = sizeNow - currentNoOfMessagesInQueue;
    				
    				//logic not right....
    				for(int i = currentNoOfMessagesInQueue; i < diff; i++) {
    					if(i == 0 || i == 1) {
    						try {
								send(Server.messageQueue.getPacketAtPosition(i));
								currentNoOfMessagesInQueue++;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					}
    					else {
    						try {
								send(Server.messageQueue.getPacketAtPosition(i-1));
								currentNoOfMessagesInQueue++;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    				}
    			}
    		}
    		/*int sentMessages = 0;
            while (true) {
        	
            	if(Server.messageQueue.size() > sentMessages) {
			        try {    	
			               Packet currentPacket = Server.messageQueue.getPacketAtPosition(sentMessages);//Server.messageQueue.pop();
			               for (ClientHandler client : Server.clients) {
			                     send(client, currentPacket);
			                }
			               sentMessages++;
			             } catch (Exception e) {
			                    System.err.println("No more messages to send.");
			                    break;
			             }
			        }
         }*/
    }

}
