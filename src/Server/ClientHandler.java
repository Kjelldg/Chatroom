package Server;

import Resources.Packet;
import xml_history.Write_XML;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private String username;
	public ObjectOutputStream out;
	private boolean loggedIn = false;
	private Write_XML write_XML = new Write_XML();

	// In charge of creating a thread for the client and handling packets from the
	// client.
	public ClientHandler(Socket socket) {
		// System.out.println("Client connected.");
		this.socket = socket;

		try {
			// Establishes streams to and from the client.
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			System.err.println(String.format("Could not open streams for %s", socket.getRemoteSocketAddress()));
			close();
		}
	}

	// Closes streams and socket when error occurs.
	private void close() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println(
					String.format("Could not close streams for %s, retrying...", socket.getRemoteSocketAddress()));
			close();
		}
	}

	private void login(Packet initPacket) {

		// reads the initial packet from client
		String username = initPacket.getUsername();
		String password = initPacket.getData();

		// logs in the client if the user exist
		if (initPacket.getFlag() == Packet.PASSWORD && Server.database.userExistence(username)) {
			loggedIn = Server.database.logOnUser(username, password);

			if (loggedIn) {
				this.username = username;
				try {
					out.writeObject(new Packet(Packet.TEXT, "Server", "Logged in"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					out.writeObject(new Packet(Packet.TEXT, "Server", "Wrong user credentials, could not log in"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// if Client has sent a Password packet and user doesn't exist, create one!
		else if (initPacket.getFlag() == Packet.PASSWORD && !Server.database.userExistence(username)) {
			Server.database.createUser(username, password);
			System.out.println(String.format("User %s has been created.", username));
			try {
				out.writeObject(new Packet(Packet.TEXT, "Server", "Registered"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {

		try {

			Packet initialPacket = (Packet) in.readObject();
			login(initialPacket);

			// used later to set userName to packet obj
			String welcomeMess = "Welcome " + username + " to stop chat enter '/close' as message";
			out.writeObject(new Packet(Packet.TEXT, "SERVER", welcomeMess));

		} catch (IOException e) {
			System.err.println(String.format("Could not receive message from %s", socket.getRemoteSocketAddress()));
		} catch (ClassNotFoundException e) {
			System.err.println(String.format("Wrong object received from %s", socket.getRemoteSocketAddress()));
		}

		MessageUtil msgUtil = new MessageUtil();
		while (socket.isConnected()) {
			try {
				// get new chat message from user
				Packet userMessage = (Packet) in.readObject();

				if (userMessage == null)
					return;

				if (userMessage.getFlag() == Packet.TEXT) {
					// store message
					Server.messageQueue.push(userMessage);
					// send message to all clients
					msgUtil.send(userMessage);
					// Stores message in separate XML-file.
					write_XML.write_Message(userMessage.getData(), username);

				} else {
					System.err.println("Wrong packet flag sent from client.");
				}

			} catch (IOException e) {
				System.err.println(String.format("Could not receive message from %s", socket.getRemoteSocketAddress()));
			} catch (ClassNotFoundException e) {
				System.err.println(String.format("Wrong object received from %s", socket.getRemoteSocketAddress()));
			}
		}
	}

}
