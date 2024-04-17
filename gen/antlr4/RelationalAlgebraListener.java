// Generated from C:\Users\mayar\OneDrive\Desktop\VisualizationTool\src\antlr4\RelationalAlgebra.g4 by ANTLR 4.9.3
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RelationalAlgebraParser}.
 */
public interface RelationalAlgebraListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code fullJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFullJoin(RelationalAlgebraParser.FullJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fullJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFullJoin(RelationalAlgebraParser.FullJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orderby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrderby(RelationalAlgebraParser.OrderbyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orderby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrderby(RelationalAlgebraParser.OrderbyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGroupby(RelationalAlgebraParser.GroupbyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGroupby(RelationalAlgebraParser.GroupbyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code union}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnion(RelationalAlgebraParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code union}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnion(RelationalAlgebraParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rightJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRightJoin(RelationalAlgebraParser.RightJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rightJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRightJoin(RelationalAlgebraParser.RightJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSelection(RelationalAlgebraParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSelection(RelationalAlgebraParser.SelectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSimpleRelation(RelationalAlgebraParser.SimpleRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSimpleRelation(RelationalAlgebraParser.SimpleRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rename}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRename(RelationalAlgebraParser.RenameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rename}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRename(RelationalAlgebraParser.RenameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code naturalJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNaturalJoin(RelationalAlgebraParser.NaturalJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code naturalJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNaturalJoin(RelationalAlgebraParser.NaturalJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setDifference}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSetDifference(RelationalAlgebraParser.SetDifferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setDifference}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSetDifference(RelationalAlgebraParser.SetDifferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intersection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntersection(RelationalAlgebraParser.IntersectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intersection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntersection(RelationalAlgebraParser.IntersectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code catesianProduct}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCatesianProduct(RelationalAlgebraParser.CatesianProductContext ctx);
	/**
	 * Exit a parse tree produced by the {@code catesianProduct}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCatesianProduct(RelationalAlgebraParser.CatesianProductContext ctx);
	/**
	 * Enter a parse tree produced by the {@code leftJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLeftJoin(RelationalAlgebraParser.LeftJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code leftJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLeftJoin(RelationalAlgebraParser.LeftJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code projection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterProjection(RelationalAlgebraParser.ProjectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code projection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitProjection(RelationalAlgebraParser.ProjectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNestedRelation(RelationalAlgebraParser.NestedRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNestedRelation(RelationalAlgebraParser.NestedRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#selectionExpr}.
	 * @param ctx the parse tree
	 */
	void enterSelectionExpr(RelationalAlgebraParser.SelectionExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#selectionExpr}.
	 * @param ctx the parse tree
	 */
	void exitSelectionExpr(RelationalAlgebraParser.SelectionExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#projectionExpr}.
	 * @param ctx the parse tree
	 */
	void enterProjectionExpr(RelationalAlgebraParser.ProjectionExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#projectionExpr}.
	 * @param ctx the parse tree
	 */
	void exitProjectionExpr(RelationalAlgebraParser.ProjectionExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#renameExpr}.
	 * @param ctx the parse tree
	 */
	void enterRenameExpr(RelationalAlgebraParser.RenameExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#renameExpr}.
	 * @param ctx the parse tree
	 */
	void exitRenameExpr(RelationalAlgebraParser.RenameExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#groupbyExpr}.
	 * @param ctx the parse tree
	 */
	void enterGroupbyExpr(RelationalAlgebraParser.GroupbyExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#groupbyExpr}.
	 * @param ctx the parse tree
	 */
	void exitGroupbyExpr(RelationalAlgebraParser.GroupbyExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#groupByAttrs}.
	 * @param ctx the parse tree
	 */
	void enterGroupByAttrs(RelationalAlgebraParser.GroupByAttrsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#groupByAttrs}.
	 * @param ctx the parse tree
	 */
	void exitGroupByAttrs(RelationalAlgebraParser.GroupByAttrsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#groupByAttr}.
	 * @param ctx the parse tree
	 */
	void enterGroupByAttr(RelationalAlgebraParser.GroupByAttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#groupByAttr}.
	 * @param ctx the parse tree
	 */
	void exitGroupByAttr(RelationalAlgebraParser.GroupByAttrContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#orderbyExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrderbyExpr(RelationalAlgebraParser.OrderbyExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#orderbyExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrderbyExpr(RelationalAlgebraParser.OrderbyExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#orders}.
	 * @param ctx the parse tree
	 */
	void enterOrders(RelationalAlgebraParser.OrdersContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#orders}.
	 * @param ctx the parse tree
	 */
	void exitOrders(RelationalAlgebraParser.OrdersContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#order}.
	 * @param ctx the parse tree
	 */
	void enterOrder(RelationalAlgebraParser.OrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#order}.
	 * @param ctx the parse tree
	 */
	void exitOrder(RelationalAlgebraParser.OrderContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#direction}.
	 * @param ctx the parse tree
	 */
	void enterDirection(RelationalAlgebraParser.DirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#direction}.
	 * @param ctx the parse tree
	 */
	void exitDirection(RelationalAlgebraParser.DirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#attributes}.
	 * @param ctx the parse tree
	 */
	void enterAttributes(RelationalAlgebraParser.AttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#attributes}.
	 * @param ctx the parse tree
	 */
	void exitAttributes(RelationalAlgebraParser.AttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(RelationalAlgebraParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(RelationalAlgebraParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#fullvalue}.
	 * @param ctx the parse tree
	 */
	void enterFullvalue(RelationalAlgebraParser.FullvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#fullvalue}.
	 * @param ctx the parse tree
	 */
	void exitFullvalue(RelationalAlgebraParser.FullvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#renameAttrs}.
	 * @param ctx the parse tree
	 */
	void enterRenameAttrs(RelationalAlgebraParser.RenameAttrsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#renameAttrs}.
	 * @param ctx the parse tree
	 */
	void exitRenameAttrs(RelationalAlgebraParser.RenameAttrsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#renameAttr}.
	 * @param ctx the parse tree
	 */
	void enterRenameAttr(RelationalAlgebraParser.RenameAttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#renameAttr}.
	 * @param ctx the parse tree
	 */
	void exitRenameAttr(RelationalAlgebraParser.RenameAttrContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#conditions}.
	 * @param ctx the parse tree
	 */
	void enterConditions(RelationalAlgebraParser.ConditionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#conditions}.
	 * @param ctx the parse tree
	 */
	void exitConditions(RelationalAlgebraParser.ConditionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(RelationalAlgebraParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(RelationalAlgebraParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#logicalOps}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOps(RelationalAlgebraParser.LogicalOpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#logicalOps}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOps(RelationalAlgebraParser.LogicalOpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#compared}.
	 * @param ctx the parse tree
	 */
	void enterCompared(RelationalAlgebraParser.ComparedContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#compared}.
	 * @param ctx the parse tree
	 */
	void exitCompared(RelationalAlgebraParser.ComparedContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#comp}.
	 * @param ctx the parse tree
	 */
	void enterComp(RelationalAlgebraParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#comp}.
	 * @param ctx the parse tree
	 */
	void exitComp(RelationalAlgebraParser.CompContext ctx);
	/**
	 * Enter a parse tree produced by {@link RelationalAlgebraParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(RelationalAlgebraParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link RelationalAlgebraParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(RelationalAlgebraParser.DataContext ctx);
}