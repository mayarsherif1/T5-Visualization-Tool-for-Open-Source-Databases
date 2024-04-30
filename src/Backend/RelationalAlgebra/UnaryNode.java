package Backend.RelationalAlgebra;

public abstract class UnaryNode extends RelationalAlgebraNode {
    protected RelationalAlgebraNode child;

    public UnaryNode(RelationalAlgebraNode child) {
        this.child = child;
    }

    public RelationalAlgebraNode getChild() {
        return this.child;
    }
}