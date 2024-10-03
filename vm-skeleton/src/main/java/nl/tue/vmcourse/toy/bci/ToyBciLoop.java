package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;

import java.math.BigInteger;
import java.util.*;

public class ToyBciLoop extends ToyAbstractFunctionBody {
    private final Bytecode bytecode;
    private final List<Object> locals;
    private static GlobalScope globalScope;

    /**
     * Bytecode are the bytecode instructions from the generator and locals are all the elements for the local scope
     *
     * @param bytecode
     */
    public ToyBciLoop(Bytecode bytecode) {
        this.bytecode = bytecode;
        this.locals = new ArrayList<>();
    }

    /**
     * Add the function arguments
     *
     * @param extractedArgument the argument to be extracted
     * @return the value of the argument
     */
    private List<Object> resolveArgument(Object extractedArgument) {
        List<Object> arguments = new ArrayList<>();
        if (extractedArgument instanceof VirtualFrame) {
            if (((VirtualFrame) extractedArgument).getArguments().length == 0) {
                return null;
            }
            if (((VirtualFrame) extractedArgument).getArguments().length > 0) {
                arguments.addAll(Arrays.asList(((VirtualFrame) extractedArgument).getArguments()));
            }
        }
        return arguments;
    }

    /**
     * The following function executes the bytecode which is stored on the frame
     *
     * @param frame place where bytecode is stored
     * @return TODO
     */
    public Object execute(VirtualFrame frame) {
        Stack<Object> stack = new Stack<>();
        locals.clear();

        if (frame.getArguments() != null) {
            for (int i = 0; i < frame.getArguments().length; i++) {
                Object arg = frame.getArguments()[i];
                List<Object> argumentsToBeAdded = resolveArgument(arg);
                if (argumentsToBeAdded != null && !argumentsToBeAdded.isEmpty()) {
                    locals.addAll(argumentsToBeAdded);
                }
            }
        }
        int pc = 0;
        if (frame.getArguments().length > 0 && frame.getArguments()[0] instanceof GlobalScope) {
            globalScope = (GlobalScope) frame.getArguments()[0];
        }
        bytecode.printBytecode();
        while (pc < bytecode.getSize()) {
            Instruction instr = bytecode.getInstruction(pc);
            Opcode opcode = instr.getOpcode();
            Integer frameSlot = instr.getFrameSlot();
            int operand = instr.getOperand();
            // TODO: refactor the switch statement. Export common logic into separate methods, after the general structure is there
            // TODO!!!!Currently, the mistake is in the JumpIfFalse construction, so the control flow.
            // TODO: Fix throwing of errors with something else
            switch (opcode) {
                case OP_LITERAL_STRING, OP_FUNCTION_NAME -> pushLiteralToStack(bytecode, operand, stack, String.class);
                case OP_LITERAL_LONG -> pushLiteralToStack(bytecode, operand, stack, Long.class);
                case OP_LITERAL_BOOLEAN -> pushLiteralToStack(bytecode, operand, stack, Boolean.class);
                case OP_LITERAL_BIGINT -> pushLiteralToStack(bytecode, operand, stack, BigInteger.class);
                case OP_STORE -> {
                    if (frameSlot != null && !stack.isEmpty()) {
                        if (stack.peek() instanceof Map) {
                            Map map = (Map) stack.pop();
                            map.replace(instr.getVariableName(), new Object());
                            locals.add(map);
                        } else if (frameSlot < locals.size()) {
                            locals.set(frameSlot, stack.pop());
                        } else {
                            locals.add(frameSlot, stack.pop());
                        }
                    }

                }
                case OP_ADD -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    Object answer = add(left, right);
                    if (answer != null) {
                        stack.push(answer);
                    }
                }
                case OP_SUB -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    Object answer = subtract(left, right);
                    if (answer != null) {
                        stack.push(answer);
                    }
                }
                case OP_DIV -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    Object answer = divide(left, right);
                    if (answer != null) {
                        stack.push(answer);
                    }
                }
                case OP_MUL -> {
                    Object right = stack.pop();
                    Object left = stack.pop();
                    Object answer = multiply(left, right);
                    if (answer != null) {
                        stack.push(answer);
                    }
                }

                case OP_LOGICAL_AND -> {
                    Object left = stack.pop();
                    Object right = stack.pop();
                    if (left.equals(true) && right.equals(true)) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }
                }

                case OP_LOGICAL_OR -> {
                    Object left = stack.pop();
                    Object right = stack.pop();
                    if (left.equals(true) || right.equals(true)) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }
                }

                case OP_NEW -> {
                    Map<String, Object> newObject = new HashMap<>();
                    stack.push(newObject);
                }

                case OP_SET_PROPERTY -> {
                    String propertyName;
                    if (bytecode.getConstantPoolSize() > 0) {
                        propertyName = (String) bytecode.getElementFromConstantPool(operand);
                    } else {
                        propertyName = (String) stack.pop();
                    }
                    Object value = stack.pop();
                    Object receiver = stack.pop();
                    if (receiver instanceof Map) {
                        ((Map<String, Object>) receiver).put(propertyName, value);
                    } else {
                        System.out.println("Something with setter of the property went wrong...");
                    }
                }

                case OP_GET_PROPERTY -> {
                    if (bytecode.getConstantPoolSize() > 0) {
                        String propertyName = (String) bytecode.getElementFromConstantPool(operand);
                        Object receiver = stack.pop();
                        if (receiver instanceof Map) {
                            stack.push(((Map<?, ?>) receiver).get(propertyName));
                        } else {
                            System.out.println("Something with the getter of the object property went wrong...");
                        }
                    } else {
                        String propertyName = (String) stack.pop();
                        Object receiver = stack.pop();
                        if (receiver instanceof Map) {
                            stack.push(((Map<?, ?>) receiver).get(propertyName));
                        }
                    }

                }

                case OP_NOP -> {
                }
                case OP_JUMP -> {
                    pc += operand;
                }
                case OP_JUMP_IF_FALSE -> {
                    if (!((Boolean) stack.pop())) {
                        pc += operand;
                    }
                }
                case OP_PRINT -> {
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

                    boolean result = switch (operand) {
                        case 0 -> equalsComparison(left, right);            // for ==
                        case 1 -> !equalsComparison(left, right);           // for !=
                        case 2 -> lessThanComparison(left, right);          // for <
                        case 3 -> lessThanOrEqualComparison(left, right);   // for <=
                        case 4 -> greaterThanComparison(left, right);       // for >
                        case 5 -> greaterThanOrEqualComparison(left, right);// for >=
                        // TODO: Check later what exactly to do with default, what error to throw
                        default -> throw new RuntimeException("Unknown comparison type");
                    };

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
                    if (frameSlot != null) {
                        Object value = locals.get(frameSlot);
                        stack.push(value);
                    }
                }
                // TODO: Check how to handle function
                case OP_CALL -> {
                    int numberOfFunctionArguments = operand;

                    Object[] args = new Object[numberOfFunctionArguments];
                    for (int i = numberOfFunctionArguments - 1; i >= 0; i--) {
                        args[i] = stack.pop();
                    }

                    String functionName = (String) stack.pop();

                    RootCallTarget function = globalScope.getFunction(functionName);
                    if (function == null) {
                        throw new RuntimeException("Function not found: " + functionName);
                    }

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
     * Pushes the literal value to the stack.
     *
     * @param bytecode the array where all instructions are stored
     * @param operand  the operand of the instruction, in this case used for index of element in constant pool
     * @param stack    the stack where the values are stored
     * @param type     the type of the literal value
     * @param <T>      the generic which allows for type casting
     */
    private <T> void pushLiteralToStack(Bytecode bytecode, int operand, Stack<Object> stack, Class<T> type) {
        T literalValue = type.cast(bytecode.getElementFromConstantPool(operand));
        stack.push(literalValue);
    }

    /**
     * A helper method to add the two values together.
     *
     * @param left  the part of the addition
     * @param right the right part of the addition
     * @return the sum, or concatenation in case one of the values is a string
     */
    private Object add(Object left, Object right) {
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
            return ((Number) left).intValue() + ((Number) right).intValue();
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).add((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).add(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).add((BigInteger) right);
        } else if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        } else {
            throw new RuntimeException("Unsupported types for addition");
        }
    }


    /**
     * A helper method to subtract the two values together.
     *
     * @param left  the part of the subtraction
     * @param right the right part of the subtraction
     * @return the difference of the two values
     */
    private Object subtract(Object left, Object right) {
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
            return ((Number) left).intValue() - ((Number) right).intValue();
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).subtract((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).subtract(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).subtract((BigInteger) right);
        } else {
            throw new RuntimeException("Unsupported types for subtraction");
        }
    }

    /**
     * A helper method to multiply the two values together.
     *
     * @param left  the part of the product
     * @param right the right part of the product
     * @return the product of the two values
     */
    private Object multiply(Object left, Object right) {
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
            return ((Number) left).intValue() * ((Number) right).intValue();
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).multiply((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).multiply(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).multiply((BigInteger) right);
        } else {
            throw new RuntimeException("Unsupported types for multiplication");
        }
    }

    /**
     * A helper method to divide the two values.
     *
     * @param left  the part of the division
     * @param right the right part of the division, cannot be null
     * @return the division of the two values
     */
    private Object divide(Object left, Object right) {
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger) && !right.toString().equals("0")) {
            return ((Number) left).intValue() / ((Number) right).intValue();
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).divide((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).divide(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).divide((BigInteger) right);
        } else {
            throw new RuntimeException("Unsupported types for division");
        }
    }


    /**
     * Compares for equality the two values.
     *
     * @param left  value
     * @param right value
     * @return true if equal, otherwise false
     */
    private boolean equalsComparison(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return numericEquals((Number) left, (Number) right);
        } else if (left instanceof String && right instanceof String) {
            return left.equals(right);
        } else if (left instanceof Boolean && right instanceof Boolean) {
            return left.equals(right);
        }
        throw new RuntimeException("Invalid comparison types for '=='.");
    }

    /**
     * Compares for less than the two values.
     *
     * @param left  value
     * @param right value
     * @return true if left is less than the right, otherwise false
     */
    private boolean lessThanComparison(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return numericCompare((Number) left, (Number) right) < 0;
        }
        throw new RuntimeException("Cannot compare non-numeric values with '<'.");
    }

    /**
     * Compares for less than or equal the two values.
     *
     * @param left  value
     * @param right value
     * @return true if left is less than or equal the right, otherwise false
     */
    private boolean lessThanOrEqualComparison(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return numericCompare((Number) left, (Number) right) <= 0;
        }
        throw new RuntimeException("Cannot compare non-numeric values with '<='.");
    }

    /**
     * Compares for greater than the two values.
     *
     * @param left  value
     * @param right value
     * @return true if left is greater than the right, otherwise false
     */
    private boolean greaterThanComparison(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return numericCompare((Number) left, (Number) right) > 0;
        }
        throw new RuntimeException("Cannot compare non-numeric values with '>'.");
    }

    /**
     * Compares for greater than or equal the two values.
     *
     * @param left  value
     * @param right value
     * @return true if left is greater than or equal the right, otherwise false
     */
    private boolean greaterThanOrEqualComparison(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return numericCompare((Number) left, (Number) right) >= 0;
        }
        throw new RuntimeException("Cannot compare non-numeric values with '>='.");
    }


    /**
     * Compare two numeric values taking into account their types.
     *
     * @param left  value
     * @param right value
     * @return -1 if left is less than right, 0 if equal, 1 if greater
     */
    private int numericCompare(Number left, Number right) {
        if (left instanceof BigInteger) {
            if (right instanceof BigInteger) {
                return ((BigInteger) left).compareTo((BigInteger) right);
            } else if (right instanceof Long) {
                return ((BigInteger) left).compareTo(BigInteger.valueOf((Long) right));
            } else {
                throw new IllegalArgumentException("Incompatible types for comparison: " + right.getClass().getName());
            }
        } else if (left instanceof Long) {
            if (right instanceof BigInteger) {
                return -((BigInteger) right).compareTo(BigInteger.valueOf((Long) left));
            } else if (right instanceof Long) {
                return Long.compare((Long) left, (Long) right);
            } else {
                System.out.println("Incompatible types");
            }
        } else {
            if (left == null || right == null) {
                // TODO: later add custom logic here
                System.out.println("Cannot compare if one or two are null values");
            }
            return Long.compare(left.longValue(), right.longValue());
        }
        return Long.compare(left.longValue(), right.longValue());
    }


    /**
     * Compares for equality the two numeric values taking into account their types.
     *
     * @param left  value
     * @param right value
     * @return true if equal, otherwise false
     */
    private boolean numericEquals(Number left, Number right) {
        if (left instanceof BigInteger) {
            if (right instanceof BigInteger) {
                return left.equals(right);
            } else if (right instanceof Long) {
                return left.equals(BigInteger.valueOf((Long) right));
            } else {
                //TODO
                System.out.println("Incompatible types");
            }
        } else if (left instanceof Long) {
            if (right instanceof BigInteger) {
                return BigInteger.valueOf((Long) left).equals(right);
            } else if (right instanceof Long) {
                return left.longValue() == right.longValue();
            } else {
//                Todo
                System.out.println("Incompatible types");
            }
        } else {
            if (left == null || right == null) {
                // TODO
                System.out.println("Cannot compare if one or two are null values");
            }
            return left.longValue() == right.longValue();
        }
        return left.longValue() == right.longValue();
    }


    /**
     * Utility function to check the type of the variable (Number, Boolean, String, etc.).
     *
     * @param value to be checked
     */
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
