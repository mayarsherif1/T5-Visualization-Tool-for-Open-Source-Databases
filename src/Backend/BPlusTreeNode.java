package Backend;

import java.util.ArrayList;

public class BPlusTreeNode {
    boolean isLeaf;
    ArrayList<Integer> keys;
    BPlusTreeNode parent;
    BPlusTreeNode next; // For leaf nodes
    ArrayList<BPlusTreeNode> children; // For internal nodes
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

    public ArrayList<BPlusTreeNode> getChildren() {
        return this.children;
    }


    public ArrayList<Integer> getKeys() {
        return keys;
    }

    public void setChildren(ArrayList<BPlusTreeNode> children) {
        this.children = children;
    }

    public void setKeys(ArrayList<Integer> keys) {
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
