package Backend.RelationalAlgebra;


import java.util.List;

public class ProjectionNode extends RelationalAlgebraNode {
    List<String> columns;
    RelationalAlgebraNode child;

    public ProjectionNode(List<String> columns, RelationalAlgebraNode child) {
        this.columns = columns;
        this.child = child;
    }

    void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    int estimateResultSize() {
        return child.estimateResultSize();
    }
}

