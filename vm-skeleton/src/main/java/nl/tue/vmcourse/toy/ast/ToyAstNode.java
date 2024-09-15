package nl.tue.vmcourse.toy.ast;

import nl.tue.vmcourse.toy.interpreter.ToyNode;

import java.util.List;

public abstract class ToyAstNode extends ToyNode {

    public abstract List<ToyAstNode> getChildren();

}
