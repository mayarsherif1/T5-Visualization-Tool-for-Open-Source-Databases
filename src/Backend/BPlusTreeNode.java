package Backend;

import java.util.ArrayList;

import static Backend.BPlusTree.MAX_KEYS;

public class BPlusTreeNode extends Node{
    private ArrayList<Comparable> keys;
    private BPlusTreeNode next;
    public BPlusTreeNode(boolean isLeaf){
        super(null);
        this.isLeaf= isLeaf;
        this.keys= new ArrayList<>();
        this.setChildren(new ArrayList<>());
    }
    public void setNext(BPlusTreeNode next){
        this.next=next;
    }
    public BPlusTreeNode getNext(){
        return next;
    }

    public ArrayList<Comparable> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Comparable> keys) {
        this.keys = keys;
    }

    public void addKey(Comparable newKey){
        int i =0;
        while (i< keys.size() && newKey.compareTo(keys.get(i))>= 0){
            i++;
        }

        keys.add(i,newKey);
        if(keys.size() >MAX_KEYS){
            this.split();
        }
    }

    public void split() {
        if (this.keys.size() <= MAX_KEYS) {
            System.err.println("Attempted to split a node that does not exceed the maximum key limit");
            return;
        }
        System.out.println("BPlusTreeNode split method");
        int midIndex = this.keys.size() / 2;
        System.out.println("midIndex: " + midIndex);
        Comparable midKey = this.keys.get(midIndex);
        //int startIndex = this.isLeaf ? midIndex + 1 : midIndex;

        BPlusTreeNode newNode = new BPlusTreeNode(this.isLeaf);
        while (this.keys.size()> midIndex + (this.isLeaf ? 0 : 1)){
            newNode.keys.add(this.keys.remove(midIndex + (this.isLeaf ? 0 : 1)));
        }
        if (!this.isLeaf && this.getChildren().size() > midIndex + 1) {
            newNode.getChildren().addAll(this.getChildren().subList(midIndex + 1, this.getChildren().size()));
            this.getChildren().subList(midIndex + 1, this.getChildren().size()).clear();
            for (Node child : newNode.getChildren()) {
                child.setParent(newNode);
            }
        }
        if (this.isLeaf) {
            // Update next pointers
            newNode.setNext(this.getNext());
            this.setNext(newNode);
        }
        if (this.getParent() == null) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.getKeys().add(this.keys.remove(midIndex));             newRoot.getChildren().add(this);
            newRoot.getChildren().add(newNode);
            this.setParent(newRoot);
            newNode.setParent(newRoot);
        } else {
            // Insert the middle key into the parent node
            ((BPlusTreeNode)this.getParent()).insertInParentNode(this.keys.remove(midIndex), newNode);
        }
    }

    public void insertInParentNode(Comparable key, BPlusTreeNode newNode) {
        int insertPosition = -1;
        for (int i = 0; i < this.keys.size(); i++) {
            if (key.compareTo(this.keys.get(i)) < 0) {
                insertPosition = i;
                break;
            }
        }
        if (insertPosition == -1) {
            insertPosition = this.keys.size();
        }

        this.keys.add(insertPosition, key);
        this.getChildren().add(insertPosition + 1, newNode);
        newNode.setParent(this);

        if (this.keys.size() > MAX_KEYS) {
            this.split();
        }
    }



//        if(!keys.isEmpty()) {
//            newNode.keys.addAll(this.keys.subList(midIndex, this.keys.size()));
//            this.keys.subList(midIndex, this.keys.size()).clear();
//            if (!this.isLeaf) {
//                if (this.getChildren().size() > midIndex + 1) {
//                    newNode.getChildren().addAll(this.getChildren().subList(midIndex + 1, this.getChildren().size()));
//                    this.getChildren().subList(midIndex + 1, this.getChildren().size()).clear();
//
//                    for (Node child : newNode.getChildren()) {
//                        child.setParent(newNode);
//                    }
//                }
//            }
//
//            if (this.isLeaf) {
//                newNode.setNext(this.next);
//                this.setNext(newNode);
//            }
//        }
//        else {
//            System.out.println("Attempted to split a node with insufficient keys.");
//        }
//    }

    public boolean removeKey(Comparable key){
        return keys.remove(key);
    }

}