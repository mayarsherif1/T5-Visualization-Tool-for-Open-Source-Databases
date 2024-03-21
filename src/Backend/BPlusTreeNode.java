package Backend;

import java.util.ArrayList;
import java.util.List;

public abstract class BPlusTreeNode{
    protected List<Comparable> keys;
    protected int maxKeys;
    protected BPlusTreeNode parent;

    public BPlusTreeNode(int order) {
        this.keys = new ArrayList<>();
        this.maxKeys = order - 1;
        this.parent = null;
    }

    abstract void insert(Comparable key);
    abstract boolean isOverflow();
    abstract BPlusTreeNode split();
    abstract void handleOverflow();
    abstract boolean delete(Comparable key);
    abstract boolean isUnderflow();
    abstract void handleUnderflow();

    protected int findIndexForKey(Comparable key) {
        int index = 0;
        while (index < keys.size() && keys.get(index).compareTo(key) < 0) {
            index++;
        }
        return index;
    }
    public BPlusTreeNode getParent() {
        return parent;
    }

    public void setParent(BPlusTreeNode parent) {
        this.parent = parent;
    }

    public List<Comparable> getKeys() {
        return this.keys;
    }

}