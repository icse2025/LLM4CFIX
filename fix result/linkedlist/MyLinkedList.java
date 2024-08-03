package linkedlist;
import java.io.IOException;
class MyLinkedList {
    private MyListNode _header;    
    private int _maxsize;
    public MyLinkedList(int n) {
        this._header = new MyListNode(null);
        this._maxsize = n;
    }
    public boolean isEmpty() {
        return this._header._next == null;
    }
    public void clear() {
        this._header._next = null;
    }
    public MyLinkedListItr first() {
        return new MyLinkedListItr(this._header._next);
    }
    public void insert(Object x, MyLinkedListItr p) {
        if (p != null && p._current != null) {
            MyListNode tmp;
            synchronized (this) {
                tmp = new MyListNode(x, p._current._next);
            } 
            p._current._next = tmp;
        }
    }
    public void addLast(Object x) {
        MyListNode itr = this._header;
        while (itr._next != null)
            itr = itr._next;
        insert(x, new MyLinkedListItr(itr));
    }
    public int size() {
        MyListNode itr = this._header;
        int i = 0;
        while (itr._next != null) {
            i++;
            itr = itr._next;
        }
        return i;
    }
    public MyLinkedListItr find(Object x) {
        MyListNode itr = this._header._next;
        while (itr != null && !itr._element.equals(x))
            itr = itr._next;
        return new MyLinkedListItr(itr);
    }
    public MyLinkedListItr findPrevious(Object x) {
        MyListNode itr = this._header;
        while (itr._next != null && !itr._next._element.equals(x))
            itr = itr._next;
        return new MyLinkedListItr(itr);
    }
    public void remove(Object x) {
        MyLinkedListItr p = findPrevious(x);
        if (p._current._next != null)
            p._current._next = p._current._next._next;  
    }
    public void printList(MyLinkedList theList) throws IOException {
        int x;
        if (theList.isEmpty())
            x = 1;
        else {
            MyLinkedListItr itr = theList.first();
            for (; !itr.isPastEnd(); itr.advance())
                x = 1;
        }
        if (this.size() != this._maxsize)
            throw new RuntimeException("bug found");
    }
}
