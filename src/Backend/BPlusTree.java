package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {
    private BPlusTreeNode root;
    private int order;

    public BPlusTree(int order){
        this.order=order;
        this.root= new BPlusTreeLeafNode(order);
    }
    public void insert(Comparable key) {
        root.insert(key);
        if (root.isOverflow()) {
            BPlusTreeNode newRoot = root.split();
            BPlusTreeInternalNode newInternalRoot = new BPlusTreeInternalNode(order);
            newInternalRoot.keys.add(newRoot.keys.get(0));
            newInternalRoot.getChildren().add(root);
            newInternalRoot.getChildren().add(newRoot);
            root.setParent(newInternalRoot);
            newRoot.setParent(newInternalRoot);
            root = newInternalRoot;
        }
    }
    public boolean search(Comparable key) {
        BPlusTreeNode current = root;
        while (!(current instanceof BPlusTreeLeafNode)) {
            BPlusTreeInternalNode internal = (BPlusTreeInternalNode) current;
            int i = 0;
            for (; i < internal.keys.size(); i++) {
                if (key.compareTo(internal.keys.get(i)) < 0) {
                    break;
                }
            }
            current = internal.getChildren().get(i);
        }
        return current.keys.contains(key);
    }

    @Override
    public void update(Comparable oldValue, Comparable newValue) {
        delete(oldValue);
        insert(newValue);

    }

    @Override
    public List<Comparable> traverse() {
        List<Comparable> result = new ArrayList<>();
        BPlusTreeNode current = root;
        while (current instanceof BPlusTreeInternalNode) {
            BPlusTreeInternalNode internal = (BPlusTreeInternalNode) current;
            current = internal.getChildren().get(0);
        }
        while (current != null) {
            BPlusTreeLeafNode leaf = (BPlusTreeLeafNode) current;
            result.addAll(leaf.getKeys());
            current = leaf.getNext();
        }
        return result;
    }


    public BPlusTreeNode getRoot() {
        return this.root;
    }

    @Override
    public void delete(Comparable key) {
        boolean success = root.delete(key);
        if (success && root.isUnderflow()) {
            if (root instanceof BPlusTreeInternalNode && ((BPlusTreeInternalNode) root).getChildren().size() == 1) {
                this.root = ((BPlusTreeInternalNode) root).getChildren().get(0);
                this.root.setParent(null);
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}