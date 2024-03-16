package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {
    private BPlusTreeNode root;
    private static final int MAX_KEYS = 2;

    public BPlusTree() {
        this.root = new BPlusTreeNode(false);
    }

    public BPlusTreeNode getRoot(){
        return this.root;
    }

    @Override
    public void insert(Comparable x) {
        System.out.println("BPlusTree insert method");
        BPlusTreeNode currentNode = root;
            int i =0;
            while (i<currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(i))> 0){
                i++;
            }

//            if(i<currentNode.getChildren().size()){
//                currentNode = (BPlusTreeNode) currentNode.getChildren().get(i);
//            }
        currentNode.getKeys().add(i,x);
        //insertIntoLeaf(currentNode,x);
        if(currentNode.getKeys().size()>MAX_KEYS){
            splitLeaf(currentNode);

        }

//        if(currentNode.isLeaf){
//            insertIntoLeaf(currentNode,x);
//            if(currentNode.getKeys().size()>MAX_KEYS){
//                splitLeaf(currentNode);
//            }
//            else {
//                while (!currentNode.isLeaf){
//                    int i =0;
//                    while (i< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(i))>=0){
//                        i++;
//
//                    }
//                    currentNode= (BPlusTreeNode) currentNode.getChildren().get(i);
//                }
//                insertIntoLeaf(currentNode,x);
//                if(currentNode.getKeys().size()> MAX_KEYS){
//                    splitLeaf(currentNode);
//                }
//            }
//        }
    }

    private void splitLeaf(BPlusTreeNode leaf) {
        System.out.println("BPlusTree splitLeaf method");
        int point= (MAX_KEYS+1)/2;
        System.out.println("BPlusTree splitLeaf method point: "+ point);
        BPlusTreeNode newLeaf = new BPlusTreeNode(true);
        //transfer to new leaf
        while (leaf.getKeys().size() > point) {
            newLeaf.getKeys().add(leaf.getKeys().remove(point));
        }
        System.out.println("BPlusTree splitLeaf method newLeaf keys: "+ newLeaf.getKeys());

        //update pointers
        //todo make the middle value the new root/parent and the rest are children.
        newLeaf.setNext(leaf.getNext());
        System.out.println("BPlusTree splitLeaf method newLeaf next: " + newLeaf.getNext());

        leaf.setNext(newLeaf);
        System.out.println("BPlusTree splitLeaf method leaf next: " + leaf.getNext());
        Comparable splitKey = newLeaf.getKeys().get(0);

//        newLeaf.getChildren().add(leaf.getChildren().get(0));
//        leaf.getChildren().set(0,newLeaf);

        if (leaf==root){
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            System.out.println("BPlusTree splitLeaf method newRoot: "+ newRoot);
            newRoot.getKeys().add(splitKey);
            System.out.println("BPlusTree splitLeaf method newRoot keys: "+ newRoot.getKeys());
            newRoot.addChild(leaf);
            newRoot.addChild(newLeaf);
            System.out.println("BPlusTree splitLeaf method newRoot children: "+ newRoot.getChildren());
            root= newRoot;
            //update parent
            leaf.setParent(newRoot);
            newLeaf.setParent(newRoot);

        }
        else {
            System.out.println("going to insertIntoParent method");
            insertIntoParent(leaf, splitKey, newLeaf);
        }

    }

    private void insertIntoParent(BPlusTreeNode leaf, Comparable key, BPlusTreeNode newLeaf) {
        System.out.println("BPlusTree insertIntoParent method");
        BPlusTreeNode parent = (BPlusTreeNode) leaf.getParent();
//        int index = parent.getChildren().indexOf(leaf);
//        parent.getKeys().add(index,key);
//        parent.getChildren().add(index+1,newLeaf);
//        newLeaf.setParent(parent);

        if(parent==null) {

            System.out.println("BPlusTree insertIntoParent parent==null");
            parent = new BPlusTreeNode(false);
            leaf.setParent(parent);
            parent.getChildren().add(leaf);
            System.out.println("BPlusTree insertIntoParent method parent children" + parent.getChildren());

        }
        int i =0;
        while (i< parent.getKeys().size() && key.compareTo(parent.getKeys().get(i))>0){
            i++;
        }
        parent.getKeys().add(i,key);
        parent.getChildren().add(i+1,newLeaf);
        leaf.setParent(parent);
        newLeaf.setParent(parent);
        if(parent.getKeys().size()>MAX_KEYS){
            System.out.println("BPlusTree insertIntoParent method parent before splitting: "+ parent);
            splitInternalNode(parent);
            System.out.println("BPlusTree insertIntoParent method parent after splitting: "+ parent);
        }
        if (root==leaf){
            root=parent;
        }
    }

    private void splitParent(BPlusTreeNode parent) {
        System.out.println("BPlusTree splitParent method");

        int midIndex = parent.getKeys().size()/2;
        System.out.println("BPlusTree splitParent method midIndex: "+ midIndex);

        Comparable midKey = parent.getKeys().get(midIndex);
        System.out.println("BPlusTree splitParent method midKey: "+ midKey);


        BPlusTreeNode newParent = new BPlusTreeNode(false);
        //transfer to new parent
        newParent.getKeys().addAll(parent.getKeys().subList(midIndex+1,parent.getKeys().size()));
        newParent.getChildren().addAll(parent.getChildren().subList(midIndex+1,parent.getChildren().size()+1));
        parent.getKeys().subList(midIndex,parent.getKeys().size()).clear();
        parent.getChildren().subList(midIndex+1,parent.getChildren().size()).clear();
        System.out.println("BPlusTree splitParent method newParent: "+ newParent);
        System.out.println("BPlusTree splitParent method parent: " + parent);



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

    private void splitInternalNode(BPlusTreeNode internalNode){
        int splitIndex = MAX_KEYS/2;
        Comparable middleKey= internalNode.getKeys().get(splitIndex);
        BPlusTreeNode rightNode = new BPlusTreeNode(false);
        rightNode.getKeys().addAll(new ArrayList<>(internalNode.getKeys().subList(splitIndex+1,internalNode.getChildren().size())));
        rightNode.setChildren(new ArrayList<>(internalNode.getChildren().subList(splitIndex+1,internalNode.getChildren().size()+1)));
        for (Node child: rightNode.getChildren()){
            ((BPlusTreeNode) child).setParent(rightNode);
        }
        internalNode.getKeys().subList(splitIndex,internalNode.getKeys().size()).clear();
        internalNode.getChildren().subList(splitIndex+1, internalNode.getChildren().size()).clear();

        if(internalNode== root){
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(middleKey);
            newRoot.getChildren().add(internalNode);
            newRoot.getChildren().add(rightNode);
            root=newRoot;
            internalNode.setParent(newRoot);
            rightNode.setParent(newRoot);
        }
        else {
            insertIntoParent(internalNode,middleKey,rightNode);
        }
    }

    private void insertIntoLeaf(BPlusTreeNode currentNode, Comparable x) {
        int i =0;

        while (i< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(i))>0){
            System.out.println("insertIntoLeaf currentNode: "+ currentNode.getKeys().get(i));
            i++;
        }
        System.out.println("insertIntoLeaf: "+i+" "+ x );
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
            return node;
        }
        else {
            for (int i = 0; i< node.getKeys().size(); i++){
                if (x.compareTo(node.getKeys().get(i))<0){
                    return findLeafNode((BPlusTreeNode) node.getChildren().get(i),x);
                }
            }
            if (!node.getChildren().isEmpty()){
                return findLeafNode((BPlusTreeNode) node.getChildren().get(node.getChildren().size() - 1),x);
            }
            else {
                throw new IllegalStateException("Internal node has no children");
            }
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
