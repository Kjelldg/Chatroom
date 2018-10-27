package Server;
import Database.Database;
import Resources.MessageQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    public static volatile ArrayList<ClientHandler> clients = new ArrayList<>();
    public static Database database;

    public static MessageQueue messageQueue = new MessageQueue();



    // Start server and listens for the specified port.
    public Server(int port) {
        try {
            // initialize the database
        	database = new Database("jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "marcus");//[3`Td?9=
            database.setUp();

            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Listening on port %s...", port));

        } catch(IOException e) {
            System.err.println(String.format("Port %s is already open.", port));
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

        while(true) {
            server.listen();
        }
        

    }
}
