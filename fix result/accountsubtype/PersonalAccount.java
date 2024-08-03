package accountsubtype;
public class PersonalAccount extends Account {
  public PersonalAccount(int number, int initialBalance) {
    super(number, initialBalance);
  }
  public synchronized void transfer(Account ac, int mn){
      amount-=mn;
      ac.amount+=mn;
  }
}
