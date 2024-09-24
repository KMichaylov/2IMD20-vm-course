package nl.tue.vmcourse.toy.bci;

import java.util.Optional;

/**
 * The instruction record is essentially a wrapper over the opcode which helps to store information about the operands.
 **/

// TODO: Create a table with all opcodes and operand numbers, to make navigation easier for later changes.
//    This needs redesign, since different values have different types.
public record Instruction(Opcode opcode, int operand) {

}

