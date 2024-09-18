package nl.tue.vmcourse.toy.bci;

public enum Opcode {
    OP_LOAD((byte) 0x01),
    OP_RETURN((byte) 0x02),
    OP_PRINT((byte) 0x03),
    OP_LOOP((byte) 0x04),
    OP_JUMP((byte) 0x05),
    OP_POP((byte) 0x06),
    OP_PUSH((byte) 0x07),
    // Comparisons
    OP_NOT((byte) 0x08),
    OP_NOT_EQUAL((byte) 0x09),
    OP_EQUAL((byte) 0x0A),
    OP_GREATER((byte) 0x0B),
    OP_LESS((byte) 0x0C),
    // Binary values
    OP_NULL((byte) 0x0D),
    OP_TRUE((byte) 0x0E),
    OP_FALSE((byte) 0x0F),
    // Binary operations
    OP_ADD((byte) 0x10),
    OP_SUB((byte) 0x11),
    OP_MUL((byte) 0x12),
    OP_DIV((byte) 0x13),
    // Literals
    OP_CONSTANT((byte) 0x14);

    private final byte value;

    Opcode(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
