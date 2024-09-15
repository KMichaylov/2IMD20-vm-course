package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;

public class AstToBciAssembler {

    public static ToyAbstractFunctionBody build(ToyStatementNode methodBlock) {
        byte[] code = compileAst(methodBlock);
        // TODO code is one argument; depending in impl other arguments might be needed (e.g., constant pool?)
        return new ToyBciLoop(code);
    }

    private static byte[] compileAst(ToyStatementNode methodBlock) {
        // TODO should explore AST and produce BC instructions.
        return new byte[] {42};
    }

    
}
