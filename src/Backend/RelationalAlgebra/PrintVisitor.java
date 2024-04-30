package Backend.RelationalAlgebra;

public class PrintVisitor implements RelationalAlgebraVisitor {
    public void visit(SelectionNode node) {
        System.out.println("Select (" + node.condition + ")");
        node.child.accept(this);
    }

    public void visit(ProjectNode node) {
        System.out.println("Project (" + String.join(", ", node.columns) + ")");
        node.child.accept(this);
    }

    public void visit(JoinNode node) {
        System.out.println("Join on (" + node.condition + ")");
        node.left.accept(this);
        node.right.accept(this);
    }
    @Override
    public void visit(ProjectionNode node) {
        System.out.println("Projection Node with columns: " + node.columns);
        node.child.accept(this);
    }
    @Override
    public void visit(TableNode node) {
        System.out.println("Table: " + node.getTableName());
    }

    @Override
    public void visit(GroupByNode groupByNode) {
        System.out.println("Group by: " + groupByNode.getGroupColumns() + " with aggregate function: " + groupByNode.getAggregateFunction());
        groupByNode.getChild().accept(this);
    }
}
