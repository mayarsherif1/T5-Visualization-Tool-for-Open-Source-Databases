package Backend;

import java.util.ArrayList;

public class BPlusTreeNode extends Node{
    private ArrayList<Comparable> keys;
    private BPlusTreeNode next;

    public BPlusTreeNode(boolean isLeaf){
        super(null);
        this.isLeaf= isLeaf;
        this.keys= new ArrayList<>();
        if(!isLeaf){
            this.setChildren(new ArrayList<>());
        }
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
        while (i< keys.size() && keys.get(i).compareTo(newKey)<0){
            i++;
        }
        keys.add(i,newKey);
    }

    public BPlusTreeNode split(){
        int midIndex = keys.size()/2;
        BPlusTreeNode newNode =new BPlusTreeNode(this.isLeaf);
        newNode.keys.addAll(this.keys.subList(midIndex,this.keys.size()));
        this.keys.subList(midIndex, this.keys.size()).clear();
        if(!this.isLeaf){
            newNode.getChildren().addAll(this.getChildren().subList(midIndex+1,this.getChildren().size()));
            this.getChildren().subList(midIndex+1,this.getChildren().size()).clear();

            for (Node child: newNode.getChildren()){
                child.setParent(newNode);
            }
        }
        if(this.isLeaf){
            newNode.setNext(this.next);
            this.setNext(newNode);
        }
        return newNode;
    }

    public boolean removeKey(Comparable key){
        return keys.remove(key);
    }

}
