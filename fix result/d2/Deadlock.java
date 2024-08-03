package d2;
public class Deadlock {
    public static void main(String[] args) {
	Object lock1 = new Object();
	Object lock2 = new Object();
	Client client1 = new Client(lock1, lock2);
	Client client2 = new Client(lock2, lock1);
	client1.start();
	client2.start();
    }
}
class Client extends Thread {
    public Object lock1;
    public Object lock2;
    public Client(Object lock1, Object lock2) {
	this.lock1 = lock1;
	this.lock2 = lock2;
    }
    public void run() {
	synchronized(lock1) {
	    synchronized(lock2) {
	    }
	}
    }
}
