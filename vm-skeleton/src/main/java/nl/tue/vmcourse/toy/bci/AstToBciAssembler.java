package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.*;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNode;

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
    private static void generateBytecode(ToyNode node, Bytecode bytecode) {
        if (node instanceof ToyBlockNode blockNode) {
//            If we have a block node, then we go through the statements and recursively call for each of them the function.
            for (ToyStatementNode statement : blockNode.getStatements()) {
                generateBytecode(statement, bytecode);
            }
//            TODO: Go to the AST implementations and add methods there.
        } else if (node instanceof ToyWriteLocalVariableNode writeNode) {
            generateBytecode(writeNode.getValueNode(), bytecode);
            bytecode.addInstruction(Opcode.OP_PUSH, 0);
            bytecode.addInstruction(Opcode.OP_STORE, writeNode.getFrameSlot());
        } else if (node instanceof ToyReadLocalVariableNode readNode) {
            bytecode.addInstruction(Opcode.OP_LOAD, readNode.getFrameSlot());
        } else if (node instanceof ToyAddNode addNode) {
            binaryInstructionHelperGenerator(addNode, addNode, Opcode.OP_ADD, bytecode);
        } else if (node instanceof ToySubNode subNode) {
            binaryInstructionHelperGenerator(subNode, subNode, Opcode.OP_SUB, bytecode);
        } else if (node instanceof ToyDivNode divNode) {
            binaryInstructionHelperGenerator(divNode, divNode, Opcode.OP_DIV, bytecode);
        } else if (node instanceof ToyMulNode mulNode) {
            binaryInstructionHelperGenerator(mulNode.getLeftUnboxed(), mulNode.getRightUnboxed(), Opcode.OP_MUL, bytecode);
        } else if (node instanceof ToyLongLiteralNode literalNode) {
            bytecode.addInstruction(Opcode.OP_CONSTANT, (int) literalNode.getValue());
        } else if (node instanceof ToyInvokeNode invokeNode) {
            // Add information for function and the corresponding operations for it
            generateBytecode(invokeNode.getFunctionNode(), bytecode);

            for (ToyExpressionNode arg : invokeNode.getToyExpressionNodes()) {
                generateBytecode(arg, bytecode);
            }

            bytecode.addInstruction(Opcode.OP_CALL, invokeNode.getToyExpressionNodes().length);
        } else if (node instanceof ToyUnboxNode unboxNode) {
            generateBytecode(unboxNode.getLeftNode(), bytecode);
        } else {
            System.out.println("Brrrrrrr");
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
     */
    private static void binaryInstructionHelperGenerator(ToyNode leftNode, ToyNode rightNode, Opcode opcode, Bytecode bytecode) {
        generateBytecode(leftNode, bytecode);
        generateBytecode(rightNode, bytecode);
        bytecode.addInstruction(opcode, 0);
    }


}
