package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

public class ToyBciLoop extends ToyAbstractFunctionBody {
    private final byte[] code;

    public ToyBciLoop(byte[] code) {
        this.code = code;
    }

    public Object execute(VirtualFrame frame) {
        int pc = 0;
        while (true) {
            switch (code[pc]) {
                case 42 -> {
                    return "Hello from your friendly BCI!";
                }
                case 43 -> pc++;
                // case ..
                default -> throw new RuntimeException("TODO");
            }
        }
        // return whatever;
    }
}
