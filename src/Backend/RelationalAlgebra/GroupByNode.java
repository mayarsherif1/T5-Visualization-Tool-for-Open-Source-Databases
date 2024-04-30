package Backend.RelationalAlgebra;

import java.util.List;

public class GroupByNode extends UnaryNode {
    private List<String> groupColumns;
    private String aggregateFunction;

    public GroupByNode(RelationalAlgebraNode child, List<String> groupColumns, String aggregateFunction) {
        super(child);
        this.groupColumns = groupColumns;
        this.aggregateFunction = aggregateFunction;
    }

    @Override
    public void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int estimateResultSize() {
        // Placeholder: real implementation would depend on the data distribution
        return getChild().estimateResultSize() / 10;
    }

    public List<String> getGroupColumns() {
        return groupColumns;
    }

    public String getAggregateFunction() {
        return aggregateFunction;
    }
}
