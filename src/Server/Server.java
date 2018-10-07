package Server;
import Database.Database;
import Resources.MessageQueue;
import Resources.Packet;
import com.sun.jmx.remote.internal.ArrayQueue;
import sun.misc.resources.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    public static Database database = new Database("jdbc:postgresql://localhost:5432/postgres",
                                  "postgres",
                                  "[3`Td?9=");

    public static MessageQueue messageQueue = new MessageQueue();



    // Start server and listens for the specified port.
    public Server(int port) {
        try {
            // initialize the database
            database.setUp();

            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Listening on port %s...", port));

            // starts a thread to send out packages to logged in clients.
            ServerThread messageHandler = new ServerThread();
            messageHandler.start();

        } catch(IOException e) {
            System.err.println(String.format("Port %s is already open.", port));
        }
    }


    public Database getDatabase() {
        return database;
    }


    public void listen() {
        try {

            // Listens for connections
            ClientHandler client = new ClientHandler(serverSocket.accept());

            // Start a client thread.
            client.start();
            // Adds the client to connected clients.
            clients.add(client);

        } catch(IOException e) {
            System.err.println("Could not connect to client.");
        }
    }


   public static void main(String args[])  {
        Server server = new Server(1337);
        server.listen();

    }
}
