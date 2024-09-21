package nl.tue.vmcourse.toy.ast;

import java.util.Arrays;
import java.util.List;

public class ToyWriteLocalVariableNode extends ToyExpressionNode {
    private final ToyExpressionNode valueNode;
    private final Integer frameSlot;
    private final ToyExpressionNode nameNode;
    private final boolean newVariable;

    public ToyWriteLocalVariableNode(ToyExpressionNode valueNode, Integer frameSlot, ToyExpressionNode nameNode, boolean newVariable) {
        super();
        this.valueNode = valueNode;
        this.frameSlot = frameSlot;
        this.nameNode = nameNode;
        this.newVariable = newVariable;
    }

    public ToyExpressionNode getValueNode() {
        return valueNode;
    }

    public Integer getFrameSlot() {
        return frameSlot;
    }

    @Override
    public List<ToyAstNode> getChildren() {
        return Arrays.asList(valueNode, nameNode);
    }


    @Override
    public String toString() {
        return "ToyWriteLocalVariableNode{" +
                "valueNode=" + valueNode +
                ", frameSlot=" + frameSlot +
                ", nameNode=" + nameNode +
                ", newVariable=" + newVariable +
                '}';
    }
}
