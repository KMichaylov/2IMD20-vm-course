// Generated from /Users/danieleb/GoogleDrive/2IMD20/labs/lab_01_AST/lab01/src/ToyLang.g4 by ANTLR 4.12.0
package nl.tue.vmcourse.toy.parser;

// DO NOT MODIFY - generated from SimpleLanguage.g4 using "mx create-sl-parser"

import java.util.ArrayList;
import java.util.List;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.interpreter.ToySyntaxErrorException;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ToyLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, WS=36, COMMENT=37, LINE_COMMENT=38, 
		IDENTIFIER=39, STRING_LITERAL=40, NUMERIC_LITERAL=41;
	public static final int
		RULE_toylanguage = 0, RULE_function = 1, RULE_block = 2, RULE_statement = 3, 
		RULE_while_statement = 4, RULE_if_statement = 5, RULE_return_statement = 6, 
		RULE_expression = 7, RULE_logic_term = 8, RULE_logic_factor = 9, RULE_arithmetic = 10, 
		RULE_term = 11, RULE_factor = 12, RULE_member_expression = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"toylanguage", "function", "block", "statement", "while_statement", "if_statement", 
			"return_statement", "expression", "logic_term", "logic_factor", "arithmetic", 
			"term", "factor", "member_expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'function'", "'('", "','", "')'", "'{'", "'}'", "'break'", "';'", 
			"'continue'", "'while'", "'if'", "'else'", "'return'", "'||'", "'&&'", 
			"'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", "'*'", "'/'", 
			"'universe'", "'undef'", "'newObject'", "'newArray'", "'true'", "'false'", 
			"'='", "'.'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"WS", "COMMENT", "LINE_COMMENT", "IDENTIFIER", "STRING_LITERAL", "NUMERIC_LITERAL"
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
	public String getGrammarFileName() { return "ToyLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	private ToyNodeFactory factory;

	public void setFactory(ToyNodeFactory factory) {
	    this.factory = factory;
	}

	public static final class BailoutErrorListener extends BaseErrorListener {

	    public BailoutErrorListener() {
	    }

	    @Override
	    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
	        throwParseError(line, charPositionInLine, (Token) offendingSymbol, msg);
	    }
	}

	public void SemErr(Token token, String message) {
	    assert token != null;
	    throwParseError(token.getLine(), token.getCharPositionInLine(), token, message);
	}

	private static void throwParseError(int line, int charPositionInLine, Token token, String message) {
	    int col = charPositionInLine + 1;
	    String location = "-- line " + line + " col " + col + ": ";
	    int length = token == null ? 1 : Math.max(token.getStopIndex() - token.getStartIndex(), 0);
	    throw new ToySyntaxErrorException(String.format("Error(s) parsing script:%n" + location + message));
	}

	public ToyLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ToylanguageContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public TerminalNode EOF() { return getToken(ToyLangParser.EOF, 0); }
		public ToylanguageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_toylanguage; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitToylanguage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ToylanguageContext toylanguage() throws RecognitionException {
		ToylanguageContext _localctx = new ToylanguageContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_toylanguage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			function();
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(29);
				function();
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(35);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionContext extends ParserRuleContext {
		public Token IDENTIFIER;
		public Token s;
		public BlockContext body;
		public List<TerminalNode> IDENTIFIER() { return getTokens(ToyLangParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(ToyLangParser.IDENTIFIER, i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(T__0);
			setState(38);
			((FunctionContext)_localctx).IDENTIFIER = match(IDENTIFIER);
			setState(39);
			((FunctionContext)_localctx).s = match(T__1);
			 factory.startFunction(((FunctionContext)_localctx).IDENTIFIER, ((FunctionContext)_localctx).s); 
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(41);
				((FunctionContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 factory.addFormalParameter(((FunctionContext)_localctx).IDENTIFIER); 
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(43);
					match(T__2);
					setState(44);
					((FunctionContext)_localctx).IDENTIFIER = match(IDENTIFIER);
					 factory.addFormalParameter(((FunctionContext)_localctx).IDENTIFIER); 
					}
					}
					setState(50);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(53);
			match(T__3);
			setState(54);
			((FunctionContext)_localctx).body = block(false);
			 factory.finishFunction(((FunctionContext)_localctx).body.result); 
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

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public boolean inLoop;
		public ToyStatementNode result;
		public Token s;
		public StatementContext statement;
		public Token e;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public BlockContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block(boolean inLoop) throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<ToyStatementNode> body = new ArrayList<>(); 
			setState(58);
			((BlockContext)_localctx).s = match(T__4);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3852518567556L) != 0)) {
				{
				{
				setState(59);
				((BlockContext)_localctx).statement = statement(inLoop);
				 body.add(((BlockContext)_localctx).statement.result); 
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			((BlockContext)_localctx).e = match(T__5);
			 ((BlockContext)_localctx).result =  factory.finishBlock(body); 
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

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public boolean inLoop;
		public ToyStatementNode result;
		public While_statementContext while_statement;
		public Token b;
		public Token c;
		public If_statementContext if_statement;
		public Return_statementContext return_statement;
		public ExpressionContext expression;
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public Return_statementContext return_statement() {
			return getRuleContext(Return_statementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public StatementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement(boolean inLoop) throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 6, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				{
				setState(70);
				((StatementContext)_localctx).while_statement = while_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).while_statement.result; 
				}
				break;
			case T__6:
				{
				setState(73);
				((StatementContext)_localctx).b = match(T__6);
				 if (inLoop) { ((StatementContext)_localctx).result =  factory.createBreak(((StatementContext)_localctx).b); } else { SemErr(((StatementContext)_localctx).b, "break used outside of loop"); } 
				setState(75);
				match(T__7);
				}
				break;
			case T__8:
				{
				setState(76);
				((StatementContext)_localctx).c = match(T__8);
				 if (inLoop) { ((StatementContext)_localctx).result =  factory.createContinue(((StatementContext)_localctx).c); } else { SemErr(((StatementContext)_localctx).c, "continue used outside of loop"); } 
				setState(78);
				match(T__7);
				}
				break;
			case T__10:
				{
				setState(79);
				((StatementContext)_localctx).if_statement = if_statement(inLoop);
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).if_statement.result; 
				}
				break;
			case T__12:
				{
				setState(82);
				((StatementContext)_localctx).return_statement = return_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).return_statement.result; 
				}
				break;
			case T__1:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__30:
			case IDENTIFIER:
			case STRING_LITERAL:
			case NUMERIC_LITERAL:
				{
				setState(85);
				((StatementContext)_localctx).expression = expression();
				setState(86);
				match(T__7);
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).expression.result; 
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class While_statementContext extends ParserRuleContext {
		public ToyStatementNode result;
		public Token w;
		public ExpressionContext condition;
		public BlockContext body;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public While_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitWhile_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_statementContext while_statement() throws RecognitionException {
		While_statementContext _localctx = new While_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			((While_statementContext)_localctx).w = match(T__9);
			setState(92);
			match(T__1);
			setState(93);
			((While_statementContext)_localctx).condition = expression();
			setState(94);
			match(T__3);
			setState(95);
			((While_statementContext)_localctx).body = block(true);
			 ((While_statementContext)_localctx).result =  factory.createWhile(((While_statementContext)_localctx).w, ((While_statementContext)_localctx).condition.result, ((While_statementContext)_localctx).body.result); 
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

	@SuppressWarnings("CheckReturnValue")
	public static class If_statementContext extends ParserRuleContext {
		public boolean inLoop;
		public ToyStatementNode result;
		public Token i;
		public ExpressionContext condition;
		public BlockContext then;
		public BlockContext block;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public If_statementContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public If_statementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitIf_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statementContext if_statement(boolean inLoop) throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 10, RULE_if_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			((If_statementContext)_localctx).i = match(T__10);
			setState(99);
			match(T__1);
			setState(100);
			((If_statementContext)_localctx).condition = expression();
			setState(101);
			match(T__3);
			setState(102);
			((If_statementContext)_localctx).then = ((If_statementContext)_localctx).block = block(inLoop);
			 ToyStatementNode elsePart = null; 
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(104);
				match(T__11);
				setState(105);
				((If_statementContext)_localctx).block = block(inLoop);
				 elsePart = ((If_statementContext)_localctx).block.result; 
				}
			}

			 ((If_statementContext)_localctx).result =  factory.createIf(((If_statementContext)_localctx).i, ((If_statementContext)_localctx).condition.result, ((If_statementContext)_localctx).then.result, elsePart); 
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

	@SuppressWarnings("CheckReturnValue")
	public static class Return_statementContext extends ParserRuleContext {
		public ToyStatementNode result;
		public Token r;
		public ExpressionContext expression;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitReturn_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_statementContext return_statement() throws RecognitionException {
		Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_return_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			((Return_statementContext)_localctx).r = match(T__12);
			 ToyExpressionNode value = null;
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3852518555652L) != 0)) {
				{
				setState(114);
				((Return_statementContext)_localctx).expression = expression();
				 value = ((Return_statementContext)_localctx).expression.result; 
				}
			}

			 ((Return_statementContext)_localctx).result =  factory.createReturn(((Return_statementContext)_localctx).r, value); 
			setState(120);
			match(T__7);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public Logic_termContext logic_term;
		public Token op;
		public List<Logic_termContext> logic_term() {
			return getRuleContexts(Logic_termContext.class);
		}
		public Logic_termContext logic_term(int i) {
			return getRuleContext(Logic_termContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			((ExpressionContext)_localctx).logic_term = logic_term();
			 ((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).logic_term.result; 
			setState(130);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(124);
					((ExpressionContext)_localctx).op = match(T__13);
					setState(125);
					((ExpressionContext)_localctx).logic_term = logic_term();
					 ((ExpressionContext)_localctx).result =  factory.createBinary(((ExpressionContext)_localctx).op, _localctx.result, ((ExpressionContext)_localctx).logic_term.result); 
					}
					} 
				}
				setState(132);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_termContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public Logic_factorContext logic_factor;
		public Token op;
		public List<Logic_factorContext> logic_factor() {
			return getRuleContexts(Logic_factorContext.class);
		}
		public Logic_factorContext logic_factor(int i) {
			return getRuleContext(Logic_factorContext.class,i);
		}
		public Logic_termContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitLogic_term(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_termContext logic_term() throws RecognitionException {
		Logic_termContext _localctx = new Logic_termContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_logic_term);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			((Logic_termContext)_localctx).logic_factor = logic_factor();
			 ((Logic_termContext)_localctx).result =  ((Logic_termContext)_localctx).logic_factor.result; 
			setState(141);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(135);
					((Logic_termContext)_localctx).op = match(T__14);
					setState(136);
					((Logic_termContext)_localctx).logic_factor = logic_factor();
					 ((Logic_termContext)_localctx).result =  factory.createBinary(((Logic_termContext)_localctx).op, _localctx.result, ((Logic_termContext)_localctx).logic_factor.result); 
					}
					} 
				}
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_factorContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public ArithmeticContext arithmetic;
		public Token op;
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public Logic_factorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_factor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitLogic_factor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_factorContext logic_factor() throws RecognitionException {
		Logic_factorContext _localctx = new Logic_factorContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_logic_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			((Logic_factorContext)_localctx).arithmetic = arithmetic();
			 ((Logic_factorContext)_localctx).result =  ((Logic_factorContext)_localctx).arithmetic.result; 
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(146);
				((Logic_factorContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4128768L) != 0)) ) {
					((Logic_factorContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(147);
				((Logic_factorContext)_localctx).arithmetic = arithmetic();
				 ((Logic_factorContext)_localctx).result =  factory.createBinary(((Logic_factorContext)_localctx).op, _localctx.result, ((Logic_factorContext)_localctx).arithmetic.result); 
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public TermContext term;
		public Token op;
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_arithmetic);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			((ArithmeticContext)_localctx).term = term();
			 ((ArithmeticContext)_localctx).result =  ((ArithmeticContext)_localctx).term.result; 
			setState(160);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(154);
					((ArithmeticContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__21 || _la==T__22) ) {
						((ArithmeticContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(155);
					((ArithmeticContext)_localctx).term = term();
					 ((ArithmeticContext)_localctx).result =  factory.createBinary(((ArithmeticContext)_localctx).op, _localctx.result, ((ArithmeticContext)_localctx).term.result); 
					}
					} 
				}
				setState(162);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public FactorContext factor;
		public Token op;
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_term);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			((TermContext)_localctx).factor = factor();
			 ((TermContext)_localctx).result =  ((TermContext)_localctx).factor.result; 
			setState(171);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(165);
					((TermContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__23 || _la==T__24) ) {
						((TermContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(166);
					((TermContext)_localctx).factor = factor();
					 ((TermContext)_localctx).result =  factory.createBinary(((TermContext)_localctx).op, _localctx.result, ((TermContext)_localctx).factor.result); 
					}
					} 
				}
				setState(173);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public ToyExpressionNode result;
		public Token IDENTIFIER;
		public Member_expressionContext member_expression;
		public Token STRING_LITERAL;
		public Token NUMERIC_LITERAL;
		public Token d;
		public Token s;
		public ExpressionContext expr;
		public Token e;
		public TerminalNode IDENTIFIER() { return getToken(ToyLangParser.IDENTIFIER, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(ToyLangParser.STRING_LITERAL, 0); }
		public TerminalNode NUMERIC_LITERAL() { return getToken(ToyLangParser.NUMERIC_LITERAL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Member_expressionContext member_expression() {
			return getRuleContext(Member_expressionContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_factor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(174);
				((FactorContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 ToyExpressionNode assignmentName = factory.createStringLiteral(((FactorContext)_localctx).IDENTIFIER, false); 
				setState(180);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(176);
					((FactorContext)_localctx).member_expression = member_expression(null, null, assignmentName);
					 ((FactorContext)_localctx).result =  ((FactorContext)_localctx).member_expression.result; 
					}
					break;
				case 2:
					{
					 ((FactorContext)_localctx).result =  factory.createRead(assignmentName); 
					}
					break;
				}
				}
				break;
			case STRING_LITERAL:
				{
				setState(182);
				((FactorContext)_localctx).STRING_LITERAL = match(STRING_LITERAL);
				 ((FactorContext)_localctx).result =  factory.createStringLiteral(((FactorContext)_localctx).STRING_LITERAL, true); 
				}
				break;
			case NUMERIC_LITERAL:
				{
				setState(184);
				((FactorContext)_localctx).NUMERIC_LITERAL = match(NUMERIC_LITERAL);
				 ((FactorContext)_localctx).result =  factory.createNumericLiteral(((FactorContext)_localctx).NUMERIC_LITERAL); 
				}
				break;
			case T__25:
				{
				setState(186);
				((FactorContext)_localctx).d = match(T__25);
				 ((FactorContext)_localctx).result =  factory.createUniverse(((FactorContext)_localctx).d); 
				}
				break;
			case T__26:
				{
				setState(188);
				((FactorContext)_localctx).d = match(T__26);
				 ((FactorContext)_localctx).result =  factory.createUndef(((FactorContext)_localctx).d); 
				}
				break;
			case T__27:
				{
				setState(190);
				((FactorContext)_localctx).d = match(T__27);
				 ((FactorContext)_localctx).result =  factory.createNewObject(((FactorContext)_localctx).d); 
				}
				break;
			case T__28:
				{
				setState(192);
				((FactorContext)_localctx).d = match(T__28);
				 ((FactorContext)_localctx).result =  factory.createNewObject(((FactorContext)_localctx).d);
				}
				break;
			case T__29:
				{
				setState(194);
				((FactorContext)_localctx).d = match(T__29);
				 ((FactorContext)_localctx).result =  factory.createBooleanLiteral(true); 
				}
				break;
			case T__30:
				{
				setState(196);
				((FactorContext)_localctx).d = match(T__30);
				 ((FactorContext)_localctx).result =  factory.createBooleanLiteral(false); 
				}
				break;
			case T__1:
				{
				setState(198);
				((FactorContext)_localctx).s = match(T__1);
				setState(199);
				((FactorContext)_localctx).expr = expression();
				setState(200);
				((FactorContext)_localctx).e = match(T__3);
				 ((FactorContext)_localctx).result =  factory.createParenExpression(((FactorContext)_localctx).expr.result, ((FactorContext)_localctx).s.getStartIndex(), ((FactorContext)_localctx).e.getStopIndex() - ((FactorContext)_localctx).s.getStartIndex() + 1); 
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Member_expressionContext extends ParserRuleContext {
		public ToyExpressionNode r;
		public ToyExpressionNode assignmentReceiver;
		public ToyExpressionNode assignmentName;
		public ToyExpressionNode result;
		public ExpressionContext expression;
		public Token e;
		public Token IDENTIFIER;
		public Member_expressionContext member_expression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode IDENTIFIER() { return getToken(ToyLangParser.IDENTIFIER, 0); }
		public Member_expressionContext member_expression() {
			return getRuleContext(Member_expressionContext.class,0);
		}
		public Member_expressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Member_expressionContext(ParserRuleContext parent, int invokingState, ToyExpressionNode r, ToyExpressionNode assignmentReceiver, ToyExpressionNode assignmentName) {
			super(parent, invokingState);
			this.r = r;
			this.assignmentReceiver = assignmentReceiver;
			this.assignmentName = assignmentName;
		}
		@Override public int getRuleIndex() { return RULE_member_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ToyLangVisitor ) return ((ToyLangVisitor<? extends T>)visitor).visitMember_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Member_expressionContext member_expression(ToyExpressionNode r,ToyExpressionNode assignmentReceiver,ToyExpressionNode assignmentName) throws RecognitionException {
		Member_expressionContext _localctx = new Member_expressionContext(_ctx, getState(), r, assignmentReceiver, assignmentName);
		enterRule(_localctx, 26, RULE_member_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ToyExpressionNode receiver = r;
			                                                  ToyExpressionNode nestedAssignmentName = null; 
			setState(237);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				{
				setState(206);
				match(T__1);
				 List<ToyExpressionNode> parameters = new ArrayList<>();
				                                                  if (receiver == null) {
				                                                      receiver = factory.createRead(assignmentName);
				                                                  } 
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3852518555652L) != 0)) {
					{
					setState(208);
					((Member_expressionContext)_localctx).expression = expression();
					 parameters.add(((Member_expressionContext)_localctx).expression.result); 
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(210);
						match(T__2);
						setState(211);
						((Member_expressionContext)_localctx).expression = expression();
						 parameters.add(((Member_expressionContext)_localctx).expression.result); 
						}
						}
						setState(218);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(221);
				((Member_expressionContext)_localctx).e = match(T__3);
				 ((Member_expressionContext)_localctx).result =  factory.createCall(receiver, parameters, ((Member_expressionContext)_localctx).e); 
				}
				break;
			case T__31:
				{
				setState(223);
				match(T__31);
				setState(224);
				((Member_expressionContext)_localctx).expression = expression();
				 if (assignmentName == null) {
				                                                      SemErr((((Member_expressionContext)_localctx).expression!=null?(((Member_expressionContext)_localctx).expression.start):null), "invalid assignment target");
				                                                  } else if (assignmentReceiver == null) {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createAssignment(assignmentName, ((Member_expressionContext)_localctx).expression.result);
				                                                  } else {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createWriteProperty(assignmentReceiver, assignmentName, ((Member_expressionContext)_localctx).expression.result);
				                                                  } 
				}
				break;
			case T__32:
				{
				setState(227);
				match(T__32);
				 if (receiver == null) {
				                                                       receiver = factory.createRead(assignmentName);
				                                                  } 
				setState(229);
				((Member_expressionContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 nestedAssignmentName = factory.createStringLiteral(((Member_expressionContext)_localctx).IDENTIFIER, false);
				                                                  ((Member_expressionContext)_localctx).result =  factory.createReadProperty(receiver, nestedAssignmentName); 
				}
				break;
			case T__33:
				{
				setState(231);
				match(T__33);
				 if (receiver == null) {
				                                                      receiver = factory.createRead(assignmentName);
				                                                  } 
				setState(233);
				((Member_expressionContext)_localctx).expression = expression();
				 nestedAssignmentName = ((Member_expressionContext)_localctx).expression.result;
				                                                  ((Member_expressionContext)_localctx).result =  factory.createReadProperty(receiver, nestedAssignmentName); 
				setState(235);
				match(T__34);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(242);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(239);
				((Member_expressionContext)_localctx).member_expression = member_expression(_localctx.result, receiver, nestedAssignmentName);
				 ((Member_expressionContext)_localctx).result =  ((Member_expressionContext)_localctx).member_expression.result; 
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

	public static final String _serializedATN =
		"\u0004\u0001)\u00f5\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0005\u0000\u001f\b"+
		"\u0000\n\u0000\f\u0000\"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001/\b\u0001\n\u0001\f\u00012\t\u0001\u0003"+
		"\u00014\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002?\b"+
		"\u0002\n\u0002\f\u0002B\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003Z\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0003\u0005m\b\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006v\b"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0081\b\u0007\n"+
		"\u0007\f\u0007\u0084\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0005\b\u008c\b\b\n\b\f\b\u008f\t\b\u0001\t\u0001\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0003\t\u0097\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0005\n\u009f\b\n\n\n\f\n\u00a2\t\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00aa\b\u000b"+
		"\n\u000b\f\u000b\u00ad\t\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0003\f\u00b5\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00cc\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005"+
		"\r\u00d7\b\r\n\r\f\r\u00da\t\r\u0003\r\u00dc\b\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00ee\b\r\u0001\r\u0001\r\u0001"+
		"\r\u0003\r\u00f3\b\r\u0001\r\u0000\u0000\u000e\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0003\u0001\u0000"+
		"\u0010\u0015\u0001\u0000\u0016\u0017\u0001\u0000\u0018\u0019\u0106\u0000"+
		"\u001c\u0001\u0000\u0000\u0000\u0002%\u0001\u0000\u0000\u0000\u00049\u0001"+
		"\u0000\u0000\u0000\u0006Y\u0001\u0000\u0000\u0000\b[\u0001\u0000\u0000"+
		"\u0000\nb\u0001\u0000\u0000\u0000\fp\u0001\u0000\u0000\u0000\u000ez\u0001"+
		"\u0000\u0000\u0000\u0010\u0085\u0001\u0000\u0000\u0000\u0012\u0090\u0001"+
		"\u0000\u0000\u0000\u0014\u0098\u0001\u0000\u0000\u0000\u0016\u00a3\u0001"+
		"\u0000\u0000\u0000\u0018\u00cb\u0001\u0000\u0000\u0000\u001a\u00cd\u0001"+
		"\u0000\u0000\u0000\u001c \u0003\u0002\u0001\u0000\u001d\u001f\u0003\u0002"+
		"\u0001\u0000\u001e\u001d\u0001\u0000\u0000\u0000\u001f\"\u0001\u0000\u0000"+
		"\u0000 \u001e\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000!#\u0001"+
		"\u0000\u0000\u0000\" \u0001\u0000\u0000\u0000#$\u0005\u0000\u0000\u0001"+
		"$\u0001\u0001\u0000\u0000\u0000%&\u0005\u0001\u0000\u0000&\'\u0005\'\u0000"+
		"\u0000\'(\u0005\u0002\u0000\u0000(3\u0006\u0001\uffff\uffff\u0000)*\u0005"+
		"\'\u0000\u0000*0\u0006\u0001\uffff\uffff\u0000+,\u0005\u0003\u0000\u0000"+
		",-\u0005\'\u0000\u0000-/\u0006\u0001\uffff\uffff\u0000.+\u0001\u0000\u0000"+
		"\u0000/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000001\u0001\u0000"+
		"\u0000\u000014\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u00003)\u0001"+
		"\u0000\u0000\u000034\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"56\u0005\u0004\u0000\u000067\u0003\u0004\u0002\u000078\u0006\u0001\uffff"+
		"\uffff\u00008\u0003\u0001\u0000\u0000\u00009:\u0006\u0002\uffff\uffff"+
		"\u0000:@\u0005\u0005\u0000\u0000;<\u0003\u0006\u0003\u0000<=\u0006\u0002"+
		"\uffff\uffff\u0000=?\u0001\u0000\u0000\u0000>;\u0001\u0000\u0000\u0000"+
		"?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000"+
		"\u0000AC\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000CD\u0005\u0006"+
		"\u0000\u0000DE\u0006\u0002\uffff\uffff\u0000E\u0005\u0001\u0000\u0000"+
		"\u0000FG\u0003\b\u0004\u0000GH\u0006\u0003\uffff\uffff\u0000HZ\u0001\u0000"+
		"\u0000\u0000IJ\u0005\u0007\u0000\u0000JK\u0006\u0003\uffff\uffff\u0000"+
		"KZ\u0005\b\u0000\u0000LM\u0005\t\u0000\u0000MN\u0006\u0003\uffff\uffff"+
		"\u0000NZ\u0005\b\u0000\u0000OP\u0003\n\u0005\u0000PQ\u0006\u0003\uffff"+
		"\uffff\u0000QZ\u0001\u0000\u0000\u0000RS\u0003\f\u0006\u0000ST\u0006\u0003"+
		"\uffff\uffff\u0000TZ\u0001\u0000\u0000\u0000UV\u0003\u000e\u0007\u0000"+
		"VW\u0005\b\u0000\u0000WX\u0006\u0003\uffff\uffff\u0000XZ\u0001\u0000\u0000"+
		"\u0000YF\u0001\u0000\u0000\u0000YI\u0001\u0000\u0000\u0000YL\u0001\u0000"+
		"\u0000\u0000YO\u0001\u0000\u0000\u0000YR\u0001\u0000\u0000\u0000YU\u0001"+
		"\u0000\u0000\u0000Z\u0007\u0001\u0000\u0000\u0000[\\\u0005\n\u0000\u0000"+
		"\\]\u0005\u0002\u0000\u0000]^\u0003\u000e\u0007\u0000^_\u0005\u0004\u0000"+
		"\u0000_`\u0003\u0004\u0002\u0000`a\u0006\u0004\uffff\uffff\u0000a\t\u0001"+
		"\u0000\u0000\u0000bc\u0005\u000b\u0000\u0000cd\u0005\u0002\u0000\u0000"+
		"de\u0003\u000e\u0007\u0000ef\u0005\u0004\u0000\u0000fg\u0003\u0004\u0002"+
		"\u0000gl\u0006\u0005\uffff\uffff\u0000hi\u0005\f\u0000\u0000ij\u0003\u0004"+
		"\u0002\u0000jk\u0006\u0005\uffff\uffff\u0000km\u0001\u0000\u0000\u0000"+
		"lh\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000"+
		"\u0000no\u0006\u0005\uffff\uffff\u0000o\u000b\u0001\u0000\u0000\u0000"+
		"pq\u0005\r\u0000\u0000qu\u0006\u0006\uffff\uffff\u0000rs\u0003\u000e\u0007"+
		"\u0000st\u0006\u0006\uffff\uffff\u0000tv\u0001\u0000\u0000\u0000ur\u0001"+
		"\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000"+
		"wx\u0006\u0006\uffff\uffff\u0000xy\u0005\b\u0000\u0000y\r\u0001\u0000"+
		"\u0000\u0000z{\u0003\u0010\b\u0000{\u0082\u0006\u0007\uffff\uffff\u0000"+
		"|}\u0005\u000e\u0000\u0000}~\u0003\u0010\b\u0000~\u007f\u0006\u0007\uffff"+
		"\uffff\u0000\u007f\u0081\u0001\u0000\u0000\u0000\u0080|\u0001\u0000\u0000"+
		"\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u000f\u0001\u0000\u0000"+
		"\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0003\u0012\t\u0000"+
		"\u0086\u008d\u0006\b\uffff\uffff\u0000\u0087\u0088\u0005\u000f\u0000\u0000"+
		"\u0088\u0089\u0003\u0012\t\u0000\u0089\u008a\u0006\b\uffff\uffff\u0000"+
		"\u008a\u008c\u0001\u0000\u0000\u0000\u008b\u0087\u0001\u0000\u0000\u0000"+
		"\u008c\u008f\u0001\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000"+
		"\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u0011\u0001\u0000\u0000\u0000"+
		"\u008f\u008d\u0001\u0000\u0000\u0000\u0090\u0091\u0003\u0014\n\u0000\u0091"+
		"\u0096\u0006\t\uffff\uffff\u0000\u0092\u0093\u0007\u0000\u0000\u0000\u0093"+
		"\u0094\u0003\u0014\n\u0000\u0094\u0095\u0006\t\uffff\uffff\u0000\u0095"+
		"\u0097\u0001\u0000\u0000\u0000\u0096\u0092\u0001\u0000\u0000\u0000\u0096"+
		"\u0097\u0001\u0000\u0000\u0000\u0097\u0013\u0001\u0000\u0000\u0000\u0098"+
		"\u0099\u0003\u0016\u000b\u0000\u0099\u00a0\u0006\n\uffff\uffff\u0000\u009a"+
		"\u009b\u0007\u0001\u0000\u0000\u009b\u009c\u0003\u0016\u000b\u0000\u009c"+
		"\u009d\u0006\n\uffff\uffff\u0000\u009d\u009f\u0001\u0000\u0000\u0000\u009e"+
		"\u009a\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000\u0000\u0000\u00a0"+
		"\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1"+
		"\u0015\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a4\u0003\u0018\f\u0000\u00a4\u00ab\u0006\u000b\uffff\uffff\u0000\u00a5"+
		"\u00a6\u0007\u0002\u0000\u0000\u00a6\u00a7\u0003\u0018\f\u0000\u00a7\u00a8"+
		"\u0006\u000b\uffff\uffff\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000\u00a9"+
		"\u00a5\u0001\u0000\u0000\u0000\u00aa\u00ad\u0001\u0000\u0000\u0000\u00ab"+
		"\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac"+
		"\u0017\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ae"+
		"\u00af\u0005\'\u0000\u0000\u00af\u00b4\u0006\f\uffff\uffff\u0000\u00b0"+
		"\u00b1\u0003\u001a\r\u0000\u00b1\u00b2\u0006\f\uffff\uffff\u0000\u00b2"+
		"\u00b5\u0001\u0000\u0000\u0000\u00b3\u00b5\u0006\f\uffff\uffff\u0000\u00b4"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b5"+
		"\u00cc\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005(\u0000\u0000\u00b7\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00b8\u00b9\u0005)\u0000\u0000\u00b9\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00ba\u00bb\u0005\u001a\u0000\u0000\u00bb\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00bc\u00bd\u0005\u001b\u0000\u0000\u00bd\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00be\u00bf\u0005\u001c\u0000\u0000\u00bf\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00c0\u00c1\u0005\u001d\u0000\u0000\u00c1\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00c2\u00c3\u0005\u001e\u0000\u0000\u00c3\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00c4\u00c5\u0005\u001f\u0000\u0000\u00c5\u00cc"+
		"\u0006\f\uffff\uffff\u0000\u00c6\u00c7\u0005\u0002\u0000\u0000\u00c7\u00c8"+
		"\u0003\u000e\u0007\u0000\u00c8\u00c9\u0005\u0004\u0000\u0000\u00c9\u00ca"+
		"\u0006\f\uffff\uffff\u0000\u00ca\u00cc\u0001\u0000\u0000\u0000\u00cb\u00ae"+
		"\u0001\u0000\u0000\u0000\u00cb\u00b6\u0001\u0000\u0000\u0000\u00cb\u00b8"+
		"\u0001\u0000\u0000\u0000\u00cb\u00ba\u0001\u0000\u0000\u0000\u00cb\u00bc"+
		"\u0001\u0000\u0000\u0000\u00cb\u00be\u0001\u0000\u0000\u0000\u00cb\u00c0"+
		"\u0001\u0000\u0000\u0000\u00cb\u00c2\u0001\u0000\u0000\u0000\u00cb\u00c4"+
		"\u0001\u0000\u0000\u0000\u00cb\u00c6\u0001\u0000\u0000\u0000\u00cc\u0019"+
		"\u0001\u0000\u0000\u0000\u00cd\u00ed\u0006\r\uffff\uffff\u0000\u00ce\u00cf"+
		"\u0005\u0002\u0000\u0000\u00cf\u00db\u0006\r\uffff\uffff\u0000\u00d0\u00d1"+
		"\u0003\u000e\u0007\u0000\u00d1\u00d8\u0006\r\uffff\uffff\u0000\u00d2\u00d3"+
		"\u0005\u0003\u0000\u0000\u00d3\u00d4\u0003\u000e\u0007\u0000\u00d4\u00d5"+
		"\u0006\r\uffff\uffff\u0000\u00d5\u00d7\u0001\u0000\u0000\u0000\u00d6\u00d2"+
		"\u0001\u0000\u0000\u0000\u00d7\u00da\u0001\u0000\u0000\u0000\u00d8\u00d6"+
		"\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000\u00d9\u00dc"+
		"\u0001\u0000\u0000\u0000\u00da\u00d8\u0001\u0000\u0000\u0000\u00db\u00d0"+
		"\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc\u00dd"+
		"\u0001\u0000\u0000\u0000\u00dd\u00de\u0005\u0004\u0000\u0000\u00de\u00ee"+
		"\u0006\r\uffff\uffff\u0000\u00df\u00e0\u0005 \u0000\u0000\u00e0\u00e1"+
		"\u0003\u000e\u0007\u0000\u00e1\u00e2\u0006\r\uffff\uffff\u0000\u00e2\u00ee"+
		"\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005!\u0000\u0000\u00e4\u00e5\u0006"+
		"\r\uffff\uffff\u0000\u00e5\u00e6\u0005\'\u0000\u0000\u00e6\u00ee\u0006"+
		"\r\uffff\uffff\u0000\u00e7\u00e8\u0005\"\u0000\u0000\u00e8\u00e9\u0006"+
		"\r\uffff\uffff\u0000\u00e9\u00ea\u0003\u000e\u0007\u0000\u00ea\u00eb\u0006"+
		"\r\uffff\uffff\u0000\u00eb\u00ec\u0005#\u0000\u0000\u00ec\u00ee\u0001"+
		"\u0000\u0000\u0000\u00ed\u00ce\u0001\u0000\u0000\u0000\u00ed\u00df\u0001"+
		"\u0000\u0000\u0000\u00ed\u00e3\u0001\u0000\u0000\u0000\u00ed\u00e7\u0001"+
		"\u0000\u0000\u0000\u00ee\u00f2\u0001\u0000\u0000\u0000\u00ef\u00f0\u0003"+
		"\u001a\r\u0000\u00f0\u00f1\u0006\r\uffff\uffff\u0000\u00f1\u00f3\u0001"+
		"\u0000\u0000\u0000\u00f2\u00ef\u0001\u0000\u0000\u0000\u00f2\u00f3\u0001"+
		"\u0000\u0000\u0000\u00f3\u001b\u0001\u0000\u0000\u0000\u0012 03@Ylu\u0082"+
		"\u008d\u0096\u00a0\u00ab\u00b4\u00cb\u00d8\u00db\u00ed\u00f2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}