package nl.tue.vmcourse.toy.ast;

public class ToyLongLiteralNode extends ToyExpressionNode {
    private final long value;

    public ToyLongLiteralNode(long value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        return "ToyLongLiteralNode{" +
                "value=" + value +
                '}';
    }
}
