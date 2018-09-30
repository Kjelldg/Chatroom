package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import Server.Interfaces.Message;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>(); // TODO - implement client.
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
        // TODO - fix when client is implemented
        try {

            // Listens for connections
            ClientHandler client = new ClientHandler(serverSocket.accept(), messages);

            // Start a client thread.
            client.start(); // TODO implement client.

            // Adds the cllient to connected clients.
            clients.add(client);

        } catch(IOException e) {
            System.err.println("Could not connect to client.");
        }
    }


    /*public static void main(String args[]) throws Exception {
        Server server = new Server(8080);
        server.listen();

    }*/
}
