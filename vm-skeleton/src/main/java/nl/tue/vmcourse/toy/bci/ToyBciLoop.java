package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ToyBciLoop extends ToyAbstractFunctionBody {
    private final Bytecode bytecode;
    private final List<Object> locals;

    /**
     * Bytecode are the bytecode instructions from the generator and locals are all the elements for the local scope
     *
     * @param bytecode
     */
    public ToyBciLoop(Bytecode bytecode) {
        this.bytecode = bytecode;
        this.locals = new ArrayList<Object>();
    }

    /**
     * The following function executes the bytecode which is stored on the frame
     *
     * @param frame place where bytecode is stored
     * @return TODO
     */
    public Object execute(VirtualFrame frame) {
        Stack<Object> stack = new Stack<>();
        int pc = 0;

//        TODO: Idea: I think the general structure of the bytecode is problematic, currently, it does not make sense how things are organized and ordered. Check the whole logic
        while (pc < bytecode.getSize()) {
            bytecode.printBytecode();
            Instruction instr = bytecode.getInstruction(pc);
            Opcode opcode = instr.opcode();
            int operand = instr.operand();
            pc++;

            switch (opcode) {
                case OP_CONSTANT -> {
                    stack.push(operand);
                }
                case OP_ADD -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Number && right instanceof Number) {
                        stack.push(((Number) left).intValue() + ((Number) right).intValue());
                    } else {
                        // TODO throw corresponding error:
                        throw new RuntimeException("TODO");
                    }
                }
                case OP_STORE -> {
                    locals.add(operand, stack.pop());
                }
                case OP_SUB -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Number && right instanceof Number) {
                        stack.push(((Number) left).intValue() - ((Number) right).intValue());
                    } else {
                        // TODO throw corresponding error:
                        throw new RuntimeException("TODO");
                    }
                }
                case OP_DIV -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Number && right instanceof Number && !right.toString().equals("0")) {
                        stack.push(((Number) left).intValue() / ((Number) right).intValue());
                    }
                }
                case OP_MUL -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Number && right instanceof Number) {
                        stack.push(((Number) left).intValue() * ((Number) right).intValue());
                    }
                }
                case OP_JUMP -> pc = operand;
                case OP_JUMP_IF_FALSE -> {
                    if (!((Boolean) stack.pop())) {
                        pc = operand;
                    }
                }
                case OP_PRINT -> {
                    System.out.println(locals.getLast());
                }
                case OP_LOAD -> {
                    Object value = locals.get(operand);
                    stack.push(value);
                }
                case OP_RETURN -> {
                    if (!stack.isEmpty()) {
                        return stack.pop();
                    } else {
                        System.out.println("Stack is empty");
                        return null;
                    }
                }
                default -> {
                    // TODO throw corresponding error:
                    throw new RuntimeException("TODO");
                }
            }
        }
        // return whatever;
        return null;
    }

}
