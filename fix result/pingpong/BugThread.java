package pingpong;
public class BugThread extends Thread {
    BuggedProgram bg;
    public BugThread(BuggedProgram bg) {
        this.bg = bg;
    }
    public void run() {
        this.ping();
    }
    public void ping() {
        bg.pingPong();
    }
}
