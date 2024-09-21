package nl.tue.vmcourse.toy.ast;

import java.util.Collections;
import java.util.List;

public abstract class ToyStatementNode extends ToyAstNode {

    @Override
    public List<ToyAstNode> getChildren() {
        // We just return an empty list at this top-level statement
        return Collections.emptyList();
//        throw new RuntimeException("TODO: return children if any (or empty list otherwise)");
    }

}
