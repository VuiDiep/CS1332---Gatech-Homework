package homework1Summer;

import java.util.NoSuchElementException;
/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Xuan Vui Diep
 * @version 1.0
 * @userid xdiep3
 * @GTID 903741208
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot insert index that is out of range.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        } else if (head == null) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
            size++;
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            SinglyLinkedListNode temp = new SinglyLinkedListNode<>(data);
            temp.setNext(curr.getNext());
            curr.setNext(temp);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        } else if (head == null) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
            size++;
        } else {
            SinglyLinkedListNode temp = new SinglyLinkedListNode<>(data);
            temp.setNext(head);
            head = temp;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        } else if (head == null) {
            head = new SinglyLinkedListNode<>(data);
            tail = head;
            size++;
        } else {
            SinglyLinkedListNode temp = new SinglyLinkedListNode<>(data);
            tail.setNext(temp);
            tail = temp;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T removed = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot insert index that is out of range.");
        } else if (index == 0) {
            removed = head.getData();
            removeFromFront();
        } else if (index == size - 1) {
            removed = tail.getData();
            removeFromBack();
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
        }
        return removed;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T removed = head.getData();
        if (this.isEmpty()) {
            throw new NoSuchElementException("the list is empty");
        } else if (size == 1) {
            head = null;
            size--;
        } else {
            head = head.getNext();
            size--;
        }
        return removed;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T removed = null;
        if (this.isEmpty()) {
            throw new NoSuchElementException("the list is empty");
        } else if (size == 1) {
            removed = head.getData();
            head = null;
            size--;
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < size - 2; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext().getData();
            curr.setNext(null);
            tail = curr;
            size--;
        }
        return removed;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        T dataAtIndex = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot insert index that is out of range.");
        } else if (index == 0) {
            dataAtIndex = head.getData();
        } else if (index == size - 1) {
            dataAtIndex = tail.getData();
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            dataAtIndex = curr.getData();
        }
        return dataAtIndex;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        T removed = null;
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (this.isEmpty()) {
            throw new NoSuchElementException("the list is empty");
        } else {
            SinglyLinkedListNode<T> curr = head;
            SinglyLinkedListNode<T> removedCurr = null;
            if (head.getData().equals(data)) {
                removedCurr = head;
            }
            for (int i = 0; i < size - 1; i++) {
                if (curr.getNext().getData().equals(data)) {
                    removedCurr = curr;
                }
                curr = curr.getNext();
            }
            if (removedCurr == null) {
                throw new NoSuchElementException("The requested data was not found");
            }
            if (removedCurr == head) {
                return removeFromFront();
            }
            removed = removedCurr.getNext().getData();
            removedCurr.setNext(removedCurr.getNext().getNext());
            size--;
        }
        return removed;
        
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        SinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}