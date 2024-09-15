package nl.tue.vmcourse.toy.ast;

public class ToyReturnNode extends ToyStatementNode {
    private final ToyExpressionNode valueNode;

    public ToyReturnNode(ToyExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public String toString() {
        return "ToyReturnNode{" +
                "valueNode=" + valueNode +
                '}';
    }
}
