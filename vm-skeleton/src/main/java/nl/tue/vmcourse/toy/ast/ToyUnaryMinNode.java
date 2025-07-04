package nl.tue.vmcourse.toy.ast;

public class ToyUnaryMinNode extends ToyExpressionNode {

    private final ToyExpressionNode exp;

    public ToyUnaryMinNode(ToyExpressionNode exp) {
        this.exp = exp;
    }

    public ToyExpressionNode getExp() {
        return exp;
    }

    @Override
    public String toString() {
        return "ToyUnaryMinNode{" +
                "exp=" + exp +
                '}';
    }
}
