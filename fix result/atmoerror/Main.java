package atmoerror;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        Thread t1 = new Thread(new Customer(5, account));
        Thread t2 = new Thread(new Customer(5, account));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        if (account.getTotal() != 10)
            throw new RuntimeException();
    }
}
