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
import xml_history.*;

public class ChatClient {

	protected static Socket socket;
	protected static String userName;
	private static String password;
	private static Scanner userInput;

	protected static ObjectOutputStream out;
	protected static ObjectInputStream in;

	public static void main(String[] args) {
		System.out.println("Welcome to chat.\n Would you like to connect now? (yes/no)");
		userInput = new Scanner(System.in);
		String response = userInput.nextLine();

		if (response.equalsIgnoreCase("yes")) {
			setupClient();
			System.out.println(userName + " is connecting...");
			if (connectedToServer()) {
				System.out.println("connected to: " + socket.getInetAddress());

				try {
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					// send username and password to server
					out.writeObject(new Packet(Packet.PASSWORD, userName, password));
					// return message from server prompting username: like: welcome userName write
					// "/close" to close connection
					Packet greet = (Packet) in.readObject();
					System.out.println(greet.getData());
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}


				
				Thread input = new Thread(new InputThread(userInput));
				Thread getServerResponse = new Thread(new RetriveMessageThread());

				input.start();
				getServerResponse.start();

				// loop until user is typing /close
				while (true) {

					if(!input.isAlive())
						break;
						
				}
				
				closeConnection();
			}
			else {
				System.out.println("Goodbye");
			}

		} 

	}

	private static boolean connectedToServer() {

		try {
			InetAddress adress = InetAddress.getByName("localhost");
			socket = new Socket(adress, 1337);
			return true;
		} catch (Exception e) {
			System.out.println("failed to connect");
			return false;
		}
	}

	private static void closeConnection() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setupClient() {
		boolean valid = false;

		while (!valid) {
			System.out.println("Please enter an awesome online handle: ");
			String tempUserName = userInput.nextLine();
			System.out.println("Do you want " + tempUserName + " as you online handle? (yes/no)");
			if (userInput.nextLine().equalsIgnoreCase("yes")) {
				userName = tempUserName;
				System.out.println("Please enter a password: ");
				password = userInput.nextLine();
				valid = true;
			}
		}
	}

}
