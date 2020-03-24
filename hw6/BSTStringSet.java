import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author
 */
public class BSTStringSet implements StringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        // FIXME: PART A
        nodePut(s, _root);
    }

    private Node nodePut(String str, Node n) {
        if (_root == null) {
            _root = new Node(str);
        } else {
            if (str.compareTo(n.s) == 0) {
                return n;
            } else if (str.compareTo(n.s) < 0) { //go left
                if (n.left == null) {
                    n.left = new Node(str);
                } else {
                    nodePut(str, n.left);
                }
            } else if (str.compareTo(n.s) > 0) {
                if (n.right == null) {
                    n.right = new Node(str);
                } else {
                    nodePut(str, n.right);
                }
            }
        }
        return _root;
    }


    @Override
    public boolean contains(String s) {
        if (_root.s == s) {
            return true;
        } else if (_root == null) {
            return false;
        }
        return containsHelper(s, _root); // FIXME: PART A
    }

    private boolean containsHelper(String s, Node n) {
        if (s == n.s) {
            return true;
        } else if (s.compareTo(n.s) < 0) {
            if (n.left == null) {
                return false;
            } else {
                return containsHelper(s, n.left);
            }
        } else if (s.compareTo(n.s) > 0) {
            if (n.right == null) {
                return false;
            } else {
                return containsHelper(s, n.right);
            }
        }
        return false;
    }
    @Override
    public List<String> asList() {
        List<String> list = new ArrayList<String>();
        Iterator<String> it = iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list; // FIXME: PART A
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    // FIXME: UNCOMMENT THE NEXT LINE FOR PART B
    public Iterator<String> iterator(String low, String high) {
        return new BSTIteratorRange(_root, low, high);  // FIXME: PART B
    }

    public static class BSTIteratorRange implements Iterator<String> {
        private Stack<Node> _toDo = new Stack<>();
        private String low;
        private String high;
        BSTIteratorRange(Node node, String low, String high) {
            this.low = low;
            this.high = high;
            addTree(node);
        }
        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node node = _toDo.pop();
            addTree(node.right);
            while (node.s.compareTo(low) < 0) {
                node = _toDo.pop();
                addTree(node.right);
            }
            return node.s;

        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void addTree(Node node) {
            while (node != null) {
                if (node.s.compareTo(high) < 0) {
                    _toDo.push(node);
                }
                node = node.left;
            }
        }
    }


    /** Root node of the tree. */
    private Node _root;
}
