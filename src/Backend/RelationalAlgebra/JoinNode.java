package Backend.RelationalAlgebra;

public class JoinNode extends BinaryNode {
    String condition;

    JoinNode(RelationalAlgebraNode left, RelationalAlgebraNode right, String condition) {
        super(left, right);
        this.condition = condition;
    }
    @Override
    public void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    public String getCondition() {
        return this.condition;
    }

    @Override
    public int estimateResultSize() {
        double selectivity = 0.1;
        return (int) (left.estimateResultSize() * right.estimateResultSize() * selectivity);
    }
}