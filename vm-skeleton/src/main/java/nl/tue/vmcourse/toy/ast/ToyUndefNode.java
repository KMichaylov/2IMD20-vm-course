package nl.tue.vmcourse.toy.ast;

public class ToyUndefNode extends ToyExpressionNode {

    public static final Object UNDEF = new Object();

    @Override
    public String toString() {
        return "undef";
    }
}
