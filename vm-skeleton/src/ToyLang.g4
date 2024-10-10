/*
 * Copyright (c) 2012, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/*
 * The parser and lexer need to be generated using "mx create-sl-parser".
 */

grammar ToyLang;

@parser::header
{
// DO NOT MODIFY - generated from SimpleLanguage.g4 using "mx create-sl-parser"

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import interpreter.ast.ToyExpressionNode;
import interpreter.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.interpreter.ToySyntaxErrorException;
}

@lexer::header
{
// DO NOT MODIFY
}

@parser::members
{
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
/*
public static Map<TruffleString, RootCallTarget> parseSL(SLLanguage language, Source source) {
    SimpleLanguageLexer lexer = new SimpleLanguageLexer(CharStreams.fromString(source.getCharacters().toString()));
    SimpleLanguageParser parser = new SimpleLanguageParser(new CommonTokenStream(lexer));
    lexer.removeErrorListeners();
    parser.removeErrorListeners();
    BailoutErrorListener listener = new BailoutErrorListener(source);
    lexer.addErrorListener(listener);
    parser.addErrorListener(listener);
    parser.factory = new SLNodeFactory(language, source);
    parser.source = source;
    parser.simplelanguage();
    return parser.factory.getAllFunctions();
}*/
}

// parser




toylanguage
:
function function* EOF
;


function
:
'function'
IDENTIFIER
s='('
                                                { factory.startFunction($IDENTIFIER, $s); }
(
    IDENTIFIER                                  { factory.addFormalParameter($IDENTIFIER); }
    (
        ','
        IDENTIFIER                              { factory.addFormalParameter($IDENTIFIER); }
    )*
)?
')'
body=block[false]                               { factory.finishFunction($body.result); }
;



block [boolean inLoop] returns [ToyStatementNode result]
:                                               { factory.startBlock();
                                                  List<ToyStatementNode> body = new ArrayList<>(); }
s='{'
(
    statement[inLoop]                           { body.add($statement.result); }
)*
e='}'
                                                { $result = factory.finishBlock(body); }
;


statement [boolean inLoop] returns [ToyStatementNode result]
:
(
    while_statement                             { $result = $while_statement.result; }
|
    b='break'                                   { if (inLoop) { $result = factory.createBreak($b); } else { SemErr($b, "break used outside of loop"); } }
    ';'
|
    c='continue'                                { if (inLoop) { $result = factory.createContinue($c); } else { SemErr($c, "continue used outside of loop"); } }
    ';'
|
    if_statement[inLoop]                        { $result = $if_statement.result; }
|
    return_statement                            { $result = $return_statement.result; }
|
    expression ';'                              { $result = $expression.result; }
)
;


while_statement returns [ToyStatementNode result]
:
w='while'
'('
condition=expression
')'
body=block[true]                                { $result = factory.createWhile($w, $condition.result, $body.result); }
;


if_statement [boolean inLoop] returns [ToyStatementNode result]
:
i='if'
'('
condition=expression
')'
then=block[inLoop]                              { ToyStatementNode elsePart = null; }
(
    'else'
    block[inLoop]                               { elsePart = $block.result; }
)?                                              { $result = factory.createIf($i, $condition.result, $then.result, elsePart); }
;


return_statement returns [ToyStatementNode result]
:
r='return'                                      { ToyExpressionNode value = null; }
(
    expression                                  { value = $expression.result; }
)?                                              { $result = factory.createReturn($r, value); }
';'
;


expression returns [ToyExpressionNode result]
:
logic_term                                      { $result = $logic_term.result; }
(
    op='||'
    logic_term                                  { $result = factory.createBinary($op, $result, $logic_term.result); }
)*
;


logic_term returns [ToyExpressionNode result]
:
logic_factor                                    { $result = $logic_factor.result; }
(
    op='&&'
    logic_factor                                { $result = factory.createBinary($op, $result, $logic_factor.result); }
)*
;


logic_factor returns [ToyExpressionNode result]
:
arithmetic                                      { $result = $arithmetic.result; }
(
    op=('<' | '<=' | '>' | '>=' | '==' | '!=' )
    arithmetic                                  { $result = factory.createBinary($op, $result, $arithmetic.result); }
)?
;


arithmetic returns [ToyExpressionNode result]
:
term                                            { $result = $term.result; }
(
    op=('+' | '-')
    term                                        { $result = factory.createBinary($op, $result, $term.result); }
)*
;


term returns [ToyExpressionNode result]
:
factor                                          { $result = $factor.result; }
(
    op=('*' | '/')
    factor                                      { $result = factory.createBinary($op, $result, $factor.result); }
)*
;


factor returns [ToyExpressionNode result]
:
(
    IDENTIFIER                                  { ToyExpressionNode assignmentName = factory.createStringLiteral($IDENTIFIER, false); }
    (
        member_expression[null, null, assignmentName] { $result = $member_expression.result; }
    |
                                                { $result = factory.createRead(assignmentName); }
    )
|
    STRING_LITERAL                              { $result = factory.createStringLiteral($STRING_LITERAL, true); }
|
    NUMERIC_LITERAL                             { $result = factory.createNumericLiteral($NUMERIC_LITERAL); }
|
    d='universe'                                { $result = factory.createUniverse($d); }
|
    d='undef'                                { $result = factory.createUndef($d); }
|
    d='newObject'                                { $result = factory.createNewObject($d); }
|
    d='newArray'                                { $result = factory.createNewArray($d); }
|
    d='true'                                { $result = factory.createBooleanLiteral(true); }
|
    d='false'                                { $result = factory.createBooleanLiteral(false); }
|
    s='('
    expr=expression
    e=')'                                       { $result = factory.createParenExpression($expr.result, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
)
;


member_expression [ToyExpressionNode r, ToyExpressionNode assignmentReceiver, ToyExpressionNode assignmentName] returns [ToyExpressionNode result]
:                                               { ToyExpressionNode receiver = r;
                                                  ToyExpressionNode nestedAssignmentName = null; }
(
    '('                                         { List<ToyExpressionNode> parameters = new ArrayList<>();
                                                  if (receiver == null) {
                                                      receiver = factory.createRead(assignmentName);
                                                  } }
    (
        expression                              { parameters.add($expression.result); }
        (
            ','
            expression                          { parameters.add($expression.result); }
        )*
    )?
    e=')'
                                                { $result = factory.createCall(receiver, parameters, $e); }
|
    '='
    expression                                  { if (assignmentName == null) {
                                                      SemErr($expression.start, "invalid assignment target");
                                                  } else if (assignmentReceiver == null) {
                                                      $result = factory.createAssignment(assignmentName, $expression.result);
                                                  } else {
                                                      $result = factory.createWriteProperty(assignmentReceiver, assignmentName, $expression.result);
                                                  } }
|
    '.'                                         { if (receiver == null) {
                                                       receiver = factory.createRead(assignmentName);
                                                  } }
    IDENTIFIER
                                                { nestedAssignmentName = factory.createStringLiteral($IDENTIFIER, false);
                                                  $result = factory.createReadProperty(receiver, nestedAssignmentName); }
|
    '['                                         { if (receiver == null) {
                                                      receiver = factory.createRead(assignmentName);
                                                  } }
    expression
                                                { nestedAssignmentName = $expression.result;
                                                  $result = factory.createReadProperty(receiver, nestedAssignmentName); }
    ']'
)
(
    member_expression[$result, receiver, nestedAssignmentName] { $result = $member_expression.result; }
)?
;

// lexer

WS : [ \t\r\n\u000C]+ -> skip;
COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT : '//' ~[\r\n]* -> skip;

fragment LETTER : [A-Z] | [a-z] | '_' | '$';
fragment NON_ZERO_DIGIT : '-' | [1-9];
fragment DIGIT : [0-9];
fragment HEX_DIGIT : [0-9] | [a-f] | [A-F];
fragment OCT_DIGIT : [0-7];
fragment BINARY_DIGIT : '0' | '1';
fragment TAB : '\t';
fragment STRING_CHAR : ~('"' | '\r' | '\n');

IDENTIFIER : LETTER (LETTER | DIGIT)*;
STRING_LITERAL : '"' STRING_CHAR* '"';
NUMERIC_LITERAL : '0' | NON_ZERO_DIGIT DIGIT*;
