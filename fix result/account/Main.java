package account;
import java.io.*;
public class Main {
  public static void main(String[] args) {
      try{
	ManageAccount.num = 10;
          ManageAccount[] bank=new ManageAccount[ManageAccount.num];
          String[] accountName={new String("A"),new String("B"),new String("C"),new String("D"),new String("E"),
                                                       new String("F"),new String("G"),new String("H"),new String("I"),new String("J"),};
          for (int j=0;j<ManageAccount.num;j++){
              bank[j]=new ManageAccount(accountName[j],100);
              ManageAccount.accounts[j].print();;
              }
        for (int k=0;k<ManageAccount.num;k++){
              bank[k].start();
              }
        for (int k=0;k<ManageAccount.num;k++){
              bank[k].join();
              }
        ManageAccount.printAllAccounts();
        boolean bug = false;
        for (int k=0;k<ManageAccount.num;k++){
            if(ManageAccount.accounts[k].amount<300){
                          bug=true;
                          }
            else if(ManageAccount.accounts[k].amount>300){
                          bug=true;
                          }
        }
        if(bug) 
		throw new RuntimeException("bug found");
        } catch(InterruptedException e){
        }
  }
}
