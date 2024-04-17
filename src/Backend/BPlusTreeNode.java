package Backend;

import java.util.ArrayList;

public class BPlusTreeNode<T extends Comparable<T>> {
    boolean isLeaf;
    ArrayList<T> keys;
    BPlusTreeNode<T> parent;
    BPlusTreeNode<T> next;
    ArrayList<BPlusTreeNode<T>> children;
    int order;

    public BPlusTreeNode(int order, boolean isLeaf) {
        this.order = order;
        this.isLeaf = isLeaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public ArrayList<BPlusTreeNode<T>> getChildren() {
        return this.children;
    }

    public ArrayList<T> getKeys() {
        return keys;
    }

    public void setChildren(ArrayList<BPlusTreeNode<T>> children) {
        this.children = children;
    }

    public void setKeys(ArrayList<T> keys) {
        this.keys = keys;
    }

    public void printNode() {
        System.out.print("[");
        for (int i = 0; i < keys.size(); i++) {
            System.out.print(keys.get(i));
            if (i < keys.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
}
