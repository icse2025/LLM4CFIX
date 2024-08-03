package wrongLock;
public class TClass2 extends Thread {
    WrongLock wl;
    public TClass2 (WrongLock wl) {
    	this.wl=wl;
    }
    public void run() {
    	wl.B();
    }
}
