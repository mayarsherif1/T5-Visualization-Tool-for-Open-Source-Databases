package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {
    private BPlusTreeNode root;
    private static final int MAX_KEYS = 5;

    public BPlusTree() {
        this.root = new BPlusTreeNode(false);
    }

    public BPlusTreeNode getRoot(){
        return this.root;
    }

    @Override
    public void insert(Comparable x) {
        System.out.println("BPlusTree insert method");
        BPlusTreeNode currentNode =root;

        while (!currentNode.isLeaf()){
            int i =0;
            System.out.println("currentNode keys size: " +currentNode.getKeys().size());
            System.out.println("currentNode keys: " +currentNode.getKeys());

            while (i<currentNode.getKeys().size() && x.compareTo(currentNode.getKeys().get(i))>= 0){
                System.out.println("currentNode keys i: " +currentNode.getKeys().get(i));
                i++;
            }
            if(i<currentNode.getChildren().size()) {
                currentNode = (BPlusTreeNode) currentNode.getChildren().get(i);
            }
            else {
                System.out.println("Attempted to navigate to a child that does not exist.");
                break;
            }
        }
        int insertPos =0;
        while (insertPos< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(insertPos))>0 ){
            insertPos++;
        }
        currentNode.getKeys().add(insertPos,x);


//            if(i<currentNode.getChildren().size()){
//                currentNode = (BPlusTreeNode) currentNode.getChildren().get(i);
//            }
        //currentNode.getKeys().add(i,x);
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
        int point= (leaf.getKeys().size())/2;
        List<Comparable> newKeys = new ArrayList<>(leaf.getKeys().subList(point + 1, leaf.getKeys().size()));
        System.out.println("BPlusTree splitLeaf method point: "+ point);
        BPlusTreeNode newLeaf = new BPlusTreeNode(true);
        newLeaf.getKeys().addAll(newKeys);
        Comparable splitKey = leaf.getKeys().get(point);
        leaf.getKeys().subList(point, leaf.getKeys().size()).clear();

        if (leaf.getNext() != null) {
            newLeaf.setNext(leaf.getNext());
        }
        leaf.setNext(newLeaf);

        if (leaf == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(splitKey);
            newRoot.addChild(leaf);
            newRoot.addChild(newLeaf);
            root = newRoot;
            leaf.setParent(newRoot);
            newLeaf.setParent(newRoot);
        }
        else {
            System.out.println("going to insertIntoParent method");
            insertIntoParent(leaf, splitKey, newLeaf);
        }

    }

    private void insertIntoParent(BPlusTreeNode child, Comparable key, BPlusTreeNode newChild) {
        System.out.println("BPlusTree insertIntoParent method");

        if(child.getParent()==null) {
            System.out.println("BPlusTree insertIntoParent parent==null");
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(key);
            newRoot.addChild(child);
            newRoot.addChild(newChild);
            root = newRoot;
            child.setParent(newRoot);
            newChild.setParent(newRoot);
            System.out.println("New root created with keys: " + newRoot.getKeys());
            return;
        }
        BPlusTreeNode parent =(BPlusTreeNode) child.getParent();
        int index = 0;
        while (index < parent.getKeys().size() && key.compareTo(parent.getKeys().get(index)) > 0) {
            index++;
        }
        parent.getKeys().add(index,key);
        parent.getChildren().add(index+1,newChild);

        newChild.setParent(parent);
        if (parent.getKeys().size()>MAX_KEYS){
            splitParent(parent);
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
        int splitIndex = (internalNode.getKeys().size()+1)/2;
        Comparable splitKey = internalNode.getKeys().get(splitIndex);

        BPlusTreeNode newNode = new BPlusTreeNode(false);
        List<Comparable> newKeys = new ArrayList<>(internalNode.getKeys().subList(splitIndex+1,internalNode.getKeys().size()));
        List<Node> newChildren = new ArrayList<>(internalNode.getChildren().subList(splitIndex + 1, internalNode.getChildren().size()));

        newNode.getKeys().addAll(newKeys);
        newNode.getChildren().addAll(newChildren);

        internalNode.getKeys().subList(splitIndex,internalNode.getKeys().size()).clear();
        internalNode.getChildren().subList(splitIndex+1,internalNode.getChildren().size()).clear();

        for (Node child: newNode.getChildren()){
            ((BPlusTreeNode)child).setParent(newNode);
        }
//        Comparable midKey = internalNode.getKeys().remove(splitIndex);

//        while (internalNode.getKeys().size()>splitIndex+1){
//            rightNode.getKeys().add(internalNode.getKeys().remove(splitIndex+1));
//            rightNode.addChild(internalNode.getChildren().remove(splitIndex+1));
//        }
//        internalNode.getKeys().remove(splitIndex);
//
//        rightNode.getKeys().addAll(new ArrayList<>(internalNode.getKeys().subList(splitIndex+1,internalNode.getChildren().size())));
//        rightNode.setChildren(new ArrayList<>(internalNode.getChildren().subList(splitIndex+1,internalNode.getChildren().size()+1)));
//
//        for (Node child: rightNode.getChildren()){
//            ((BPlusTreeNode) child).setParent(rightNode);
//        }
//        internalNode.getKeys().subList(splitIndex,internalNode.getKeys().size()).clear();
//        internalNode.getChildren().subList(splitIndex+1, internalNode.getChildren().size()).clear();

        if(internalNode== root){
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(splitKey);
            newRoot.addChild(internalNode);
            newRoot.addChild(newNode);
            root=newRoot;
            internalNode.setParent(newRoot);
            newNode.setParent(newRoot);
        }
        else {
            insertIntoParent(internalNode,splitKey,newNode);
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
