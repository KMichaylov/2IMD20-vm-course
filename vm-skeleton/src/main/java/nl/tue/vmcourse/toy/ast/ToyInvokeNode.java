package nl.tue.vmcourse.toy.ast;

import java.util.Arrays;

public class ToyInvokeNode extends ToyExpressionNode {
    private final ToyExpressionNode functionNode;
    private final ToyExpressionNode[] toyExpressionNodes;

    public ToyInvokeNode(ToyExpressionNode functionNode, ToyExpressionNode[] toyExpressionNodes) {
        super();
        this.functionNode = functionNode;
        this.toyExpressionNodes = toyExpressionNodes;
    }

    @Override
    public String toString() {
        return "ToyInvokeNode{" +
                "functionNode=" + functionNode +
                ", toyExpressionNodes=" + Arrays.toString(toyExpressionNodes) +
                '}';
    }
}
