package Backend;

import java.util.List;

public class BinaryTree extends GenericTree {
    private Node root;

    private class Node {
        Comparable value;
        Node left, right;
        public Node(Comparable value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    @Override
    public void insert(Comparable x) {
        root = insertRecord(root, x);
    }

    private Node insertRecord(Node current, Comparable x) {
        if (current == null) {
            return new Node(x);
        }

        if (x.compareTo(current.value) < 0) {
            current.left = insertRecord(current.left, x);
        } else if (x.compareTo(current.value) > 0) {
            current.right = insertRecord(current.right, x);
        } else {
            // todo handle duplicate scenario
            return current;
        }

        return current;
    }

    @Override
    public void delete(Comparable x) {
        root = deleteRecord(root, x);
    }

    private Node deleteRecord(Node current, Comparable x) {
        if (current == null) {
            return null;
        }

        if (x.compareTo(current.value) == 0) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            Comparable smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecord(current.right, smallestValue);
            return current;
        }
        if (x.compareTo(current.value) < 0) {
            current.left = deleteRecord(current.left, x);
            return current;
        }
        current.right = deleteRecord(current.right, x);
        return current;
    }

    private Comparable findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    @Override
    public boolean search(Comparable x) {
        return searchRec(root, x);
    }

    private boolean searchRec(Node current, Comparable x) {
        if (current == null) {
            return false;
        }
        if (x.compareTo(current.value) == 0) {
            return true;
        }
        return x.compareTo(current.value) < 0 ? searchRec(current.left, x) : searchRec(current.right, x);
    }

    @Override
    public void update(Comparable oldValue, Comparable newValue) {
        delete(oldValue);
        insert(newValue);
    }

    @Override
    public List<Comparable> traverse() {
        traverseInOrder(root);
        return null;
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
