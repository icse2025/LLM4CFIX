package twoStage;
public class ReadThread extends Thread {
	TwoStage ts;
	public ReadThread(TwoStage ts) {
		this.ts=ts;
	}
	public void run() {
		ts.B();
	}
}
