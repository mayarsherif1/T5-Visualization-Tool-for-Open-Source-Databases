package Backend;

import java.util.ArrayList;

public class BPlusTree<T extends Comparable<T>> {
    private BPlusTreeNode<T> root;
    private final int order;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusTreeNode<>(order, true);
    }

    private BPlusTreeNode<T> search(BPlusTreeNode<T> root, T key) {
        BPlusTreeNode<T> current = root;
        while (!current.isLeaf) {
            int i = 0;
            while (i < current.keys.size()) {
                if (key.compareTo(current.keys.get(i)) < 0) {
                    current = current.children.get(i);
                    break;
                }
                if (i == current.keys.size() - 1) {
                    current = current.children.get(i + 1);
                    break;
                }
                i++;
            }
        }
        return current;
    }

    public void insert(T key) {
        BPlusTreeNode<T> leaf = search(root, key);
        if (leaf.keys.size() < order - 1) {
            insertInLeafBPlusTreeNode(leaf, key);
        } else {
            BPlusTreeNode<T> newLeaf = splitLeafBPlusTreeNode(leaf, key);
            //insertInParent(leaf, newLeaf.keys.get(0), newLeaf);
        }
    }

    private void insertInLeafBPlusTreeNode(BPlusTreeNode<T> BPlusTreeNode, T key) {
        int i = 0;
        while (i < BPlusTreeNode.keys.size() && key.compareTo(BPlusTreeNode.keys.get(i)) > 0) {
            i++;
        }
        BPlusTreeNode.keys.add(i, key);
        System.out.println("Inserted key: " + key);
        System.out.println("Keys in BPlusTreeNode: " + BPlusTreeNode.keys);
    }

    private BPlusTreeNode<T> splitLeafBPlusTreeNode(BPlusTreeNode<T> leaf, T key) {
        ArrayList<T> tempKeys = new ArrayList<>(leaf.keys);
        int i = 0;
        while (i < tempKeys.size() && key.compareTo(tempKeys.get(i)) > 0) {
            i++;
        }
        tempKeys.add(i, key);

        int mid = (order - 1) / 2;
        BPlusTreeNode<T> newLeaf = new BPlusTreeNode<>(order, true);

        leaf.keys.clear();
        leaf.keys.addAll(tempKeys.subList(0, mid));

        newLeaf.keys.addAll(tempKeys.subList(mid, tempKeys.size()));

        newLeaf.next = leaf.next;
        leaf.next = newLeaf;

        newLeaf.parent = leaf.parent;

        if (leaf.parent != null) {
            insertInParent(leaf, newLeaf.keys.get(0), newLeaf);
        } else {
            BPlusTreeNode<T> newRoot = new BPlusTreeNode<>(order, false);
            newRoot.keys.add(newLeaf.keys.get(0));
            newRoot.children.add(leaf);
            newRoot.children.add(newLeaf);
            root = newRoot;
            leaf.parent = newRoot;
            newLeaf.parent = newRoot;
        }

        return newLeaf;
    }

    private void insertInParent(BPlusTreeNode<T> oldBPlusTreeNode, T key, BPlusTreeNode<T> newBPlusTreeNode) {
        BPlusTreeNode<T> parent = oldBPlusTreeNode.parent;
        if (parent == null) {
            parent = new BPlusTreeNode<>(order, false);
            root = parent;
            oldBPlusTreeNode.parent = parent;
            parent.children.add(oldBPlusTreeNode);
        }
        int index = 0;
        while (index < parent.keys.size() && key.compareTo(parent.keys.get(index)) > 0) {
            index++;
        }

        parent.keys.add(index, key);
        parent.children.add(index + 1, newBPlusTreeNode);
        newBPlusTreeNode.parent = parent;
        if (parent.keys.size() >= order) {
            BPlusTreeNode<T> newParent = splitInternalBPlusTreeNode(parent);
        }
    }

    private BPlusTreeNode<T> splitInternalBPlusTreeNode(BPlusTreeNode<T> internal) {
        int mid = internal.keys.size() / 2;
        T keyToMoveUp = internal.keys.get(mid);
        BPlusTreeNode<T> newInternal = new BPlusTreeNode<>(order, false);
        newInternal.children.addAll(new ArrayList<>(internal.children.subList(mid + 1, internal.children.size())));
        newInternal.keys.addAll(new ArrayList<>(internal.keys.subList(mid + 1, internal.keys.size())));

        for (BPlusTreeNode<T> child : newInternal.children) {
            child.parent = newInternal;
        }
        internal.keys.subList(mid, internal.keys.size()).clear();
        internal.children.subList(mid + 1, internal.children.size()).clear();

        if (internal == root) {
            BPlusTreeNode<T> newRoot = new BPlusTreeNode<>(order, false);
            newRoot.keys.add(keyToMoveUp);
            newRoot.children.add(internal);
            newRoot.children.add(newInternal);
            root = newRoot;
            internal.parent = newRoot;
            newInternal.parent = newRoot;
        } else {
            insertInParent(internal, keyToMoveUp, newInternal);
        }

        return newInternal;
    }

    public BPlusTreeNode<T> getRoot() {
        return this.root;
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(BPlusTreeNode<T> BPlusTreeNode, String indent, boolean last) {
        if (BPlusTreeNode == null) return;

        System.out.print(indent);
        if (last) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "|   ";
        }

        BPlusTreeNode.printNode();
        System.out.println();

        if (!BPlusTreeNode.isLeaf) {
            int i;
            for (i = 0; i < BPlusTreeNode.getChildren().size() - 1; i++) {
                printTree(BPlusTreeNode.getChildren().get(i), indent, false);
            }
            if (BPlusTreeNode.getChildren().size() > 0) {
                printTree(BPlusTreeNode.getChildren().get(i), indent, true);
            }
        }
    }
}
