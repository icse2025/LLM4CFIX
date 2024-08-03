package wrongLock;
public class TClass1 extends Thread {
    WrongLock wl;
    public TClass1 (WrongLock wl) {
    	this.wl=wl;
    }
    public void run() {
    	wl.A();
    }
}
