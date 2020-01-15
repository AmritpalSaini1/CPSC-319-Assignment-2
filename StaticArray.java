/*
 * **************************
 * 	StaticArray.java 
 * 
 * ************************** 
 */

/**
 * An implementation of a static array. An array is a sequenced collection of
 * variables all of the same type. Static arrays have a size that is fixed when
 * they are created.
 * <p>
 * 
 * Adapted from: Data Structures and Algorithms in Java, Sixth Edition
 * <p>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 * <p>
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 * @author Md Mamunur Rashid (mmncit@gmail.com)
 * 
 * 
 */

public class StaticArray<E> {
    // instance variables
    /** Default array capacity. */
    public static final int CAPACITY = 16; // default array capacity

    /** Generic array used for storage of array elements. */
    private E[] data; // generic array used for storage

    /** Current number of elements in the array. */
    private int size = 0; // current number of elements

    // constructors
    /** Creates an array array with default initial capacity. */
    public StaticArray() {
	this(CAPACITY);
    } // constructs array with default capacity

    /**
     * Constructs array with default capacity.
     * 
     * @param capacity
     *            default capacity
     */
    @SuppressWarnings({ "unchecked" })
    public StaticArray(int capacity) { // constructs array with given capacity
	data = (E[]) new Object[capacity]; // safe cast; compiler may give
					   // warning
    }

    // public methods
    /**
     * Returns the number of elements in the array.
     * 
     * @return number of elements in the array
     */
    public int size() {
	return size;
    }

    /**
     * Returns the capacity in the array.
     * 
     * @return number of size of the storage
     */
    public int capacity() {
	return data.length;
    }

    /**
     * Tests whether the array array is empty.
     * 
     * @return true if the array array is empty, false otherwise
     */
    public boolean isEmpty() {
	return size == 0;
    }

    /**
     * Returns (but does not remove) the element at index i.
     * 
     * @param i
     *            the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException
     *             if index &lt; 0 || index &gt;= size()
     */
    public E get(int i) throws IndexOutOfBoundsException {
	checkBoundsExclusive(i);
	return data[i];
    }

    /**
     * Replaces the element at the specified index, and returns the element
     * previously stored.
     * 
     * @param i
     *            the index of the element to replace
     * @param e
     *            the new element to be stored
     * @return the previously stored element
     * @throws IndexOutOfBoundsException
     *             if index &lt; 0 || index &gt;= size()
     */
    public E set(int i, E e) throws IndexOutOfBoundsException {
	checkBoundsExclusive(i);
	E temp = data[i];
	data[i] = e;
	return temp;
    }

    /**
     * Inserts the given element at the specified index of the array, shifting
     * all subsequent elements in the array one position further to make room.
     * 
     * @param i
     *            the index at which the new element should be stored
     * @param e
     *            the new element to be stored
     * @throws IndexOutOfBoundsException
     *             if index &lt; 0 || index &gt; size()
     * @throws IllegalStateException
     *             if array is full
     */
    public void add(int i, E e) throws IndexOutOfBoundsException,
	    IllegalStateException {
	checkBoundInclusive(i);
	if (size == data.length) // not enough capacity
	    throw new IllegalStateException("Array is full");
	for (int k = size - 1; k >= i; k--)
	    // start by shifting rightmost
	    data[k + 1] = data[k];
	data[i] = e; // ready to place the new element
	size++;
    }

    /**
     * Appends the supplied element to the end of this array. The element, e,
     * can be an object of any type or null.
     * 
     * @param e
     *            the element to be appended to this array *
     * @throws IllegalStateException
     *             if array is full
     * @return true if the add is succeeded.
     */
    public boolean add(E e) throws IllegalStateException {
	if (size == data.length) {// not enough capacity
	    System.out.println("Size: " + size + " Length: " + data.length);
	    throw new IllegalStateException("Array is full");
	}
	data[size++] = e;
	return true;
    }

    /**
     * Removes and returns the element at the given index, shifting all
     * subsequent elements in the array one position closer to the front.
     * 
     * @param i
     *            the index of the element to be removed
     * @return the element that had be stored at the given index
     * @throws IndexOutOfBoundsException
     *             if the index is negative or greater than size()
     */
    public E remove(int i) throws IndexOutOfBoundsException {
	checkBoundsExclusive(i);
	E temp = data[i];
	for (int k = i; k < size - 1; k++)
	    // shift elements to fill hole
	    data[k] = data[k + 1];
	data[size - 1] = null; // help garbage collection
	size--;
	return temp;
    }

    // Utility methods
    /**
     * Checks that the index is in the range of existing elements (exclusive).
     * 
     * @param index
     *            the index to check
     * @throws IndexOutOfBoundsException
     *             if index &lt; 0 || index &gt;= size
     */
    private void checkBoundsExclusive(int index) {
	if (index < 0 || index >= size)
	    throw new IndexOutOfBoundsException("Illegal index: " + index
		    + ", Size:" + size);
    }

    /**
     * Checks that the index is in the range of possible elements (inclusive).
     * 
     * @param index
     *            the index to check
     * @throws IndexOutOfBoundsException
     *             if index &lt; 0 || index &gt; size
     */
    private void checkBoundInclusive(int index) {

	if (index < 0 || index > size)
	    throw new IndexOutOfBoundsException("Illegal Index: " + index
		    + ", Size: " + size);
    }

    /** Resizes internal array to have given capacity >= size. */
    @SuppressWarnings({ "unchecked" })
    protected void resize(int capacity) {
	E[] temp = (E[]) new Object[capacity]; // safe cast; compiler may give
					       // warning
	for (int k = 0; k < size; k++)
	    temp[k] = data[k];
	data = temp; // start using the new array
    }

    /**
     * Returns the lowest index at which element appears in this array, or -1 if
     * it does not appear.
     * 
     * @param e
     *            the element whose inclusion in the array is being tested
     * @return the index where e was found
     */
    public int indexOf(Object e) {
	for (int i = 0; i < size; i++)
	    if (e.equals(data[i]))
		return i;
	return -1;
    }

    /**
     * Returns true if element is in this Arrayarray.
     * 
     * @param e
     *            the element whose inclusion in the array is being tested
     * @return true if the array contains e
     */
    public boolean contains(Object e) {
	return indexOf(e) != -1;
    }

    /**
     * Obtain a static array of a subsection, from fromIndex (inclusive) to
     * toIndex.
     * 
     * @param fromIndex
     *            the index that the returned array should start from
     *            (inclusive)
     * @param toIndex
     *            the index that the returned array should go to (exclusive)
     * @return a static array backed by a subsection of the array
     * @throws IndexOutOfBoundsException
     *             if fromIndex &lt; 0 || toIndex &gt; size() || fromIndex &gt;
     *             toIndex
     */
    public StaticArray<E> subArray(int fromIndex, int toIndex) {

	if (toIndex < fromIndex)
	    throw new IndexOutOfBoundsException("fromIndex: " + fromIndex
		    + "should be less than or equal to toIndex " + toIndex);

	// check if the indices are valid
	checkBoundsExclusive(fromIndex);
	checkBoundsExclusive(toIndex);

	int n = toIndex - fromIndex + 1; // number of elements in the sub-array
	StaticArray<E> subArray = new StaticArray<>(n);

	for (int i = fromIndex; i <= toIndex; i++) {
	    // add elements into the sub-array from main array
	    subArray.add(this.get(i));
	}
	return subArray;
    }

    /**
     * Trims the capacity of this array to be equal to its size; a memory saver.
     */
    public void trimToSize() {
	if (size != data.length) {
	    @SuppressWarnings("unchecked")
	    E[] newData = (E[]) new Object[size];
	    System.arraycopy(data, 0, newData, 0, size);
	    data = newData;
	}
    }

    /**
     * Generate a string array
     * 
     * @return array of strings corresponding to the array
     */
    public String[] toStringArray() {
	// create an array of current size
	String[] strings = new String[size];

	for (int i = 0; i < size; i++)
	    // fetch and type-cast
	    strings[i] = (String) this.get(i);

	return strings;
    }

    /**
     * Produces a string representation of the contents of the array. This
     * exists for debugging purposes only.
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("(");
	for (int j = 0; j < size; j++) {
	    if (j > 0)
		sb.append(", ");
	    sb.append(data[j]);
	}
	sb.append(")");
	return sb.toString();
    }

}