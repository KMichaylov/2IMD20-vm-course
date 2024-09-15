package nl.tue.vmcourse.toy.ast;

public class ToyWritePropertyNode extends ToyExpressionNode {
    private final ToyExpressionNode receiverNode;
    private final ToyExpressionNode nameNode;
    private final ToyExpressionNode valueNode;

    public ToyWritePropertyNode(ToyExpressionNode receiverNode, ToyExpressionNode nameNode, ToyExpressionNode valueNode) {
        super();
        this.receiverNode = receiverNode;
        this.nameNode = nameNode;
        this.valueNode = valueNode;
    }

    @Override
    public String toString() {
        return "ToyWritePropertyNode{" +
                "receiverNode=" + receiverNode +
                ", nameNode=" + nameNode +
                ", valueNode=" + valueNode +
                '}';
    }
}
