package account;
public class Account {
    double amount;
    String  name;
  public Account(String nm,double amnt ) {
        amount=amnt;
        name=nm;
  }
  synchronized  void depsite(double money){
      amount+=money;
      }
  synchronized  void withdraw(double money){
      amount-=money;
      }
  synchronized  void transfer(Account ac,double mn){
      amount-=mn;
      if (name.equals("D")) {
	System.out.println("unprotected");
            ac.amount += mn;
      } else {
	synchronized (ac) { ac.amount+=mn; }
      }
  }
 synchronized void print(){
  }
      }
