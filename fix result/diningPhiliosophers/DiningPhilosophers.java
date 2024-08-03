package diningPhiliosophers;
public class DiningPhilosophers {
    public static void main(String[] args) {
        int num = 5;
        if((args != null) && (args.length > 0)) {
        	num = Integer.parseInt(args[0]);
        }
		Fork forks []=new Fork[num];
    	for (int i=0;i<num;i++) {
    		forks[i]=new Fork();
    	}
		Philosopher phils[] = new Philosopher[num];
		for (int i=0; i<num; i++){
			if (i==0)
				phils[i] = new Philosopher(forks[num-1],forks[i]);
			else
				phils[i] = new Philosopher(forks[i-1],forks[i]);
		}
		for (int i=0; i<num; i++){
			phils[i].start();
		}
    }
}
class Fork {
}
class Philosopher extends Thread {
    public Fork rightFork;
    public Fork leftFork;
    public Philosopher(Fork rightFork, Fork leftFork) {
        this.rightFork = rightFork;
        this.leftFork = leftFork;
    }
    public void run() {
        while (true) {
            synchronized (rightFork) {
                synchronized (leftFork) {
                }
            }
        }
    }
}
