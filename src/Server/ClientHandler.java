package Server;

import Server.Interfaces.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    Socket socket;
    ArrayList<Message> messages;
    ObjectInputStream in;
    ObjectOutputStream out;


    public ClientHandler(Socket socket, ArrayList<Message> messages) {
        this.socket = socket;
        System.out.println("Client connected.");
        this.messages = messages;

        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());

        } catch(IOException e) {
            System.err.println(String.format("Could not open streams for %s", socket.getRemoteSocketAddress()));
        }
    }

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
    	
    	//This code should run only once, sets userName to something the client sends and then moves on. 
    	String userName ="";
		try {
			userName = (String) in.readObject();
	    	String welcomeMess = "Welcome" + userName + "to stop chat enter '/close' as message";
	    	out.writeChars(welcomeMess);
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	
        while(socket.isConnected()) {
            try {
            	
            	//Parse new message string from user
            	String userMessage = (String) in.readObject();
            	
            	Message message = new Message(userName, userMessage);
            	
                //Message message = (Message) in.readObject();
                messages.add(message);

            } catch (IOException e) {
                System.err.println(String.format("Could not receive message from %s", socket.getRemoteSocketAddress()));
            } catch (ClassNotFoundException e) {
                System.err.println(String.format("Wrong object received from %s", socket.getRemoteSocketAddress()));
            }
        }
    }

}
