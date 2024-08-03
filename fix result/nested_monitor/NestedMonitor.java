package nested_monitor;
class SemaBuffer implements Buffer {
  protected int size;
  Semaphore full; 
  Semaphore empty;
  SemaBuffer(int size) {
    this.size = size; 
    full = new Semaphore(0);
    empty= new Semaphore(size);
  }
  synchronized public void put(Object o)
              throws InterruptedException {
    empty.down();
    full.up();
  }
  synchronized public Object get()
               throws InterruptedException{
    full.down();
    empty.up();
    return (null); 
  }
}
public class NestedMonitor  {
  static int SIZE = 5; 
  static Buffer buf;
    public static void main(String [] args) {
        buf = new SemaBuffer(SIZE);
	new Producer(buf).start();
    	new Consumer(buf).start();
    }
}
class Producer extends Thread {
    Buffer buf;
    Producer(Buffer b) {buf = b;}
    public void run() {
      try {
        while(true) {
            buf.put(null); 
        }
      } catch (InterruptedException e){}
    }
}
class Consumer extends Thread {
    Buffer buf;
    Consumer(Buffer b) {buf = b;}
    public void run() {
      try {
        while(true) {
            buf.get(); 
        }
      } catch(InterruptedException e ){}
    }
}
