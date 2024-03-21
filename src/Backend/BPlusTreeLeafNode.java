package Backend;

public class BPlusTreeLeafNode extends BPlusTreeNode {
    private BPlusTreeLeafNode next;

    public BPlusTreeLeafNode(int order) {
        super(order);
    }

    @Override
    void insert(Comparable key) {
        int index = findIndexForKey(key);
        keys.add(index, key);

        if (isOverflow()) {
            handleOverflow();
        }
    }

    @Override
    boolean isOverflow() {
        return keys.size() > maxKeys;
    }

    @Override
    BPlusTreeNode split() {
        int splitIndex = this.keys.size() / 2;

        BPlusTreeLeafNode sibling = new BPlusTreeLeafNode(this.keys.size());
        sibling.keys.addAll(this.keys.subList(splitIndex, this.keys.size()));
        this.keys.subList(splitIndex, this.keys.size()).clear();

        sibling.next = this.next;
        this.next = sibling;

        if (this.parent == null) {
            this.parent = new BPlusTreeInternalNode(maxKeys + 1);
        }
        sibling.parent = this.parent;
        return sibling;
    }

    @Override
    void handleOverflow() {
        BPlusTreeLeafNode sibling = (BPlusTreeLeafNode) split();
        if (parent == null) {
            BPlusTreeInternalNode newRoot = new BPlusTreeInternalNode(maxKeys + 1);
            newRoot.keys.add(sibling.keys.get(0));
            newRoot.addChild(this);
            newRoot.addChild(sibling);
            this.setParent(newRoot);
            sibling.setParent(newRoot);
        } else {
            sibling.setParent(parent);
            ((BPlusTreeInternalNode) parent).insertIntoParent(sibling.keys.get(0), sibling);
            parent.handleOverflow();
        }
    }

    @Override
    boolean delete(Comparable key) {
        int index = binarySearchForKey(key);
        if (index >= 0) { // Key found
            keys.remove(index);
            return true;
        }
        return false;
    }

    protected int binarySearchForKey(Comparable key) {
        int low = 0;
        int high = keys.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable midVal = keys.get(mid);
            int cmp = midVal.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    @Override
    boolean isUnderflow() {
        return keys.size() < (maxKeys + 1) / 2;
    }

    @Override
    void handleUnderflow() {
        if (tryBorrowFromSibling()) {
            return;
        }

        mergeWithSibling();
    }
    private boolean tryBorrowFromSibling() {
        BPlusTreeLeafNode leftSibling = getLeftSibling();
        BPlusTreeLeafNode rightSibling = getRightSibling();

        if (leftSibling != null && leftSibling.keys.size() > (maxKeys + 1) / 2) {
            Comparable borrowedKey = leftSibling.keys.remove(leftSibling.keys.size() - 1);
            this.keys.add(0, borrowedKey);
            updateParentAfterBorrowing(leftSibling, this);
            return true;
        } else if (rightSibling != null && rightSibling.keys.size() > (maxKeys + 1) / 2) {
            Comparable borrowedKey = rightSibling.keys.remove(0);
            this.keys.add(borrowedKey);
            updateParentAfterBorrowing(this, rightSibling);
            return true;
        }
        return false;
    }

    private void mergeWithSibling() {
        BPlusTreeLeafNode sibling = getMergeCandidate();
        if (sibling == this.next) {
            this.keys.addAll(sibling.keys);
            this.next = sibling.next;
        } else {
            sibling.keys.addAll(this.keys);
            sibling.next = this.next;
            // In a real implementation, we'd also need to handle updating 'this' node's references in the tree
        }
        updateParentAfterMerge(sibling);
    }

    private BPlusTreeLeafNode getLeftSibling() {
        if (this.parent == null || !(this.parent instanceof BPlusTreeInternalNode)) return null;
        BPlusTreeInternalNode internalParent = (BPlusTreeInternalNode)this.parent;
        int index = internalParent.getChildren().indexOf(this);
        return index > 0 ? (BPlusTreeLeafNode) internalParent.getChildren().get(index - 1) : null;
    }

    private BPlusTreeLeafNode getRightSibling() {
        if (this.parent == null || !(this.parent instanceof BPlusTreeInternalNode)) return null;
        BPlusTreeInternalNode internalParent = (BPlusTreeInternalNode)this.parent;
        int index = internalParent.getChildren().indexOf(this);
        return index < internalParent.getChildren().size() - 1 ? (BPlusTreeLeafNode) internalParent.getChildren().get(index + 1) : null;
    }

    private BPlusTreeLeafNode getMergeCandidate() {
        BPlusTreeLeafNode rightSibling = getRightSibling();
        if (rightSibling != null) {
            return rightSibling;
        }
        return getLeftSibling();
    }

    private void updateParentAfterMerge(BPlusTreeLeafNode mergeTarget) {
        if (this.parent instanceof BPlusTreeInternalNode) {
            BPlusTreeInternalNode internalParent = (BPlusTreeInternalNode)this.parent;
            int mergeIndex = internalParent.getChildren().indexOf(mergeTarget);
            if (mergeIndex != -1) {
                internalParent.keys.remove(mergeIndex - 1);
                internalParent.getChildren().remove(mergeIndex);
                if (internalParent.isUnderflow()) {
                    internalParent.handleUnderflow();
                }
            }
        }
    }

    private void updateParentAfterBorrowing(BPlusTreeLeafNode lender, BPlusTreeLeafNode borrower) {
        if (borrower.parent instanceof BPlusTreeInternalNode) {
            BPlusTreeInternalNode internalParent = (BPlusTreeInternalNode)borrower.parent;
            int borrowerIndex = internalParent.getChildren().indexOf(borrower);
            if (borrowerIndex > 0 && internalParent.getChildren().get(borrowerIndex - 1) == lender) {
                internalParent.keys.set(borrowerIndex - 1, borrower.keys.get(0));
            } else if (borrowerIndex < internalParent.getChildren().size() - 1) {
                internalParent.keys.set(borrowerIndex, lender.keys.get(0));
            }
        }
    }

    public BPlusTreeLeafNode getNext() {
        return this.next;
    }

    public void setNext(BPlusTreeLeafNode next) {
        this.next = next;
    }


}