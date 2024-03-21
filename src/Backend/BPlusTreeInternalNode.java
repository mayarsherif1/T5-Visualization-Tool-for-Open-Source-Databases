package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPlusTreeInternalNode extends BPlusTreeNode{

    private List<BPlusTreeNode> children;

    public BPlusTreeInternalNode(int order) {
        super(order);
        this.children = new ArrayList<>();
    }

    @Override
    void insert(Comparable key) {
        int index = findIndexForKey(key);
        BPlusTreeNode child = children.get(index);
        child.insert(key);

        if (child.isOverflow()) {
            child.handleOverflow();
        }
    }

    @Override
    boolean isOverflow() {
        return children.size() > maxKeys + 1;
    }

    @Override
    BPlusTreeNode split() {
        int midIndex = (keys.size() / 2);
        int totalKeys = this.keys.size();
        BPlusTreeInternalNode sibling = new BPlusTreeInternalNode(maxKeys + 1);
            sibling.keys.addAll(new ArrayList<>(this.keys.subList(midIndex + 1, totalKeys)));
            this.keys.subList(midIndex+1,totalKeys).clear();

            if(!this.children.isEmpty()){
                sibling.children.addAll(new ArrayList<>(this.children.subList(midIndex + 1, this.children.size())));
                this.children.subList(midIndex + 1, this.children.size()).clear();
                sibling.children.forEach(child -> child.setParent(sibling));
            }

        if (sibling.keys.isEmpty() || this.keys.isEmpty()) {
            throw new IllegalStateException("Split operation resulted in an empty sibling or original node.");
        }
        return sibling;

    }

    public List<BPlusTreeNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    void insertIntoParent(Comparable key, BPlusTreeNode child) {
        int index = findIndexForKey(key);
        if (index < keys.size()) {
            keys.add(index, key);
            children.add(index + 1, child);
        } else {
            keys.add(key);
            children.add(child);
        }
        child.setParent(this);

        if (isOverflow()) {
            handleOverflow();
        }
    }

    public void addChild(BPlusTreeNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    void handleOverflow() {
        if(this.keys.size()>maxKeys) {
            BPlusTreeNode sibling = this.split();
            System.out.println("handleOverflow sibling: " + sibling.toString());
            Comparable middleKey = findMiddleKey();
                if (parent == null) {
                    BPlusTreeInternalNode newRoot = new BPlusTreeInternalNode(maxKeys + 1);
                    newRoot.keys.add(middleKey);
                    newRoot.addChild(this);
                    newRoot.addChild(sibling);
                    this.parent=newRoot;
                    sibling.parent=newRoot;
                } else {
                    this.parent.insert(middleKey);
                    ((BPlusTreeInternalNode)parent).insertIntoParent(middleKey, sibling);
                    this.parent.handleOverflow();

                }
            }
        else {
            System.err.println("Sibling node's keys list is empty after a split.");
        }
    }

    private Comparable findMiddleKey() {
        int totalKeys = this.keys.size();
        int midIndex = totalKeys / 2 - 1;
        return this.keys.get(midIndex);

    }

    @Override
    boolean delete(Comparable key) {
        int childIndex = findIndexForKey(key);
        boolean success = children.get(childIndex).delete(key);

        if (!success) {
            return false;
        }

        if (children.get(childIndex).isUnderflow()) {
            handleUnderflow(childIndex);
        }

        return true;
    }
    @Override
    boolean isUnderflow() {
        return children.size() < Math.max(2, (maxKeys + 1) / 2);
    }

    @Override
    void handleUnderflow() {
        if(this.parent==null){
            if(children.size()==1){
                BPlusTreeNode newRoot = children.get(0);
                newRoot.setParent(null);
            }
        }
        else {
            BPlusTreeInternalNode parentInternal = (BPlusTreeInternalNode) this.parent;
            int nodeIndex = parentInternal.children.indexOf(this);

            boolean mergedOrBorrowed = false;
            if (nodeIndex > 0) {
                mergedOrBorrowed = tryBorrowFromSibling(nodeIndex);
            }
            if (!mergedOrBorrowed && nodeIndex < parentInternal.children.size() - 1) {
                mergedOrBorrowed = tryBorrowFromSibling(nodeIndex);
            }
            if (!mergedOrBorrowed) {
                if (nodeIndex > 0) {
                    mergeWithSibling(nodeIndex - 1);
                } else if (nodeIndex < parentInternal.children.size() - 1) {
                    mergeWithSibling(nodeIndex);
                }
            }
        }
    }


    void handleUnderflow(int childIndex) {
        BPlusTreeNode child = children.get(childIndex);

        if (tryBorrowFromSibling(childIndex)) {
            return;
        }

        mergeWithSibling(childIndex);
    }

    private void removeChildEntry(int childIndex) {
        if (childIndex > 0) keys.remove(childIndex - 1);
        children.remove(childIndex);
    }

    private void mergeWithSibling(int childIndex) {
        if (!(this instanceof BPlusTreeInternalNode)) {
            throw new IllegalStateException("mergeWithSibling called on a non-internal node.");
        }

        BPlusTreeInternalNode internalNode = (BPlusTreeInternalNode) this;
        BPlusTreeNode child = internalNode.children.get(childIndex);
        BPlusTreeNode leftSibling = childIndex > 0 ? internalNode.children.get(childIndex - 1) : null;
        BPlusTreeNode rightSibling = childIndex < internalNode.children.size() - 1 ? internalNode.children.get(childIndex + 1) : null;

        if (leftSibling != null && leftSibling.isUnderflow()) {
            mergeNodes(leftSibling, child);
            removeChildEntry(internalNode, childIndex);
        } else if (rightSibling != null && rightSibling.isUnderflow()) {
            mergeNodes(child, rightSibling);
            removeChildEntry(internalNode, childIndex + 1);
        }
        if (internalNode.isUnderflow() && internalNode.parent != null) {
            if (!(internalNode.parent instanceof BPlusTreeInternalNode)) {
                throw new IllegalStateException("Parent of internal node is not an internal node.");
            }
            BPlusTreeInternalNode parentInternalNode = (BPlusTreeInternalNode)internalNode.parent;
            parentInternalNode.handleUnderflow(parentInternalNode.children.indexOf(this));
        }
    }
    private void removeChildEntry(BPlusTreeInternalNode internalNode, int childIndex) {
        if (childIndex > 0) {
            internalNode.keys.remove(childIndex - 1);
        }
        internalNode.children.remove(childIndex);
    }

    private void mergeNodes(BPlusTreeNode leftNode, BPlusTreeNode rightNode) {
        BPlusTreeInternalNode leftInternalNode = (BPlusTreeInternalNode) leftNode;
        BPlusTreeInternalNode rightInternalNode = (BPlusTreeInternalNode) rightNode;

        if (!(leftInternalNode.getParent() instanceof BPlusTreeInternalNode)) {
            throw new IllegalStateException("Parent node is not an internal node.");
        }
        BPlusTreeInternalNode parent = (BPlusTreeInternalNode) leftInternalNode.getParent();

        int separatorIndex = parent.getChildren().indexOf(rightNode) - 1;
        Comparable separatorKey = parent.getKeys().get(separatorIndex);

        leftInternalNode.getKeys().add(separatorKey);
        leftInternalNode.getKeys().addAll(rightInternalNode.getKeys());
        for (BPlusTreeNode child : rightInternalNode.getChildren()) {
            leftInternalNode.getChildren().add(child);
            child.setParent(leftInternalNode);
        }
        parent.getKeys().remove(separatorIndex);
        parent.getChildren().remove(rightInternalNode);
        if (parent.isUnderflow()) {
            parent.handleUnderflow();
        }
    }


    private boolean tryBorrowFromSibling(int childIndex) {
        BPlusTreeNode sibling;
        boolean borrowFromRight = false;
        int separatorIndex = childIndex - 1;
        if (childIndex < children.size() - 1 && children.get(childIndex + 1).keys.size() > (maxKeys + 1) / 2) {
            sibling = children.get(childIndex + 1);
            borrowFromRight = true;
            separatorIndex = childIndex;
        }
        else if (childIndex > 0 && children.get(childIndex - 1).keys.size() > (maxKeys + 1) / 2) {
            sibling = children.get(childIndex - 1);
        }
        else {
            return false;
        }
        BPlusTreeNode child = children.get(childIndex);
        Comparable separatorKey = keys.get(separatorIndex);

        if (borrowFromRight) {
            BPlusTreeInternalNode rightSibling = (BPlusTreeInternalNode) sibling;
            Comparable borrowedKey = rightSibling.keys.remove(0);
            BPlusTreeNode borrowedChild = rightSibling.children.remove(0);
            child.keys.add(separatorKey);
            keys.set(separatorIndex, borrowedKey);
            if (child instanceof BPlusTreeInternalNode) {
                ((BPlusTreeInternalNode) child).children.add(borrowedChild);
                borrowedChild.setParent(child);
            }
        } else {
            BPlusTreeInternalNode leftSibling = (BPlusTreeInternalNode) sibling;
            Comparable borrowedKey = leftSibling.keys.remove(leftSibling.keys.size() - 1);
            BPlusTreeNode borrowedChild = leftSibling.children.remove(leftSibling.children.size() - 1);
            child.keys.add(0, separatorKey);
            keys.set(separatorIndex, borrowedKey);
            if (child instanceof BPlusTreeInternalNode) {
                ((BPlusTreeInternalNode) child).children.add(0, borrowedChild);
                borrowedChild.setParent(child);
            }
        }

        return true;
    }
}