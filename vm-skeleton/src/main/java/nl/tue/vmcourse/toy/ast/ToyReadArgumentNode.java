package nl.tue.vmcourse.toy.ast;

public class ToyReadArgumentNode extends ToyExpressionNode {
    private final int parameterCount;

    public ToyReadArgumentNode(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    public int getParameterCount() {
        return parameterCount;
    }

    @Override
    public String toString() {
        return "ToyReadArgumentNode{" +
                "parameterCount=" + parameterCount +
                '}';
    }
}
