package Resources;

import java.io.Serializable;

public class Packet implements Serializable {
    private String username;
    private String data;
    public final static int PASSWORD = 1;
    public final static int TEXT = 2;
    public final static int REGISTER = 3;

    // used for determine if the data is for a password or a message.
    private int flag;

    /*
    Example 1 - Sending a login packet to the server.
    Packet login = new Packet(Packet.PASSWORD, "TestUser", "password123");
    clientOutputStream.writeObject(login);

    Example 2 - Sending a text to the chat when the user is logged in.
    Packet message = new Packet(Packet.TEXT, "TestUser", "Hello everyone!");
    clientOutputStream.writeObject(login);

    Example 3 - Sending registration request to the server.
    Packet register = new Packet(Packet.REGISTER, "TestUser", "Hunter2");
    clientOutputStream.writeObject(login);
    */


    // use to send messages to the server
    public Packet(int flag, String username, String data) {
        this.flag = flag;
        this.username = username;
        this.data = data;
    }


    public int getFlag() {
        return flag;
    }

    public String getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }
}
