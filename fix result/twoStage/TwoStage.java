package twoStage;
public class TwoStage {
    public Data data1,data2;
    public TwoStage (Data data1, Data data2) {
	this.data1=data1;
	this.data2=data2;
    }
    public void A () {
       synchronized (data1) {
           data1.value=1;
       }
       synchronized (data2) {   		
           data2.value=data1.value+1;
       }
    }
    public void B () {
	int t1=-1, t2=-1;
        synchronized (data1) {
	    if (data1.value==0) return ; 
	        t1=data1.value;
	}
	synchronized (data2) {
	    t2=data2.value;
	}
        if (t2 != (t1+1))
	    throw new RuntimeException("bug found");
    }
}
