package nl.tue.vmcourse.toy.ast;

public class ToyBooleanLiteralNode extends ToyExpressionNode {
    private final boolean value;

    public ToyBooleanLiteralNode(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ToyBooleanLiteralNode{" +
                "value=" + value +
                '}';
    }
}
