package nl.tue.vmcourse.toy.ast;

public class ToyWhileNode extends ToyStatementNode {
    private final ToyExpressionNode conditionNode;
    private final ToyStatementNode bodyNode;

    public ToyWhileNode(ToyExpressionNode conditionNode, ToyStatementNode bodyNode) {
        this.conditionNode = conditionNode;
        this.bodyNode = bodyNode;
    }

    public ToyExpressionNode getConditionNode() {
        return conditionNode;
    }

    public ToyStatementNode getBodyNode() {
        return bodyNode;
    }

    @Override
    public String toString() {
        return "ToyWhileNode{" +
                "conditionNode=" + conditionNode +
                ", bodyNode=" + bodyNode +
                '}';
    }
}
