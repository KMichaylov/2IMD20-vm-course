// Generated from /Users/danieleb/GoogleDrive/2IMD20/labs/lab_01_AST/lab01/src/ToyLang.g4 by ANTLR 4.12.0
package nl.tue.vmcourse.toy.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ToyLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ToyLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#toylanguage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToylanguage(ToyLangParser.ToylanguageContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(ToyLangParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ToyLangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ToyLangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(ToyLangParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(ToyLangParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(ToyLangParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ToyLangParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#logic_term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_term(ToyLangParser.Logic_termContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#logic_factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_factor(ToyLangParser.Logic_factorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(ToyLangParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(ToyLangParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(ToyLangParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyLangParser#member_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember_expression(ToyLangParser.Member_expressionContext ctx);
}