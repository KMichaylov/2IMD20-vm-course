package nl.tue.vmcourse.toy.ast;

public class ToyUnboxNode extends ToyExpressionNode {
    private final ToyExpressionNode leftNode;

    public ToyUnboxNode(ToyExpressionNode leftNode) {
        super();
        this.leftNode = leftNode;
    }

    public ToyExpressionNode getLeftNode() {
        return leftNode;
    }

    @Override
    public String toString() {
        return "ToyUnboxNode{" +
                "leftNode=" + leftNode +
                '}';
    }
}
