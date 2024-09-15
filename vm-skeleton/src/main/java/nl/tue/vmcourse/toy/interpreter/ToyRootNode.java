package nl.tue.vmcourse.toy.interpreter;

import nl.tue.vmcourse.toy.lang.FrameDescriptor;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class ToyRootNode extends ToyNode {
    private final FrameDescriptor frameDescriptor;
    private final ToyAbstractFunctionBody functionBodyNode;
    private final String functionName;
    private final RootCallTarget callTarget;

    public ToyRootNode(FrameDescriptor build, ToyAbstractFunctionBody functionBodyNode, String functionName) {
        this.frameDescriptor = build;
        this.functionBodyNode = functionBodyNode;
        this.functionName = functionName;
        this.callTarget = new RootCallTarget(this);
    }

    public RootCallTarget getCallTarget() {
        return callTarget;
    }

    public Object execute(VirtualFrame frame) {
        return functionBodyNode.execute(frame);
    }
}
