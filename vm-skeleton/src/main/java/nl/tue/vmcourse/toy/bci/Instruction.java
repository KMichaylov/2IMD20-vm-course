package nl.tue.vmcourse.toy.bci;

/**
 * The instruction record is essentially a wrapper over the opcode which helps to store information about the operands.
 **/
public record Instruction(Opcode opcode, int operand) {

}

