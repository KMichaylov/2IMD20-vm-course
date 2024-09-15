package nl.tue.vmcourse.toy.interpreter;

import nl.tue.vmcourse.toy.lang.VirtualFrame;

public abstract class ToyAbstractFunctionBody extends ToyNode {

    public abstract Object execute(VirtualFrame frame);

}
