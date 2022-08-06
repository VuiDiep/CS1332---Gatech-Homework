package homework6Summer;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Xuan Vui Diep
 * @version 1.0
 * @userid xdiep3
 * @GTID 903741208
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            T temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j > -1 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }

    }

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null");
        }
        boolean swapped;
        for (int i = 0; i < arr.length   - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }

    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator i s null");
        }
        if (arr.length <= 1) {
            return;
        }
        //copy the two halves of the array
        int midIndex = arr.length / 2;
        T[] leftArr = (T[]) (new Object[midIndex]);
        for (int i = 0; i < midIndex; i++) {
            leftArr[i] = arr[i];
        }
        T[] rightArr = (T[]) (new Object[arr.length - midIndex]);
        for (int i = midIndex; i < arr.length; i++) {
            rightArr[i - midIndex] = arr[i];
        }
        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);
        //indices for merging
        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;
        //merge data into original array only while both subarrays are not empty
        while (leftIndex < midIndex && rightIndex < arr.length - midIndex) {
            if (comparator.compare(leftArr[leftIndex],
                    rightArr[rightIndex]) <= 0) {
                arr[currentIndex] = leftArr[leftIndex];
                leftIndex++;
            } else {
                arr[currentIndex] = rightArr[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }
        //handles emptying the other subarray if one became empty in first loop
        while (leftIndex < midIndex) {
            arr[currentIndex] = leftArr[leftIndex];
            leftIndex++;
            currentIndex++;
        }
        while (rightIndex < arr.length - midIndex) {
            arr[currentIndex] = rightArr[rightIndex];
            rightIndex++;
            currentIndex++;
        }

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("array is null");
        }
        int maxNum = arr[0];
        int maxDigits = 1;
        //find the greatest number and its number of digits
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxNum) {
                maxNum = Math.abs(arr[i]);
            }
        }
        while (maxNum >= 10) {
            maxDigits++;
            maxNum = maxNum / 10;
        }
        //instantiate bucket array
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int x = 1; //factor to divide numbers by
        for (int i = 0; i < maxDigits; i++) {
            for (Integer num : arr) {
                int currentDigit = (num / x) % 10 + 9;
                buckets[currentDigit].addLast(num);
            }
            int index = 0;
            for (int k = 0; k < buckets.length; k++) {
                for (Integer item : buckets[k]) {
                    arr[index++] = item;
                }
                buckets[k].clear();
            }
            x *= 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i : data) {
            q.add(i);
        }
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = q.remove();
        }
        return arr;
    }
}