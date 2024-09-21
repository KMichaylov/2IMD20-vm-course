package nl.tue.vmcourse.toy.ast;

import java.util.Collections;
import java.util.List;

public class ToyLongLiteralNode extends ToyExpressionNode {
    private final long value;

    public ToyLongLiteralNode(long value) {
        super();
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public List<ToyAstNode> getChildren() {
        return Collections.emptyList(); // No children for literal nodes
    }

    @Override
    public String toString() {
        return "ToyLongLiteralNode{" +
                "value=" + value +
                '}';
    }
}
