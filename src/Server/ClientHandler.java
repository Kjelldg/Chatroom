package Server;

import Resources.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    Socket socket;
    ArrayList<Packet> messages;
    ObjectInputStream in;
    ObjectOutputStream out;

    // In charge of creating a thread for the client and handling packets from the client.
    public ClientHandler(Socket socket, ArrayList<Packet> messages) {
        this.socket = socket;
        System.out.println("Client connected.");
        this.messages = messages;

        try {
            // Establishes streams to and from the client.
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());

        } catch(IOException e) {
            System.err.println(String.format("Could not open streams for %s", socket.getRemoteSocketAddress()));
            close();
        }
    }

    // Closes streams and socket when error occurs.
    void close() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println(String.format("Could not close streams for %s, retrying...", socket.getRemoteSocketAddress()));
            close();
        }
    }

    @Override
    public void run() {
    	
    	//This code should run only once, registers user to db(?) and sends welcome confirmation back to the client 
    	String userName = "";
		try {
			
			Packet user = (Packet) in.readObject();
			//TODO: logic to save user to db goes here
			
			//used later to set userName to packet obj
			userName = user.getUsername();
	    	String welcomeMess = "Welcome" + user.getUsername() + " to stop chat enter '/close' as message";
	    	out.writeObject(new Packet(Packet.TEXT, user.getUsername(), welcomeMess));
	    	
		} catch (ClassNotFoundException | IOException e1) {
			
			e1.printStackTrace();
		}

    	
        while(socket.isConnected()) {
            try {
            	
            	//get new chat message from user
            	Packet userMessage = (Packet) in.readObject();
            	
            	if(userMessage.getFlag()==Packet.TEXT) {
            		// store message
            		messages.add(userMessage);
            		//echo message
            		out.writeObject(userMessage);
            	}
            	else {
            		//TODO: Store user data in db?
            	}
                

            } catch (IOException e) {
                System.err.println(String.format("Could not receive message from %s", socket.getRemoteSocketAddress()));
            } catch (ClassNotFoundException e) {
                System.err.println(String.format("Wrong object received from %s", socket.getRemoteSocketAddress()));
            }
        }
    }

}
