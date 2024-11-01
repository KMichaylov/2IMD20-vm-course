package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;

/**
 * The bytecode class is essentially a holder for all bytecode instructions and helper data for a given program.
 */
public class Bytecode {
    private final List<Instruction> instructions;
    private final List<Object> constantPool;
    private final List<Integer> continueJumps;
    private final List<Integer> breakJumps;

    public Bytecode() {
        this.instructions = new ArrayList<>();
        this.constantPool = new ArrayList<>();
        this.continueJumps = new ArrayList<>();
        this.breakJumps = new ArrayList<>();
    }

    /**
     * Get the size of the constant pool, used for the object creation.
     *
     * @return the size of the constant pool.
     */
    public int getConstantPoolSize() {
        return constantPool.size();
    }

    /**
     * Add an instruction to the bytecode, this is for non-variable instructions.
     *
     * @param opcode  the opcode of the instruction
     * @param operand a miscellaneous operand, can be used as a jump target, etc.
     * @return a number for the index of the element being added
     */
    public int addInstruction(Opcode opcode, Integer operand) {
        instructions.add(new Instruction(opcode, operand));
        return instructions.size() - 1;
    }

    /**
     * Same as the addInstruction, but specifically for variable instructions.
     *
     * @param opcode       the opcode of the instruction
     * @param operand      a miscellaneous operand, can be used as a jump target, etc
     * @param variableName name of the variable
     * @param frameSlot    indicates on which frame slot the variable is stored
     * @param newVariable  boolean to indicate if the variable is newly defined
     */
    public void addVariableInstruction(Opcode opcode, Integer operand, String variableName, Integer frameSlot, Boolean newVariable) {
        instructions.add(new Instruction(opcode, operand, variableName, frameSlot, newVariable));
    }

    /**
     * Update an instruction at a given index with a new instruction.
     *
     * @param index   the index of the instruction
     * @param operand the operand of the instruction, could be anything depending on the instruction
     */
    public void updateInstruction(int index, Integer operand) {
        Instruction oldInstruction = instructions.get(index);
        instructions.set(index, new Instruction(oldInstruction.getOpcode(), operand));
    }

    public void updateInstruction(int index, String variableName) {
        Instruction oldInstruction = instructions.get(index);
        instructions.set(index, new Instruction(oldInstruction.getOpcode(), oldInstruction.getOperand(), variableName, oldInstruction.getFrameSlot(), oldInstruction.isNewVariable()));
    }

    /**
     * Get an instruction at the given index.
     *
     * @param index of the instruction
     * @return the instruction
     */
    public Instruction getInstruction(int index) {
        return instructions.get(index);
    }

    /**
     * Get the size of the bytecode.
     *
     * @return the size of the bytecode
     */
    public int getSize() {
        return instructions.size();
    }


    /**
     * Adds an element to the constant pool, where all variable values are stored.
     *
     * @param element to be added
     * @return the index of the element in the constant pool
     */
    public int addToConstantPool(Object element) {
        if (element instanceof Object) {
            constantPool.add(element);
            return constantPool.size() - 1;
        } else {
            return -1;
        }

    }

    /**
     * Replace the element at the index with the object
     *
     * @param operand index of the element
     * @param element object to replace the element
     */
    public void replaceConstantPoolElement(int operand, Object element) {
        constantPool.set(operand, element);
    }

    /**
     * Get an element from the constant pool.
     *
     * @param index of the element
     * @return the element
     */
    public Object getElementFromConstantPool(int index) {
        if (constantPool.get(index) != null) {
            return constantPool.get(index);
        } else {
            return null;
        }
    }

    /**
     * Specifically adds the position of the loop start, to satisfy the continue operand.
     *
     * @param position of the loop start
     */
    public void addContinueJump(int position) {
        continueJumps.add(position);
    }

    /**
     * Specifically adds the position of the loop start, to satisfy the break operand
     *
     * @param position of the loop start
     */
    public void addBreakJump(int position) {
        breakJumps.add(position);
    }

    /**
     * Essentially it updates for all elements the location of the loop start
     *
     * @param target for the loop start
     */
    public void updateContinueJumps(int target) {
        for (int index : continueJumps) {
            updateInstruction(index, target - index - 1);
        }
        continueJumps.clear();
    }

    /**
     * Essentially it updates for all elements the location of the loop end for the break statement
     *
     * @param target for the loop start
     */
    public void updateBreakJumps(int target) {
        for (int index : breakJumps) {
            updateInstruction(index, target - index - 1);
        }
        breakJumps.clear();
    }

    /**
     * Simply prints the bytecode.
     */
    public void printBytecode() {
        for (Instruction instr : instructions) {
            System.out.println(instr);
        }
    }

}
