package Server;


import Resources.Packet;

import java.io.IOException;

// Used for sending messages from the message queue to clients
public class ServerThread extends Thread {


    private void send(ClientHandler client, Packet packet) {
        try {
            client.out.writeObject(packet);
            client.out.flush();
        } catch(IOException e) {
            System.err.println("Could not send message to client.");
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                Packet currentPacket = Server.messageQueue.pop();

                for (ClientHandler client : Server.clients) {
                    send(client, currentPacket);
                }

                } catch (Exception e) {
                    System.err.println("Queue is empty.");
                }
            }
        }
    }



