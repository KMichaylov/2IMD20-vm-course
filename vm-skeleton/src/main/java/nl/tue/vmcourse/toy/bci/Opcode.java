package nl.tue.vmcourse.toy.bci;

//TODO: Look at this again, then move to the Instruction file and see how it can be useful.
//TODO: Look at the opcode and see what is necessary and what not. Check other resources and see what other people are doing
//TODO: Add javadoc

//TODO: Maybe create a separate opcode for while loops
// TODO: Add some port have a sequential order for the opcodes
public enum Opcode {
    OP_TYPEOF((byte) 0x01),
    OP_RETURN((byte) 0x02),
    OP_PRINT((byte) 0x03),
    OP_LOOP((byte) 0x04),
    OP_JUMP((byte) 0x05),
    // Comparison operators
    OP_COMPARE((byte) 0x06),
    // Binary values
    OP_NOT((byte) 0x30),
    OP_NULL((byte) 0x0D),
    OP_TRUE((byte) 0x0E),
    OP_FALSE((byte) 0x0F),
    // Binary operations
    OP_ADD((byte) 0x10),
    OP_SUB((byte) 0x11),
    OP_MUL((byte) 0x12),
    OP_DIV((byte) 0x13),
    OP_CONCAT((byte) 0x14),
    OP_LOGICAL_AND((byte) 0x23),
    OP_LOGICAL_OR((byte) 0x24),
    // Literals
    OP_LITERAL_STRING((byte) 0x15),
    OP_LITERAL_LONG((byte) 0x20),
    OP_LITERAL_BOOLEAN((byte) 0x21),
    OP_LITERAL_BIGINT((byte) 0x22),

    // For storing and calling functions
    OP_STORE((byte) 0x16),
    OP_LOAD((byte) 0x01),
    OP_CALL((byte) 0x17),

    // Conditionals for jumping
    OP_JUMP_IF_TRUE((byte) 0x19),
    OP_JUMP_IF_FALSE((byte) 0x1A),

    // Idle operations
    OP_NOP((byte) 0x1B),

    // For functions
    OP_FUNCTION_NAME((byte) 0x1E),

    // Built-in functions
    OP_IS_INSTANCE((byte) 0x1C),
    OP_NANO_TIME((byte) 0x1D);


    private final byte value;

    Opcode(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
