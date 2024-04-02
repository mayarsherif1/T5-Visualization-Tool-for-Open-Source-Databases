package Backend;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public boolean isLeaf;
    private Comparable data;
    private Node parent;
    private List<Node> children;

    public Node(Comparable data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
        child.setParent(this);
    }

    public void addChild(int index, Node child) {
        if (index < 0 || index > children.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + children.size());
        }
        this.children.add(index, child);
        child.setParent(this);
    }

    public Node removeChildAtIndex(int index) {
        if (index >= 0 && index < this.children.size()) {
            Node childToRemove = this.children.get(index);
            this.children.remove(index);
            childToRemove.setParent(null);
            return childToRemove;
        }
        return null;
    }


    public void addChildren(List<Node> newChildren){
        for(Node child: newChildren){
            addChild(child);
        }
    }

    public boolean removeChild(Node child) {
        boolean removed = this.children.remove(child);
        if (removed && child.parent==this){
            child.parent=null;
        }
        return removed;
    }

    public Comparable getData() {
        return data;
    }

    public void setData(Comparable data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }
    public boolean isLeaf(){
        return children.isEmpty();
    }

    public void setChildren(List<Node> children) {
        for (Node child: children){
            child.setParent(this);
        }
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" + "data=" + data + ", childrenCount=" + children.size() + '}';
    }

    public Node findNode(Comparable target){
        if(data.equals(target)){
            return this;

        }
        for (Node child: children){
            Node found = child.findNode(target);
            if(found!=null){
                return found;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(o==null || getClass()!= o.getClass())
            return false;
        Node node = (Node)o;
        if(data==null && node.data!=null)
            return false;
        if(data!=null && !data.equals(node.data))
            return false;

        return true;

    }
    @Override
    public int hashCode(){
        return (data!= null)? data.hashCode() :0;
    }


}
