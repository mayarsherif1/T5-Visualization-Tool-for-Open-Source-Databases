package Backend;

import java.util.ArrayList;

public class BPlusTree {
    private BPlusTreeNode root;
    private final int order;

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusTreeNode(order, true);
    }

    private BPlusTreeNode search(BPlusTreeNode root, int key) {
        BPlusTreeNode current = root;
        while (!current.isLeaf) {
            int i = 0;
            while (i < current.keys.size()) {
                if (key < current.keys.get(i)) {
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
    public void insert(int key) {
        BPlusTreeNode leaf = search(root, key);
        if (leaf.keys.size() < order - 1) {
            insertInLeafBPlusTreeNode(leaf, key);
        } else {
            BPlusTreeNode newLeaf = splitLeafBPlusTreeNode(leaf, key);
            //insertInParent(leaf, newLeaf.keys.get(0), newLeaf);
        }
    }

    private void insertInLeafBPlusTreeNode(BPlusTreeNode BPlusTreeNode, int key) {
        int i = 0;
        while (i < BPlusTreeNode.keys.size() && key > BPlusTreeNode.keys.get(i)) {
            i++;
        }
        BPlusTreeNode.keys.add(i, key);
        System.out.println("Inserted key: " + key);
        System.out.println("Keys in BPlusTreeNode: " + BPlusTreeNode.keys);
    }
    private BPlusTreeNode splitLeafBPlusTreeNode(BPlusTreeNode leaf, int key) {
        ArrayList<Integer> tempKeys = new ArrayList<>(leaf.keys);
        int i = 0;
        while (i < tempKeys.size() && key > tempKeys.get(i)) {
            i++;
        }
        tempKeys.add(i, key);

        int mid = (order - 1) / 2;
        BPlusTreeNode newLeaf = new BPlusTreeNode(order, true);

        leaf.keys.clear();
        leaf.keys.addAll(tempKeys.subList(0, mid));

        newLeaf.keys.addAll(tempKeys.subList(mid, tempKeys.size()));

        newLeaf.next = leaf.next;
        leaf.next = newLeaf;

        newLeaf.parent = leaf.parent;

        if (leaf.parent != null) {
            insertInParent(leaf, newLeaf.keys.get(0), newLeaf);
        } else {
            BPlusTreeNode newRoot = new BPlusTreeNode(order, false);
            newRoot.keys.add(newLeaf.keys.get(0));
            newRoot.children.add(leaf);
            newRoot.children.add(newLeaf);
            root = newRoot;
            leaf.parent = newRoot;
            newLeaf.parent = newRoot;
        }

        return newLeaf;
    }


    private void insertInParent(BPlusTreeNode oldBPlusTreeNode, int key, BPlusTreeNode newBPlusTreeNode) {
        BPlusTreeNode parent = oldBPlusTreeNode.parent;
        if (parent == null) {
            parent = new BPlusTreeNode(order, false);
            root = parent;
            oldBPlusTreeNode.parent = parent;
            parent.children.add(oldBPlusTreeNode);
        }
        int index = 0;
        while (index < parent.keys.size() && key > parent.keys.get(index)) {
            index++;
        }

        parent.keys.add(index, key);
        parent.children.add(index + 1, newBPlusTreeNode);
        newBPlusTreeNode.parent = parent;
        if (parent.keys.size() >= order) {
            BPlusTreeNode newParent = splitInternalBPlusTreeNode(parent);
        }
    }

    private BPlusTreeNode splitInternalBPlusTreeNode(BPlusTreeNode internal) {
        int mid = internal.keys.size() / 2;
        int keyToMoveUp = internal.keys.get(mid);
        BPlusTreeNode newInternal = new BPlusTreeNode(order, false);
        newInternal.children.addAll(new ArrayList<>(internal.children.subList(mid + 1, internal.children.size())));
        newInternal.keys.addAll(new ArrayList<>(internal.keys.subList(mid + 1, internal.keys.size())));

        for (BPlusTreeNode child : newInternal.children) {
            child.parent = newInternal;
        }
        internal.keys.subList(mid, internal.keys.size()).clear();
        internal.children.subList(mid + 1, internal.children.size()).clear();

        if (internal == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(order, false);
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

    public BPlusTreeNode getRoot() {
        return this.root;
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(BPlusTreeNode BPlusTreeNode, String indent, boolean last) {
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


