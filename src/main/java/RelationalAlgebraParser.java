// Generated from RelationalAlgebra.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RelationalAlgebraParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, ASC=3, DESC=4, PROJECTION=5, SELECTION=6, RENAME=7, RENAME_ATTR=8, 
		UNION=9, INTERSECTION=10, DIFFERENCE=11, CARTESIAN=12, NATURAL_JOIN=13, 
		LEFT_OUTER_JOIN=14, RIGHT_OUTER_JOIN=15, FULL_OUTER_JOIN=16, ORDER_BY=17, 
		GROUP_BY=18, NOT_EQUAL=19, EQUAL=20, GREATER_EQUAL=21, GREATER=22, LESSER_EQUAL=23, 
		LESSER=24, STRING=25, NUMBER=26, PERIOD=27, COMMA=28, LPAREN=29, RPAREN=30, 
		ARROW=31, SEMI=32, WS=33;
	public static final int
		RULE_expr = 0, RULE_selectionExpr = 1, RULE_projectionExpr = 2, RULE_renameExpr = 3, 
		RULE_groupbyExpr = 4, RULE_groupByAttrs = 5, RULE_groupByAttr = 6, RULE_orderbyExpr = 7, 
		RULE_orders = 8, RULE_order = 9, RULE_direction = 10, RULE_attributes = 11, 
		RULE_attribute = 12, RULE_fullvalue = 13, RULE_renameAttrs = 14, RULE_renameAttr = 15, 
		RULE_conditions = 16, RULE_condition = 17, RULE_logicalOps = 18, RULE_compared = 19, 
		RULE_comp = 20, RULE_data = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"expr", "selectionExpr", "projectionExpr", "renameExpr", "groupbyExpr", 
			"groupByAttrs", "groupByAttr", "orderbyExpr", "orders", "order", "direction", 
			"attributes", "attribute", "fullvalue", "renameAttrs", "renameAttr", 
			"conditions", "condition", "logicalOps", "compared", "comp", "data"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'\u00CF\u20AC'", "'\u00CF\u0192'", "'\u00CF\uFFFD'", 
			"'\u00E2\u2020\uFFFD'", "'\u00E2\u02C6\u00AA'", "'\u00E2\u02C6\u00A9'", 
			"'-'", "'\u00E2\u00A8\u00AF'", "'\u00E2\u00A8\uFFFD'", "'\u00E2\u0178\u2022'", 
			"'\u00E2\u0178\u2013'", "'\u00E2\u0178\u2014'", "'\u00CF\u201E'", "'\u00CE\u00B3'", 
			"'!='", "'='", "'>='", "'>'", "'<='", "'<'", null, null, "'.'", "','", 
			"'('", "')'", "'\u00E2\u2020\u2019'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AND", "OR", "ASC", "DESC", "PROJECTION", "SELECTION", "RENAME", 
			"RENAME_ATTR", "UNION", "INTERSECTION", "DIFFERENCE", "CARTESIAN", "NATURAL_JOIN", 
			"LEFT_OUTER_JOIN", "RIGHT_OUTER_JOIN", "FULL_OUTER_JOIN", "ORDER_BY", 
			"GROUP_BY", "NOT_EQUAL", "EQUAL", "GREATER_EQUAL", "GREATER", "LESSER_EQUAL", 
			"LESSER", "STRING", "NUMBER", "PERIOD", "COMMA", "LPAREN", "RPAREN", 
			"ARROW", "SEMI", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RelationalAlgebra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RelationalAlgebraParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FullJoinContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode FULL_OUTER_JOIN() { return getToken(RelationalAlgebraParser.FULL_OUTER_JOIN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public FullJoinContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterFullJoin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitFullJoin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitFullJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrderbyContext extends ExprContext {
		public OrderbyExprContext orderbyExpr() {
			return getRuleContext(OrderbyExprContext.class,0);
		}
		public OrderbyContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterOrderby(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitOrderby(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitOrderby(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GroupbyContext extends ExprContext {
		public GroupbyExprContext groupbyExpr() {
			return getRuleContext(GroupbyExprContext.class,0);
		}
		public GroupbyContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterGroupby(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitGroupby(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitGroupby(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnionContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode UNION() { return getToken(RelationalAlgebraParser.UNION, 0); }
		public UnionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterUnion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitUnion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitUnion(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RightJoinContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RIGHT_OUTER_JOIN() { return getToken(RelationalAlgebraParser.RIGHT_OUTER_JOIN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public RightJoinContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterRightJoin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitRightJoin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitRightJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelectionContext extends ExprContext {
		public SelectionExprContext selectionExpr() {
			return getRuleContext(SelectionExprContext.class,0);
		}
		public SelectionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitSelection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitSelection(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SimpleRelationContext extends ExprContext {
		public TerminalNode STRING() { return getToken(RelationalAlgebraParser.STRING, 0); }
		public SimpleRelationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterSimpleRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitSimpleRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitSimpleRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RenameContext extends ExprContext {
		public RenameExprContext renameExpr() {
			return getRuleContext(RenameExprContext.class,0);
		}
		public RenameContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterRename(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitRename(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitRename(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NaturalJoinContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode NATURAL_JOIN() { return getToken(RelationalAlgebraParser.NATURAL_JOIN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public NaturalJoinContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterNaturalJoin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitNaturalJoin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitNaturalJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetDifferenceContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode DIFFERENCE() { return getToken(RelationalAlgebraParser.DIFFERENCE, 0); }
		public SetDifferenceContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterSetDifference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitSetDifference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitSetDifference(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntersectionContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode INTERSECTION() { return getToken(RelationalAlgebraParser.INTERSECTION, 0); }
		public IntersectionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterIntersection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitIntersection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitIntersection(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CatesianProductContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CARTESIAN() { return getToken(RelationalAlgebraParser.CARTESIAN, 0); }
		public CatesianProductContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterCatesianProduct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitCatesianProduct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitCatesianProduct(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LeftJoinContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LEFT_OUTER_JOIN() { return getToken(RelationalAlgebraParser.LEFT_OUTER_JOIN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public LeftJoinContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterLeftJoin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitLeftJoin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitLeftJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProjectionContext extends ExprContext {
		public ProjectionExprContext projectionExpr() {
			return getRuleContext(ProjectionExprContext.class,0);
		}
		public ProjectionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterProjection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitProjection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitProjection(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NestedRelationContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(RelationalAlgebraParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RelationalAlgebraParser.RPAREN, 0); }
		public NestedRelationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterNestedRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitNestedRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitNestedRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECTION:
				{
				_localctx = new SelectionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(45);
				selectionExpr();
				}
				break;
			case PROJECTION:
				{
				_localctx = new ProjectionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(46);
				projectionExpr();
				}
				break;
			case GROUP_BY:
				{
				_localctx = new GroupbyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(47);
				groupbyExpr();
				}
				break;
			case RENAME:
				{
				_localctx = new RenameContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(48);
				renameExpr();
				}
				break;
			case ORDER_BY:
				{
				_localctx = new OrderbyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(49);
				orderbyExpr();
				}
				break;
			case LPAREN:
				{
				_localctx = new NestedRelationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(50);
				match(LPAREN);
				setState(51);
				expr(0);
				setState(52);
				match(RPAREN);
				}
				break;
			case STRING:
				{
				_localctx = new SimpleRelationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(92);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(90);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new IntersectionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(57);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(58);
						match(INTERSECTION);
						setState(59);
						expr(16);
						}
						break;
					case 2:
						{
						_localctx = new UnionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(60);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(61);
						match(UNION);
						setState(62);
						expr(15);
						}
						break;
					case 3:
						{
						_localctx = new SetDifferenceContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(63);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(64);
						match(DIFFERENCE);
						setState(65);
						expr(14);
						}
						break;
					case 4:
						{
						_localctx = new NaturalJoinContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(66);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(67);
						match(NATURAL_JOIN);
						setState(69);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
						case 1:
							{
							setState(68);
							condition();
							}
							break;
						}
						setState(71);
						expr(13);
						}
						break;
					case 5:
						{
						_localctx = new LeftJoinContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(72);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(73);
						match(LEFT_OUTER_JOIN);
						setState(74);
						condition();
						setState(75);
						expr(12);
						}
						break;
					case 6:
						{
						_localctx = new RightJoinContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(77);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(78);
						match(RIGHT_OUTER_JOIN);
						setState(79);
						condition();
						setState(80);
						expr(11);
						}
						break;
					case 7:
						{
						_localctx = new FullJoinContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(82);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(83);
						match(FULL_OUTER_JOIN);
						setState(84);
						condition();
						setState(85);
						expr(10);
						}
						break;
					case 8:
						{
						_localctx = new CatesianProductContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(87);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(88);
						match(CARTESIAN);
						setState(89);
						expr(9);
						}
						break;
					}
					} 
				}
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SelectionExprContext extends ParserRuleContext {
		public TerminalNode SELECTION() { return getToken(RelationalAlgebraParser.SELECTION, 0); }
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SelectionExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterSelectionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitSelectionExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitSelectionExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionExprContext selectionExpr() throws RecognitionException {
		SelectionExprContext _localctx = new SelectionExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selectionExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(SELECTION);
			setState(96);
			conditions();
			setState(97);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProjectionExprContext extends ParserRuleContext {
		public TerminalNode PROJECTION() { return getToken(RelationalAlgebraParser.PROJECTION, 0); }
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ProjectionExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_projectionExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterProjectionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitProjectionExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitProjectionExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProjectionExprContext projectionExpr() throws RecognitionException {
		ProjectionExprContext _localctx = new ProjectionExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_projectionExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(PROJECTION);
			setState(100);
			attributes();
			setState(101);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenameExprContext extends ParserRuleContext {
		public TerminalNode RENAME() { return getToken(RelationalAlgebraParser.RENAME, 0); }
		public TerminalNode STRING() { return getToken(RelationalAlgebraParser.STRING, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public RenameAttrsContext renameAttrs() {
			return getRuleContext(RenameAttrsContext.class,0);
		}
		public RenameExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renameExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterRenameExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitRenameExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitRenameExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenameExprContext renameExpr() throws RecognitionException {
		RenameExprContext _localctx = new RenameExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_renameExpr);
		try {
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				match(RENAME);
				setState(104);
				match(STRING);
				setState(105);
				expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(106);
				match(RENAME);
				setState(107);
				renameAttrs();
				setState(108);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupbyExprContext extends ParserRuleContext {
		public TerminalNode GROUP_BY() { return getToken(RelationalAlgebraParser.GROUP_BY, 0); }
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(RelationalAlgebraParser.SEMI, 0); }
		public GroupByAttrsContext groupByAttrs() {
			return getRuleContext(GroupByAttrsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public GroupbyExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupbyExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterGroupbyExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitGroupbyExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitGroupbyExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupbyExprContext groupbyExpr() throws RecognitionException {
		GroupbyExprContext _localctx = new GroupbyExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_groupbyExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(GROUP_BY);
			setState(113);
			attributes();
			setState(114);
			match(SEMI);
			setState(115);
			groupByAttrs();
			setState(116);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupByAttrsContext extends ParserRuleContext {
		public List<GroupByAttrContext> groupByAttr() {
			return getRuleContexts(GroupByAttrContext.class);
		}
		public GroupByAttrContext groupByAttr(int i) {
			return getRuleContext(GroupByAttrContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(RelationalAlgebraParser.COMMA, 0); }
		public GroupByAttrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupByAttrs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterGroupByAttrs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitGroupByAttrs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitGroupByAttrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupByAttrsContext groupByAttrs() throws RecognitionException {
		GroupByAttrsContext _localctx = new GroupByAttrsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_groupByAttrs);
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				groupByAttr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				groupByAttr();
				setState(120);
				match(COMMA);
				setState(121);
				groupByAttr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupByAttrContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(RelationalAlgebraParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(RelationalAlgebraParser.STRING, i);
		}
		public TerminalNode LPAREN() { return getToken(RelationalAlgebraParser.LPAREN, 0); }
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RelationalAlgebraParser.RPAREN, 0); }
		public TerminalNode ARROW() { return getToken(RelationalAlgebraParser.ARROW, 0); }
		public GroupByAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupByAttr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterGroupByAttr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitGroupByAttr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitGroupByAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupByAttrContext groupByAttr() throws RecognitionException {
		GroupByAttrContext _localctx = new GroupByAttrContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_groupByAttr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(STRING);
			setState(126);
			match(LPAREN);
			setState(127);
			attributes();
			setState(128);
			match(RPAREN);
			setState(129);
			match(ARROW);
			setState(130);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderbyExprContext extends ParserRuleContext {
		public TerminalNode ORDER_BY() { return getToken(RelationalAlgebraParser.ORDER_BY, 0); }
		public OrdersContext orders() {
			return getRuleContext(OrdersContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OrderbyExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderbyExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterOrderbyExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitOrderbyExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitOrderbyExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderbyExprContext orderbyExpr() throws RecognitionException {
		OrderbyExprContext _localctx = new OrderbyExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_orderbyExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(ORDER_BY);
			setState(133);
			orders();
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(134);
				expr(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrdersContext extends ParserRuleContext {
		public List<OrderContext> order() {
			return getRuleContexts(OrderContext.class);
		}
		public OrderContext order(int i) {
			return getRuleContext(OrderContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(RelationalAlgebraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(RelationalAlgebraParser.COMMA, i);
		}
		public OrdersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orders; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterOrders(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitOrders(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitOrders(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrdersContext orders() throws RecognitionException {
		OrdersContext _localctx = new OrdersContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_orders);
		try {
			int _alt;
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				order();
				setState(142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(138);
						match(COMMA);
						setState(139);
						order();
						}
						} 
					}
					setState(144);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(145);
				order();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public DirectionContext direction() {
			return getRuleContext(DirectionContext.class,0);
		}
		public OrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_order; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterOrder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitOrder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitOrder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderContext order() throws RecognitionException {
		OrderContext _localctx = new OrderContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_order);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			attribute();
			setState(149);
			direction();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectionContext extends ParserRuleContext {
		public TerminalNode ASC() { return getToken(RelationalAlgebraParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(RelationalAlgebraParser.DESC, 0); }
		public DirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_direction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterDirection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitDirection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitDirection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DirectionContext direction() throws RecognitionException {
		DirectionContext _localctx = new DirectionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_direction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			_la = _input.LA(1);
			if ( !(_la==ASC || _la==DESC) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributesContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(RelationalAlgebraParser.COMMA, 0); }
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public AttributesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterAttributes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitAttributes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitAttributes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributesContext attributes() throws RecognitionException {
		AttributesContext _localctx = new AttributesContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_attributes);
		try {
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				attribute();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				attribute();
				setState(155);
				match(COMMA);
				setState(156);
				attributes();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(RelationalAlgebraParser.STRING, 0); }
		public FullvalueContext fullvalue() {
			return getRuleContext(FullvalueContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_attribute);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(STRING);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
				fullvalue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FullvalueContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(RelationalAlgebraParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(RelationalAlgebraParser.STRING, i);
		}
		public TerminalNode PERIOD() { return getToken(RelationalAlgebraParser.PERIOD, 0); }
		public FullvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterFullvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitFullvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitFullvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FullvalueContext fullvalue() throws RecognitionException {
		FullvalueContext _localctx = new FullvalueContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_fullvalue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(STRING);
			setState(165);
			match(PERIOD);
			setState(166);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenameAttrsContext extends ParserRuleContext {
		public RenameAttrContext renameAttr() {
			return getRuleContext(RenameAttrContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(RelationalAlgebraParser.COMMA, 0); }
		public RenameAttrsContext renameAttrs() {
			return getRuleContext(RenameAttrsContext.class,0);
		}
		public RenameAttrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renameAttrs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterRenameAttrs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitRenameAttrs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitRenameAttrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenameAttrsContext renameAttrs() throws RecognitionException {
		RenameAttrsContext _localctx = new RenameAttrsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_renameAttrs);
		try {
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				renameAttr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				renameAttr();
				setState(170);
				match(COMMA);
				setState(171);
				renameAttrs();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenameAttrContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(RelationalAlgebraParser.STRING, 0); }
		public TerminalNode RENAME_ATTR() { return getToken(RelationalAlgebraParser.RENAME_ATTR, 0); }
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public RenameAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renameAttr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterRenameAttr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitRenameAttr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitRenameAttr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenameAttrContext renameAttr() throws RecognitionException {
		RenameAttrContext _localctx = new RenameAttrContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_renameAttr);
		try {
			setState(179);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(175);
				match(STRING);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(176);
				match(STRING);
				setState(177);
				match(RENAME_ATTR);
				setState(178);
				attribute();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionsContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public LogicalOpsContext logicalOps() {
			return getRuleContext(LogicalOpsContext.class,0);
		}
		public ConditionsContext conditions() {
			return getRuleContext(ConditionsContext.class,0);
		}
		public ConditionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterConditions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitConditions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitConditions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionsContext conditions() throws RecognitionException {
		ConditionsContext _localctx = new ConditionsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_conditions);
		try {
			setState(186);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(181);
				condition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(182);
				condition();
				setState(183);
				logicalOps();
				setState(184);
				conditions();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<ComparedContext> compared() {
			return getRuleContexts(ComparedContext.class);
		}
		public ComparedContext compared(int i) {
			return getRuleContext(ComparedContext.class,i);
		}
		public CompContext comp() {
			return getRuleContext(CompContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			compared();
			setState(189);
			comp();
			setState(190);
			compared();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOpsContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(RelationalAlgebraParser.AND, 0); }
		public TerminalNode OR() { return getToken(RelationalAlgebraParser.OR, 0); }
		public LogicalOpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterLogicalOps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitLogicalOps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitLogicalOps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOpsContext logicalOps() throws RecognitionException {
		LogicalOpsContext _localctx = new LogicalOpsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_logicalOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparedContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public DataContext data() {
			return getRuleContext(DataContext.class,0);
		}
		public ComparedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compared; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterCompared(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitCompared(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitCompared(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparedContext compared() throws RecognitionException {
		ComparedContext _localctx = new ComparedContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_compared);
		try {
			setState(196);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				attribute();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				data();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompContext extends ParserRuleContext {
		public TerminalNode NOT_EQUAL() { return getToken(RelationalAlgebraParser.NOT_EQUAL, 0); }
		public TerminalNode EQUAL() { return getToken(RelationalAlgebraParser.EQUAL, 0); }
		public TerminalNode GREATER_EQUAL() { return getToken(RelationalAlgebraParser.GREATER_EQUAL, 0); }
		public TerminalNode GREATER() { return getToken(RelationalAlgebraParser.GREATER, 0); }
		public TerminalNode LESSER_EQUAL() { return getToken(RelationalAlgebraParser.LESSER_EQUAL, 0); }
		public TerminalNode LESSER() { return getToken(RelationalAlgebraParser.LESSER, 0); }
		public CompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitComp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitComp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompContext comp() throws RecognitionException {
		CompContext _localctx = new CompContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_comp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NOT_EQUAL) | (1L << EQUAL) | (1L << GREATER_EQUAL) | (1L << GREATER) | (1L << LESSER_EQUAL) | (1L << LESSER))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(RelationalAlgebraParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(RelationalAlgebraParser.STRING, 0); }
		public DataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).enterData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RelationalAlgebraListener ) ((RelationalAlgebraListener)listener).exitData(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RelationalAlgebraVisitor ) return ((RelationalAlgebraVisitor<? extends T>)visitor).visitData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_data);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			_la = _input.LA(1);
			if ( !(_la==STRING || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 15);
		case 1:
			return precpred(_ctx, 14);
		case 2:
			return precpred(_ctx, 13);
		case 3:
			return precpred(_ctx, 12);
		case 4:
			return precpred(_ctx, 11);
		case 5:
			return precpred(_ctx, 10);
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#\u00cd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\5\2:\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\5\2H\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2]\n\2\f\2\16\2`\13\2\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5q\n\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\5\7~\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\5\t\u008a\n\t\3\n\3\n\3\n\7\n\u008f\n\n\f\n\16\n\u0092\13\n\3\n"+
		"\5\n\u0095\n\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\5\r\u00a1\n"+
		"\r\3\16\3\16\5\16\u00a5\n\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\5\20\u00b0\n\20\3\21\3\21\3\21\3\21\5\21\u00b6\n\21\3\22\3\22\3\22"+
		"\3\22\3\22\5\22\u00bd\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\5\25"+
		"\u00c7\n\25\3\26\3\26\3\27\3\27\3\27\2\3\2\30\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,\2\6\3\2\5\6\3\2\3\4\3\2\25\32\3\2\33\34\2\u00d0"+
		"\29\3\2\2\2\4a\3\2\2\2\6e\3\2\2\2\bp\3\2\2\2\nr\3\2\2\2\f}\3\2\2\2\16"+
		"\177\3\2\2\2\20\u0086\3\2\2\2\22\u0094\3\2\2\2\24\u0096\3\2\2\2\26\u0099"+
		"\3\2\2\2\30\u00a0\3\2\2\2\32\u00a4\3\2\2\2\34\u00a6\3\2\2\2\36\u00af\3"+
		"\2\2\2 \u00b5\3\2\2\2\"\u00bc\3\2\2\2$\u00be\3\2\2\2&\u00c2\3\2\2\2(\u00c6"+
		"\3\2\2\2*\u00c8\3\2\2\2,\u00ca\3\2\2\2./\b\2\1\2/:\5\4\3\2\60:\5\6\4\2"+
		"\61:\5\n\6\2\62:\5\b\5\2\63:\5\20\t\2\64\65\7\37\2\2\65\66\5\2\2\2\66"+
		"\67\7 \2\2\67:\3\2\2\28:\7\33\2\29.\3\2\2\29\60\3\2\2\29\61\3\2\2\29\62"+
		"\3\2\2\29\63\3\2\2\29\64\3\2\2\298\3\2\2\2:^\3\2\2\2;<\f\21\2\2<=\7\f"+
		"\2\2=]\5\2\2\22>?\f\20\2\2?@\7\13\2\2@]\5\2\2\21AB\f\17\2\2BC\7\r\2\2"+
		"C]\5\2\2\20DE\f\16\2\2EG\7\17\2\2FH\5$\23\2GF\3\2\2\2GH\3\2\2\2HI\3\2"+
		"\2\2I]\5\2\2\17JK\f\r\2\2KL\7\20\2\2LM\5$\23\2MN\5\2\2\16N]\3\2\2\2OP"+
		"\f\f\2\2PQ\7\21\2\2QR\5$\23\2RS\5\2\2\rS]\3\2\2\2TU\f\13\2\2UV\7\22\2"+
		"\2VW\5$\23\2WX\5\2\2\fX]\3\2\2\2YZ\f\n\2\2Z[\7\16\2\2[]\5\2\2\13\\;\3"+
		"\2\2\2\\>\3\2\2\2\\A\3\2\2\2\\D\3\2\2\2\\J\3\2\2\2\\O\3\2\2\2\\T\3\2\2"+
		"\2\\Y\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_\3\3\2\2\2`^\3\2\2\2ab\7"+
		"\b\2\2bc\5\"\22\2cd\5\2\2\2d\5\3\2\2\2ef\7\7\2\2fg\5\30\r\2gh\5\2\2\2"+
		"h\7\3\2\2\2ij\7\t\2\2jk\7\33\2\2kq\5\2\2\2lm\7\t\2\2mn\5\36\20\2no\5\2"+
		"\2\2oq\3\2\2\2pi\3\2\2\2pl\3\2\2\2q\t\3\2\2\2rs\7\24\2\2st\5\30\r\2tu"+
		"\7\"\2\2uv\5\f\7\2vw\5\2\2\2w\13\3\2\2\2x~\5\16\b\2yz\5\16\b\2z{\7\36"+
		"\2\2{|\5\16\b\2|~\3\2\2\2}x\3\2\2\2}y\3\2\2\2~\r\3\2\2\2\177\u0080\7\33"+
		"\2\2\u0080\u0081\7\37\2\2\u0081\u0082\5\30\r\2\u0082\u0083\7 \2\2\u0083"+
		"\u0084\7!\2\2\u0084\u0085\7\33\2\2\u0085\17\3\2\2\2\u0086\u0087\7\23\2"+
		"\2\u0087\u0089\5\22\n\2\u0088\u008a\5\2\2\2\u0089\u0088\3\2\2\2\u0089"+
		"\u008a\3\2\2\2\u008a\21\3\2\2\2\u008b\u0090\5\24\13\2\u008c\u008d\7\36"+
		"\2\2\u008d\u008f\5\24\13\2\u008e\u008c\3\2\2\2\u008f\u0092\3\2\2\2\u0090"+
		"\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0095\3\2\2\2\u0092\u0090\3\2"+
		"\2\2\u0093\u0095\5\24\13\2\u0094\u008b\3\2\2\2\u0094\u0093\3\2\2\2\u0095"+
		"\23\3\2\2\2\u0096\u0097\5\32\16\2\u0097\u0098\5\26\f\2\u0098\25\3\2\2"+
		"\2\u0099\u009a\t\2\2\2\u009a\27\3\2\2\2\u009b\u00a1\5\32\16\2\u009c\u009d"+
		"\5\32\16\2\u009d\u009e\7\36\2\2\u009e\u009f\5\30\r\2\u009f\u00a1\3\2\2"+
		"\2\u00a0\u009b\3\2\2\2\u00a0\u009c\3\2\2\2\u00a1\31\3\2\2\2\u00a2\u00a5"+
		"\7\33\2\2\u00a3\u00a5\5\34\17\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3\2\2"+
		"\2\u00a5\33\3\2\2\2\u00a6\u00a7\7\33\2\2\u00a7\u00a8\7\35\2\2\u00a8\u00a9"+
		"\7\33\2\2\u00a9\35\3\2\2\2\u00aa\u00b0\5 \21\2\u00ab\u00ac\5 \21\2\u00ac"+
		"\u00ad\7\36\2\2\u00ad\u00ae\5\36\20\2\u00ae\u00b0\3\2\2\2\u00af\u00aa"+
		"\3\2\2\2\u00af\u00ab\3\2\2\2\u00b0\37\3\2\2\2\u00b1\u00b6\7\33\2\2\u00b2"+
		"\u00b3\7\33\2\2\u00b3\u00b4\7\n\2\2\u00b4\u00b6\5\32\16\2\u00b5\u00b1"+
		"\3\2\2\2\u00b5\u00b2\3\2\2\2\u00b6!\3\2\2\2\u00b7\u00bd\5$\23\2\u00b8"+
		"\u00b9\5$\23\2\u00b9\u00ba\5&\24\2\u00ba\u00bb\5\"\22\2\u00bb\u00bd\3"+
		"\2\2\2\u00bc\u00b7\3\2\2\2\u00bc\u00b8\3\2\2\2\u00bd#\3\2\2\2\u00be\u00bf"+
		"\5(\25\2\u00bf\u00c0\5*\26\2\u00c0\u00c1\5(\25\2\u00c1%\3\2\2\2\u00c2"+
		"\u00c3\t\3\2\2\u00c3\'\3\2\2\2\u00c4\u00c7\5\32\16\2\u00c5\u00c7\5,\27"+
		"\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7)\3\2\2\2\u00c8\u00c9"+
		"\t\4\2\2\u00c9+\3\2\2\2\u00ca\u00cb\t\5\2\2\u00cb-\3\2\2\2\219G\\^p}\u0089"+
		"\u0090\u0094\u00a0\u00a4\u00af\u00b5\u00bc\u00c6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}