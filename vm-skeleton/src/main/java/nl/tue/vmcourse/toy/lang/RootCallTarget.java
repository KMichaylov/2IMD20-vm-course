package nl.tue.vmcourse.toy.lang;

import nl.tue.vmcourse.toy.interpreter.ToyRootNode;

public class RootCallTarget {
    private final ToyRootNode rootNode;

    public RootCallTarget(ToyRootNode rootNode) {
        this.rootNode = rootNode;
    }

    public Object invoke(Object... arguments) {
        VirtualFrame frame = new VirtualFrame(arguments);
        return rootNode.execute(frame);
    }

}
