package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	
	private static Socket socket;
	private static String userName;
	private static Scanner userInput;
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	/*
	 * Commented out so that server can run from main()
	 */
	
	/*
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
					out = new PrintWriter(socket.getOutputStream());
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//send username to server
				out.print(userName);
				//return message from server prompting username: like: welcome userName write "/close" to close connection
				try {
					String greeting = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String message = userInput.nextLine();
				//loop until user is typing /close 
				while(!message.equalsIgnoreCase("/close")) {
					//send message
					out.print(message);
					out.flush();
					
					//retrieve messages - NOT SURE IF THIS WORKS
					String serverMessage = "";
					try {
						serverMessage = in.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(serverMessage != null) {
						System.out.println(serverMessage);
						//save to file logic goes here..
					}
				}
				

			}
		}
		else {
			System.out.println("Goodbye");
		}
		
	}*/
	
	private static boolean connectedToServer() {
		
		try {
			InetAddress adress = InetAddress.getByName("www.fabfourstudios.com");
			socket = new Socket(adress, 10000);
			return true;
		}
		catch (Exception e) {
			System.out.println("failed to connect");
			return false;
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
