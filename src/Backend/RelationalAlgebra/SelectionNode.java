package Backend.RelationalAlgebra;

public class SelectionNode extends UnaryNode {
    String condition;
    RelationalAlgebraNode child;

   public SelectionNode(String condition, RelationalAlgebraNode child) {
       super(child);
       this.condition = condition;
   }

   @Override
    public void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    int estimateResultSize() {
        if (condition.contains(">") || condition.contains("<")) {
            return estimateRange(condition);
        } else if (condition.contains("!=")) {
            return estimateNotEqual(condition);
        }
        return (int) (child.estimateResultSize() * 0.5);
    }

    private int estimateRange(String condition) {
        return (int) (child.estimateResultSize() * 0.3);
    }
    private int estimateNotEqual(String condition) {
        return (int) (child.estimateResultSize() * 0.9);
    }

    public String getCondition() {
        return this.condition;
    }
}