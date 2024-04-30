package Backend.RelationalAlgebra;

interface RelationalAlgebraVisitor {
    void visit(ProjectNode node);
    void visit(JoinNode node);
    void visit(SelectionNode node);
    void visit(ProjectionNode node);

    void visit(TableNode tableNode);

    void visit(GroupByNode groupByNode);
}