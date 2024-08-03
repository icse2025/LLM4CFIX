package accountsubtype;
public class Main {
    public static void main(String[] args) {
        int numBusinessAccounts = 20;
        int numPersonalAccounts = 1;
        if (args != null && args.length == 2) {
            numBusinessAccounts = Integer.parseInt(args[0]);
            numPersonalAccounts = Integer.parseInt(args[1]);
        }
        Bank bank = new Bank(numBusinessAccounts, numPersonalAccounts, 100);
        bank.work();
        bank.printAllAccounts();
        for (int i = 0; i < numBusinessAccounts + numPersonalAccounts; i++) {
            if (bank.getAccount(i).getBalance() != 300) {
                throw new RuntimeException("bug found");
            }
        }
    }
}
