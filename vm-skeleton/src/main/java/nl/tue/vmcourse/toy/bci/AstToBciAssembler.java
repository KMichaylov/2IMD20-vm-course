package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNode;

import java.util.Optional;

public class AstToBciAssembler {

    /**
     * We send the code to the ToyBciLoop to execute the bytecode commands.
     *
     * @param methodBlock
     * @return
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
                bytecode.addInstruction(Opcode.OP_STORE, writeNode.getFrameSlot());
            }
            case ToyReadLocalVariableNode readNode -> bytecode.addInstruction(Opcode.OP_LOAD, readNode.getFrameSlot());
            case ToyAddNode addNode ->
                    binaryInstructionHelperGenerator(addNode.getLeftUnboxed(), addNode.getRightUnboxed(), Opcode.OP_ADD, bytecode, 0);
            case ToySubNode subNode ->
                    binaryInstructionHelperGenerator(subNode.getLeftUnboxed(), subNode.getRightUnboxed(), Opcode.OP_SUB, bytecode, 0);
            case ToyDivNode divNode ->
                    binaryInstructionHelperGenerator(divNode.getLeftUnboxed(), divNode.getRightUnboxed(), Opcode.OP_DIV, bytecode, 0);
            case ToyMulNode mulNode ->
                    binaryInstructionHelperGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode, 0);

            // For the comparisons we use the provided AST and in case we need > or >=, we just negate the results
            case ToyEqualNode equalNode -> {
                generateBytecode(equalNode.getLeftUnboxed(), bytecode);
                generateBytecode(equalNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 0);
            }

            case ToyLessThanNode lessThanNode -> {
                generateBytecode(lessThanNode.getLeftUnboxed(), bytecode);
                generateBytecode(lessThanNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 1);
            }

            case ToyLessOrEqualNode lessOrEqualNode -> {
                generateBytecode(lessOrEqualNode.getLeftUnboxed(), bytecode);
                generateBytecode(lessOrEqualNode.getRightUnboxed(), bytecode);
                bytecode.addInstruction(Opcode.OP_COMPARE, 2);
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
                int indexOfLong = bytecode.addToConstantPool(literalNode.getValue());
                bytecode.addInstruction(Opcode.OP_LITERAL_LONG, indexOfLong);
            }
            case ToyBooleanLiteralNode booleanLiteralNode -> {
                int indexOfBoolean = bytecode.addToConstantPool(booleanLiteralNode.isValue());
                bytecode.addInstruction(Opcode.OP_LITERAL_BOOLEAN, indexOfBoolean);
            }
            case ToyBigIntegerLiteralNode bigIntegerLiteralNode -> {
                int indexOfBigInteger = bytecode.addToConstantPool(bigIntegerLiteralNode.getBigInteger());
                bytecode.addInstruction(Opcode.OP_LITERAL_BIGINT, indexOfBigInteger);
            }

            case ToyInvokeNode invokeNode -> {
                // Generate bytecode for the function invocation (println)
                generateBytecode(invokeNode.getFunctionNode(), bytecode);

                // Add bytecode for each argument of the function
                for (ToyExpressionNode arg : invokeNode.getToyExpressionNodes()) {
                    generateBytecode(arg, bytecode);  // Process the argument (either a literal or variable)
                }

                // Add the OP_PRINT instruction for println (assuming println is a special function)
                if (invokeNode.getFunctionNode() instanceof ToyFunctionLiteralNode functionNode) {
                    if (functionNode.getName().equals("println")) {
                        bytecode.addInstruction(Opcode.OP_PRINT, 0);
                    } else {
                        bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
                    }
                }
            }
            // TODO: Change, not sure if this is correct
            case ToyIfNode ifNode -> {
                generateBytecode(ifNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1); // Placeholder -1 for offset

                generateBytecode(ifNode.getThenPartNode(), bytecode);

                int jumpToEndLocation = -1;
                if (ifNode.getElsePartNode() != null) {
                    jumpToEndLocation = bytecode.addInstruction(Opcode.OP_JUMP, -1); // Placeholder -1 for offset
                }

                int elseOrEndLocation = bytecode.getSize(); // This is the target for "jump if false"
                bytecode.patchInstruction(jumpIfFalseLocation, elseOrEndLocation - jumpIfFalseLocation - 1);

                if (ifNode.getElsePartNode() != null) {
                    generateBytecode(ifNode.getElsePartNode(), bytecode);
                    int endLocation = bytecode.getSize();
                    bytecode.patchInstruction(jumpToEndLocation, endLocation - jumpToEndLocation - 1);
                }
            }

            case ToyWhileNode whileNode -> {
                int loopStart = bytecode.getSize();

                generateBytecode(whileNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, -1); // Placeholder -1 for offset

                generateBytecode(whileNode.getBodyNode(), bytecode);

                bytecode.addInstruction(Opcode.OP_JUMP, loopStart - bytecode.getSize() - 1); // Jump back to the start of the loop

                bytecode.patchInstruction(jumpIfFalseLocation, bytecode.getSize() - jumpIfFalseLocation - 1);
            }
            case ToyUnboxNode unboxNode -> generateBytecode(unboxNode.getLeftNode(), bytecode);
            case null, default -> System.out.println("Brrrrrrr");

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


}
