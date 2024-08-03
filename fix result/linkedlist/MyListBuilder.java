package linkedlist;
import java.io.IOException;
class MyListBuilder implements Runnable
{
	public boolean _debug = true;
	public Object _list = null;
	public int _bound1 = 0;
	public int _bound2 = 0;
	public MyListBuilder(Object lst,int bnd1,int bnd2,boolean dbg)
	{
		this._debug = dbg;
			this._list = (MyLinkedList)lst;
		this._bound1 = bnd1;
		this._bound2 = bnd2;
	}
	public void run()
	{
		for ( int i = this._bound1; i < this._bound2 ;i++ )
		{
				((MyLinkedList)_list).addLast(new Integer(i));
		}
	}
	public void print()
	{
		int size;
		int x;
			size = ((MyLinkedList)_list).size();
		try
		{
				((MyLinkedList)this._list).printList((MyLinkedList)_list);
		}
		catch(IOException e)
		{
		}
	}
	public void empty()
	{
			((MyLinkedList)_list).clear();
	}
}
