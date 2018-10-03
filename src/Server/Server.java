package Server;
import Resources.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>(); // TODO - implement client.
    private ArrayList<Packet> messages = new ArrayList<>();
    // TODO - add a message queue.


    // Start server and listens for the specified port.
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Listening on port %s...", port));

        } catch(IOException e) {
            System.err.println(String.format("Port %s is already open.", port));
        }
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
