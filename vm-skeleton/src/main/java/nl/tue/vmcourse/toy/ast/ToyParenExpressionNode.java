package nl.tue.vmcourse.toy.ast;

public class ToyParenExpressionNode extends ToyExpressionNode {
    private final ToyExpressionNode expressionNode;

    public ToyParenExpressionNode(ToyExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    @Override
    public String toString() {
        return "ToyParenExpressionNode{" +
                "expressionNode=" + expressionNode +
                '}';
    }
}
