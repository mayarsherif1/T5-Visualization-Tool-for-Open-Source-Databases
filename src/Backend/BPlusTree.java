package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {
    private BPlusTreeNode root;
    static int MAX_KEYS = 2;

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
        while (!currentNode.isLeaf()){
            int i =0;
            System.out.println("currentNode keys size: " +currentNode.getKeys().size());
            System.out.println("currentNode keys: " +currentNode.getKeys());
            System.out.println("currentNode keys size: " +currentNode.getKeys().size() );
            while (i<currentNode.getKeys().size() && x.compareTo(currentNode.getKeys().get(i))>= 0){
                System.out.println("currentNode keys i: " +currentNode.getKeys().get(i));
                i++;
            }
            if(!currentNode.getChildren().isEmpty()&& i<currentNode.getChildren().size()) {
                currentNode = (BPlusTreeNode) currentNode.getChildren().get(i);
            }
            else {
                throw new IllegalStateException("Attempted to navigate to a child that does not exist.");
            }
        }
        int insertPos =0;
        while (insertPos< currentNode.getKeys().size()&& x.compareTo(currentNode.getKeys().get(insertPos))>0 ){
            insertPos++;
        }
        currentNode.getKeys().add(insertPos,x);

        if(currentNode.getKeys().size()>MAX_KEYS){
            splitLeaf(currentNode);
        }

    }

    private void splitLeaf(BPlusTreeNode leaf) {
        System.out.println("BPlusTree splitLeaf method");
        int point = (MAX_KEYS + 1) / 2;

        System.out.println("BPlusTree splitLeaf method point: " + point);
        BPlusTreeNode newLeaf1 = new BPlusTreeNode(true);
        BPlusTreeNode newLeaf2 = new BPlusTreeNode(true);

        //newLeaf2.getKeys().add(splitKey);

        for (int i = 0; i < point; i++) {
            newLeaf1.getKeys().add(leaf.getKeys().get(i));
        }
        for (int i = point; i < leaf.getKeys().size(); i++) {
            newLeaf2.getKeys().add(leaf.getKeys().get(i));
        }

        newLeaf1.setNext(newLeaf2);
        newLeaf2.setNext(leaf.getNext());
//        //leaf.setNext(newLeaf1);
//        leaf.getChildren().add(newLeaf1);
//        leaf.getChildren().add(newLeaf2);
//        newLeaf1.setParent(leaf);
//        newLeaf2.setParent(leaf);
        Comparable splitKey = leaf.getKeys().remove(0);


        if (leaf == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(leaf.getKeys().get(0));
            newRoot.addChild(newLeaf1);
            newRoot.addChild(newLeaf2);
            root = newRoot;
            newLeaf1.setParent(newRoot);
            newLeaf2.setParent(newRoot);
        } else {
            BPlusTreeNode parent = (BPlusTreeNode) leaf.getParent();
            System.out.println("BPlusTree splitLeaf method parent: " + parent.toString());
            int leafPosition = parent.getChildren().indexOf(leaf);
            System.out.println("leafPosition: " + leafPosition);
            Comparable newKey = newLeaf2.getKeys().get(0);
            System.out.println("newKey: " + newKey);

            parent.getChildren().remove(leafPosition);
            parent.addChild(newLeaf1);
            parent.getChildren().add(leafPosition + 1, newLeaf2);
            System.out.println("BPlusTree splitLeaf method parent keys: " + parent.getKeys());
            System.out.println("BPlusTree splitLeaf method parent children: " + parent.getChildren());


            //parent.getKeys().add(leafPosition,splitKey);

            if (leafPosition < parent.getKeys().size()) {
                parent.getKeys().add(newKey);
                System.out.println("BPlusTree splitLeaf method parent keys: " + parent.getKeys());
            } else {
                parent.getKeys().add(leafPosition, newKey);
                System.out.println("BPlusTree splitLeaf method parent keys: " + parent.getKeys());
            }

            newLeaf1.setParent(parent);
            newLeaf2.setParent(parent);
            System.out.println("BPlusTree splitLeaf method newLeaf1: " + newLeaf1.getParent());
            System.out.println("BPlusTree splitLeaf method newLeaf2: " + newLeaf2.getParent());

            if (parent.getKeys().size() > MAX_KEYS) {
                splitInternalNode(parent);
            }
        }
    }
//
//        Comparable smallestNewLeafKey = newLeaf1.getKeys().get(0);
//
//        if (leaf==root){
//            BPlusTreeNode newRoot = new BPlusTreeNode(false);
//            //System.out.println("BPlusTree splitLeaf method newRoot: "+ newRoot);
//            newRoot.getKeys().add(smallestNewLeafKey);
//            newLeaf.getKeys().remove(0);
//            //System.out.println("BPlusTree splitLeaf method newRoot keys: "+ newRoot.getKeys());
//            newRoot.addChild(leaf);
//            newRoot.addChild(newLeaf);
//            //System.out.println("BPlusTree splitLeaf method newRoot children: "+ newRoot.getChildren());
//            root= newRoot;
//            //update parent
//            leaf.setParent(newRoot);
//            newLeaf.setParent(newRoot);
//
//        }
//        else {
//            BPlusTreeNode parent = (BPlusTreeNode) leaf.getParent();
//            if (parent == null) {
//                parent = new BPlusTreeNode(false);
//                leaf.setParent(parent);
//                newLeaf.setParent(parent);
//                parent.getKeys().add(splitKey);
//                parent.getChildren().add(leaf);
//                parent.getChildren().add(newLeaf);
//                root = parent;
//            } else {
//                System.out.println("going to insertIntoParent method");
//                insertIntoParent((BPlusTreeNode) leaf.getParent(), smallestNewLeafKey, newLeaf);
//                newLeaf.getKeys().remove(0);             }
//        }


    private void insertIntoParent(BPlusTreeNode leaf, Comparable key, BPlusTreeNode newLeaf) {
        System.out.println("BPlusTree insertIntoParent method");
        BPlusTreeNode parent = (BPlusTreeNode) leaf.getParent();
        System.out.println("BPlusTree insertIntoParent method parent: "+ parent);

        if(parent==null) {
            System.out.println("BPlusTree insertIntoParent parent==null");
            parent = new BPlusTreeNode(false);
            leaf.setParent(parent);
            newLeaf.setParent(parent);
            parent.addChild(leaf);
            parent.addChild(newLeaf);
            parent.getKeys().add(key);
            System.out.println("BPlusTree insertIntoParent method parent keys: "+ parent.getKeys());
            System.out.println("BPlusTree insertIntoParent method parent children: "+ parent.getChildren());
            root = parent;
        }
        else {
            int i =0;
            while (i< parent.getKeys().size() && key.compareTo(parent.getKeys().get(i))>0){
                i++;
            }
            parent.getKeys().add(i,key);
            parent.getChildren().add(i+1,newLeaf);
            newLeaf.setParent(parent);
            System.out.println("BPlusTree insertIntoParent method parent keys: "+ parent.getKeys());
            System.out.println("BPlusTree insertIntoParent method parent children: "+ parent.getChildren());


        }

        if(parent.getKeys().size()>MAX_KEYS){
            System.out.println("BPlusTree insertIntoParent method parent before splitting: "+ parent);
            splitInternalNode(parent);
            System.out.println("BPlusTree insertIntoParent method parent after splitting: "+ parent);
        }
    }

    private void splitParent(BPlusTreeNode parent) {
        System.out.println("BPlusTree splitParent method");

        int midIndex = parent.getKeys().size()/2;
        System.out.println("BPlusTree splitParent method midIndex: "+ midIndex);

        Comparable midKey = parent.getKeys().get(midIndex);
        System.out.println("BPlusTree splitParent method midKey: "+ midKey);


        BPlusTreeNode newParent = new BPlusTreeNode(false);
        newParent.getKeys().addAll(parent.getKeys().subList(midIndex+1,parent.getKeys().size()));
        newParent.getChildren().addAll(parent.getChildren().subList(midIndex+1,parent.getChildren().size()+1));
        parent.getKeys().subList(midIndex,parent.getKeys().size()).clear();
        parent.getChildren().subList(midIndex+1,parent.getChildren().size()).clear();
        System.out.println("BPlusTree splitParent method newParent: "+ newParent);
        System.out.println("BPlusTree splitParent method parent: " + parent);

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

    private void splitInternalNode(BPlusTreeNode internalNode) {
        System.out.println("BPlusTree splitInternalNode method");
        int midpoint = (internalNode.getKeys().size() + 1) / 2-1;
        System.out.println("BPlusTree splitInternalNode method midpoint: " + midpoint);
        BPlusTreeNode rightNode = new BPlusTreeNode(false);
        for (int i = midpoint + 1; i < internalNode.getKeys().size(); i++) {
            rightNode.getKeys().add(internalNode.getKeys().get(i));
        }
        for (int i = midpoint + 1; i < internalNode.getChildren().size(); i++) {
            //rightNode.addChild(internalNode.getChildren().get(i));
            BPlusTreeNode child = (BPlusTreeNode) internalNode.getChildren().get(i);
            rightNode.addChild(child);
            child.setParent(rightNode);
        }

        Comparable middleKey = internalNode.getKeys().remove(midpoint);
        System.out.println("BPlusTree splitInternalNode method middleKey: " + middleKey);
        internalNode.getKeys().subList(midpoint, internalNode.getKeys().size()).clear();
        internalNode.getChildren().subList(midpoint + 1, internalNode.getChildren().size()).clear();

        if (internalNode == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(middleKey);
            System.out.println("BPlusTree splitInternalNode method newRoot keys: " + newRoot.getKeys());
            newRoot.addChild(internalNode);
            newRoot.addChild(rightNode);
            System.out.println("BPlusTree splitInternalNode method newRoot children: " + newRoot.getChildren());
            internalNode.setParent(newRoot);
            System.out.println("BPlusTree splitInternalNode method internalNode: " + internalNode);
            System.out.println("BPlusTree splitInternalNode method internalNode parent: " + internalNode.getParent());
            rightNode.setParent(newRoot);
            System.out.println("BPlusTree splitInternalNode method rightNode: " + rightNode);
            System.out.println("BPlusTree splitInternalNode method rightNode parent: " + rightNode.getParent());
            root = newRoot;
        } else {
            insertIntoParent((BPlusTreeNode) internalNode.getParent(), middleKey, rightNode);
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