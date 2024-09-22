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
//            TODO: Go to the AST implementations and add methods there.
            }
            case ToyWriteLocalVariableNode writeNode -> {
                generateBytecode(writeNode.getValueNode(), bytecode);
                bytecode.addInstruction(Opcode.OP_STORE, writeNode.getFrameSlot());
            }
            case ToyReadLocalVariableNode readNode ->
                    bytecode.addInstruction(Opcode.OP_LOAD, readNode.getFrameSlot());
            case ToyAddNode addNode ->
                    binaryInstructionHelperGenerator(addNode.getLeftUnboxed(), addNode.getRightUnboxed(), Opcode.OP_ADD, bytecode, 0);
            case ToySubNode subNode ->
                    binaryInstructionHelperGenerator(subNode.getLeftUnboxed(), subNode.getRightUnboxed(), Opcode.OP_SUB, bytecode, 0);
            case ToyDivNode divNode ->
                    binaryInstructionHelperGenerator(divNode.getLeftUnboxed(), divNode.getRightUnboxed(), Opcode.OP_DIV, bytecode, 0);
            case ToyMulNode mulNode ->
                    binaryInstructionHelperGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode, 0);
            case ToyEqualNode equalNode -> {
                generateBytecode(equalNode.getLeftUnboxed(), bytecode);

                generateBytecode(equalNode.getRightUnboxed(), bytecode);

                // operand encodings:
                // 0 is for ==
                // 1 for <
                // 2 for >
                // 3 for <=
                // 4 for >=
                bytecode.addInstruction(Opcode.OP_COMPARE, 0);
            }
            // I use the following opcodes:
            //0 for boolean
            //1 for long
            //2 for string
            //3 for big integer
            case ToyStringLiteralNode stringLiteralNode -> {
                int indexOfString = bytecode.addToConstantPool(stringLiteralNode.getValue());
                bytecode.addInstruction(Opcode.OP_CONSTANT, 2);
            }
//            TODO: Implement these methods according to the new changes.
            case ToyLongLiteralNode literalNode ->
                    bytecode.addInstruction(Opcode.OP_CONSTANT, (int) literalNode.getValue());
            case ToyBooleanLiteralNode booleanLiteralNode -> {
                bytecode.addInstruction(Opcode.OP_CONSTANT, 0);
            }
            case ToyBigIntegerLiteralNode bigIntegerLiteralNode -> {
                bytecode.addInstruction(Opcode.OP_CONSTANT, 0);
            }


            case ToyInvokeNode invokeNode -> {
                // Add information for function and the corresponding operations for it
                generateBytecode(invokeNode.getFunctionNode(), bytecode);

                for (ToyExpressionNode arg : invokeNode.getToyExpressionNodes()) {
                    generateBytecode(arg, bytecode);
                }

//            bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
            }
            // TODO: Change, not sure if this is correct
            case ToyIfNode ifNode -> {
                generateBytecode(ifNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, 0);

                generateBytecode(ifNode.getThenPartNode(), bytecode);

                if (ifNode.getElsePartNode() != null) {
                    int jumpToEndLocation = bytecode.addInstruction(Opcode.OP_JUMP, 0);

                    bytecode.patchInstruction(jumpIfFalseLocation, bytecode.getSize());

                    generateBytecode(ifNode.getElsePartNode(), bytecode);

                    bytecode.patchInstruction(jumpToEndLocation, bytecode.getSize());
                } else {
                    bytecode.patchInstruction(jumpIfFalseLocation, bytecode.getSize());
                }
            }
            case ToyUnboxNode unboxNode -> generateBytecode(unboxNode.getLeftNode(), bytecode);
            case ToyFunctionLiteralNode toyFunctionLiteralNode -> {
                if (toyFunctionLiteralNode.getName().equals("println")) {
                    bytecode.addInstruction(Opcode.OP_PRINT, 0);
                }
            }
//        TODO: Change, not sure if this is correct
            case ToyWhileNode whileNode -> {
                int loopStart = bytecode.getSize();

                generateBytecode(whileNode.getConditionNode(), bytecode);

                int jumpIfFalseLocation = bytecode.addInstruction(Opcode.OP_JUMP_IF_FALSE, 0);

                generateBytecode(whileNode.getBodyNode(), bytecode);

                bytecode.addInstruction(Opcode.OP_JUMP, loopStart);

                bytecode.patchInstruction(jumpIfFalseLocation, bytecode.getSize());
            }
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
