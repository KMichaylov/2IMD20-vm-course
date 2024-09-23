package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: add javadoc
public class Bytecode {
    private final List<Instruction> instructions;
    private final List<Object> constantPool;

    public Bytecode() {
        this.instructions = new ArrayList<>();
        this.constantPool = new ArrayList<>();
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

    public int addToConstantPool(Object element) {
        if (element instanceof Object) {
            constantPool.add(element);
            return constantPool.size() - 1;
        } else {
            return -1;
        }

    }

    public Object getElementFromConstantPool(int index) {
        if (constantPool.get(index) != null) {
            return constantPool.get(index);
        } else {
            return null;
        }
    }


    public void printBytecode() {
        for (Instruction instr : instructions) {
            System.out.println(STR."\{instr.opcode()} \{instr.operand()}");
        }
    }


}
