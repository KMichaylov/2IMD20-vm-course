package nl.tue.vmcourse.toy.ast;

public class ToyReadLocalVariableNode extends ToyExpressionNode {
    private final Integer frameSlot;

    public ToyReadLocalVariableNode(Integer frameSlot) {
        super();
        this.frameSlot = frameSlot;
    }

    @Override
    public String toString() {
        return "ToyReadLocalVariableNode{" +
                "frameSlot=" + frameSlot +
                '}';
    }
}
