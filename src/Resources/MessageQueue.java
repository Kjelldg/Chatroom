package Resources;
import java.util.ArrayList;

public class MessageQueue {
    ArrayList<Packet> mq = new ArrayList<>();


    // push packet to array
    public void push(Packet p) {
        mq.add(p);
    }

    // gets and removes the first element of the array
    public Packet pop() {
        Packet p = mq.get(0);
        mq.remove(0);
        return p;
    }


}
