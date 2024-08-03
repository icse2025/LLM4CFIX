package reorder2;
public class SetCheck2 {
    private int a=0;
    private int b=0;
    void set(int i) {
    synchronized (this) {
    	a = i;
    	System.out.println("a set to " + i);
    }
	synchronized (this) {
		b = -i;
		System.out.println("b set to " + -i);
	}
    }
    boolean check() {
    	System.out.println("a = " + a + " b = " + b);
	return (a + b == 0);
    }
}
