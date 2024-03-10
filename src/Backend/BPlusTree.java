package Backend;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree extends GenericTree {

    private class Node {
        public Node parent;
        boolean isLeaf;
        ArrayList<Comparable> keys;
        ArrayList<Node> childNodes;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new ArrayList<>();
            this.childNodes = new ArrayList<>();
            this.parent=null;
        }
    }

    private Node root;
    private static final int MAX_KEYS = 5;

    public BPlusTree() {
        this.root = new Node(false);
    }

    @Override
    public void insert(Comparable x) {
        Node currentNode = root;
        if(currentNode.isLeaf){
            insertIntoLeaf(currentNode,x);
            if(currentNode.keys.size()>MAX_KEYS){
                splitLeaf(currentNode);
            }
            else {
                while (!currentNode.isLeaf){
                    int i =0;
                    while (i<currentNode.keys.size()&& x.compareTo(currentNode.keys.get(i))>=0){
                        i++;

                    }
                    currentNode=currentNode.childNodes.get(i);
                }
                insertIntoLeaf(currentNode,x);
                if(currentNode.keys.size()> MAX_KEYS){
                    splitLeaf(currentNode);
                }
            }
        }
    }

    private void splitLeaf(Node leaf) {
        int point = leaf.keys.size()/2;
        Node newLeaf = new Node(true);
        //transfer to new leaf
        newLeaf.keys.addAll(leaf.keys.subList(point, leaf.keys.size()));
        leaf.keys.subList(point, leaf.keys.size()).clear();

        //update pointers
        newLeaf.childNodes.add(leaf.childNodes.get(0));
        leaf.childNodes.set(0,newLeaf);

        if (leaf==root){
            Node newRoot = new Node(false);
            newRoot.keys.add(newLeaf.keys.get(0));
            newRoot.childNodes.add(leaf);
            newRoot.childNodes.add(newLeaf);
            root= newRoot;
            //update parent
            leaf.parent=newRoot;
            newLeaf.parent=newRoot;

        }
        else {
            insertIntoParent(leaf, newLeaf.keys.get(0), newLeaf);
        }

    }

    private void insertIntoParent(Node leaf, Comparable key, Node newLeaf) {
        Node parent = leaf.parent;
        if(parent== null ){
            parent= new Node(false);
            root= parent;
        }
        int i =0;
        while (i< parent.keys.size() && key.compareTo(parent.keys.get(i))>0){
            i++;
        }
        parent.keys.add(i,key);
        parent.childNodes.add(i+1,newLeaf);

        if(parent.keys.size()>MAX_KEYS){
            splitParent(parent);
        }
    }

    private void splitParent(Node parent) {
        int midIndex = parent.keys.size()/2;
        Comparable midKey = parent.keys.get(midIndex);

        Node newParent = new Node(false);
        //transfer to new parent
        newParent.keys.addAll(parent.keys.subList(midIndex+1,parent.keys.size()));
        newParent.childNodes.addAll(parent.childNodes.subList(midIndex+1,parent.childNodes.size()+1));
        parent.keys.subList(midIndex,parent.keys.size()).clear();
        parent.childNodes.subList(midIndex+1,parent.childNodes.size()).clear();

        //update parent pointers
        for (Node child : newParent.childNodes){
            child.parent= newParent;
        }
        if(parent== root){
            Node newRoot = new Node(false);
            newRoot.keys.add(midKey);
            newRoot.childNodes.add(parent);
            newRoot.childNodes.add(newParent);
            root=newRoot;
            parent.parent=newRoot;
            newParent.parent=newRoot;
        }
        else {
            insertIntoParent(parent,midKey,newParent);
        }

    }

    private void insertIntoLeaf(Node currentNode, Comparable x) {
        int i =0;
        while (i< currentNode.keys.size()&& x.compareTo(currentNode.keys.get(i))>0){
            i++;
        }
        currentNode.keys.add(i,x);
    }

    @Override
    public void delete(Comparable x) {
        // Real B+ tree logic will handle finding the key, deleting it, and then handling underflow in the leaf and potentially in parent nodes
        Node leafNode = findLeafNode(root,x);
        if (leafNode!=null){
            boolean deleted = leafNode.keys.remove(x);
            if(deleted){
                if ((leafNode.keys.size()<Math.ceil(MAX_KEYS/2.0))&& leafNode!= root){
                    handleUnderFlow(leafNode);
                }
            }
        }

    }

    private Node findLeafNode(Node node, Comparable x) {
        if(node.isLeaf){
            if(node.keys.contains(x)){
                return node;
            }
            else {
                return null;
            }
        }
        else {
            for (int i =0; i< node.keys.size(); i++){
                if (x.compareTo(node.keys.get(i))<0){
                    return findLeafNode(node.childNodes.get(i),x);
                }
            }
            return findLeafNode(node.childNodes.getLast(),x);
        }
    }

    private void handleUnderFlow(Node node) {
        Node parent = node.parent;
        int nodeIndex = parent.childNodes.indexOf(node);

        Node left = (nodeIndex>0)? parent.childNodes.get(nodeIndex-1): null;
        Node right = (nodeIndex<parent.childNodes.size()-1)?parent.childNodes.get(nodeIndex+1): null;

        //borrowing
        if(left!= null&& left.keys.size()>Math.ceil(MAX_KEYS/2.0)){
            //borrow from left
            Comparable borrowedKey = left.keys.remove(left.keys.size()-1);
            node.keys.add(0,borrowedKey);
            parent.keys.set(nodeIndex-1,node.keys.get(0));

        }
        else if (right!= null && right.keys.size()> Math.ceil(MAX_KEYS/2.0)){
            Comparable borrowedKey = right.keys.removeFirst();
            node.keys.add(borrowedKey);
            parent.keys.set(nodeIndex,right.keys.get(0));
        }
        else {
            if (left != null) {
                left.keys.addAll(node.keys);
                parent.childNodes.remove(node);
                parent.keys.remove(nodeIndex - 1);
            } else if (right != null) {
                node.keys.addAll(right.keys);
                parent.childNodes.remove(right);
                parent.keys.remove(nodeIndex);
            }
            if (parent == root && parent.keys.isEmpty()) {
                root = node;
                root.parent = null;
            } else if (parent != root && parent.keys.size() < Math.ceil(MAX_KEYS / 2.0)) {
                handleUnderFlow(parent);
            }
        }
    }

    @Override
    public boolean search(Comparable x) {
        Node currentNode = root;
        while (!currentNode.isLeaf) {
            boolean found = false;
            for (int i = 0; i < currentNode.keys.size(); i++) {
                if (x.compareTo(currentNode.keys.get(i)) < 0) {
                    currentNode = currentNode.childNodes.get(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentNode = currentNode.childNodes.get(currentNode.childNodes.size() - 1);
            }
        }
        return currentNode.keys.contains(x);
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
            keys.addAll(current.keys);
            if(!current.childNodes.isEmpty()&& current.isLeaf){
                current= current.childNodes.get(0);
            }
            else {
                current=null;
            }
        }
        return keys;
    }

    private Node findLeftMostLeaf(Node node) {
        if (node== null){
            return null;
        }
        while (!node.isLeaf){
            if (node.childNodes.isEmpty()){
                return null;
            }
            node= node.childNodes.get(0);
        }
        return node;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


}
