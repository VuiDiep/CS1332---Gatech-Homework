package homework5Summer;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Xuan Vui Diep
 * @userid xdiep3
 * @GTID 903741208
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        for (T element: data) {
            add(element);
        }

    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        if (root == null) {
            root = new AVLNode<T>(data);
            size++;
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * Helper method to get height from left child
     * @param node Node to get height
     * @return height of the node
     */
    private int heightLeft(AVLNode<T> node) {
        if (node.getLeft() == null) {
            return -1;
        } else {
            return node.getLeft().getHeight();
        }
    }
    /**
     * Helper method to get height from right child
     * @param node Node to get height
     * @return height of the node
     */
    private int heightRight(AVLNode<T> node) {
        if (node.getRight() == null) {
            return -1;
        } else {
            return node.getRight().getHeight();
        }
    }

    /**
     * Helper method to get Balance Factor
     * @param node Node to get balance
     * @return balance factor
     */
    private int balance(AVLNode<T> node) {
        return heightLeft(node) - heightRight(node);
    }

    /**
     * Rotation right method that rotates right around the specified node
     * @param node Node that need to be rotated
     * @return recursive return
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        AVLNode<T> grandChild = child.getRight();

        child.setRight(node);
        node.setLeft(grandChild);

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        child.setHeight(Math.max(heightLeft(child), heightRight(child)) + 1);
        child.setBalanceFactor(balance(child));
        return child;
    }

    /**
     * Rotation left method that rotates left around the specified node
     * @param node Node that need to be rotated
     * @return recursive return
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> child = node.getRight();
        AVLNode<T> grandChild = child.getLeft();

        child.setLeft(node);
        node.setRight(grandChild);

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        child.setHeight(Math.max(heightLeft(child), heightRight(child)) + 1);
        child.setBalanceFactor(balance(child));
        return child;
    }

    /**
     * Helper method that add travel to the tree, add data and balance the tree
     * @param data Data that need to be added
     * @param node current node
     * @return next node
     *
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) == 0) {
            return node;
        }
        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        if (node.getBalanceFactor() > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return rotateLeft(node);
        }
        if (node.getBalanceFactor() > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }



    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(data, root, dummy);
        size--;
        if (dummy.getData() == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            return dummy.getData();
        }
    }
    /**
     * Helper method that recursive to find the node to remove and balance the tree after removing it
     * @param node the node for recursion
     * @param dummy dummy node with removed data
     * @param data data need to check for
     * @return return the current node
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            return null;
        }
        if (node.getData().compareTo(data) == 0) {
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                T removed = node.getData();
                dummy.setData(removed);
                return null;
            } else if (node.getLeft() == null) {
                T removed = node.getData();
                dummy.setData(removed);
                return node.getRight();
            } else if (node.getRight() == null) {
                T removed = node.getData();
                dummy.setData(removed);
                return node.getLeft();
            } else {
                T removed = node.getData();
                dummy.setData(removed);
                AVLNode<T> dummy2 = new AVLNode<>(data);
                node.setLeft(predecessor(node.getLeft(), dummy2));
                node.setData(dummy2.getData());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), dummy));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setLeft(removeHelper(data, node.getLeft(), dummy));
        }

        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));

        if (node.getBalanceFactor() > 1 && node.getLeft().getBalanceFactor() >= 0) {
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && node.getRight().getBalanceFactor() <= 0) {
            return rotateLeft(node);
        }
        if (node.getBalanceFactor() > 1 && node.getLeft().getBalanceFactor() < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1 && node.getRight().getBalanceFactor() > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Predecessor remove helper method
     * @param node the current node
     * @param dummy2 dummy2 for predecessor
     * @return value of predecessor
     */
    private AVLNode<T> predecessor(AVLNode<T> node, AVLNode<T> dummy2) {
        if (node.getRight() == null) {
            dummy2.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessor(node.getRight(), dummy2));
        }
        node.setHeight(Math.max(heightLeft(node), heightRight(node)) + 1);
        node.setBalanceFactor(balance(node));
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            throw new NoSuchElementException("Data not found");
        }
        AVLNode<T> curr = root;
        T gotData = getHelper(data, curr);
        if (gotData == null) {
            throw new NoSuchElementException("Data not found");
        } else {
            return gotData;
        }
    }

    /**
     * Get helper method.
     * @param data Data to be found.
     * @param curr Current node.
     * @return Node to be recursed.
     */
    private T getHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        } else  {
            return getHelper(data, curr.getLeft());
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> curr = root;
        T found = containsHelper(data, curr);
        return !(found == null);
    }

    /**
     * Contains helper method.
     * @param data Data to be found.
     * @param curr Current node.
     * @return Node to be recursed.
     */
    private T containsHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(data, curr.getRight());
        } else  {
            return containsHelper(data, curr.getLeft());
        }
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     *
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *
     * findMedian() should return 40
     *
     * @throws java.util.NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (size == 0) {
            throw new NoSuchElementException("the tree is empty");
        }
        int count = countNode(root);
        int currCount = 0;
        AVLNode<T> curr = root;
        AVLNode<T> pre = null;
        AVLNode<T> prev = null;
        while (curr != null) {
            if (curr.getLeft() == null) {
                currCount++;
                if (count % 2 != 0 && currCount == (count + 1) / 2) {
                    return prev.getData();
                } else if (count % 2 == 0 && currCount == (count / 2) + 1) {
                    throw new NoSuchElementException("the tree contains an even number of data");
                }
            } else {
                pre = curr.getLeft();
                while (pre.getRight() != null && pre.getRight() != curr) {
                    pre = pre.getRight();
                }
                if (pre.getRight() == null) {
                    pre.setRight(curr);
                    curr = curr.getLeft();
                } else {
                    pre.setRight(null);
                    prev = pre;
                    currCount++;
                    if (count % 2 != 0 && currCount == (count + 1) / 2) {
                        return curr.getData();
                    } else if (count % 2 == 0 && currCount == (count / 2) + 1) {
                        throw new NoSuchElementException("the tree contains an even number of data");
                    }
                }
            }
        }
        return curr.getData();
    }

    /**
     * Computes the amount of child nodes in the left subtree of node
     * Runs in constant time.
     *
     * @param  node the node whose left subtree size to compute.
     * @return the amount of child nodes in the left subtree.
     */
    private int countNode(AVLNode<T> node) {
        AVLNode<T> curr = null;
        AVLNode<T> pre = null;
        int count = 0;
        if (root == null) {
            return count;
        }
        curr = root;
        while (curr != null) {
            if (curr.getLeft() == null) {
                count++;
                curr = curr.getRight();
            } else {
                pre = curr.getLeft();
                while (pre.getRight() != null && pre.getRight() != curr) {
                    pre = pre.getRight();
                }
                if (pre.getRight() == null) {
                    pre.setRight(curr);
                    curr = curr.getLeft();
                } else {
                    pre.setRight(null);
                    count++;
                    curr = curr.getRight();
                }
            }
        }
        return count;
    }



    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}