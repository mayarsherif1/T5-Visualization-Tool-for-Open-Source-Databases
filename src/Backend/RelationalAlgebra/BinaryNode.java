package Backend.RelationalAlgebra;

public abstract class BinaryNode extends RelationalAlgebraNode{
    protected RelationalAlgebraNode left;
    protected RelationalAlgebraNode right;

    public BinaryNode(RelationalAlgebraNode left, RelationalAlgebraNode right) {
        this.left = left;
        this.right = right;
    }

    public RelationalAlgebraNode getLeft() {
        return this.left;
    }

    public RelationalAlgebraNode getRight() {
        return this.right;
    }
    @Override
    public abstract void accept(RelationalAlgebraVisitor visitor);
}
