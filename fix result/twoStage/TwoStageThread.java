package twoStage;
public class TwoStageThread extends Thread {
	TwoStage ts;
	public TwoStageThread(TwoStage ts) {
		this.ts=ts;
	}
	public void run() {
		ts.A();
	}
}
