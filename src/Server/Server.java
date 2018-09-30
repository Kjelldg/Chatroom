package Server;

import Server.Interfaces.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private boolean running = true;
    private ArrayList<ClientHandler> clients = new ArrayList<>(); // TODO - implement client.
    // TODO - add a message queue.

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
            ClientHandler client = new ClientHandler(serverSocket.accept(), messages);
            client.start(); // TODO implement client.
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
