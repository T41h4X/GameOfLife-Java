import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A dynamic array implementation that can grow and shrink as needed.
 * This is a custom implementation to replace Java's ArrayList.
 * 
 * @param <T> the type of elements stored in this dynamic array
 * @author Your Name Here
 */
public class DynamicArray<T> implements Iterable<T> {

    private static final int INITCAP = 2; // default initial capacity
    private T[] storage; // underlying array, you MUST use this for credit (do not change the name or type)
    private int currentSize; // current number of elements

    /**
     * Default constructor that creates a DynamicArray with initial capacity of 2.
     */
    @SuppressWarnings("unchecked")
    public DynamicArray() {
        storage = (T[]) new Object[INITCAP];
        currentSize = 0;
    }

    /**
     * Constructor that creates a DynamicArray with specified initial capacity.
     * 
     * @param initCapacity the initial capacity of the storage array
     * @throws IllegalArgumentException if initCapacity < 1
     */
    @SuppressWarnings("unchecked")
    public DynamicArray(int initCapacity) {
        if (initCapacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be zero or negative.");
        }
        storage = (T[]) new Object[initCapacity];
        currentSize = 0;
    }

    /**
     * Reports the current number of elements.
     * 
     * @return the number of elements currently in the array
     */
    public int size() {
        return currentSize;
    }

    /**
     * Reports the maximum number of elements before expansion is needed.
     * 
     * @return the current capacity of the storage array
     */
    public int capacity() {
        return storage.length;
    }

    /**
     * Changes the item at the given index to be the given value.
     * Returns the old item at that index.
     * Note: You cannot add new items with this method.
     * 
     * @param index the index to set
     * @param value the new value
     * @return the old value at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T set(int index, T value) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        T oldValue = storage[index];
        storage[index] = value;
        return oldValue;
    }

    /**
     * Returns the item at the given index.
     * 
     * @param index the index to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T get(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        return storage[index];
    }

    /**
     * Appends an element to the end of the list.
     * Doubles the capacity if no space is available.
     * 
     * @param value the value to add
     * @return true (as specified by Collection.add)
     */
    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        if (currentSize >= storage.length) {
            // Double the capacity
            T[] newStorage = (T[]) new Object[storage.length * 2];
            for (int i = 0; i < currentSize; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }
        storage[currentSize] = value;
        currentSize++;
        return true;
    }

    /**
     * Inserts the given value at the given index.
     * Shifts elements if needed, doubles capacity if no space available.
     * 
     * @param index the index at which to insert
     * @param value the value to insert
     * @throws IndexOutOfBoundsException if index is invalid for insertion
     */
    @SuppressWarnings("unchecked")
    public void add(int index, T value) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }

        if (currentSize >= storage.length) {
            // Double the capacity
            T[] newStorage = (T[]) new Object[storage.length * 2];
            for (int i = 0; i < currentSize; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }

        // Shift elements to the right
        for (int i = currentSize; i > index; i--) {
            storage[i] = storage[i - 1];
        }

        storage[index] = value;
        currentSize++;
    }

    /**
     * Removes and returns the element at the given index.
     * Shifts elements to remove the gap.
     * Halves capacity if number of elements falls below 1/3 of capacity.
     * 
     * @param index the index of the element to remove
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }

        T removedElement = storage[index];

        // Shift elements to the left
        for (int i = index; i < currentSize - 1; i++) {
            storage[i] = storage[i + 1];
        }

        currentSize--;
        storage[currentSize] = null; // Help GC

        // Shrink if needed (below 1/3 capacity)
        if (storage.length > INITCAP && currentSize < storage.length / 3) {
            T[] newStorage = (T[]) new Object[storage.length / 2];
            for (int i = 0; i < currentSize; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }

        return removedElement;
    }

    /**
     * Returns an iterator over the elements in this dynamic array.
     * 
     * @return an iterator over the elements
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return storage[currentIndex++];
            }

            public boolean hasNext() {
                return currentIndex < currentSize;
            }
        };
    }

    /**
     * Returns a string representation of this dynamic array.
     * This method is provided for debugging purposes.
     * 
     * @return a string representation of the array
     */
    public String toString() {
        StringBuilder s = new StringBuilder("Dynamic array with " + size()
            + " items and a capacity of " + capacity() + ":");
        for (int i = 0; i < size(); i++) {
            s.append("\n ["+i+"]: " + get(i));
        }
        return s.toString();
    }

    /**
     * Main method for testing the DynamicArray implementation.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String args[]) {
        DynamicArray<Integer> ida = new DynamicArray<>();
        if ((ida.size() == 0) && (ida.capacity() == 2)) {
            System.out.println("Yay 1");
        }

        boolean ok = true;
        for (int i = 0; i < 3; i++)
            ok = ok && ida.add(i * 5);
        if (ok && ida.size() == 3 && ida.get(2) == 10 && ida.capacity() == 4) {
            System.out.println("Yay 2");
        }

        ida.add(1, -10);
        ida.add(4, 100);
        if (ida.set(1, -20) == -10 && ida.get(2) == 5 && ida.size() == 5
            && ida.capacity() == 8) {
            System.out.println("Yay 3");
        }

        if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100
            && ida.size() == 2 && ida.capacity() == 4) {
            System.out.println("Yay 4");
        }

        System.out.print("Printing values: ");
        for (Integer i : ida) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }
}
