package wrongLock;
public class Main {
    static int iNum1=1;
    static int iNum2=1;
    public void run() {
    	Data data=new Data();
    	WrongLock wl=new WrongLock(data);
    	for (int i=0;i<iNum1;i++)
    		new TClass1(wl).start();
    	for (int i=0;i<iNum2;i++)
    		new TClass2(wl).start();
    }
    public static void main(String[] args) {
	if (args.length < 2){
           Main t = new Main();
    	   t.run();
	}else{
	   iNum1 = Integer.parseInt(args[0]);
	   iNum2 = Integer.parseInt(args[1]);
	   Main t = new Main();
	   t.run();
	}
    }
}
