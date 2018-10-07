package Server;
import Database.Database;
import Resources.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private ArrayList<Packet> messages = new ArrayList<>();
    public static Database database = new Database("jdbc:postgresql://localhost:5432/postgres",
                                  "postgres",
                                  "[3`Td?9=");
    // TODO - add a message queue.
    // MessageQueue messages = new MessageQueue(size?);


    // Start server and listens for the specified port.
    public Server(int port) {
        try {
            database.setUp();
            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Listening on port %s...", port));

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
            ClientHandler client = new ClientHandler(serverSocket.accept(), messages);

            // Start a client thread.
            client.start();
            // Adds the cllient to connected clients.
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
