package wrongLock;
public class WrongLock {
    Data data;
    public WrongLock(Data data) {
        this.data = data;
    }
    public void A() {
        synchronized (data) {
            int x = data.value;
            data.value++;
            if (data.value != (x + 1))
                throw new RuntimeException("bug found");
        }
    }
    public void B() {
        synchronized (this) {
            data.value++;
        }
    }
}
