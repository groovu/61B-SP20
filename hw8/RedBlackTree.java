/**
 * Simple Red-Black tree implementation, where the keys are of type T.
 @ author
 */
public class RedBlackTree<T extends Comparable<T>> {

    /** Root of the tree. */
    private RBTreeNode<T> root;

    /**
     * Empty constructor.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Constructor that builds this from given BTree (2-3-4) tree.
     *
     * @param tree BTree (2-3-4 tree).
     */
    public RedBlackTree(BTree<T> tree) {
        BTree.Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /**
     * Builds a RedBlack tree that has isometry with given 2-3-4 tree rooted at
     * given node r, and returns the root node.
     *
     * @param r root of the 2-3-4 tree.
     * @return root of the Red-Black tree for given 2-3-4 tree.
     */
    RBTreeNode<T> buildRedBlackTree(BTree.Node<T> r) {
        if (r == null) {
            return null;
        }
        RBTreeNode<T> left = null; RBTreeNode<T> right = null; T mid = null;
        int items = r.getItemCount();
        if (items == 1) {
            mid = r.getItemAt(0);
            left = buildRedBlackTree(r.getChildAt(0));
            right = buildRedBlackTree(r.getChildAt(1));
        } else if (items >= 2) {
            mid = r.getItemAt(1);
            left = new RBTreeNode(false, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
            if (items == 3) {
                right = new RBTreeNode(false, r.getItemAt(2), buildRedBlackTree(r.getChildAt(2)), buildRedBlackTree(r.getChildAt(3)));
            } else {
                right = buildRedBlackTree(r.getChildAt(2));
            }
        }
        return new RBTreeNode<T>(true, mid, left, right);
    }

    /**
     * Rotates the (sub)tree rooted at given node to the right, and returns the
     * new root of the (sub)tree. If rotation is not possible somehow,
     * immediately return the input node.
     *
     * @param node root of the given (sub)tree.
     * @return new root of the (sub)tree.
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // YOUR CODE HERE
        RBTreeNode newRoot = node.left;
        RBTreeNode newsRight = newRoot.right;

        newRoot.right = node;
        node.left = newsRight;

        return newRoot;
    }

    /**
     * Rotates the (sub)tree rooted at given node to the left, and returns the
     * new root of the (sub)tree. If rotation is not possible somehow,
     * immediately return the input node.
     *
     * @param node root of the given (sub)tree.
     * @return new root of the (sub)tree.
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // YOUR CODE HERE
        RBTreeNode newRoot = node.right;
        RBTreeNode newsLeft = newRoot.left;

        newRoot.left = node;
        node.right = newsLeft;
        return newRoot;
    }

    /**
     * Flips the color of the node and its children. Assume that the node has
     * both left and right children.
     *
     * @param node tree node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Returns whether a given node is red. null nodes (children of leaf) are
     * automatically considered black.
     *
     * @param node node
     * @return node is red.
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Insert given item into this tree.
     *
     * @param item item
     */
    void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Recursivelty insert item into this tree. returns the (new) root of the
     * subtree rooted at given node after insertion. node == null implies that
     * we are inserting a new node at the bottom.
     *
     * @param node node
     * @param item item
     * @return (new) root of the subtree rooted at given node.
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        System.out.println("insert not implemented, checking to see if RBT is created.");

        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode(false, item);
            // YOUR CODE HERE

        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
            // YOUR CODE HERE

        } else {
            // YOUR CODE HERE
            node.right = insert(node.right,item);
        }

        // handle case C and "Right-leaning" situation.
        // node is right child of red, rotate left on node.
        if (isRed(node.right) && !isRed(node.left)) {
            // YOUR CODE HERE
            node = rotateLeft(node);
        }

        // handle case B
        // node is left child of red,  rotate right on node.
        if (isRed(node.left) && isRed(node.left.left)) {
            // YOUR CODE HERE
            node = rotateRight(node);

        }

        // handle case A
        // node is rightchild of black, flip colors, change root to black.
        if (isRed(node.left) && isRed(node.right)) {
            // YOUR CODE HERE
            flipColors(node);
            root.isBlack = true;

        }
        return node;
    }

    /** Public accesser method for the root of the tree.*/
    public RBTreeNode<T> graderRoot() {
        return root;
    }


    /**
     * RedBlack tree node.
     *
     * @param <T> type of item.
     */
    static class RBTreeNode<T> {

        /** Item. */
        protected final T item;

        /** True if the node is black. */
        protected boolean isBlack;

        /** Pointer to left child. */
        protected RBTreeNode<T> left;

        /** Pointer to right child. */
        protected RBTreeNode<T> right;

        /**
         * A node that is black iff BLACK, containing VALUE, with empty
         * children.
         */
        RBTreeNode(boolean black, T value) {
            this(black, value, null, null);
        }

        /**
         * Node that is black iff BLACK, contains VALUE, and has children
         * LFT AND RGHT.
         */
        RBTreeNode(boolean black, T value,
                   RBTreeNode<T> lft, RBTreeNode<T> rght) {
            isBlack = black;
            item = value;
            left = lft;
            right = rght;
        }
    }

}