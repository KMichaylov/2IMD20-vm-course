package nl.tue.vmcourse.toy.ast;

import java.util.Arrays;
import java.util.List;

public class ToyDivNode extends ToyExpressionNode {
    private final ToyExpressionNode leftUnboxed;
    private final ToyExpressionNode rightUnboxed;

    public ToyDivNode(ToyExpressionNode leftUnboxed, ToyExpressionNode rightUnboxed) {
        this.leftUnboxed = leftUnboxed;
        this.rightUnboxed = rightUnboxed;
    }

    public ToyExpressionNode getLeftUnboxed() {
        return leftUnboxed;
    }

    public ToyExpressionNode getRightUnboxed() {
        return rightUnboxed;
    }

    @Override
    public List<ToyAstNode> getChildren() {
        return Arrays.asList(leftUnboxed, rightUnboxed);
    }

    @Override
    public String toString() {
        return "ToyDivNode{" +
                "leftUnboxed=" + leftUnboxed +
                ", rightUnboxed=" + rightUnboxed +
                '}';
    }
}
