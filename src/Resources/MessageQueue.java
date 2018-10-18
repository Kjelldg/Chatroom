package Resources;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class MessageQueue {
    ArrayList<Packet> mq = new ArrayList<>();


    // push packet to array
    public void push(Packet p) {
        System.out.println("Packet has been pushed to the queue");
        mq.add(p);
    }

    // gets and removes the first element of the array
    public Packet pop() {

        if(!mq.isEmpty()) {
            Packet p = mq.get(0);
            mq.remove(0);
            return p;
        } else {
            throw new EmptyStackException();
        }
    }

    public int size() {
        return this.mq.size();
    }
}
