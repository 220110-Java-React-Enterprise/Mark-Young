package bankingApp;

import java.util.Iterator;

/**
 * A fairly simple arraylist implementation extending custom list interface.
 * Default size is 2, grows by size * 2 when needed.
 * When an element is added or removed at an index other elements are not re-arranged.
 *
 * @param <E>
 */
public class CustomArrayList<E> implements CustomListInterface<E> {
    private Object[] array;
    private int size;
    private int maxSize;

    /**
     * Default constructor, creates an empty underlying array with maxSize 2
     */
    public CustomArrayList() {
        maxSize = 2;
        size = 0;
        array = new Object[maxSize];
    }

    /**
     * Sized constructor, creates an empty object with maxSize size
     * @param size the initial size of the underlying array
     */

    // I am editing this part to:
    //      1) throw IndexOutOfBoundsException
    //      2) set size of array to maxSize instead of size

    public CustomArrayList(int size) throws IndexOutOfBoundsException {
        IndexOutOfBoundsException exc = new IndexOutOfBoundsException();
        if (size < 0) {
            throw exc;
        }
        this.maxSize = size;
        this.size = 0;
        this.array = new Object[this.maxSize];
    }

    /**
     * Element list constructor, takes in variable number of objects and creates an underlying
     * array large enough to fit them.
     * @param e
     */
    public CustomArrayList(E... e) {
        maxSize = size = e.length;
        array = new Object[size];

        // System.arraycopy(e, 0, array, 0, size);

        for (int i = 0; i < size; ++i) {
            array[i] = e[i];
        }

    }


    /**
     * Adds an object to the underlying array after all previously added objects.
     * If array needs to grow, it invokes grow method.
     * @param o object to be added
     */
    @Override
    public void add(Object o) {
        //Implement this method
        // NOTE: if size >= maxSize we need to grow array

        if(this.size >= this.maxSize) {
            growArray();
        }
        this.array[this.size] = o;
        this.size++;
    }

    /**
     * Adds object at specified index, advancing the size of the underlying array. This will
     * require us to shift all later elements further down the index order
     * @param index index location where object will be inserted
     * @param e element to be inserted
     * @throws IndexOutOfBoundsException
     */
    @Override
    public void add(E e, int index) throws IndexOutOfBoundsException {
        //Implement this method

        IndexOutOfBoundsException exc = new IndexOutOfBoundsException();

        if (index < 0) {
            throw exc;
        } else if (index > this.size) {
            throw exc;
        } else if (index == this.size) {
            if(this.size >= this.maxSize) {
                growArray();
            }
            array[index] = e;
            this.size++;
        } else {
            for (int i = index; i < this.size; i++) {
                array[this.size + index - i] = array[this.size + index - i - 1];
            }
            array[index] = e;
            this.size++;
        }
    }




/* I wrote a setter method for this CustomArrayList
 This method, given an index of the list, sets that location to the new elem E.
 @param index: desired index of changing element.
 @param elem: element to replace old one at desired index.
 */
    // @Override
    public void set(int index, E elem) throws IndexOutOfBoundsException {

        IndexOutOfBoundsException exc = new IndexOutOfBoundsException();

        if (index < 0) {
            throw exc;
        } else if (index == this.size) {
            this.add(elem);
        } else if (index > this.size) {
            throw exc;
        } else {
            array[index] = elem;
        }
    }





    /**
     * gets the object located at supplied index
     * @param index index of object to get
     * @return object located at index
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        //Implement this method

        IndexOutOfBoundsException exc = new IndexOutOfBoundsException();

        if (index < 0) {
           throw exc;
        } else if (index >= this.size) {
           throw exc;
        } else {
           return (E) array[index];
        }
    }

    /**
     * Emptys the underlying array by setting it's private reference to null and allowing
     * the old array to be garbage collected.
     */
    @Override
    public void clear() {
        //Implement this method
        // oh boy, when i stopped using this method, it started working.

        this.array = null;
        this.array = new Object[this.maxSize];
        this.size = 0;
    }

    /**
     * Check if object o is found within underlying array, using Object.equals() method
     * @param o object to search for
     * @return index location of first instance of matching object. -1 if not found.
     */
    @Override
    public int contains(Object o) {
        //Implement this method

        for(int i = 0; i < this.size; i++) {
            if (o.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes object at specified index from underlying array, we will then
     * need to shift the remaining elements up in the index order to fill in the gap
     * @param index index of object to remove from array
     */
    @Override
    public void remove(int index) throws IndexOutOfBoundsException {
        //Implement this method

        IndexOutOfBoundsException exc = new IndexOutOfBoundsException();

        if (index < 0) {
            throw exc;
        } else if (index >= this.size) {
            throw exc;
        } else if (index == this.size - 1) {
            this.add(null, index);
            this.size--;
        } else {
            for (int i = index; i < this.size-1; i++) {
                this.array[i] = this.array[i+1];
            }
            this.add(null, this.size - 1);
            this.size--;
        }
    }

    /**
     * returns size of array. This is the one greater than the index of the most advanced stored object,
     * not the maxSize which controls growth of the underlying array.
     * @return one greater than index of most advanced stored object
     */
    @Override
    public int size() {
        //Implement this method

        return this.size;
    }


    /**
     * Doubles the size of the underlying array by creating a new array and copying the
     * contents of the previous array into it.
     */
    private void growArray(){
        //System.out.println("Growing Array from " + maxSize + " to " + maxSize * 2);
        //set up new array
        maxSize = maxSize * 2;
        Object[] tempArray = array;
        array = new Object[maxSize];

        //copy to new array
        for (int i = 0; i < size; i++) {
            array[i] = tempArray[i];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            //cursor
            int cursor = 0;

            /**
             * checks if linked list has another node, testing if cursor points to a node
             * or if it is null
             * @return true if cursor points to a node, false if cursor node reference is null
             */
            @Override
            public boolean hasNext() {
                if (cursor <= size - 1) {
                    return true;
                }
                return false;
            }

            /**
             * returns node the cursor points to, then advances cursor to next node
             * @return the object at the location of cursor
             */
            @Override
            public E next() {
                return (E) array[cursor++];
            }
        };
    }

}
