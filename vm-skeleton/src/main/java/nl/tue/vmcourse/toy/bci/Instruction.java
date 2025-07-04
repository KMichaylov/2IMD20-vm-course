package nl.tue.vmcourse.toy.bci;

/**
 * The instruction class is essentially a wrapper over the opcode which helps to store information about the operands.
 **/
public class Instruction {
    private final Opcode opcode;
    private final Integer operand;
    private final String variableName;
    private final Integer frameSlot;
    private final Boolean newVariable;

    /**
     * Constructor for the instruction which create a new instruction with the opcode and operand.
     * @param opcode the opcode of the command
     * @param operand index which points to the constant pool
     */
    public Instruction(Opcode opcode, Integer operand) {
        this.opcode = opcode;
        this.operand = operand;
        this.variableName = null;
        this.frameSlot = null;
        this.newVariable = null;
    }
    /**
     * Constructor for the instruction.
     * @param opcode the opcode of the command
     * @param operand index which points to the constant pool, in many cases it has the same value as the frame slot
     * @param variableName the name of the variable if it exists
     * @param frameSlot points to the constant pool, in many cases it has the same value as the operand
     * @param newVariable indicates if the variable is newly defined, or already exists
     */
    public Instruction(Opcode opcode, Integer operand, String variableName, Integer frameSlot, Boolean newVariable) {
        this.opcode = opcode;
        this.operand = operand;
        this.variableName = variableName;
        this.frameSlot = frameSlot;
        this.newVariable = newVariable;
    }

    /**
     * Get the opcode of the instruction.
     * @return the opcode
     */
    public Opcode getOpcode() {
        return opcode;
    }


    /**
     * Get the operand of the instruction.
     * @return the operand
     */
    public Integer getOperand() {
        return operand;
    }

    /**
     * Get the variable name of the instruction. If it does not exist, returns null.
     * @return variable name or null
     */
    public String getVariableName() {
        return variableName;
    }


    /**
     * Get the frame slot where the instruction is stored
     * @return the frame slot
     */
    public Integer getFrameSlot() {
        return frameSlot;
    }

    /**
     * Indicates if the variable in the instruction is newly defined.
     * @return true if the variable is new, false otherwise
     */
    public Boolean isNewVariable() {
        return newVariable;
    }

    /**
     * String representation for all parts of the instruction.
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Instruction{opcode=%s, operand=%d, variableName='%s', frameSlot=%d, newVariable=%b}".formatted(opcode, operand, variableName, frameSlot, newVariable);    }
}

