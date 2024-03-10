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
        child.setParent(this);
        this.children.add(child);
    }

    public boolean removeChild(Node child) {
        return this.children.remove(child);
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
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                ", children=" + children +
                '}';
    }

    // Additional methods like find, update can be implemented based on specific requirements
}
