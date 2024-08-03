package airline;
import java.io.FileOutputStream;
public  class Bug implements Runnable{
    static int  Num_Of_Seats_Sold =0;
    int         Maximum_Capacity, Num_of_tickets_issued;
    boolean     StopSales = false;
    Thread      threadArr[] ;
    FileOutputStream output;
    private String fileName;
    public Bug (String fileName, int size, int cushion){
        this.fileName = fileName;
	Num_of_tickets_issued = size;   
        Maximum_Capacity = Num_of_tickets_issued - cushion;
        threadArr = new Thread[Num_of_tickets_issued];
        for( int i=0; i < Num_of_tickets_issued; i++) {
           threadArr[i] = new Thread (this) ;
            if( StopSales ){
                 Num_Of_Seats_Sold--;
                 break;
            }
            threadArr[i].start();  
         }
         if (Num_Of_Seats_Sold > Maximum_Capacity)
             throw new RuntimeException("bug found");
     }
    public void run() {
        Num_Of_Seats_Sold++;                       
        if (Num_Of_Seats_Sold > Maximum_Capacity)  
            StopSales = true;                      
    }
}
