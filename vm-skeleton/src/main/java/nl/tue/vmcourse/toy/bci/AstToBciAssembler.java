package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNode;

import java.math.BigInteger;

public class AstToBciAssembler {

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
//    TODO: Check the whole logic and see how AST changes when passed different expressions
    // TODO: Refactor the whole tree.
    private static void generateBytecode(ToyNode node, Bytecode bytecode) {
        switch (node) {
            case ToyBlockNode blockNode -> {
//            If we have a block node, then we go through the statements and recursively call for each of them the function.
                for (ToyStatementNode statement : blockNode.getStatements()) {
                    generateBytecode(statement, bytecode);
                }
            }

            case ToyWriteLocalVariableNode writeNode -> {
                generateBytecode(writeNode.getValueNode(), bytecode);
                String variableName = ((ToyStringLiteralNode) writeNode.getNameNode()).getValue();
                if (variableName.equals("null")) {
                    variableName = null;
                }
                bytecode.addVariableInstruction(
                        Opcode.OP_STORE,
                        0,
                        variableName,
                        writeNode.getFrameSlot(),
                        writeNode.isNewVariable()
                );
            }
            case ToyReadLocalVariableNode readNode -> {
                bytecode.addVariableInstruction(
                        Opcode.OP_LOAD,
                        readNode.getFrameSlot(),
                        null,
                        readNode.getFrameSlot(),
                        false
                );
            }
//            TODO: Check how to read functional arguments, consult books
            case ToyReadArgumentNode readArgumentNode -> {
                bytecode.addInstruction(Opcode.OP_LOAD, readArgumentNode.getParameterCount());
            }
            case ToyAddNode addNode ->
                    binaryInstructionHelperGenerator(addNode.getLeftUnboxed(), addNode.getRightUnboxed(), Opcode.OP_ADD, bytecode, 0);
            case ToySubNode subNode ->
                    binaryInstructionHelperGenerator(subNode.getLeftUnboxed(), subNode.getRightUnboxed(), Opcode.OP_SUB, bytecode, 0);
            case ToyDivNode divNode ->
                    binaryInstructionHelperGenerator(divNode.getLeftUnboxed(), divNode.getRightUnboxed(), Opcode.OP_DIV, bytecode, 0);
            case ToyMulNode mulNode ->
                    binaryInstructionHelperGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode, 0);


            // Boolean operations: TODO: If the left part is false, then we don't look at the right part
            case ToyLogicalAndNode logicalAndNode -> {
                binaryInstructionHelperGenerator(logicalAndNode.getLeftUnboxed(), logicalAndNode.getRightUnboxed(), Opcode.OP_LOGICAL_AND, bytecode, 0);
            }

            case ToyLogicalOrNode logicalOrNode -> {
                binaryInstructionHelperGenerator(logicalOrNode.getLeftUnboxed(), logicalOrNode.getRightUnboxed(), Opcode.OP_LOGICAL_OR, bytecode, 0);
            }

            // Operations regarding the execution of loops

            case ToyContinueNode continueNode -> {
                int continueJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
                bytecode.addContinueJump(continueJumpIndex);
            }

            case ToyBreakNode breakNode -> {
                int breakJumpIndex = bytecode.addInstruction(Opcode.OP_JUMP, -1);
                bytecode.addBreakJump(breakJumpIndex);
            }

            case ToyParenExpressionNode parenExpressionNode -> {
                generateBytecode(parenExpressionNode.getExpressionNode(), bytecode);
                bytecode.addInstruction(Opcode.OP_NOP, 0);
            }


            // For the comparisons we use the provided AST and in case we need > or >=, we just negate the results
            case ToyEqualNode equalNode -> {
                generateBytecode(equalNode.getLeftUnboxed(), bytecode);
                generateBytecode(equalNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 0);
            }

            case ToyLessThanNode lessThanNode -> {
                generateBytecode(lessThanNode.getLeftUnboxed(), bytecode);
                generateBytecode(lessThanNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 2);
            }

            case ToyLessOrEqualNode lessOrEqualNode -> {
                generateBytecode(lessOrEqualNode.getLeftUnboxed(), bytecode);
                generateBytecode(lessOrEqualNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 3);
            }

            case ToyLogicalNotNode toyLogicalNotNode -> {
                generateBytecode(toyLogicalNotNode.getToyLessOrEqualNode(), bytecode);
                bytecode.addInstruction(Opcode.OP_NOT, 0);
            }
            case ToyStringLiteralNode stringLiteralNode -> {
                int indexOfString = bytecode.addToConstantPool(stringLiteralNode.getValue());
                bytecode.addInstruction(Opcode.OP_LITERAL_STRING, indexOfString);
            }
            case ToyLongLiteralNode literalNode -> {
                long value = literalNode.getValue();
                final long LONG_UPPERBOUND = 2147483647;
                if (value < LONG_UPPERBOUND) {
                    int indexOfLong = bytecode.addToConstantPool(literalNode.getValue());
                    bytecode.addInstruction(Opcode.OP_LITERAL_LONG, indexOfLong);
                } else {
                    int indexOfBigInt = bytecode.addToConstantPool(BigInteger.valueOf(literalNode.getValue()));
                    bytecode.addInstruction(Opcode.OP_LITERAL_BIGINT, indexOfBigInt);
                }

            }
            case ToyBooleanLiteralNode booleanLiteralNode -> {
                int indexOfBoolean = bytecode.addToConstantPool(booleanLiteralNode.isValue());
                bytecode.addInstruction(Opcode.OP_LITERAL_BOOLEAN, indexOfBoolean);
            }
            // Actually, mostly, they don't pass a bigint in the AST, so I need to check it in the long literal and there determine what happens
            case ToyBigIntegerLiteralNode bigIntegerLiteralNode -> {
                int indexOfBigInteger = bytecode.addToConstantPool(bigIntegerLiteralNode.getBigInteger());
                bytecode.addInstruction(Opcode.OP_LITERAL_BIGINT, indexOfBigInteger);
            }

//            TODO: Redesign and think of other approaches regarding where to store variables. Main priority for today
            case ToyInvokeNode invokeNode -> {
                if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode functionNode) {
                    String functionName = functionNode.getName();
                    if (!isBuiltInFunction(functionName)) {
                        // Here, we add the function name, so that the function can be executed.
                        int functionNameIndex = bytecode.addToConstantPool(functionNode.getName());
                        bytecode.addInstruction(Opcode.OP_FUNCTION_NAME, functionNameIndex);
                    }

                }

                for (ToyExpressionNode expression : invokeNode.getToyExpressionNodes()) {
                    generateBytecode(expression, bytecode);
                }
//                TODO: Should also check here what type the function is (extract into separate method)

                if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode functionNode) {
                    switch (functionNode.getName()) {
                        // The cases check for built-in functions
                        case "println" -> bytecode.addInstruction(Opcode.OP_PRINT, 0);
                        case "typeOf" -> bytecode.addInstruction(Opcode.OP_TYPEOF, 0);
                        case "isInstance" -> bytecode.addInstruction(Opcode.OP_IS_INSTANCE, 0);
                        case "nanoTime" -> bytecode.addInstruction(Opcode.OP_NANO_TIME, 0);
                        // The operand is the number of arguments
                        default -> bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
                    }

                }
            }
            case ToyIfNode ifNode -> {
                generateBytecode(ifNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1); // Placeholder -1 for offset

                generateBytecode(ifNode.getThenPartNode(), bytecode);

                int jumpToEndLocation = -1;
                if (ifNode.getElsePartNode() != null) {
                    jumpToEndLocation = bytecode.addInstruction(Opcode.OP_JUMP, -1); // Placeholder -1 for offset
                }

                int elseOrEndLocation = bytecode.getSize(); // This is the target for "jump if false"
                bytecode.updateInstruction(jumpIfFalseLocation, elseOrEndLocation - jumpIfFalseLocation - 1);

                if (ifNode.getElsePartNode() != null) {
                    generateBytecode(ifNode.getElsePartNode(), bytecode);
                    int endLocation = bytecode.getSize();
                    bytecode.updateInstruction(jumpToEndLocation, endLocation - jumpToEndLocation - 1);
                }
            }

            case ToyWhileNode whileNode -> {
                int loopStart = bytecode.getSize();

                generateBytecode(whileNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1);

                generateBytecode(whileNode.getBodyNode(), bytecode);

                // Go back to the beginning of the loop while iterating the loop
                bytecode.addInstruction(Opcode.OP_JUMP, loopStart - bytecode.getSize() - 1);

                // After traversing the whole loop, get the final location
                int loopEnd = bytecode.getSize();

                // Ensures that in case of a false condition, we can continue with the next statements outside the loop
                bytecode.updateInstruction(jumpIfFalseLocation, loopEnd - jumpIfFalseLocation - 1);

                // Update the jumps for continue and break to point to the correct locations
                bytecode.updateContinueJumps(loopStart);
                bytecode.updateBreakJumps(loopEnd);
            }

            case ToyReturnNode returnNode -> {
                generateBytecode(returnNode.getValueNode(), bytecode);
                bytecode.addInstruction(Opcode.OP_RETURN, 0);
            }
            case ToyUnboxNode unboxNode -> generateBytecode(unboxNode.getLeftNode(), bytecode);
            case null, default -> System.out.println(STR."Brrrrrrr\{node.getClass().getName()}");

//            throw new RuntimeException("Unknown AST node type: " + node.getClass().getSimpleName());
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
    private static void binaryInstructionHelperGenerator(ToyNode leftNode, ToyNode rightNode, Opcode opcode, Bytecode bytecode, int operand) {
        generateBytecode(leftNode, bytecode);
        generateBytecode(rightNode, bytecode);
        bytecode.addInstruction(opcode, operand);
    }

    /**
     * A helper method to decide if the current function is a built-in one.
     *
     * @param functionName the name of the function
     * @return true if function is built-in, false otherwise
     */
    private static boolean isBuiltInFunction(String functionName) {
        boolean result;
        switch (functionName) {
            case "println", "typeOf", "isInstance", "nanoTime" -> result = true;
            default -> result = false;
        }
        return result;
    }


}
