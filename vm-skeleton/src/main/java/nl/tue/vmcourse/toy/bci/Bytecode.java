package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;

public class Bytecode {
    private final List<Instruction> instructions;

    public Bytecode() {
        this.instructions = new ArrayList<>();
    }

    public void addInstruction(Opcode opcode, int operand) {
        instructions.add(new Instruction(opcode, operand));
    }

    public Instruction getInstruction(int index) {
        return instructions.get(index);
    }

    public int getSize() {
        return instructions.size();
    }

    public void printBytecode() {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instr = instructions.get(i);
            System.out.println("[" + i + "] " + instr.getOpcode() + " " + instr.getOperand());
        }
    }
}
