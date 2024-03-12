package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {
    private BPlusTreeNode root;
    private static final int MAX_KEYS = 5;

    public BPlusTree() {
        this.root = new BPlusTreeNode(false);
    }

    @Override
    public void insert(Comparable x) {
        BPlusTreeNode currentNode = root;
        if(currentNode.isLeaf){
            insertIntoLeaf(currentNode,x);
            if(currentNode.getKeys().size()>MAX_KEYS){
                splitLeaf(currentNode);
            }
            else {
                while (!currentNode.isLeaf){
                    int i =0;
                    while (i< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(i))>=0){
                        i++;

                    }
                    currentNode= (BPlusTreeNode) currentNode.getChildren().get(i);
                }
                insertIntoLeaf(currentNode,x);
                if(currentNode.getKeys().size()> MAX_KEYS){
                    splitLeaf(currentNode);
                }
            }
        }
    }

    private void splitLeaf(BPlusTreeNode leaf) {
        int point = leaf.getKeys().size()/2;
        BPlusTreeNode newLeaf = new BPlusTreeNode(true);
        //transfer to new leaf
        newLeaf.getKeys().addAll(leaf.getKeys().subList(point, leaf.getKeys().size()));
        leaf.getKeys().subList(point, leaf.getKeys().size()).clear();

        //update pointers
        newLeaf.getChildren().add(leaf.getChildren().get(0));
        leaf.getChildren().set(0,newLeaf);

        if (leaf==root){
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(newLeaf.getKeys().get(0));
            newRoot.getChildren().add(leaf);
            newRoot.getChildren().add(newLeaf);
            root= newRoot;
            //update parent
            leaf.setParent(newRoot);
            newLeaf.setParent(newRoot);

        }
        else {
            insertIntoParent(leaf, newLeaf.getKeys().get(0), newLeaf);
        }

    }

    private void insertIntoParent(BPlusTreeNode leaf, Comparable key, BPlusTreeNode newLeaf) {
        BPlusTreeNode parent = (BPlusTreeNode) leaf.getParent();
        if(parent== null ){
            parent= new BPlusTreeNode(false);
            root= parent;
        }
        int i =0;
        while (i< parent.getKeys().size() && key.compareTo(parent.getKeys().get(i))>0){
            i++;
        }
        parent.getKeys().add(i,key);
        parent.getChildren().add(i+1,newLeaf);
        newLeaf.setParent(parent);
        if(parent.getKeys().size()>MAX_KEYS){
            splitParent(parent);
        }
    }

    private void splitParent(BPlusTreeNode parent) {
        int midIndex = parent.getKeys().size()/2;
        Comparable midKey = parent.getKeys().get(midIndex);

        BPlusTreeNode newParent = new BPlusTreeNode(false);
        //transfer to new parent
        newParent.getKeys().addAll(parent.getKeys().subList(midIndex+1,parent.getKeys().size()));
        newParent.getChildren().addAll(parent.getChildren().subList(midIndex+1,parent.getChildren().size()+1));
        parent.getKeys().subList(midIndex,parent.getKeys().size()).clear();
        parent.getChildren().subList(midIndex+1,parent.getChildren().size()).clear();

        //update parent pointers
        for (Node child : newParent.getChildren()){
            child.setParent(newParent);
        }
        if(parent== root){
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(midKey);
            newRoot.getChildren().add(parent);
            newRoot.getChildren().add(newParent);
            root=newRoot;
            parent.setParent(newRoot);
            newParent.setParent(newRoot);
        }
        else {
            insertIntoParent(parent,midKey,newParent);
        }

    }

    private void insertIntoLeaf(BPlusTreeNode currentNode, Comparable x) {
        int i =0;
        while (i< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(i))>0){
            i++;
        }
        currentNode.getKeys().add(i,x);
    }

    @Override
    public void delete(Comparable x) {
        // Real B+ tree logic will handle finding the key, deleting it, and then handling underflow in the leaf and potentially in parent nodes
        BPlusTreeNode leafNode = findLeafNode(root,x);
        if (leafNode!=null){
            boolean deleted = leafNode.getKeys().remove(x);
            if(deleted){
                if ((leafNode.getKeys().size()<Math.ceil(MAX_KEYS/2.0))&& leafNode!= root){
                    handleUnderFlow(leafNode);
                }
            }
        }

    }

    private BPlusTreeNode findLeafNode(BPlusTreeNode node, Comparable x) {
        if(node.isLeaf){
            if(node.getKeys().contains(x)){
                return node;
            }
            else {
                return null;
            }
        }
        else {
            for (int i =0; i< node.getKeys().size(); i++){
                if (x.compareTo(node.getKeys().get(i))<0){
                    return findLeafNode((BPlusTreeNode) node.getChildren().get(i),x);
                }
            }
            return findLeafNode((BPlusTreeNode) node.getChildren().get(node.getChildren().size()-1),x);
        }
    }

    private void handleUnderFlow(BPlusTreeNode node) {
        BPlusTreeNode parent = (BPlusTreeNode) node.getParent();
        int nodeIndex = parent.getChildren().indexOf(node);

        BPlusTreeNode left = (nodeIndex>0)? (BPlusTreeNode) parent.getChildren().get(nodeIndex-1) : null;
        BPlusTreeNode right = (nodeIndex<parent.getChildren().size()-1)? (BPlusTreeNode) parent.getChildren().get(nodeIndex+1) : null;

        //borrowing
        if(left!= null&& left.getKeys().size()>Math.ceil(MAX_KEYS/2.0)){
            //borrow from left
            Comparable borrowedKey = left.getKeys().remove(left.getKeys().size()-1);
            node.getKeys().add(0,borrowedKey);
            parent.getKeys().set(nodeIndex-1,node.getKeys().get(0));

        }
        else if (right!= null && right.getKeys().size()> Math.ceil(MAX_KEYS/2.0)){
            Comparable borrowedKey = right.getKeys().removeFirst();
            node.getKeys().add(borrowedKey);
            parent.getKeys().set(nodeIndex,right.getKeys().get(0));
        }
        else {
            if (left != null) {
                left.getKeys().addAll(node.getKeys());
                parent.getChildren().remove(node);
                parent.getKeys().remove(nodeIndex - 1);
            } else if (right != null) {
                node.getKeys().addAll(right.getKeys());
                parent.getChildren().remove(right);
                parent.getKeys().remove(nodeIndex);
            }
            if (parent == root && parent.getKeys().isEmpty()) {
                root = node;
                root.setParent(null);
            } else if (parent != root && parent.getKeys().size() < Math.ceil(MAX_KEYS / 2.0)) {
                handleUnderFlow(parent);
            }
        }
    }

    @Override
    public boolean search(Comparable x) {
        BPlusTreeNode currentNode = root;
        while (!currentNode.isLeaf) {
            boolean found = false;
            for (int i = 0; i < currentNode.getKeys().size(); i++) {
                if (x.compareTo(currentNode.getKeys().get(i)) < 0) {
                    currentNode = (BPlusTreeNode) currentNode.getChildren().get(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentNode = (BPlusTreeNode) currentNode.getChildren().get(currentNode.getChildren().size() - 1);
            }
        }
        return currentNode.getKeys().contains(x);
    }

    @Override
    public void update(Comparable oldValue, Comparable newValue) {
        delete(oldValue);
        insert(newValue);
    }

    @Override
    public List<Comparable> traverse() {
        List<Comparable> keys = new ArrayList<>();
        Node current = findLeftMostLeaf(root);
        while (current!=null){
            keys.addAll(((BPlusTreeNode) current).getKeys());
            if(!current.getChildren().isEmpty()&& current.isLeaf){
                current= current.getChildren().get(0);
            }
            else {
                current=null;
            }
        }
        return keys;
    }

    private BPlusTreeNode findLeftMostLeaf(BPlusTreeNode node) {
        if (node== null){
            return null;
        }
        while (!node.isLeaf){
            if (node.getChildren().isEmpty()){
                return null;
            }
            node=(BPlusTreeNode) node.getChildren().get(0);
        }
        return node;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


}
