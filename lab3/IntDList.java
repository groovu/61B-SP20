/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Vivant Sakore (1/29/2020)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        int value = 0;
//        DNode ptr = this._front;
//        if (ptr._val == 0){
//            return value;
//        }
//        while (ptr._next != null){
//            value += 1;
//            ptr = ptr._next;
//        }
        IntDList ptr = this;
        DNode counter = this._front;
        if (ptr._front == null && ptr._back == null){
            return value;
        } else {
            value += 1;
            while (counter._next != null){
                value += 1;
                counter = counter._next;
            }
        }
        return value;
    }

    /**
     * @param i index of element to return,
     *          where i = 0 returns the first element,
     *          i = 1 returns the second element,
     *          i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size for positive indices
     *          and -size <= i <= -1 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        // FIXME: Implement this method and return correct value
//        if (i >= 0) {
//            DNode pointer = _front;
//            while (i > 0) {
//                pointer = pointer._next;
//                i -= 1;
//            }
//        return pointer._val;
//        } else {
//            DNode pointer = _back;
//            while (i < 0) {
//                pointer = pointer._prev;
//                i += 1;
//            }
//            return pointer._val;
//        }
//    }
        DNode pointer = _front;
        if (i == 0) {
            return pointer._val;
        } else if (i > 0) {
            while (i > 0) {
                pointer = pointer._next;
                i -= 1;
            }
        } else {
            pointer = _back;
            i += 1;
            while (i < 0) {
                pointer = pointer._prev;
                i += 1;
            }
        }
        return pointer._val;
    }

    public DNode getIndex(int i) {
        // FIXME: Implement this method and return correct value
//        if (i >= 0) {
//            DNode pointer = _front;
//            while (i > 0) {
//                pointer = pointer._next;
//                i -= 1;
//            }
//        return pointer._val;
//        } else {
//            DNode pointer = _back;
//            while (i < 0) {
//                pointer = pointer._prev;
//                i += 1;
//            }
//            return pointer._val;
//        }
//    }
        DNode pointer = _front;
        if (i == 0) {
            return pointer;
        } else if (i > 0) {
            while (i > 0) {
                pointer = pointer._next;
                i -= 1;
            }
        } else {
            pointer = _back;
            i += 1;
            while (i < 0) {
                pointer = pointer._prev;
                i += 1;
            }
        }
        return pointer;
    }


    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        if (_front != null) {
            _front = new DNode(null, d, _front);
            _front._next._prev = _front;
        } else {
            _front = _back = new DNode(null, d, null);
        }
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        if (_back != null) {
            _back = new DNode(_back, d, null);
            _back._prev._next = _back;
        } else {
            _back = _front = new DNode(null, d, null);
        }
    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position, and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices (including insertions at front and back)
     *              and -(size+1) <= index <= -1 for negative indices (including insertions at front and back).
     */
    public void insertAtIndex2(int d, int index) {
        if (this._front == null && this._back == null) {
            DNode a = new DNode(null, d, null);
            this._front = this._back = a;
        } else if (index >= size()-1){
            DNode insertEnd = new DNode(_back, d, null);
            _back._next = insertEnd;
            this._back = insertEnd;
        } else {
            DNode ptr = getIndex(index);
            DNode insert = new DNode(ptr._prev, d, ptr);
            if (ptr._prev != null) {
                ptr._prev._next = insert;
                ptr._prev = insert;
            }
            ptr._prev = insert;
            if (index == 0){
                this._front = insert;
            }
        }
        // FIXME: Implement this method
    }

    public void insertAtIndex(int d, int index) {
        if (this._front == null && this._back == null) {
            this._front = this._back = new DNode(null, d, null);
        }
        DNode ptr = getIndex(index);
        if (ptr == null) {
            if (index >= size()){
                DNode ptrr = this._back;
                DNode insertEnd = new DNode(ptrr, d, null);
                ptrr._next = insertEnd;
                this._back = insertEnd;
            } else {
                DNode ptrr = this._front;
                DNode insertEnd = new DNode(null, d, ptrr);
                ptrr._prev = insertEnd;
                this._front = insertEnd;
            }
        } else if (ptr._prev == null & ptr._next == null){ //x_x
            DNode insertEnd = new DNode(null, d, ptr);
            ptr._prev = insertEnd;
        } else if ((ptr._prev != null && ptr._next == null) || index >= size()) { // o_x
            DNode insertEnd = new DNode(ptr, d, null);
            ptr._next = insertEnd;
            //ptr._prev._next = insertEnd;
            //ptr._prev = insertEnd;
            this._back = insertEnd;
        } else if (ptr._prev == null && ptr._next != null) { // x_o
            DNode insertEnd = new DNode(null, d, ptr);
            ptr._prev = insertEnd;
            this._front = insertEnd;
        } else {
            if (index >= 0) {
                DNode insertEnd = new DNode(ptr._prev, d, ptr);
                ptr._prev._next = insertEnd;
                ptr._prev = insertEnd;
            } else {
                DNode insertEnd = new DNode(ptr, d, ptr._next);
                ptr._next._prev = insertEnd;
                ptr._next = insertEnd;
            }
        }
    }

    /**
     * Removes the first item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        int value = _front._val;
        if (_front == _back) {
            _front = null;
            _back = null;
        } else {
            _front = _front._next;
            _front._prev = null;
        }
        return value;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        int value = _back._val;
        if (_front == _back) {
            _front = null;
            _back = null;
        } else {
            _back = _back._prev;
            _back._next = null;
        }
        return value;
    }

    /**
     * @param index index of element to be deleted,
     *          where index = 0 returns the first element,
     *          index = 1 will delete the second element,
     *          index = -1 will delete the last element,
     *          index = -2 will delete the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices (including deletions at front and back)
     *              and -size <= index <= -1 for negative indices (including deletions at front and back).
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        // FIXME: Implement this method and return correct value
        return 0;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        String intDString = "[";
        int loop = size();
        int counter = 0;
        if (size() == 0){
            return "[]";
        }
        for (counter = 0; counter < loop - 1; counter += 1) {
            intDString += get(counter) +", ";
        }
        intDString += get(counter) + "]";
        return intDString;
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
