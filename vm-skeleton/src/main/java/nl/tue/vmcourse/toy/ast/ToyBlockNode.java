package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.lang.VirtualFrame;

import java.util.Arrays;
import java.util.List;

public class ToyBlockNode extends ToyStatementNode {
    private final ToyStatementNode[] statements;

    public ToyBlockNode(ToyStatementNode[] nodes) {
        super();
        this.statements = nodes;
    }

    public Iterable<? extends ToyStatementNode> getStatements() {
        return List.of(statements);
    }

    @Override
    public String toString() {
        return "ToyBlockNode{" +
                "statements=" + Arrays.toString(statements) +
                '}';
    }
}
