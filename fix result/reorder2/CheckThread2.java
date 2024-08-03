package reorder2;
public class CheckThread2 extends Thread {
    SetCheck2 sc;
    public CheckThread2(SetCheck2 sc) {
	this.sc=sc;
    }
    public void run() {
	boolean rst=sc.check();
	if (rst!= true)
		throw new RuntimeException("bug found");
    }
}
