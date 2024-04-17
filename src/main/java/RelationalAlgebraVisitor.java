// Generated from RelationalAlgebra.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RelationalAlgebraParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RelationalAlgebraVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code fullJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullJoin(RelationalAlgebraParser.FullJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orderby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderby(RelationalAlgebraParser.OrderbyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code groupby}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupby(RelationalAlgebraParser.GroupbyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code union}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(RelationalAlgebraParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rightJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightJoin(RelationalAlgebraParser.RightJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(RelationalAlgebraParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleRelation(RelationalAlgebraParser.SimpleRelationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rename}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRename(RelationalAlgebraParser.RenameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code naturalJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaturalJoin(RelationalAlgebraParser.NaturalJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code setDifference}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetDifference(RelationalAlgebraParser.SetDifferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intersection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntersection(RelationalAlgebraParser.IntersectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code catesianProduct}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatesianProduct(RelationalAlgebraParser.CatesianProductContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leftJoin}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftJoin(RelationalAlgebraParser.LeftJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code projection}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjection(RelationalAlgebraParser.ProjectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedRelation}
	 * labeled alternative in {@link RelationalAlgebraParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedRelation(RelationalAlgebraParser.NestedRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#selectionExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionExpr(RelationalAlgebraParser.SelectionExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#projectionExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjectionExpr(RelationalAlgebraParser.ProjectionExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#renameExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameExpr(RelationalAlgebraParser.RenameExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#groupbyExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupbyExpr(RelationalAlgebraParser.GroupbyExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#groupByAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByAttrs(RelationalAlgebraParser.GroupByAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#groupByAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByAttr(RelationalAlgebraParser.GroupByAttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#orderbyExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderbyExpr(RelationalAlgebraParser.OrderbyExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#orders}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrders(RelationalAlgebraParser.OrdersContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#order}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrder(RelationalAlgebraParser.OrderContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#direction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirection(RelationalAlgebraParser.DirectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#attributes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributes(RelationalAlgebraParser.AttributesContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(RelationalAlgebraParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#fullvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullvalue(RelationalAlgebraParser.FullvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#renameAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameAttrs(RelationalAlgebraParser.RenameAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#renameAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameAttr(RelationalAlgebraParser.RenameAttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#conditions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditions(RelationalAlgebraParser.ConditionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(RelationalAlgebraParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#logicalOps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOps(RelationalAlgebraParser.LogicalOpsContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#compared}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompared(RelationalAlgebraParser.ComparedContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp(RelationalAlgebraParser.CompContext ctx);
	/**
	 * Visit a parse tree produced by {@link RelationalAlgebraParser#data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData(RelationalAlgebraParser.DataContext ctx);
}