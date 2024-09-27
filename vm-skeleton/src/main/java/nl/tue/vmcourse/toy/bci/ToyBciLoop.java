package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ast.ToyFunctionLiteralNode;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// This class really needs rewriting
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
        this.locals = new ArrayList<>();
    }

    private Object resolveArgument(Object arg) {
        while (arg instanceof VirtualFrame) {
            arg = ((VirtualFrame) arg).getArguments()[0];
        }
        return arg;
    }

    /**
     * The following function executes the bytecode which is stored on the frame
     *
     * @param frame place where bytecode is stored
     * @return TODO
     */
    public Object execute(VirtualFrame frame) {
        Stack<Object> stack = new Stack<>();
        // Check if there are arguments on the frame and add them to the locals if yes

        // TODO: PROBLEM IS RELATED TO HOW VARIABLES ARE PASSED!!!!
        if (frame.getArguments() != null) {
            locals.clear();
            for (int i = 0; i < frame.getArguments().length; i++) {
                Object arg = frame.getArguments()[i];
                locals.add(resolveArgument(arg));
            }
        }
        int pc = 0;
//        TODO: Idea: I think the general structure of the bytecode is problematic, currently, it does not make sense how things are organized and ordered. Check the whole logic
        bytecode.printBytecode();
        while (pc < bytecode.getSize()) {
            Instruction instr = bytecode.getInstruction(pc);
            Opcode opcode = instr.opcode();
            int operand = instr.operand();
            // TODO: remove duplicates
            // TODO: refactor the switch statement. Export common logic into separate methods, after the general structure is there
            // TODO!!!!Currently, the mistake is in the JumpIfFalse construction, so the control flow.
            // TODO: Fix throwing of errors with something else
            switch (opcode) {
                case OP_LITERAL_STRING, OP_FUNCTION_NAME -> {
                    String stringLiteral = (String) bytecode.getElementFromConstantPool(operand);
                    stack.push(stringLiteral);
                }
                case OP_LITERAL_LONG -> {
                    Long longLiteral = (Long) bytecode.getElementFromConstantPool(operand);
                    stack.push(longLiteral);
                }
                case OP_LITERAL_BOOLEAN -> {
                    Boolean booleanLiteral = (Boolean) bytecode.getElementFromConstantPool(operand);
                    stack.push(booleanLiteral);
                }
                case OP_LITERAL_BIGINT -> {
                    BigInteger bigIntegerLiteral = (BigInteger) bytecode.getElementFromConstantPool(operand);
                    stack.push(bigIntegerLiteral);
                }
                case OP_STORE -> {
                    locals.add(operand, stack.pop());
                }
//                TODO: Fix this bad nesting of if/else blocks, try another way to organize the code, since currently it is quite bad
                case OP_ADD -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    // Might arrise a problem if one of the numbers is considered int, generally this vague Object idea is not the best!
                    if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
                        stack.push(((Number) left).intValue() + ((Number) right).intValue());
                    } else if (left instanceof BigInteger && right instanceof BigInteger) {
                        stack.push(((BigInteger) left).add((BigInteger) right));
                    } else if (left instanceof BigInteger && right instanceof Long) {
                        stack.push(((BigInteger) left).add(BigInteger.valueOf((Long) right)));
                    } else if (left instanceof Long && right instanceof BigInteger) {
                        stack.push((BigInteger.valueOf((Long) left)).add((BigInteger) right));
                    } else {
                        // TODO throw corresponding error:
                        throw new RuntimeException("TODO");
                    }
                }
//                TODO: For each operation, if one number is BigInt, we just perform the operations corresponding to it.
                case OP_SUB -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Long && right instanceof Long) {
                        stack.push(((Number) left).intValue() - ((Number) right).intValue());
                    } else if (left instanceof BigInteger && right instanceof BigInteger) {
                        stack.push(((BigInteger) left).subtract((BigInteger) right));
                    } else if (left instanceof BigInteger && right instanceof Long) {
                        stack.push(((BigInteger) left).subtract(BigInteger.valueOf((Long) right)));
                    } else if (left instanceof Long && right instanceof BigInteger) {
                        stack.push((BigInteger.valueOf((Long) left)).subtract((BigInteger) right));
                    } else {
                        // TODO throw corresponding error:
                        throw new RuntimeException("TODO");
                    }
                }
                case OP_DIV -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Long && right instanceof Long && !right.toString().equals("0")) {
                        stack.push(((Number) left).intValue() / ((Number) right).intValue());
                    } else if (left instanceof BigInteger && right instanceof BigInteger) {
                        stack.push(((BigInteger) left).divide((BigInteger) right));
                    } else if (left instanceof BigInteger && right instanceof Long) {
                        stack.push(((BigInteger) left).divide(BigInteger.valueOf((Long) right)));
                    } else if (left instanceof Long && right instanceof BigInteger) {
                        stack.push((BigInteger.valueOf((Long) left)).divide((BigInteger) right));
                    }
                }
                case OP_MUL -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (left instanceof Long && right instanceof Long) {
                        stack.push(((Number) left).intValue() * ((Number) right).intValue());
                    } else if (left instanceof BigInteger && right instanceof BigInteger) {
                        stack.push(((BigInteger) left).multiply((BigInteger) right));
                    } else if (left instanceof BigInteger && right instanceof Long) {
                        stack.push(((BigInteger) left).multiply(BigInteger.valueOf((Long) right)));
                    } else if (left instanceof Long && right instanceof BigInteger) {
                        stack.push((BigInteger.valueOf((Long) left)).multiply((BigInteger) right));
                    }
                }
                // Have a relative jump, based on the given bytecode
                case OP_JUMP -> {
                    pc += operand;
                }
                case OP_JUMP_IF_FALSE -> {
                    if (!((Boolean) stack.pop())) {
                        //if (!(Boolean) stack.peek()) {
                        pc += operand;
                    }
                }
                case OP_PRINT -> {
                    // System.out.println(locals.getLast());
                    Object valueToPrint = stack.pop();
                    System.out.println(valueToPrint);
                }
                // TODO: Extract some logic into separate method
                // TODO: Later add support for Object and Function
                case OP_TYPEOF -> {
                    Object valueToCheckTypeOf = stack.pop();
                    if (valueToCheckTypeOf instanceof Long || valueToCheckTypeOf instanceof BigInteger) {
                        stack.push("Number");
                    } else if (valueToCheckTypeOf instanceof Boolean) {
                        stack.push("Boolean");
                    } else if (valueToCheckTypeOf instanceof String) {
                        stack.push("String");
                    } else {
                        stack.push("NULL");
                    }
                }
                case OP_IS_INSTANCE -> {
                    Object valueToCheckInstanceOf = stack.pop();
                    Object typeToCompareWith = stack.pop();

                    String returnedValue = checkValueType(valueToCheckInstanceOf);

                    boolean isInstance = returnedValue.equals(typeToCompareWith);

                    stack.push(isInstance);
                }
                case OP_NANO_TIME -> {
                    stack.push(System.nanoTime());
                }
                case OP_COMPARE -> {
                    Object right = stack.pop();
                    Object left = stack.pop();

                    boolean result = false;
                    switch (operand) {
                        case 0: // ==
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() == ((Number) right).longValue();
                            } else if (left instanceof String && right instanceof String) {
                                result = left.equals(right);
                            } else {
                                throw new RuntimeException("Invalid comparison types for '=='.");
                            }
                            break;
                        case 1: // !=
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() != ((Number) right).longValue();
                            } else if (left instanceof String && right instanceof String) {
                                result = !left.equals(right);
                            } else {
                                throw new RuntimeException("Invalid comparison types for '!='.");
                            }
                            break;
                        case 2: // <
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() < ((Number) right).longValue();
                            } else {
                                throw new RuntimeException("Cannot compare non-numeric values with '<'.");
                            }
                            break;
                        case 3: // <=
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() <= ((Number) right).longValue();
                            } else {
                                throw new RuntimeException("Cannot compare non-numeric values with '<='.");
                            }
                            break;
                        case 4: // >
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() > ((Number) right).longValue();
                            } else {
                                throw new RuntimeException("Cannot compare non-numeric values with '>'.");
                            }
                            break;
                        case 5: // >=
                            if (left instanceof Number && right instanceof Number) {
                                result = ((Number) left).longValue() >= ((Number) right).longValue();
                            } else {
                                throw new RuntimeException("Cannot compare non-numeric values with '>='.");
                            }
                            break;
                        default:
                            throw new RuntimeException("Unknown comparison type");
                    }

                    stack.push(result);
                }
                case OP_NOT -> {
                    if (!stack.isEmpty()) {
                        Object value = stack.pop();
                        // We just negate the boolean value
                        if (value instanceof Boolean) {
                            boolean negated = !(Boolean) value;
                            stack.push(negated);
                        }
                    }
                }
                case OP_LOAD -> {
                    Object value = locals.get(operand);
                    stack.push(value);
                }
                // TODO: Check how to handle function
                case OP_CALL -> {
                    int numberOfFunctionArguments = operand;

                    // Pop arguments from the stack
                    Object[] args = new Object[numberOfFunctionArguments];
                    for (int i = numberOfFunctionArguments - 1; i >= 0; i--) {
                        args[i] = stack.pop();
                    }

                    String functionName = (String) stack.pop();

                    GlobalScope globalScope = (GlobalScope) frame.getArguments()[0];

                    RootCallTarget function = globalScope.getFunction(functionName);
                    if (function == null) {
                        throw new RuntimeException("Function not found: " + functionName);
                    }

                    // Create a copy of locals that will be passed to invoke function, so that we don't have the modifying problem.

                    // Create a new frame with the arguments
                    VirtualFrame newFrame = new VirtualFrame(args);

                    // Invoke the function and push the return value onto the stack
                    Object returnValue = function.invoke(newFrame);
                    stack.push(returnValue);
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
            pc++;

        }
        // TODO: decide what to return in the end;
        return null;
    }


    /**
     * Utility function to check the type of the variable (Number, Boolean, String, etc.)
     */
//    TODO: Probably create a custom class. Also export common logic from the previous function.
    private String checkValueType(Object value) {
        if (value instanceof Long || value instanceof BigInteger) {
            return "Number";
        } else if (value instanceof Boolean) {
            return "Boolean";
        } else if (value instanceof String) {
            return "String";
        } else {
            return "NULL";
        }
    }

}
