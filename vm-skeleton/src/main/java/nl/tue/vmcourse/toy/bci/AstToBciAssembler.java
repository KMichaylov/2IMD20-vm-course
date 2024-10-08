package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNode;

import java.math.BigInteger;
import java.util.Map;

public class AstToBciAssembler {

    private static boolean isArgument = false;

    private static final Map<String, Opcode> BUILTIN_FUNCTIONS = Map.of(
            "println", Opcode.OP_PRINT,
            "typeOf", Opcode.OP_TYPEOF,
            "isInstance", Opcode.OP_IS_INSTANCE,
            "nanoTime", Opcode.OP_NANO_TIME,
            "eval", Opcode.OP_EVAL,
            "getSize", Opcode.OP_GET_SIZE,
            "stacktrace", Opcode.OP_STACKTRACE
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
        return new ToyBciLoop(bytecode);
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
        if (node instanceof ToyBlockNode blockNode) {
            for (ToyStatementNode statement : blockNode.getStatements()) {
                generateBytecode(statement, bytecode);
            }
        } else if (node instanceof ToyWriteLocalVariableNode writeNode) {
            generateBytecode(writeNode.getValueNode(), bytecode);

            String variableName = ((ToyStringLiteralNode) writeNode.getNameNode()).getValue();
            if ("null".equals(variableName)) {
                variableName = null;
            }

            if (writeNode.getValueNode() instanceof ToyReadArgumentNode) {
                return;
            }

            bytecode.addVariableInstruction(
                    Opcode.OP_STORE,
                    0,
                    variableName,
                    writeNode.getFrameSlot(),
                    writeNode.isNewVariable()
            );
        } else if (node instanceof ToyReadLocalVariableNode readNode) {
            bytecode.addVariableInstruction(
                    Opcode.OP_LOAD,
                    readNode.getFrameSlot(),
                    null,
                    readNode.getFrameSlot(),
                    false
            );
        } else if (node instanceof ToyReadArgumentNode readArgumentNode) {
            int parameterCount = readArgumentNode.getParameterCount();
            bytecode.addVariableInstruction(Opcode.OP_LOAD, parameterCount, null, parameterCount, false);
        }

        // For the comparisons we use the provided AST classes and in case we need > or >=, we just negate the results
        else if (node instanceof ToyEqualNode equalNode) {
            comparisonNodeHelper(equalNode.getLeftUnboxed(), equalNode.getRightUnboxed(), Opcode.OP_COMPARE, 0, bytecode);
        } else if (node instanceof ToyLessThanNode lessThanNode) {
            comparisonNodeHelper(lessThanNode.getLeftUnboxed(), lessThanNode.getRightUnboxed(), Opcode.OP_COMPARE, 2, bytecode);
        } else if (node instanceof ToyLessOrEqualNode lessOrEqualNode) {
            comparisonNodeHelper(lessOrEqualNode.getLeftUnboxed(), lessOrEqualNode.getRightUnboxed(), Opcode.OP_COMPARE, 3, bytecode);
        } else if (node instanceof ToyLogicalNotNode toyLogicalNotNode) {
            generateBytecode(toyLogicalNotNode.getToyLessOrEqualNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_NOT, 0);
        } else if (node instanceof ToyStringLiteralNode stringLiteralNode) {
            literalNodeHelper(stringLiteralNode.getValue(), Opcode.OP_LITERAL_STRING, bytecode);
        } else if (node instanceof ToyLongLiteralNode literalNode) {
            long value = literalNode.getValue();
            final long LONG_UPPERBOUND = 2147483647;
            if (value < LONG_UPPERBOUND) {
                literalNodeHelper(literalNode.getValue(), Opcode.OP_LITERAL_LONG, bytecode);

            } else {
                literalNodeHelper(BigInteger.valueOf(literalNode.getValue()), Opcode.OP_LITERAL_BIGINT, bytecode);
            }

        } else if (node instanceof ToyBooleanLiteralNode booleanLiteralNode) {
            literalNodeHelper(booleanLiteralNode.isValue(), Opcode.OP_LITERAL_BOOLEAN, bytecode);

        } else if (node instanceof ToyBigIntegerLiteralNode bigIntegerLiteralNode) {
            literalNodeHelper(bigIntegerLiteralNode.getBigInteger(), Opcode.OP_LITERAL_BIGINT, bytecode);

        } else if (node instanceof ToyAddNode addNode)
            binaryInstructionGenerator(addNode.getLeftUnboxed(), addNode.getRightUnboxed(), Opcode.OP_ADD, bytecode, 0);
        else if (node instanceof ToySubNode subNode)
            binaryInstructionGenerator(subNode.getLeftUnboxed(), subNode.getRightUnboxed(), Opcode.OP_SUB, bytecode, 0);
        else if (node instanceof ToyDivNode divNode)
            binaryInstructionGenerator(divNode.getLeftUnboxed(), divNode.getRightUnboxed(), Opcode.OP_DIV, bytecode, 0);
        else if (node instanceof ToyMulNode mulNode)
            binaryInstructionGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode, 0);
        else if (node instanceof ToyLogicalAndNode logicalAndNode) {
            binaryInstructionFixerForBooleans(logicalAndNode.getLeftUnboxed(), logicalAndNode.getRightUnboxed(), Opcode.OP_JUMP_IF_FALSE, Opcode.OP_LOGICAL_AND, bytecode, 0);
        } else if (node instanceof ToyLogicalOrNode logicalOrNode) {
            binaryInstructionFixerForBooleans(logicalOrNode.getLeftUnboxed(), logicalOrNode.getRightUnboxed(), Opcode.OP_JUMP_IF_TRUE, Opcode.OP_LOGICAL_OR, bytecode, 0);
        } else if (node instanceof ToyContinueNode) {
            int continueJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
            bytecode.addContinueJump(continueJumpIndex);
        } else if (node instanceof ToyBreakNode) {
            int breakJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
            bytecode.addBreakJump(breakJumpIndex);
        } else if (node instanceof ToyParenExpressionNode parenExpressionNode) {
            generateBytecode(parenExpressionNode.getExpressionNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_NOP, 0);
        }

//            TODO: Redesign and think of other approaches regarding where to store variables. Main priority for today
        else if (node instanceof ToyInvokeNode invokeNode) {
            if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode functionNode) {
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
                }
            }
            for (ToyExpressionNode expression : invokeNode.getToyExpressionNodes()) {
                isArgument = true;
                generateBytecode(expression, bytecode);
            }
            if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode functionNode) {
                String functionName = functionNode.getName();
                addFunctionToBytecode(bytecode, invokeNode, functionName);
            } else if (invokeNode.getFunctionNode() instanceof ToyReadLocalVariableNode readLocalVariableNode) {
                bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
            } else if (invokeNode.getFunctionNode() instanceof ToyReadPropertyNode readPropertyNode) {
                generateBytecode(readPropertyNode.getReceiverNode(), bytecode);
                Object propertyName = ((ToyStringLiteralNode) readPropertyNode.getNameNode()).getValue();
                int propertyIndex = bytecode.addToConstantPool(propertyName);
                bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
            }

        } else if (node instanceof ToyReadPropertyNode readPropertyNode) {
            readObjectProperty(readPropertyNode, bytecode);
        } else if (node instanceof ToyWritePropertyNode writePropertyNode) {
            writeObjectProperty(writePropertyNode, bytecode);
        } else if (node instanceof ToyFunctionLiteralNode functionLiteralNode) {
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
        } else if (node instanceof ToyIfNode ifNode) {
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
        } else if (node instanceof ToyWhileNode whileNode) {
            int loopStart = bytecode.getSize();

            generateBytecode(whileNode.getConditionNode(), bytecode);

            int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1);

            generateBytecode(whileNode.getBodyNode(), bytecode);

            bytecode.addInstruction(Opcode.OP_JUMP, loopStart - bytecode.getSize() - 1);

            int loopEnd = bytecode.getSize();

            bytecode.updateInstruction(jumpIfFalseLocation, loopEnd - jumpIfFalseLocation - 1);

            bytecode.updateContinueJumps(loopStart);
            bytecode.updateBreakJumps(loopEnd);
        } else if (node instanceof ToyReturnNode returnNode) {
            generateBytecode(returnNode.getValueNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_RETURN, 0);
        } else if (node instanceof ToyUnboxNode unboxNode) generateBytecode(unboxNode.getLeftNode(), bytecode);
        else if (node == null) {
            System.out.println("Brrrrrrr null value received");
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
            case "getSize" -> {
                bytecode.addInstruction(Opcode.OP_GET_SIZE, 0);
            }
            case "stacktrace" -> bytecode.addInstruction(Opcode.OP_STACKTRACE, 0);
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
            case "println", "typeOf", "isInstance", "nanoTime", "eval", "getSize", "stacktrace", "new", "exit" ->
                    result = true;
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


    private static void readObjectProperty(ToyReadPropertyNode readPropertyNode, Bytecode bytecode) {
        generateBytecode(readPropertyNode.getReceiverNode(), bytecode);
        if (readPropertyNode.getNameNode() instanceof ToyStringLiteralNode) {
            String propertyName = ((ToyStringLiteralNode) readPropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyName);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
        } else if (readPropertyNode.getNameNode() instanceof ToyLongLiteralNode) {
            Long propertyArrayValue = ((ToyLongLiteralNode) readPropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyArrayValue);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, propertyIndex);
        } else {
            generateBytecode(readPropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_GET_PROPERTY, 0);
        }
    }

    private static void writeObjectProperty(ToyWritePropertyNode writePropertyNode, Bytecode bytecode) {
        isArgument = false;
        generateBytecode(writePropertyNode.getReceiverNode(), bytecode);
        generateBytecode(writePropertyNode.getValueNode(), bytecode);
        if (writePropertyNode.getNameNode() instanceof ToyStringLiteralNode) {
            String propertyName = ((ToyStringLiteralNode) writePropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyName);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, propertyIndex);
        } else if (writePropertyNode.getNameNode() instanceof ToyLongLiteralNode) {
            Long propertyArrayValue = ((ToyLongLiteralNode) writePropertyNode.getNameNode()).getValue();
            int propertyIndex = bytecode.addToConstantPool(propertyArrayValue);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, propertyIndex);
        } else {
            generateBytecode(writePropertyNode.getNameNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_SET_PROPERTY, 0);
        }
    }

}
