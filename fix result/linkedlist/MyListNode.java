package linkedlist;
    class MyListNode
    {
		public Object _element;			
        public MyListNode _next;		
        MyListNode( Object theElement ){ this( theElement, null ); }
        MyListNode( Object theElement, MyListNode n )
        {
			synchronized ( this )
			{
				this._element = theElement;
				this._next = n;
            }
        }
    }
