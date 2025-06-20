package nl.tue.vmcourse.toy.ast;

public class ToyFunctionLiteralNode extends ToyExpressionNode {
    private final String name;

    public ToyFunctionLiteralNode(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ToyFunctionLiteralNode{" +
                "name='" + name + '\'' +
                '}';
    }
}
