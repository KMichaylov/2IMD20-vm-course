package nl.tue.vmcourse.toy.lang;

import java.util.ArrayList;
import java.util.List;

public class VirtualFrame {
    private final Object[] arguments;
    private final List<Object> locals;

    public VirtualFrame(Object[] arguments) {
        this.arguments = arguments;
        this.locals = new ArrayList<>();
    }

    public Object[] getArguments() {
        return arguments;
    }

    public List<Object> getLocals() {
        return locals;
    }

    public Object getConstant(int index) {
        return arguments[index];
    }
}
