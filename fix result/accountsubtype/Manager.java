package accountsubtype;
public class Manager extends Thread {
  Bank bank;
  Account account;
  int accountNumber;
  public Manager(Bank b, Account a, int n) {
    bank = b;
    account = a;
    accountNumber = n;
  }
  public void run(){
    int nextNumber;
    account.deposit(300);
    nextNumber = bank.nextAccountNumber(accountNumber);
    account.transfer(bank.getAccount(nextNumber),10);
    account.deposit(10);
    account.withdraw(20);
    account.deposit(10);
    account.transfer(bank.getAccount(nextNumber),10);
    account.withdraw(100);
  }
}
