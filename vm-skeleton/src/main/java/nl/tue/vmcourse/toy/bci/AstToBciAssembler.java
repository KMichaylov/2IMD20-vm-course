package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNode;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class AstToBciAssembler {

    private static boolean isArgument = false;

    // Todo add as an optimization the ability to scan the file to see if there is any stacktrace in the source.
    private static final Map<String, Object> stackTraceElements = new LinkedHashMap<>();
    private static final Map<String, Opcode> BUILTIN_FUNCTIONS = Map.ofEntries(
            Map.entry("println", Opcode.OP_PRINT),
            Map.entry("typeOf", Opcode.OP_TYPEOF),
            Map.entry("isInstance", Opcode.OP_IS_INSTANCE),
            Map.entry("nanoTime", Opcode.OP_NANO_TIME),
            Map.entry("eval", Opcode.OP_EVAL),
            Map.entry("defineFunction", Opcode.OP_DEFINE_FUNCTION),
            Map.entry("getSize", Opcode.OP_GET_SIZE),
            Map.entry("stacktrace", Opcode.OP_STACKTRACE),
            Map.entry("hasSize", Opcode.OP_HAS_SIZE),
            Map.entry("subString", Opcode.OP_SUB_STRING),
            Map.entry("hasProperty", Opcode.OP_HAS_PROPERTY),
            Map.entry("deleteProperty", Opcode.OP_DELETE_PROPERTY),
            Map.entry("helloEqualsWorld", Opcode.OP_HELLO_EQUALS_WORLD)
    );

    /**
     * We send the code to the ToyBciLoop to execute the bytecode commands.
     *
     * @param methodBlock the AST for the whole method that should be parsed
     * @return the result of the execution of bytecode commands
     */
    public static ToyAbstractFunctionBody build(ToyStatementNode methodBlock) {
        Bytecode bytecode = compileAst(methodBlock);
        // TODO code is one argument; depending in impl other arguments might be needed (e.g., constant pool?)
        return new ToyBciLoop(bytecode, stackTraceElements);
    }

    /**
     * Here return the answer converted from AST to bytecode.
     *
     * @param methodBlock
     * @return
     */
    private static Bytecode compileAst(ToyStatementNode methodBlock) {
        // TODO should explore AST and produce BC instructions.
        Bytecode bytecode = new Bytecode();
        generateBytecode(methodBlock, bytecode);
        return bytecode;
    }

    /**
     * Here, we essentially recursively traverse the tree and convert the commands from AST to the custom bytecode format
     * we defined. The idea mainly comes from the lecture slides.
     *
     * @param node     The node for which we traverse the tree
     * @param bytecode The bytecode placeholder
     */
    // TODO: Refactor the whole tree. Continue with it even later.
    private static void generateBytecode(ToyNode node, Bytecode bytecode) {
        if (node instanceof ToyBlockNode) {
            ToyBlockNode blockNode = (ToyBlockNode) node;
            for (ToyStatementNode statement : blockNode.getStatements()) {
                generateBytecode(statement, bytecode);
            }
        } else if (node instanceof ToyWriteLocalVariableNode) {
            ToyWriteLocalVariableNode writeNode = (ToyWriteLocalVariableNode) node;
            generateBytecode(writeNode.getValueNode(), bytecode);

            String variableName = ((ToyStringLiteralNode) writeNode.getNameNode()).getValue();
            if ("null".equals(variableName)) {
                variableName = null;
            }

            if (writeNode.getValueNode() instanceof ToyReadArgumentNode) {
                return;
            }
            stackTraceElements.put(variableName, null);

            bytecode.addVariableInstruction(
                    Opcode.OP_STORE,
                    0,
                    variableName,
                    writeNode.getFrameSlot(),
                    writeNode.isNewVariable()
            );
        } else if (node instanceof ToyReadLocalVariableNode) {
            ToyReadLocalVariableNode readNode = (ToyReadLocalVariableNode) node;
            bytecode.addVariableInstruction(
                    Opcode.OP_LOAD,
                    readNode.getFrameSlot(),
                    null,
                    readNode.getFrameSlot(),
                    false
            );
        } else if (node instanceof ToyReadArgumentNode) {
            ToyReadArgumentNode readArgumentNode = (ToyReadArgumentNode) node;
            int parameterCount = readArgumentNode.getParameterCount();
            bytecode.addVariableInstruction(Opcode.OP_READ_ARGUMENT, parameterCount, null, parameterCount, false);
        }

        // For the comparisons we use the provided AST classes and in case we need > or >=, we just negate the results
        else if (node instanceof ToyEqualNode) {
            ToyEqualNode equalNode = (ToyEqualNode) node;
            comparisonNodeHelper(equalNode.getLeftUnboxed(), equalNode.getRightUnboxed(), Opcode.OP_COMPARE, 0, bytecode);
        } else if (node instanceof ToyLessThanNode) {
            ToyLessThanNode lessThanNode = (ToyLessThanNode) node;
            comparisonNodeHelper(lessThanNode.getLeftUnboxed(), lessThanNode.getRightUnboxed(), Opcode.OP_COMPARE, 2, bytecode);
        } else if (node instanceof ToyLessOrEqualNode) {
            ToyLessOrEqualNode lessOrEqualNode = (ToyLessOrEqualNode) node;
            comparisonNodeHelper(lessOrEqualNode.getLeftUnboxed(), lessOrEqualNode.getRightUnboxed(), Opcode.OP_COMPARE, 3, bytecode);
        } else if (node instanceof ToyLogicalNotNode) {
            ToyLogicalNotNode toyLogicalNotNode = (ToyLogicalNotNode) node;
            generateBytecode(toyLogicalNotNode.getToyLessOrEqualNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_NOT, 0);
        } else if (node instanceof ToyStringLiteralNode) {
            ToyStringLiteralNode stringLiteralNode = (ToyStringLiteralNode) node;
            literalNodeHelper(stringLiteralNode.getValue(), Opcode.OP_LITERAL_STRING, bytecode);
        } else if (node instanceof ToyLongLiteralNode) {
            ToyLongLiteralNode literalNode = (ToyLongLiteralNode) node;
            long value = literalNode.getValue();
            final long LONG_UPPERBOUND = 2147483647;
            if (value < LONG_UPPERBOUND) {
                literalNodeHelper(literalNode.getValue(), Opcode.OP_LITERAL_LONG, bytecode);

            } else {
                literalNodeHelper(BigInteger.valueOf(literalNode.getValue()), Opcode.OP_LITERAL_BIGINT, bytecode);
            }

        } else if (node instanceof ToyBooleanLiteralNode) {
            ToyBooleanLiteralNode booleanLiteralNode = (ToyBooleanLiteralNode) node;
            literalNodeHelper(booleanLiteralNode.isValue(), Opcode.OP_LITERAL_BOOLEAN, bytecode);

        } else if (node instanceof ToyBigIntegerLiteralNode) {
            ToyBigIntegerLiteralNode bigIntegerLiteralNode = (ToyBigIntegerLiteralNode) node;
            literalNodeHelper(bigIntegerLiteralNode.getBigInteger(), Opcode.OP_LITERAL_BIGINT, bytecode);

        } else if (node instanceof ToyAddNode) {
            ToyAddNode addNode = (ToyAddNode) node;
            binaryInstructionGenerator(addNode.getLeftUnboxed(), addNode.getRightUnboxed(), Opcode.OP_ADD, bytecode, 0);
        } else if (node instanceof ToySubNode) {
            ToySubNode subNode = (ToySubNode) node;
            binaryInstructionGenerator(subNode.getLeftUnboxed(), subNode.getRightUnboxed(), Opcode.OP_SUB, bytecode, 0);
        } else if (node instanceof ToyDivNode) {
            ToyDivNode divNode = (ToyDivNode) node;
            binaryInstructionGenerator(divNode.getLeftUnboxed(), divNode.getRightUnboxed(), Opcode.OP_DIV, bytecode, 0);
        } else if (node instanceof ToyMulNode) {
            ToyMulNode mulNode = (ToyMulNode) node;
            binaryInstructionGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode, 0);
        } else if (node instanceof ToyLogicalAndNode) {
            ToyLogicalAndNode logicalAndNode = (ToyLogicalAndNode) node;
            binaryInstructionFixerForBooleans(logicalAndNode.getLeftUnboxed(), logicalAndNode.getRightUnboxed(), Opcode.OP_JUMP_IF_FALSE_BOOLEAN, Opcode.OP_LOGICAL_AND, bytecode, 0);
        } else if (node instanceof ToyLogicalOrNode) {
            ToyLogicalOrNode logicalOrNode = (ToyLogicalOrNode) node;
            binaryInstructionFixerForBooleans(logicalOrNode.getLeftUnboxed(), logicalOrNode.getRightUnboxed(), Opcode.OP_JUMP_IF_TRUE_BOOLEAN, Opcode.OP_LOGICAL_OR, bytecode, 0);
        } else if (node instanceof ToyContinueNode) {
            int continueJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
            bytecode.addContinueJump(continueJumpIndex);
        } else if (node instanceof ToyBreakNode) {
            int breakJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
            bytecode.addBreakJump(breakJumpIndex);
        } else if (node instanceof ToyParenExpressionNode) {
            ToyParenExpressionNode parenExpressionNode = (ToyParenExpressionNode) node;
            generateBytecode(parenExpressionNode.getExpressionNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_NOP, 0);
        }

//            TODO: Redesign and think of other approaches regarding where to store variables. Main priority for today
        else if (node instanceof ToyInvokeNode) {
            ToyInvokeNode invokeNode = (ToyInvokeNode) node;
            if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode) {
                ToyFunctionLiteralNode functionNode = (ToyFunctionLiteralNode) invokeNode.getFunctionNode();
                String functionName = functionNode.getName();
                // TODO This is for support of objects
                if (functionName.equals("new")) {
                    bytecode.addInstruction(Opcode.OP_NEW, 0);
                    // WE do not interpret this command as a function call, thus we exit the invoke method.
                    return;
                }
                if (!isBuiltInFunction(functionName)) {
                    // Here, we add the function name, so that the function can be executed.
                    int functionNameIndex = bytecode.addToConstantPool(functionNode.getName());
                    bytecode.addInstruction(Opcode.OP_FUNCTION_NAME, functionNameIndex);
                    stackTraceElements.put(functionName, null);

                }
            }
            for (ToyExpressionNode expression : invokeNode.getToyExpressionNodes()) {
                isArgument = true;
                generateBytecode(expression, bytecode);
            }
            if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode) {
                ToyFunctionLiteralNode functionNode = (ToyFunctionLiteralNode) invokeNode.getFunctionNode();
                String functionName = functionNode.getName();
                addFunctionToBytecode(bytecode, invokeNode, functionName);
            } else if (invokeNode.getFunctionNode() instanceof ToyReadLocalVariableNode) {
                bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
            } else if (invokeNode.getFunctionNode() instanceof ToyReadPropertyNode) {
                ToyReadPropertyNode readPropertyNode = (ToyReadPropertyNode) invokeNode.getFunctionNode();
                generateBytecode(readPropertyNode.getReceiverNode(), bytecode);
                Object propertyName = null;
                if (readPropertyNode.getNameNode() instanceof ToyStringLiteralNode) {
                    propertyName = ((ToyStringLiteralNode) readPropertyNode.getNameNode()).getValue();
                } else if (readPropertyNode.getNameNode() instanceof ToyLongLiteralNode) {
                    propertyName = ((ToyLongLiteralNode) readPropertyNode.getNameNode()).getValue();
                }
                int propertyIndex = bytecode.addToConstantPool(propertyName);
                generateBytecode(readPropertyNode.getNameNode(), bytecode);
                bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
            }

        } else if (node instanceof ToyReadPropertyNode) {
            ToyReadPropertyNode readPropertyNode = (ToyReadPropertyNode) node;
            readObjectProperty(readPropertyNode, bytecode);
        } else if (node instanceof ToyWritePropertyNode) {
            ToyWritePropertyNode writePropertyNode = (ToyWritePropertyNode) node;
            writeObjectProperty(writePropertyNode, bytecode);
        } else if (node instanceof ToyFunctionLiteralNode) {
            ToyFunctionLiteralNode functionLiteralNode = (ToyFunctionLiteralNode) node;
            String functionName = functionLiteralNode.getName();
            if (!isBuiltInFunctionForTypeChecking(functionName)) {
                literalNodeHelper(functionLiteralNode.getName(), Opcode.OP_FUNCTION_NAME, bytecode);

                // If we do not pass the literal as an argument, we do not need to call it.
                if (!isArgument)
                    bytecode.addInstruction(Opcode.OP_CALL, 0);
            }

            if (isArgument && isBuiltInFunctionForTypeChecking(functionName)) {
                literalNodeHelper(functionLiteralNode.getName(), Opcode.OP_BUILTIN, bytecode);
            }
        } else if (node instanceof ToyIfNode) {
            ToyIfNode ifNode = (ToyIfNode) node;
            generateBytecode(ifNode.getConditionNode(), bytecode);

            int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1);

            generateBytecode(ifNode.getThenPartNode(), bytecode);

            int jumpToEndLocation = -1;
            if (ifNode.getElsePartNode() != null) {
                jumpToEndLocation = bytecode.addInstruction(Opcode.OP_JUMP, -1);
            }

            int elseOrEndLocation = bytecode.getSize();
            bytecode.updateInstruction(jumpIfFalseLocation, elseOrEndLocation - jumpIfFalseLocation - 1);

            if (ifNode.getElsePartNode() != null) {
                generateBytecode(ifNode.getElsePartNode(), bytecode);
                int endLocation = bytecode.getSize();
                bytecode.updateInstruction(jumpToEndLocation, endLocation - jumpToEndLocation - 1);
            }
        } else if (node instanceof ToyWhileNode) {
            ToyWhileNode whileNode = (ToyWhileNode) node;
            int loopStart = bytecode.getSize();

            generateBytecode(whileNode.getConditionNode(), bytecode);

            int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1);

            generateBytecode(whileNode.getBodyNode(), bytecode);

            bytecode.addInstruction(Opcode.OP_JUMP, loopStart - bytecode.getSize() - 1);

            int loopEnd = bytecode.getSize();

            bytecode.updateInstruction(jumpIfFalseLocation, loopEnd - jumpIfFalseLocation - 1);

            bytecode.updateContinueJumps(loopStart);
            bytecode.updateBreakJumps(loopEnd);
        } else if (node instanceof ToyReturnNode) {
            ToyReturnNode returnNode = (ToyReturnNode) node;
            generateBytecode(returnNode.getValueNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_RETURN, 0);
        } else if (node instanceof ToyUnboxNode) {
            ToyUnboxNode unboxNode = (ToyUnboxNode) node;
            generateBytecode(unboxNode.getLeftNode(), bytecode);
        }


    }

    private static void addFunctionToBytecode(Bytecode bytecode, ToyInvokeNode invokeNode, String functionName) {
        switch (functionName) {
            case "println" -> bytecode.addInstruction(Opcode.OP_PRINT, 0);
            case "typeOf" -> bytecode.addInstruction(Opcode.OP_TYPEOF, 0);
            case "isInstance" -> bytecode.addInstruction(Opcode.OP_IS_INSTANCE, 0);
            case "nanoTime" -> bytecode.addInstruction(Opcode.OP_NANO_TIME, 0);
            case "eval" -> {
                ToyExpressionNode codeData = invokeNode.getToyExpressionNodes()[1];
                generateBytecode(codeData, bytecode);
                bytecode.addInstruction(Opcode.OP_EVAL, 0);
            }
            case "defineFunction" -> {
                if (invokeNode.getToyExpressionNodes().length >= 1) {
                    ToyExpressionNode codeData = invokeNode.getToyExpressionNodes()[0];
                    generateBytecode(codeData, bytecode);
                    bytecode.addInstruction(Opcode.OP_DEFINE_FUNCTION, 0);
                } else {
                    bytecode.addInstruction(Opcode.OP_DEFINE_FUNCTION, 0);
                }
            }
            case "getSize" -> {
                bytecode.addInstruction(Opcode.OP_GET_SIZE, 0);
            }
            case "stacktrace" -> bytecode.addInstruction(Opcode.OP_STACKTRACE, 0);
            case "hasSize" -> bytecode.addInstruction(Opcode.OP_HAS_SIZE, 0);
            case "subString" -> bytecode.addInstruction(Opcode.OP_SUB_STRING, 0);
            case "hasProperty" -> {
                generateBytecode(invokeNode.getToyExpressionNodes()[0], bytecode);
                generateBytecode(invokeNode.getToyExpressionNodes()[1], bytecode);
                bytecode.addInstruction(Opcode.OP_HAS_PROPERTY, 0);
            }
            case "deleteProperty" -> {
                generateBytecode(invokeNode.getToyExpressionNodes()[0], bytecode);
                generateBytecode(invokeNode.getToyExpressionNodes()[1], bytecode);
                bytecode.addInstruction(Opcode.OP_DELETE_PROPERTY, 0);
            }
            case "helloEqualsWorld" -> bytecode.addInstruction(Opcode.OP_HELLO_EQUALS_WORLD, 0);
            // The operand is the number of arguments
            default -> bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
        }
    }

    /**
     * The following is a helper method to generate bytecode for binary instructions such as + - * /, etc.
     *
     * @param leftNode  the left node
     * @param rightNode the right node
     * @param opcode    the corresponding opcode of the operation
     * @param bytecode  the bytecode array where bytecode instructions are added
     * @param operand   0 for number arithmetic 1 for string arithmetic
     */
    private static void binaryInstructionGenerator(ToyNode leftNode, ToyNode rightNode, Opcode opcode, Bytecode bytecode, int operand) {
        generateBytecode(leftNode, bytecode);
        generateBytecode(rightNode, bytecode);
        bytecode.addInstruction(opcode, operand);
    }


    /**
     * The following is a helper method to generate bytecode for boolean operations. It uses the short-circuit evaluation.
     *
     * @param leftNode              the left node
     * @param rightNode             the right node
     * @param opcodeJumpInstruction the corresponding opcode of the operation (JUMP_IF_TRUE or JUMP_IF_FALSE)
     * @param operation             the corresponding opcode of the operation (LOGICAL_AND or LOGICAL_OR)
     * @param bytecode              the bytecode array where bytecode instructions are added
     * @param operand               0 for number arithmetic 1 for string arithmetic
     */
    private static void binaryInstructionFixerForBooleans(ToyNode leftNode, ToyNode rightNode, Opcode opcodeJumpInstruction, Opcode operation, Bytecode bytecode, int operand) {
        generateBytecode(leftNode, bytecode);
        int jumpIfTrueLocation = bytecode.addInstruction(opcodeJumpInstruction, -1);
        generateBytecode(rightNode, bytecode);
        bytecode.updateInstruction(jumpIfTrueLocation, bytecode.getSize() - jumpIfTrueLocation - 1);
        bytecode.addInstruction(operation, 0);
    }

    /**
     * A helper method to decide if the current function is a built-in one.
     *
     * @param functionName the name of the function
     * @return true if function is built-in, false otherwise
     */
    private static boolean isBuiltInFunction(String functionName) {
        return BUILTIN_FUNCTIONS.containsKey(functionName);
    }

    /**
     * A helper method to decide if the current function is a built-in one and use this for the type of operator
     *
     * @param functionName the name of the function
     * @return true if function is built-in, false otherwise
     */
    private static boolean isBuiltInFunctionForTypeChecking(String functionName) {
        boolean result;
        switch (functionName) {
            case "println", "typeOf", "isInstance", "nanoTime", "eval", "defineFunction", "getSize", "stacktrace",
                 "new", "exit",
                 "hasSize", "subString", "hasProperty", "deleteProperty", "helloEqualsWorld" -> result = true;
            default -> result = false;
        }
        return result;
    }


    /**
     * A helper method to add a literal node to the bytecode. Could add all 4 types of literals.
     *
     * @param value    value to be added to the constant pool
     * @param opcode   of the corresponding literal
     * @param bytecode the bytecode array where the instruction will be added
     */
    private static void literalNodeHelper(Object value, Opcode opcode, Bytecode bytecode) {
        int indexOfAddedElement = bytecode.addToConstantPool(value);
        bytecode.addInstruction(opcode, indexOfAddedElement);
    }


    /**
     * A helper method to generate bytecode for the comparison nodes.
     *
     * @param leftNode  the left node element
     * @param rightNode the right node element
     * @param opcode    the opcode for the operation
     * @param operand   the number signifying the exact type of comparison
     * @param bytecode  the bytecode array where the instruction will be added
     */
    private static void comparisonNodeHelper(ToyNode leftNode, ToyNode rightNode, Opcode opcode, int operand, Bytecode bytecode) {
        generateBytecode(leftNode, bytecode);
        generateBytecode(rightNode, bytecode);
        bytecode.addInstruction(opcode, operand);
    }


    /**
     * The helper method is used to read the object property.
     *
     * @param readPropertyNode the node containing information about the property that will be read
     * @param bytecode         array where all instructions are stored
     */
    private static void readObjectProperty(ToyReadPropertyNode readPropertyNode, Bytecode bytecode) {
        generateBytecode(readPropertyNode.getReceiverNode(), bytecode);
        if (readPropertyNode.getNameNode() instanceof ToyStringLiteralNode) {
            String propertyName = ((ToyStringLiteralNode) readPropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyName);
            generateBytecode(readPropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
        } else if (readPropertyNode.getNameNode() instanceof ToyLongLiteralNode) {
            Long propertyArrayValue = ((ToyLongLiteralNode) readPropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyArrayValue);
            generateBytecode(readPropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
        } else {
            generateBytecode(readPropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, 0);
        }
    }


    /**
     * The helper method is used to write the object property.
     *
     * @param writePropertyNode the node containing information about the property
     * @param bytecode          array where all instructions are stored
     */
    private static void writeObjectProperty(ToyWritePropertyNode writePropertyNode, Bytecode bytecode) {
        isArgument = false;
        generateBytecode(writePropertyNode.getReceiverNode(), bytecode);
        generateBytecode(writePropertyNode.getValueNode(), bytecode);
        if (writePropertyNode.getNameNode() instanceof ToyStringLiteralNode) {
            String propertyName = ((ToyStringLiteralNode) writePropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyName);
            generateBytecode(writePropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, propertyIndex);
        } else if (writePropertyNode.getNameNode() instanceof ToyLongLiteralNode) {
            Long propertyArrayValue = ((ToyLongLiteralNode) writePropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyArrayValue);
            generateBytecode(writePropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, propertyIndex);
        } else {
            generateBytecode(writePropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, 0);
        }
    }

}
