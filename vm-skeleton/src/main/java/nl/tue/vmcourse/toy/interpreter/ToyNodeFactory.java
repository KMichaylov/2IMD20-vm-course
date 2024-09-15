package nl.tue.vmcourse.toy.interpreter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.bci.*;
import nl.tue.vmcourse.toy.lang.FrameDescriptor;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import org.antlr.v4.runtime.Token;

public class ToyNodeFactory {

    // TODO this could be a config flag, not a constant.
    private static final boolean DUMP_AST = true;

    public ToyExpressionNode createMinExpression(ToyExpressionNode exp) {
        return new ToyUnaryMinNode(exp);
    }

    static class LexicalScope {
        protected final LexicalScope outer;
        protected final Map<String, Integer> locals;

        LexicalScope(LexicalScope outer) {
            this.outer = outer;
            this.locals = new HashMap<>();
        }

        public Integer find(String name) {
            Integer result = locals.get(name);
            if (result != null) {
                return result;
            } else if (outer != null) {
                return outer.find(name);
            } else {
                return null;
            }
        }
    }

    private final Map<String, RootCallTarget> allFunctions;

    /* State while parsing a function. */
    private final String sourceString;
    private int functionStartPos;
    private String functionName;
    private int functionBodyStartPos; // includes parameter list
    private int parameterCount;
    private FrameDescriptor.Builder frameDescriptorBuilder;
    private List<ToyStatementNode> methodNodes;

    /* State while parsing a block. */
    private LexicalScope lexicalScope;

    public ToyNodeFactory(String source) {
        this.allFunctions = new HashMap<>();
        this.sourceString = source;
    }

    public Map<String, RootCallTarget> getAllFunctions() {
        return allFunctions;
    }

    public void startFunction(Token nameToken, Token bodyStartToken) {
        assert functionStartPos == 0;
        assert functionName == null;
        assert functionBodyStartPos == 0;
        assert parameterCount == 0;
        assert frameDescriptorBuilder == null;
        assert lexicalScope == null;

        functionStartPos = nameToken.getStartIndex();
        functionName = asString(nameToken, false);
        functionBodyStartPos = bodyStartToken.getStartIndex();
        frameDescriptorBuilder = FrameDescriptor.newBuilder();
        methodNodes = new ArrayList<>();
        startBlock();
    }

    public void addFormalParameter(Token nameToken) {
        /*
         * Method parameters are assigned to local variables at the beginning of the method. This
         * ensures that accesses to parameters are specialized the same way as local variables are
         * specialized.
         */
        final ToyReadArgumentNode readArg = new ToyReadArgumentNode(parameterCount);

        ToyExpressionNode assignment = createAssignment(createStringLiteral(nameToken, false), readArg, parameterCount);
        methodNodes.add(assignment);
        parameterCount++;
    }

    public void finishFunction(ToyStatementNode bodyNode) {
        if (bodyNode == null) {
            // a state update that would otherwise be performed by finishBlock
            lexicalScope = lexicalScope.outer;
        } else {
            methodNodes.add(bodyNode);
            final ToyStatementNode methodBlock = finishBlock(methodNodes);
            assert lexicalScope == null : "Wrong scoping of blocks in parser";

            final ToyAbstractFunctionBody functionBodyNode = AstToBciAssembler.build(methodBlock);

            // TODO remove this println (otherwise all tests will fail...)
            if (DUMP_AST) {
                System.out.println("+++++");
                System.out.println("+++++ AST for " + functionName);
                System.out.println("+++++");
                System.out.println(methodBlock.toString());
                System.out.println("+++++");
            }

            final ToyRootNode rootNode = new ToyRootNode(frameDescriptorBuilder.build(), functionBodyNode, functionName);
            allFunctions.put(functionName, rootNode.getCallTarget());
        }

        functionStartPos = 0;
        functionName = null;
        functionBodyStartPos = 0;
        parameterCount = 0;
        frameDescriptorBuilder = null;
        lexicalScope = null;
    }

    public void startBlock() {
        lexicalScope = new LexicalScope(lexicalScope);
    }

    public ToyStatementNode finishBlock(List<ToyStatementNode> bodyNodes) {
        lexicalScope = lexicalScope.outer;

        if (containsNull(bodyNodes)) {
            return null;
        }

        List<ToyStatementNode> flattenedNodes = new ArrayList<>(bodyNodes.size());
        flattenBlocks(bodyNodes, flattenedNodes);

        return new ToyBlockNode(flattenedNodes.toArray(new ToyStatementNode[flattenedNodes.size()]));
    }

    private static boolean isHaltInCondition(ToyStatementNode statement) {
        return (statement instanceof ToyIfNode) || (statement instanceof ToyWhileNode);
    }

    private void flattenBlocks(Iterable<? extends ToyStatementNode> bodyNodes, List<ToyStatementNode> flattenedNodes) {
        for (ToyStatementNode n : bodyNodes) {
            if (n instanceof ToyBlockNode) {
                flattenBlocks(((ToyBlockNode) n).getStatements(), flattenedNodes);
            } else {
                flattenedNodes.add(n);
            }
        }
    }

    public ToyStatementNode createBreak(Token breakToken) {
        return new ToyBreakNode();
    }

    public ToyStatementNode createContinue(Token continueToken) {
        return new ToyContinueNode();
    }


    public ToyStatementNode createWhile(Token whileToken, ToyExpressionNode conditionNode, ToyStatementNode bodyNode) {
        if (conditionNode == null || bodyNode == null) {
            return null;
        }

        return new ToyWhileNode(conditionNode, bodyNode);
    }


    public ToyStatementNode createIf(Token ifToken, ToyExpressionNode conditionNode, ToyStatementNode thenPartNode, ToyStatementNode elsePartNode) {
        if (conditionNode == null || thenPartNode == null) {
            return null;
        }

        return new ToyIfNode(conditionNode, thenPartNode, elsePartNode);
    }

    public ToyStatementNode createReturn(Token t, ToyExpressionNode valueNode) {
        return new ToyReturnNode(valueNode);
    }

    public ToyExpressionNode createBinary(Token opToken, ToyExpressionNode leftNode, ToyExpressionNode rightNode) {
        if (leftNode == null || rightNode == null) {
            return null;
        }
        final ToyExpressionNode leftUnboxed = new ToyUnboxNode(leftNode);
        final ToyExpressionNode rightUnboxed = new ToyUnboxNode(rightNode);

        final ToyExpressionNode result;
        switch (opToken.getText()) {
            case "+":
                result = new ToyAddNode(leftUnboxed, rightUnboxed);
                break;
            case "*":
                result = new ToyMulNode(leftUnboxed, rightUnboxed);
                break;
            case "/":
                result = new ToyDivNode(leftUnboxed, rightUnboxed);
                break;
            case "-":
                result = new ToySubNode(leftUnboxed, rightUnboxed);
                break;
            case "<":
                result = new ToyLessThanNode(leftUnboxed, rightUnboxed);
                break;
            case "<=":
                result = new ToyLessOrEqualNode(leftUnboxed, rightUnboxed);
                break;
            case ">":
                result = new ToyLogicalNotNode(new ToyLessOrEqualNode(leftUnboxed, rightUnboxed));
                break;
            case ">=":
                result = new ToyLogicalNotNode(new ToyLessThanNode(leftUnboxed, rightUnboxed));
                break;
            case "==":
                result = new ToyEqualNode(leftUnboxed, rightUnboxed);
                break;
            case "!=":
                result = new ToyLogicalNotNode(new ToyEqualNode(leftUnboxed, rightUnboxed));
                break;
            case "&&":
                result = new ToyLogicalAndNode(leftUnboxed, rightUnboxed);
                break;
            case "||":
                result = new ToyLogicalOrNode(leftUnboxed, rightUnboxed);
                break;
            default:
                throw new RuntimeException("unexpected operation: " + opToken.getText());
        }

        return result;
    }

    public ToyExpressionNode createCall(ToyExpressionNode functionNode, List<ToyExpressionNode> parameterNodes, Token finalToken) {
        if (functionNode == null || containsNull(parameterNodes)) {
            return null;
        }

        final ToyExpressionNode result = new ToyInvokeNode(functionNode, parameterNodes.toArray(new ToyExpressionNode[parameterNodes.size()]));

        return result;
    }

    public ToyExpressionNode createAssignment(ToyExpressionNode nameNode, ToyExpressionNode valueNode) {
        return createAssignment(nameNode, valueNode, null);
    }

    public ToyExpressionNode createAssignment(ToyExpressionNode nameNode, ToyExpressionNode valueNode, Integer argumentIndex) {
        if (nameNode == null || valueNode == null) {
            return null;
        }

        String name = ((ToyStringLiteralNode) nameNode).getValue();

        Integer frameSlot = lexicalScope.find(name);
        boolean newVariable = false;
        if (frameSlot == null) {
            frameSlot = frameDescriptorBuilder.addSlot(name);
            lexicalScope.locals.put(name, frameSlot);
            newVariable = true;
        }

        return new ToyWriteLocalVariableNode(valueNode, frameSlot, nameNode, newVariable);
    }

    public ToyExpressionNode createRead(ToyExpressionNode nameNode) {
        if (nameNode == null) {
            return null;
        }

        String name = ((ToyStringLiteralNode) nameNode).getValue();
        final ToyExpressionNode result;
        final Integer frameToyot = lexicalScope.find(name);
        if (frameToyot != null) {
            /* Read of a local variable. */
            result = new ToyReadLocalVariableNode(frameToyot);
        } else {
            /* Read of a global name. In our language, the only global names are functions. */
            result = new ToyFunctionLiteralNode(name);
        }

        return result;
    }

    public ToyExpressionNode createStringLiteral(Token literalToken, boolean removeQuotes) {
        return new ToyStringLiteralNode(asString(literalToken, removeQuotes));
    }

    public ToyExpressionNode createUniverse(Token token) {
        return new ToyUniverseNode();
    }

    public ToyExpressionNode createNewObject(Token token) {
        return new ToyNewObjectNode();
    }

    public ToyExpressionNode createUndef(Token token) {
        return new ToyUndefNode();
    }

    public ToyExpressionNode createBooleanLiteral(boolean value) {
        return new ToyBooleanLiteralNode(value);
    }

    private String asString(Token literalToken, boolean removeQuotes) {
        if (removeQuotes) {
            /* Remove the trailing and ending " */
            assert literalToken.getText().length() >= 2
                    && literalToken.getText().startsWith("\"")
                    && literalToken.getText().endsWith("\"");
            String text = literalToken.getText();
            return text.substring(1, text.length() - 1);
        }
        return literalToken.getText();
    }

    public ToyExpressionNode createNumericLiteral(Token literalToken) {
        ToyExpressionNode result;
        try {
            /* Try if the literal is small enough to fit into a long value. */
            result = new ToyLongLiteralNode(Long.parseLong(literalToken.getText()));
        } catch (NumberFormatException ex) {
            /* Overflow of long value, so fall back to BigInteger. */
            result = new ToyBigIntegerLiteralNode(new BigInteger(literalToken.getText()));
        }

        return result;
    }

    public ToyExpressionNode createParenExpression(ToyExpressionNode expressionNode, int start, int length) {
        if (expressionNode == null) {
            return null;
        }

        return new ToyParenExpressionNode(expressionNode);
    }

    public ToyExpressionNode createReadProperty(ToyExpressionNode receiverNode, ToyExpressionNode nameNode) {
        if (receiverNode == null || nameNode == null) {
            return null;
        }

        return new ToyReadPropertyNode(receiverNode, nameNode);
    }

    public ToyExpressionNode createWriteProperty(ToyExpressionNode receiverNode, ToyExpressionNode nameNode, ToyExpressionNode valueNode) {
        if (receiverNode == null || nameNode == null || valueNode == null) {
            return null;
        }

        return new ToyWritePropertyNode(receiverNode, nameNode, valueNode);
    }

    private static boolean containsNull(List<?> list) {
        for (Object e : list) {
            if (e == null) {
                return true;
            }
        }
        return false;
    }

}