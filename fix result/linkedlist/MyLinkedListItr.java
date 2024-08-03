package linkedlist;
    class MyLinkedListItr
    {
		public MyListNode _current;    
        MyLinkedListItr( MyListNode theNode ){ this._current = theNode; }
        public boolean isPastEnd( ){ return this._current == null; }
        public Object retrieve( )
        {
            return isPastEnd( ) ? null : this._current._element;
        }
        public void advance( )
        {
            if( !isPastEnd( ) )
                this._current = this._current._next;
        }
    }
