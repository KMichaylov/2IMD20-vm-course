package nl.tue.vmcourse.toy.bci;

//TODO: Look at this again, then move to the Instruction file and see how it can be useful.
//TODO: Look at the opcode and see what is necessary and what not. Check other resources and see what other people are doing
//TODO: Add javadoc

//TODO: Maybe create a separate opcode for while loops
// TODO: Add some port have a sequential order for the opcodes

/**
 * Enum for the different opcodes (bytecode commands that are used in the Toy language.
 */
public enum Opcode {
    // Control sequences and functions
    OP_RETURN((byte) 0x01),
    OP_LOOP((byte) 0x02),
    OP_JUMP((byte) 0x03),

    // Comparison operators
    OP_COMPARE((byte) 0x04),

    // Binary values
    OP_NOT((byte) 0x05),

    // Binary operations
    OP_ADD((byte) 0x06),
    OP_SUB((byte) 0x07),
    OP_MUL((byte) 0x08),
    OP_DIV((byte) 0x09),
    OP_CONCAT((byte) 0x1A),
    OP_LOGICAL_AND((byte) 0x1B),

    OP_LOGICAL_OR((byte) 0x1C),
    // Literals
    OP_LITERAL_STRING((byte) 0x1D),
    OP_LITERAL_LONG((byte) 0x1E),
    OP_LITERAL_BOOLEAN((byte) 0x1F),
    OP_LITERAL_BIGINT((byte) 0x10),

    // For storing and calling functions
    OP_STORE((byte) 0x11),
    OP_LOAD((byte) 0x12),
    OP_READ_ARGUMENT((byte) 0x13),
    OP_CALL((byte) 0x14),

    // Conditionals for jumping
    OP_JUMP_IF_TRUE((byte) 0x15),
    OP_JUMP_IF_FALSE((byte) 0x16),
    OP_JUMP_IF_TRUE_BOOLEAN((byte) 0x17),
    OP_JUMP_IF_FALSE_BOOLEAN((byte) 0x18),

    // Idle operations
    OP_NOP((byte) 0x19),

    // For functions
    OP_FUNCTION_NAME((byte) 0x20),

    // Built-in functions
    OP_BUILTIN((byte) 0x21),
    OP_PRINT((byte) 0x22),
    OP_TYPEOF((byte) 0x23),
    OP_IS_INSTANCE((byte) 0x24),
    OP_NANO_TIME((byte) 0x25),
    OP_STACKTRACE((byte) 0x26),
    OP_EVAL((byte) 0x27),
    OP_DEFINE_FUNCTION((byte) 0x28),
    OP_GET_SIZE((byte) 0x29),
    OP_HAS_SIZE((byte) 0x2A),
    OP_SUB_STRING((byte) 0x2B),
    OP_HAS_PROPERTY((byte) 0x2C),
    OP_DELETE_PROPERTY((byte) 0x2D),
    OP_HELLO_EQUALS_WORLD((byte) 0x2E),
    OP_EXECUTABLE((byte) 0x2F),

    // For object support
    OP_NEW((byte) 0x20),
    OP_GET_PROPERTY((byte) 0x21),
    OP_SET_PROPERTY((byte) 0x22);


    private final byte value;

    /**
     * Constructor for the Opcode enum.
     *
     * @param value The byte value of the opcode.
     */
    Opcode(byte value) {
        this.value = value;
    }

    /**
     * Getter for the value.
     *
     * @return the bytecode value of the opcode
     */
    public byte getValue() {
        return value;
    }
}
