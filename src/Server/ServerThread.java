package Server;


import Resources.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Used for sending messages from the message queue to clients
public class ServerThread extends Thread {


    private void send(ClientHandler client, Packet packet) {
        try {
            client.out.writeObject(packet);
        } catch(IOException e) {
            System.err.println("Could not send message to client.");
        }
    }

    @Override
    public void run() {
        while(true) {
            if(!Server.clients.isEmpty()) {
                Packet currentPacket = Server.messageQueue.pop();

                for (ClientHandler client : Server.clients) {
                    send(client, currentPacket);
                }
            }
        }


    }




}
