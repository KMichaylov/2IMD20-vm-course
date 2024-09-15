package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class ToyFunctionBodyNode extends ToyAbstractFunctionBody {
    private final ToyStatementNode methodBlock;

    public ToyFunctionBodyNode(ToyStatementNode methodBlock) {
        this.methodBlock = methodBlock;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        throw new RuntimeException("Cannot execute an AST node -- this is not an AST interpreter!");
    }

    @Override
    public String toString() {
        return "ToyFunctionBodyNode{" +
                "methodBlock=" + methodBlock +
                '}';
    }
}
