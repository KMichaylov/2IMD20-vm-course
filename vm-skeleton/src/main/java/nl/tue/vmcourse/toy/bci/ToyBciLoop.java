package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.jit.JITCompiler;

public class ToyBciLoop extends ToyAbstractFunctionBody {

    private static final int JIT_COMPILATION_THRESHOLD = 3;

    private final byte[] code;
    private final JITCompiler compiler;

    public ToyBciLoop(byte[] code) {
        this.code = code;
        this.compiler = new JITCompiler();
    }

    public Object execute(VirtualFrame frame) {
        int pc = 0;
        int executions = 0;
        Object objRegister = null;
        int intRegister1 = 41;
        int intRegister2 = 1;
        while (true) {
            executions++;
            switch (code[pc]) {
                case 42 -> {
                    if (executions <= JIT_COMPILATION_THRESHOLD) {
                        continue;
                    }
                    objRegister = compiler.compileAndRun(intRegister1, intRegister2);
                    return "Hello from your friendly BCI! (and your JIT: " + objRegister + ")";
                }
                case 43 -> pc++;
                // case ..
                default -> throw new RuntimeException("TODO");
            }
        }
        // return whatever;
    }
}
