package reorder2;
public class SetThread2 extends Thread {
    SetCheck2 sc;
    int i;
    public SetThread2(SetCheck2 sc, int i) {
	this.sc=sc;
	this.i = i;
    }
    public void run() {
	sc.set(i);
    }
}
