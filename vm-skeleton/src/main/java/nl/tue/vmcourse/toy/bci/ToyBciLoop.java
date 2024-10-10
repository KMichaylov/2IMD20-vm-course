package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.ToyLauncher;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.parser.ToyLangLexer;
import nl.tue.vmcourse.toy.parser.ToyLangParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Interval;

import java.math.BigInteger;
import java.util.*;

public class ToyBciLoop extends ToyAbstractFunctionBody {
    private final Bytecode bytecode;
    private final List<Object> locals;
    private static GlobalScope globalScope;
    private Map<String, Object> stackTraceElements;
    private static Map<String, Map<String, Object>> stackTracePerFunction;
    private static String currentFunctionName;

    /**
     * Bytecode are the bytecode instructions from the generator and locals are all the elements for the local scope
     *
     * @param bytecode
     */
    public ToyBciLoop(Bytecode bytecode, Map<String, Object> stackTraceElements) {
        this.bytecode = bytecode;
        this.locals = new ArrayList<>();
        this.stackTraceElements = stackTraceElements;
        this.currentFunctionName = "main";
        stackTracePerFunction = new LinkedHashMap<>();
        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
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
//        bytecode.printBytecode();
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
                            bytecode.replaceConstantPoolElement(operand, Long.valueOf(String.valueOf(locals.get(frameSlot))));
                        } else {
                            locals.add(frameSlot, stack.pop());
                            // We do this only for array properties, to update the index.
                            if (!(locals.get(frameSlot) instanceof Boolean) && !(locals.get(frameSlot) instanceof String))
                                bytecode.replaceConstantPoolElement(operand, Long.valueOf(String.valueOf(locals.get(frameSlot))));

                        }
                        stackTraceElements.replace(instr.getVariableName(), locals.get(frameSlot));
                        stackTracePerFunction.put(currentFunctionName, stackTraceElements);
                    }

                }
                case OP_ADD -> performArithmeticOperations(stack, "ADD");
                case OP_SUB -> performArithmeticOperations(stack, "SUB");
                case OP_DIV -> performArithmeticOperations(stack, "DIV");
                case OP_MUL -> performArithmeticOperations(stack, "MUL");

                case OP_LOGICAL_AND -> {
                    if (stack.size() < 2) {
                        stack.push(false);
                        break;
                    }
                    Object left = stack.pop();
                    Object right = stack.pop();
                    if (left.equals(true) && right.equals(true)) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }
                }

                case OP_LOGICAL_OR -> {
                    if (stack.size() < 2) {
                        stack.push(true);
                        break;
                    }
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
                    Object propertyName = new Object();
                    if (bytecode.getConstantPoolSize() > 0) {
                        if (bytecode.getElementFromConstantPool(operand) instanceof String) {
                            propertyName = bytecode.getElementFromConstantPool(operand).toString();
                        } else if (bytecode.getElementFromConstantPool(operand) instanceof Long) {
                            propertyName = bytecode.getElementFromConstantPool(operand).toString();
                        }
                    } else {
                        propertyName = stack.pop();
                    }
                    while (!(stack.get(stack.size() - 2) instanceof Map)) {
                        stack.pop();
                    }
                    Object value = stack.pop();
                    Object receiver = stack.pop();
                    if (receiver instanceof Map) {
                        ((Map<String, Object>) receiver).put((String) propertyName, value);
                    } else {
                        System.out.println("Something with setter of the property went wrong...");
                    }

                }

                case OP_GET_PROPERTY -> {
                    if (bytecode.getConstantPoolSize() > 0) {
                        Object propertyName = new Object();
                        if (bytecode.getElementFromConstantPool(operand) instanceof String) {
                            propertyName = bytecode.getElementFromConstantPool(operand).toString();
                        } else if (bytecode.getElementFromConstantPool(operand) instanceof Long) {
                            propertyName = bytecode.getElementFromConstantPool(operand).toString();
                        }
                        while (!(stack.get(stack.size() - 1) instanceof Map)) {
                            stack.pop();
                        }
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
                    if (!((Boolean) stack.peek())) {
                        pc += operand;
                    }
                }
                case OP_JUMP_IF_TRUE -> {
                    if ((Boolean) stack.peek()) {
                        pc += operand;
                    }
                }
                case OP_PRINT -> {
                    Object valueToPrint = stack.pop();
                    System.out.println(valueToPrint);
                }

                case OP_BUILTIN -> {
                    stack.push("Function");
                }
                // TODO: Extract some logic into separate method
                case OP_TYPEOF -> {
                    Object valueToCheckTypeOf = stack.pop();
                    if (valueToCheckTypeOf == null) {
                        stack.push("NULL");
                    } else if (globalScope.getFunction(valueToCheckTypeOf.toString()) != null ||
                            valueToCheckTypeOf.equals("Function")) {
                        stack.push("Function");
                    } else if (valueToCheckTypeOf instanceof Long || valueToCheckTypeOf instanceof BigInteger) {
                        stack.push("Number");
                    } else if (valueToCheckTypeOf instanceof Boolean) {
                        stack.push("Boolean");
                    } else if (valueToCheckTypeOf instanceof String) {
                        stack.push("String");
                    } else if (valueToCheckTypeOf instanceof Map) {
                        stack.push("Object");
                    }
                }
                case OP_IS_INSTANCE -> {
                    Object valueToCheckInstanceOf = stack.pop();
                    Object typeToCompareWith = stack.pop();

                    String returnedValue = checkValueType(valueToCheckInstanceOf);

                    boolean isInstance = returnedValue.equals(typeToCompareWith);

                    stack.push(isInstance);
                }

                case OP_STACKTRACE -> {
                    // TODO: Implement this
                    String stackTrace = generateStackTrace(currentFunctionName);
                    stack.push(stackTrace);
                }
                case OP_NANO_TIME -> {
                    stack.push(System.nanoTime());
                }

                case OP_GET_SIZE -> {
                    Object obj = locals.get(operand);
                    if (obj instanceof Map<?, ?>) {
                        stack.pop();
                        stack.push(((Map<?, ?>) obj).size());
                    }
                    if (obj instanceof String) {
                        stack.pop();
                        stack.push(((String) obj).length());
                    }
                }

                case OP_HAS_SIZE -> {
                    Object obj = stack.pop();
                    if (obj instanceof Map || obj instanceof String) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }

                }

                case OP_SUB_STRING -> {
                    Object endObj = stack.pop();
                    Object startObj = stack.pop();
                    Object strObj = stack.pop();

                    if (strObj instanceof String && startObj instanceof Long && endObj instanceof Long) {
                        int start = Math.toIntExact((Long) startObj);
                        int end = Math.toIntExact((Long) endObj);
                        stack.push(((String) strObj).substring(start, end));
                    } else {
                        System.out.println("Not a string: cannot substring");
                        return null;
                    }
                }

                case OP_HAS_PROPERTY -> {
                    Object propertyName = stack.pop();
                    Object receiver = stack.pop();
                    if (receiver instanceof Map) {
                        stack.push(((Map<?, ?>) receiver).containsKey(propertyName));
                    }
                }

                case OP_DELETE_PROPERTY -> {
                    Object propertyName = stack.pop();
                    Object receiver = stack.pop();
                    if (receiver instanceof Map) {
                        if (stack.push(((Map<?, ?>) receiver).remove(propertyName)) != null) {
                            stack.push(true);
                        } else {
                            stack.push(false);
                        }
                    }
                }


                case OP_EVAL -> {
                    Object obj = stack.pop();
                    Object answer = evalStream(CharStreams.fromString((String) obj));
                    stack.push(answer);
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

                    String functionName;
                    while (!(stack.peek() instanceof String))
                        stack.pop();
                    functionName = (String) stack.pop();
                    RootCallTarget function = globalScope.getFunction(functionName);
                    if (function == null) {
                        throw new RuntimeException("Function not found: " + functionName);
                    }

                    // TODO Error here which throws an error, logic should be overall correct.
                    currentFunctionName = functionName;
                    if (stackTracePerFunction.containsKey(currentFunctionName)) {
                        stackTracePerFunction.remove(currentFunctionName);
                        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
                    } else {
                        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
                    }

                    // Create a new frame with the arguments
                    VirtualFrame newFrame = new VirtualFrame(args);

                    // Invoke the function and push the return value onto the stack
                    Object returnValue = function.invoke(newFrame);
                    stack.push(returnValue);
                    stackTracePerFunction.remove(functionName);
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
     * Method which performs the arithmetic operations.
     *
     * @param stack     where the values are stored
     * @param operation the corresponding arithmetic operation
     */
    private void performArithmeticOperations(Stack<Object> stack, String operation) {
        Object right = stack.pop();
        Object left = stack.pop();
        Object result = switch (operation) {
            case "ADD" -> add(left, right);
            case "SUB" -> subtract(left, right);
            case "DIV" -> divide(left, right);
            case "MUL" -> multiply(left, right);
            default -> throw new RuntimeException("TODO");
        };
        if (result != null) {
            stack.push(result);
        }
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
    // TODO: Extract this logic
    private String checkValueType(Object value) {
        if (value == null) {
            return "NULL";
        } else if (globalScope.getFunction(value.toString()) != null) {
            return "Function";
        } else if (value instanceof Long || value instanceof BigInteger) {
            return "Number";
        } else if (value instanceof Boolean) {
            return "Boolean";
        } else if (value instanceof String) {
            return "String";
        } else if (value instanceof Map) {
            return "Object";
        }
        return "NULL";
    }


    // TODO Change the place of this!

    /**
     * It applies the eval function on this code.
     *
     * @param charStream the actual program.
     * @return the result of the program after execution.
     */
    public static Object evalStream(CharStream charStream) {
        String src = charStream.getText(Interval.of(0, charStream.size()));
        ToyLangLexer lex = new ToyLangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ToyLangParser parser = new ToyLangParser(tokens);
        ToyNodeFactory factory = new ToyNodeFactory(src);
        parser.setFactory(factory);
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.toylanguage();

        Map<String, RootCallTarget> allFunctions = factory.getAllFunctions();

        for (Map.Entry<String, RootCallTarget> entry : allFunctions.entrySet()) {
            globalScope.registerFunction(entry.getKey(), entry.getValue());
        }

        if (!allFunctions.isEmpty()) {
            RootCallTarget functionToEvaluate = allFunctions.values().iterator().next();
            return functionToEvaluate.invoke(globalScope);
        }
        return null;
    }


    /**
     * Generates the string represantation of the stack trace
     *
     * @param functionName the name of the current function in the frame scope
     * @return the string representation
     */
    public String generateStackTrace(String functionName) {
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<String, Map<String, Object>>> entryList = new ArrayList<>(stackTracePerFunction.entrySet());
        if (stackTracePerFunction.get(functionName).isEmpty() && !functionName.equals("main")) {
            for (int i = stackTracePerFunction.size() - 1; i >= 0; i--) {
                Map.Entry<String, Map<String, Object>> entry = entryList.get(i);
                sb.append("Frame: root ").append(entry.getKey()).append("\n");
            }
            stackTracePerFunction.put("main", new LinkedHashMap<>());
        } else {
            sb.append("Frame: root ").append(functionName);
            for (Map.Entry<String, Object> entry : stackTraceElements.entrySet()) {
                String varName = entry.getKey();
                Object value = entry.getValue();
                sb.append(", ").append(varName).append("=").append(value == null ? "null" : value.toString());
            }
        }
        return sb.toString().trim();
    }

}
