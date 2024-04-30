package Backend.RelationalAlgebra;

import java.util.List;

public class ProjectNode extends RelationalAlgebraNode {
    List<String> columns;
    RelationalAlgebraNode child;
    boolean isDistinct;

    ProjectNode(List<String> columns, RelationalAlgebraNode child, boolean isDistinct) {
        this.columns = columns;
        this.child = child;
        this.isDistinct = isDistinct;
    }

    void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    int estimateResultSize() {
        int baseSize = child.estimateResultSize();
        if (isDistinct) {
            return estimateDistinct(columns.get(0));
        }
        return baseSize;
    }
    private int estimateDistinct(String attribute) {
        return (int) (child.estimateResultSize() * 0.5);
    }

    List<String> getColumns() {
        return columns;
    }

    RelationalAlgebraNode getChild() {
        return child;
    }
}
