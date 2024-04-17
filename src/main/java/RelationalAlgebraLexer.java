// Generated from RelationalAlgebra.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RelationalAlgebraLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"AND", "OR", "ASC", "DESC", "PROJECTION", "SELECTION", "RENAME", "RENAME_ATTR", 
			"UNION", "INTERSECTION", "DIFFERENCE", "CARTESIAN", "NATURAL_JOIN", "LEFT_OUTER_JOIN", 
			"RIGHT_OUTER_JOIN", "FULL_OUTER_JOIN", "ORDER_BY", "GROUP_BY", "NOT_EQUAL", 
			"EQUAL", "GREATER_EQUAL", "GREATER", "LESSER_EQUAL", "LESSER", "STRING", 
			"NUMBER", "PERIOD", "COMMA", "LPAREN", "RPAREN", "ARROW", "SEMI", "WS", 
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
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


	public RelationalAlgebraLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RelationalAlgebra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2#\u011c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31"+
		"\3\31\3\32\6\32\u00cb\n\32\r\32\16\32\u00cc\3\33\6\33\u00d0\n\33\r\33"+
		"\16\33\u00d1\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3!\3"+
		"!\3\"\6\"\u00e3\n\"\r\"\16\"\u00e4\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'"+
		"\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3"+
		"\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38"+
		"\39\39\3:\3:\3;\3;\3<\3<\2\2=\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37= ?!A\"C#E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y"+
		"\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2\3\2\37\b\2)),,\62;C\\"+
		"aac|\3\2\62;\5\2\13\f\17\17\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GG"+
		"gg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2"+
		"PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4"+
		"\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u0104\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2"+
		"A\3\2\2\2\2C\3\2\2\2\3y\3\2\2\2\5}\3\2\2\2\7\u0080\3\2\2\2\t\u0084\3\2"+
		"\2\2\13\u0089\3\2\2\2\r\u008c\3\2\2\2\17\u008f\3\2\2\2\21\u0092\3\2\2"+
		"\2\23\u0096\3\2\2\2\25\u009a\3\2\2\2\27\u009e\3\2\2\2\31\u00a0\3\2\2\2"+
		"\33\u00a4\3\2\2\2\35\u00a8\3\2\2\2\37\u00ac\3\2\2\2!\u00b0\3\2\2\2#\u00b4"+
		"\3\2\2\2%\u00b7\3\2\2\2\'\u00ba\3\2\2\2)\u00bd\3\2\2\2+\u00bf\3\2\2\2"+
		"-\u00c2\3\2\2\2/\u00c4\3\2\2\2\61\u00c7\3\2\2\2\63\u00ca\3\2\2\2\65\u00cf"+
		"\3\2\2\2\67\u00d3\3\2\2\29\u00d5\3\2\2\2;\u00d7\3\2\2\2=\u00d9\3\2\2\2"+
		"?\u00db\3\2\2\2A\u00df\3\2\2\2C\u00e2\3\2\2\2E\u00e8\3\2\2\2G\u00ea\3"+
		"\2\2\2I\u00ec\3\2\2\2K\u00ee\3\2\2\2M\u00f0\3\2\2\2O\u00f2\3\2\2\2Q\u00f4"+
		"\3\2\2\2S\u00f6\3\2\2\2U\u00f8\3\2\2\2W\u00fa\3\2\2\2Y\u00fc\3\2\2\2["+
		"\u00fe\3\2\2\2]\u0100\3\2\2\2_\u0102\3\2\2\2a\u0104\3\2\2\2c\u0106\3\2"+
		"\2\2e\u0108\3\2\2\2g\u010a\3\2\2\2i\u010c\3\2\2\2k\u010e\3\2\2\2m\u0110"+
		"\3\2\2\2o\u0112\3\2\2\2q\u0114\3\2\2\2s\u0116\3\2\2\2u\u0118\3\2\2\2w"+
		"\u011a\3\2\2\2yz\5E#\2z{\5_\60\2{|\5K&\2|\4\3\2\2\2}~\5a\61\2~\177\5g"+
		"\64\2\177\6\3\2\2\2\u0080\u0081\5E#\2\u0081\u0082\5i\65\2\u0082\u0083"+
		"\5I%\2\u0083\b\3\2\2\2\u0084\u0085\5K&\2\u0085\u0086\5M\'\2\u0086\u0087"+
		"\5i\65\2\u0087\u0088\5I%\2\u0088\n\3\2\2\2\u0089\u008a\7\u00d1\2\2\u008a"+
		"\u008b\7\u20ae\2\2\u008b\f\3\2\2\2\u008c\u008d\7\u00d1\2\2\u008d\u008e"+
		"\7\u0194\2\2\u008e\16\3\2\2\2\u008f\u0090\7\u00d1\2\2\u0090\u0091\7\uffff"+
		"\2\2\u0091\20\3\2\2\2\u0092\u0093\7\u00e4\2\2\u0093\u0094\7\u2022\2\2"+
		"\u0094\u0095\7\uffff\2\2\u0095\22\3\2\2\2\u0096\u0097\7\u00e4\2\2\u0097"+
		"\u0098\7\u02c8\2\2\u0098\u0099\7\u00ac\2\2\u0099\24\3\2\2\2\u009a\u009b"+
		"\7\u00e4\2\2\u009b\u009c\7\u02c8\2\2\u009c\u009d\7\u00ab\2\2\u009d\26"+
		"\3\2\2\2\u009e\u009f\7/\2\2\u009f\30\3\2\2\2\u00a0\u00a1\7\u00e4\2\2\u00a1"+
		"\u00a2\7\u00aa\2\2\u00a2\u00a3\7\u00b1\2\2\u00a3\32\3\2\2\2\u00a4\u00a5"+
		"\7\u00e4\2\2\u00a5\u00a6\7\u00aa\2\2\u00a6\u00a7\7\uffff\2\2\u00a7\34"+
		"\3\2\2\2\u00a8\u00a9\7\u00e4\2\2\u00a9\u00aa\7\u017a\2\2\u00aa\u00ab\7"+
		"\u2024\2\2\u00ab\36\3\2\2\2\u00ac\u00ad\7\u00e4\2\2\u00ad\u00ae\7\u017a"+
		"\2\2\u00ae\u00af\7\u2015\2\2\u00af \3\2\2\2\u00b0\u00b1\7\u00e4\2\2\u00b1"+
		"\u00b2\7\u017a\2\2\u00b2\u00b3\7\u2016\2\2\u00b3\"\3\2\2\2\u00b4\u00b5"+
		"\7\u00d1\2\2\u00b5\u00b6\7\u2020\2\2\u00b6$\3\2\2\2\u00b7\u00b8\7\u00d0"+
		"\2\2\u00b8\u00b9\7\u00b5\2\2\u00b9&\3\2\2\2\u00ba\u00bb\7#\2\2\u00bb\u00bc"+
		"\7?\2\2\u00bc(\3\2\2\2\u00bd\u00be\7?\2\2\u00be*\3\2\2\2\u00bf\u00c0\7"+
		"@\2\2\u00c0\u00c1\7?\2\2\u00c1,\3\2\2\2\u00c2\u00c3\7@\2\2\u00c3.\3\2"+
		"\2\2\u00c4\u00c5\7>\2\2\u00c5\u00c6\7?\2\2\u00c6\60\3\2\2\2\u00c7\u00c8"+
		"\7>\2\2\u00c8\62\3\2\2\2\u00c9\u00cb\t\2\2\2\u00ca\u00c9\3\2\2\2\u00cb"+
		"\u00cc\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\64\3\2\2"+
		"\2\u00ce\u00d0\t\3\2\2\u00cf\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00cf"+
		"\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\66\3\2\2\2\u00d3\u00d4\7\60\2\2\u00d4"+
		"8\3\2\2\2\u00d5\u00d6\7.\2\2\u00d6:\3\2\2\2\u00d7\u00d8\7*\2\2\u00d8<"+
		"\3\2\2\2\u00d9\u00da\7+\2\2\u00da>\3\2\2\2\u00db\u00dc\7\u00e4\2\2\u00dc"+
		"\u00dd\7\u2022\2\2\u00dd\u00de\7\u201b\2\2\u00de@\3\2\2\2\u00df\u00e0"+
		"\7=\2\2\u00e0B\3\2\2\2\u00e1\u00e3\t\4\2\2\u00e2\u00e1\3\2\2\2\u00e3\u00e4"+
		"\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6"+
		"\u00e7\b\"\2\2\u00e7D\3\2\2\2\u00e8\u00e9\t\5\2\2\u00e9F\3\2\2\2\u00ea"+
		"\u00eb\t\6\2\2\u00ebH\3\2\2\2\u00ec\u00ed\t\7\2\2\u00edJ\3\2\2\2\u00ee"+
		"\u00ef\t\b\2\2\u00efL\3\2\2\2\u00f0\u00f1\t\t\2\2\u00f1N\3\2\2\2\u00f2"+
		"\u00f3\t\n\2\2\u00f3P\3\2\2\2\u00f4\u00f5\t\13\2\2\u00f5R\3\2\2\2\u00f6"+
		"\u00f7\t\f\2\2\u00f7T\3\2\2\2\u00f8\u00f9\t\r\2\2\u00f9V\3\2\2\2\u00fa"+
		"\u00fb\t\16\2\2\u00fbX\3\2\2\2\u00fc\u00fd\t\17\2\2\u00fdZ\3\2\2\2\u00fe"+
		"\u00ff\t\20\2\2\u00ff\\\3\2\2\2\u0100\u0101\t\21\2\2\u0101^\3\2\2\2\u0102"+
		"\u0103\t\22\2\2\u0103`\3\2\2\2\u0104\u0105\t\23\2\2\u0105b\3\2\2\2\u0106"+
		"\u0107\t\24\2\2\u0107d\3\2\2\2\u0108\u0109\t\25\2\2\u0109f\3\2\2\2\u010a"+
		"\u010b\t\26\2\2\u010bh\3\2\2\2\u010c\u010d\t\27\2\2\u010dj\3\2\2\2\u010e"+
		"\u010f\t\30\2\2\u010fl\3\2\2\2\u0110\u0111\t\31\2\2\u0111n\3\2\2\2\u0112"+
		"\u0113\t\32\2\2\u0113p\3\2\2\2\u0114\u0115\t\33\2\2\u0115r\3\2\2\2\u0116"+
		"\u0117\t\34\2\2\u0117t\3\2\2\2\u0118\u0119\t\35\2\2\u0119v\3\2\2\2\u011a"+
		"\u011b\t\36\2\2\u011bx\3\2\2\2\6\2\u00cc\u00d1\u00e4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}