package homework3Summer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("data can not be null");
        }
        size = 0;
        for (T item: data) {
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data");
        } else if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelper(root, data);
            size++;
        }
    }
    /**
     * Recursive helper method that traverse through the BST
     * @param node root node for the helper
     * @param data to add to the tree
     * @return BSTNode after operation
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return new BSTNode<T>(data);
        }
        if (node.getData().compareTo(data) == 0) {
            return node;
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            BSTNode<T> dummyNode = new BSTNode<>(null);
            root = removeHelper(dummyNode, root, data);
            return dummyNode.getData();
        }
    }

    /**
     * Recursive remove helper that traverse through the BST
     * @param dummyNode that stores value
     * @param node root of the BST
     * @param data that needs to be removed
     * @return remove node
     */
    private BSTNode<T> removeHelper(BSTNode<T> dummyNode, BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(dummyNode, node.getRight(), data));
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(dummyNode, node.getLeft(), data));
        } else {
            size--;
            dummyNode.setData(node.getData());
            if (node.getRight() == null && node.getLeft() == null) {
                return null;
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else {
                BSTNode<T> newDummy = new BSTNode<>(null);
                node.setRight(succesorHelper(node.getRight(), newDummy));
                node.setData(newDummy.getData());
            }
        }
        return node;
    }

    /**
     * Recursive successor helper that traverse through the BST
     * @param node root
     * @param dummy node that stores data
     * @return next node
     */
    private BSTNode<T> succesorHelper(BSTNode<T> node, BSTNode<T> dummy) {
        if (node.getLeft() == null) {
            dummy.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(succesorHelper(node.getLeft(), dummy));
        }
        return node;
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        return getHelper(root, data);
    }

    /**
     * Recursive get helper that traverse through the BST
     * @param node root node
     * @param data to search for
     * @return the fetched data
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            if (node.getData().compareTo(data) < 0) {
                return getHelper(node.getRight(), data);
            } else if (node.getData().compareTo(data) > 0) {
                return getHelper(node.getLeft(), data);
            } else {
                return node.getData();
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        return containHelper(root, data);
    }

    /**
     * Helper method that looks for a specific node in the BST, and returns true if it is.
     * @param curr BSTNode representing the node the program is currently on.
     * @param data the data being searched for.
     * @return the node being searched for.
     */
    private boolean containHelper(BSTNode<T> curr, T data) {
        if (curr != null) {
            if (curr.getData().compareTo(data) == 0) {
                return true;
            } else if (data.compareTo(curr.getData()) < 0) {
                return containHelper(curr.getLeft(), data);
            } else if (data.compareTo(curr.getData()) > 0) {
                return containHelper(curr.getRight(), data);
            }
        }
        return false;
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) preorderHelper(list, root);
        return list;
    }
    /**
     * Recursive helper for preorder
     * @param preorderList list of preoder value
     * @param node root
     * @return new list
     */
    private List<T> preorderHelper(List<T> preorderList, BSTNode<T> node) {
        if (node == null) {
            return preorderList;
        } else {
            preorderList.add(node.getData());
            preorderHelper(preorderList, node.getLeft());
            preorderHelper(preorderList, node.getRight());
        }
        return preorderList;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) inorderHelper(list, root);
        return list;
    }

    /**
     * recursive helper for inorder
     * @param inorderList List of inorder value
     * @param node root
     * @return new list of values
     */
    private List<T> inorderHelper(List<T> inorderList, BSTNode<T> node) {
        if (node == null) {
            return inorderList;
        } else {
            inorderHelper(inorderList, node.getLeft());
            inorderList.add(node.getData());
            inorderHelper(inorderList, node.getRight());
        }
        return inorderList;
    }


    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>(size);
        list = (ArrayList<T>) postorderHelper(list, root);
        return list;
    }

    /**
     * Recursive helper for postorder
     * @param postorderList list of posorder value
     * @param node root
     * @return new list of values
     */
    private List<T> postorderHelper(List<T> postorderList, BSTNode<T> node) {
        if (node == null) {
            return postorderList;
        } else {
            postorderHelper(postorderList, node.getLeft());
            postorderHelper(postorderList, node.getRight());
            postorderList.add(node.getData());
        }
        return postorderList;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> data = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.poll();
            if (node != null) {
                data.add(node.getData());
                queue.add(node.getLeft());
                queue.add(node.getRight());
            }
        }
        return data;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHepler(root);
    }

    /**
     * recursive helper for height
     * @param node root
     * @return height of node
     */
    private int heightHepler(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int rightIndex = heightHepler(node.getRight());
            int leftIndex = heightHepler(node.getLeft());
            if (leftIndex > rightIndex) {
                return leftIndex + 1;
            } else {
                return rightIndex + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;

    }

    /**
     * Removes all elements strictly greater than the passed in data.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     *
     * Should have a running time of O(log(n)) for balanced tree. O(n) for a degenerated tree.
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        return pruneHelper(tree, data);

    }

    /**
     * Recursive helper method to remove all the elements that are greater than given value
     * @param node the root of the tree
     * @param data the given data
     * @param <T> the generic typing of the tree
     * @return node for the deepest common ancestor
     */

    private static <T extends Comparable<? super T>> BSTNode<T> pruneHelper(BSTNode<T> node, T data) {
        if (node != null) {
            node.setRight(pruneHelper(node.getRight(), data));
            node.setLeft(pruneHelper(node.getLeft(), data));
            BSTNode<T> temp = null;
            BSTNode<T> back = null;
            if (node.getData().compareTo(data) > 0) {
                if (node.getLeft() == null) {
                    temp = node;
                    node = node.getRight();
                } else if (node.getRight() == null) {
                    temp = node;
                    node = node.getLeft();
                } else {
                    temp = node.getLeft();
                    back = node;
                    while (temp.getRight() != null) {
                        back = temp;
                        temp = temp.getRight();
                    }
                    if (node.getLeft() == temp) {
                        swapNodes(node, temp);
                        node.setLeft(temp.getLeft());
                    } else {
                        swapNodes(node, temp);
                        back.setRight(temp.getLeft());
                    }
                }
                temp = null;
            }
        }
        return node;
    }

    /***
     * method that swap two nodes
     * @param node1 the node that is needed to swap
     * @param node2 the node that is needed to swap
     * @param <T> the generic typing of the tree
     */
    public static <T extends Comparable<? super T>> void swapNodes(BSTNode<T> node1, BSTNode<T> node2) {
        T temp = node1.getData();
        node1.setData(node2.getData());
        node2.setData(temp);
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
