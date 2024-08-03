package pingpong;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class BuggedProgram {
    private DataOutputStream output;
    private int threadNumber;
    private PP pingPongPlayer;
    private int bugAppearanceNumber = 0;
    public BuggedProgram(DataOutputStream output, int threadNumber) {
        this.output = output;
        this.threadNumber = threadNumber;
        this.pingPongPlayer = new PP();
    }
    public void doWork() {
        ArrayList threads = new ArrayList();
        for (int i = 0; i < threadNumber; i++) {
            BugThread t = new BugThread(this);
            t.start();
            threads.add(t);
        }
        Iterator iterator = threads.iterator();
        while (iterator.hasNext()) {
            Thread t = (Thread) iterator.next();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
        try {
            String newLine = System.getProperty("line.separator");
            output.writeBytes(String.valueOf(this.bugAppearanceNumber + " bugs. " + newLine));
            System.out.println("Bug appeanace Number" + this.bugAppearanceNumber);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    public void pingPong() {
    	this.pingPongPlayer.getI();
        PP newPlayer;
        newPlayer = this.pingPongPlayer;
        this.pingPongPlayer = null;
        long time = System.currentTimeMillis();
        while ((System.currentTimeMillis() - time) < 50) ;
        this.pingPongPlayer = newPlayer;
    }
}
