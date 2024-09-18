package nl.tue.vmcourse.toy.bci;

public enum Opcode {
    OP_LOAD,
    OP_RETURN,
    OP_PRINT,
    OP_LOOP,
    OP_JUMP,
    OP_POP,
    OP_PUSH,
    // Comparisons
    OP_NOT,
    OP_NOT_EQUAL,
    OP_EQUAL,
    OP_GREATER,
    OP_LESS,
    // Binary values
    OP_NULL,
    OP_TRUE,
    OP_FALSE,
    // Binary operations
    OP_ADD,
    OP_SUB,
    OP_MUL,
    OP_DIV,
    // Literals
    OP_CONSTANT,
}
