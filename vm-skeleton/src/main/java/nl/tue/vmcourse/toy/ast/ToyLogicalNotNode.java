package nl.tue.vmcourse.toy.ast;

public class ToyLogicalNotNode extends ToyExpressionNode {
    private final ToyExpressionNode toyLessOrEqualNode;

    public ToyLogicalNotNode(ToyLessOrEqualNode toyLessOrEqualNode) {
        super();
        this.toyLessOrEqualNode = toyLessOrEqualNode;
    }

    public ToyLogicalNotNode(ToyExpressionNode toyLessThanNode) {
        this.toyLessOrEqualNode = toyLessThanNode;
    }

    public ToyExpressionNode getToyLessOrEqualNode() {
        return toyLessOrEqualNode;
    }

    @Override
    public String toString() {
        return "ToyLogicalNotNode{" +
                "toyLessOrEqualNode=" + toyLessOrEqualNode +
                '}';
    }
}
