package nl.tue.vmcourse.toy.ast;

public class ToyStringLiteralNode extends ToyExpressionNode {

    private final String value;

    public ToyStringLiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ToyStringLiteralNode{" +
                "value='" + value + '\'' +
                '}';
    }
}
