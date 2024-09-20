package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;

// TODO: add javadoc
public class Bytecode {
    private final List<Instruction> instructions;

    public Bytecode() {
        this.instructions = new ArrayList<>();
    }


    public int addInstruction(Opcode opcode, int operand) {
        instructions.add(new Instruction(opcode, operand));
        return instructions.size() - 1;  // Return the index of the added instruction
    }


    public void patchInstruction(int index, int operand) {
        Instruction oldInstruction = instructions.get(index);
        instructions.set(index, new Instruction(oldInstruction.opcode(), operand));
    }

    public Instruction getInstruction(int index) {
        return instructions.get(index);
    }


    public int getSize() {
        return instructions.size();
    }


    public void printBytecode() {
        for (Instruction instr : instructions) {
            System.out.println(STR."\{instr.opcode()} \{instr.operand()}");
        }
    }


}
