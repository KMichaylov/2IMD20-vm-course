package nl.tue.vmcourse.toy.bci;

/**
 * The instruction class is essentially a wrapper over the opcode which helps to store information about the operands.
 **/
public class Instruction {
    private final Opcode opcode;
    private final int operand;
    private final int line;

    public Instruction(Opcode opcode, int operand, int line) {
        this.opcode = opcode;
        this.operand = operand;
        this.line = line;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public int getOperand() {
        return operand;
    }

    public int getLine() {
        return line;
    }
}

