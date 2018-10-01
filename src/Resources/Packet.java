package Resources;

import java.io.Serializable;

public class Packet implements Serializable {
    private String username;
    private String data;

    // used for determine if the data is for a password or a message.
    private String flag; // Flag "p" = password
                         // flag "t" = text

    /*
    Example 1 - Sending a login packet to the server.
    Packet login = new Packet("p", "TestUser", "password123");
    clientOutputStream.writeObject(login);

    Example 2 - Sending a text to the chat when the user is logged in.
    Packet message = new Packet("t", "TestUser", "Hello everyone!");
    clientOutputStream.writeObject(login);
     */


    // use to send messages to the server
    public Packet(String flag, String username, String data) {
        this.flag = flag;
        this.username = username;
        this.data = data;
    }


    public String getFlag() {
        return flag;
    }

    public String getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }
}
