package com.oracle.truffle.sl.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.sl.nodes.SLExpressionNode;

@NodeInfo(shortName = "const")
public class SLBooleanLiteralNode extends SLExpressionNode {

    private final boolean value;

    public SLBooleanLiteralNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return value;
    }
}
