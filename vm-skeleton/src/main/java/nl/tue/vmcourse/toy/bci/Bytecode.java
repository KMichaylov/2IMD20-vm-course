package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The bytecode class is essentially a holder for all bytecode instructions and data for a given method.
 */
public class Bytecode {
    private final List<Instruction> instructions;
    private final List<Object> constantPool;

    public Bytecode() {
        this.instructions = new ArrayList<>();
        this.constantPool = new ArrayList<>();
    }

    public int addInstruction(Opcode opcode, Integer operand) {
        instructions.add(new Instruction(opcode, operand));
        return instructions.size() - 1;
    }

    public int addVariableInstruction(Opcode opcode, Integer operand, String variableName, Integer frameSlot, Boolean newVariable) {
        instructions.add(new Instruction(opcode, operand, variableName, frameSlot, newVariable));
        return instructions.size() - 1;
    }

    public void patchInstruction(int index, Integer operand) {
        Instruction oldInstruction = instructions.get(index);
        instructions.set(index, new Instruction(oldInstruction.getOpcode(), operand));
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
            System.out.println(instr);
        }
    }
}
