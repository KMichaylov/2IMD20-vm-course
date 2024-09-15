package nl.tue.vmcourse.toy.ast;

import java.util.List;

public abstract class ToyStatementNode extends ToyAstNode {

    @Override
    public List<ToyAstNode> getChildren() {
        throw new RuntimeException("TODO: return children if any (or empty list otherwise)");
    }

}
