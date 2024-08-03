package account;
public class ManageAccount extends Thread {
  Account account;
  static Account[] accounts=new Account[10] ;
  static int num=2;
  static int accNum=0;
  int i;
  public ManageAccount(String name,double amount) {
      account=new Account(name,amount);
      i=accNum;
      accounts[i]=account;
     accNum=(accNum+1)%num;
  }
  public void run(){
  account.depsite(300);
  account.transfer(accounts[(i+1)%num],10);
  account.depsite(10);
  account.transfer(accounts[(i+2)%num],10);
  account.withdraw(20);
  account.depsite(10);
  account.transfer(accounts[(i+1)%num],10);
  account.withdraw(100);
  }
  static public void printAllAccounts(){
            for (int j=0;j<num;j++){
                if( ManageAccount.accounts[j]!=null){
                    ManageAccount.accounts[j].print();;
                    }
            }
        }
}
