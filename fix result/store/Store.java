package store;
public class Store {
	private int customerCost;
	public Store() {
		customerCost = 0;
	}
	public void consume(int cost) {
		customerCost += cost;
	}
	public int getCost() {
		return customerCost;
	}
}
