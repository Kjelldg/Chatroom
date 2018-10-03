package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import Resources.Packet;

public class ChatClient {
	
	private static Socket socket;
	private static String userName;
	private static Scanner userInput;
	
	//private static PrintWriter out;
	//private static BufferedReader in;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	/*
	 * Commented out so that server can run from main()
	 */
	
	public static void main(String[] args) {
		System.out.println("Welcome to chat.\n Would you like to connect now? (yes/no)");
		userInput = new Scanner(System.in);
		String response = userInput.nextLine();
		
		if(response.equalsIgnoreCase("yes")) {
			setupClient();
			System.out.println(userName + " is connecting...");
			if(connectedToServer()) {
				System.out.println("connected to: " + socket.getInetAddress());
				
				try {
					//out = new PrintWriter(socket.getOutputStream());
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream());
					//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					//send username to server
					out.writeObject(new Packet(Packet.PASSWORD, userName, ""));
					//return message from server prompting username: like: welcome userName write "/close" to close connection
					Packet greet = (Packet)in.readObject();
					System.out.println(greet.getData());
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				boolean connectionClosed = false;
				//loop until user is typing /close 
				while(!connectionClosed) {
					
					System.out.println("Enter new message");
					//read user input
					String message = userInput.nextLine();
					//break loop if user prompts command. 
					if(message.equalsIgnoreCase("/close")) {
						connectionClosed = true;
						break;
					}
					//send message
					Packet pack = new Packet(Packet.TEXT, userName, message);
					//out.print(message);
					//out.flush();
					try {
						out.writeObject(pack);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//retrieve message
					Packet serverMessage = new Packet(Packet.TEXT,"","");
					try {
						serverMessage = (Packet) in.readObject();
						//serverMessage = in.readLine();
					} catch (IOException | ClassNotFoundException e) {
						
						e.printStackTrace();
					}
					if(serverMessage != null && serverMessage.getFlag() == Packet.TEXT) {
						
						System.out.println(serverMessage.getUsername()+": " +  serverMessage.getData()+"\n");
						//TODO: save to file logic goes here..
					}
				}
				
				closeConnection();
			}

		}
		else {
			System.out.println("Goodbye");
		}
		
	}
	
	private static boolean connectedToServer() {
		
		try {
			InetAddress adress = InetAddress.getByName("localhost");
			socket = new Socket(adress, 1337);
			return true;
		}
		catch (Exception e) {
			System.out.println("failed to connect");
			return false;
		}
	}
	
	private static void closeConnection() {
		try {
			socket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setupClient() {
		boolean valid = false;
		
		while(!valid) {
			System.out.println("Please enter an awesome online handle: ");
			String tempUserName = userInput.nextLine();
			System.out.println("Do you want "+ tempUserName + " as you online handle?");
				if(userInput.nextLine().equalsIgnoreCase("yes")) {
					userName = tempUserName;
					valid = true;
				}
		}
	}
	

}
