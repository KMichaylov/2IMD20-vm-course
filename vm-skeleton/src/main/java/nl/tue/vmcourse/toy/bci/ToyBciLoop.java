package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.interpreter.ToySyntaxErrorException;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.optimization.StringRopes;
import nl.tue.vmcourse.toy.parser.ToyLangLexer;
import nl.tue.vmcourse.toy.parser.ToyLangParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Interval;

import java.math.BigInteger;
import java.util.*;

import static nl.tue.vmcourse.toy.ToyLauncher.ROPES_ENABLED;

public class ToyBciLoop extends ToyAbstractFunctionBody {
    public static final int STACKOVERFLOW_THRESHOLD = 255;
    private final Bytecode bytecode;
    private HashMap<Integer, List<Object>> locals;
    private static GlobalScope globalScope;
    private static int currentDepth;
    private Map<String, Object> stackTraceElements;
    private static Map<String, Map<String, Object>> stackTracePerFunction;
    private static String currentFunctionName;
    private static String previousFunctionName;
    private static StringBuilder consoleMessages = new StringBuilder();
    private ErrorMessages errorMessages;
    private static Map<Object, Integer> tableWithVariables = new HashMap<>();
    private int currentFrameSlot;
    private boolean isEval;

    /**
     * Bytecode are the bytecode instructions from the generator and locals are all the elements for the local scope
     *
     * @param bytecode
     */
    public ToyBciLoop(Bytecode bytecode, Map<String, Object> stackTraceElements, boolean isEval) {
        this.bytecode = bytecode;
        this.locals = new HashMap<>();
        this.stackTraceElements = stackTraceElements;
        this.currentFunctionName = "main";
        this.previousFunctionName = "";
        stackTracePerFunction = new LinkedHashMap<>();
        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
        currentDepth = 0;
        errorMessages = new ErrorMessages();
        this.isEval = isEval;
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
        Stack<Object> functionArguments = new Stack<>();
        this.locals.put(currentDepth, frame.getLocals());

        if (frame.getArguments() != null) {
            for (int i = 0; i < frame.getArguments().length; i++) {
                Object arg = frame.getArguments()[i];
                List<Object> argumentsToBeAdded = resolveArgument(arg);
                if (argumentsToBeAdded != null && !argumentsToBeAdded.isEmpty()) {
                    locals.get(currentDepth).addAll(argumentsToBeAdded);
                }
            }
        }
        int pc = 0;
        if (frame.getArguments().length > 0 && frame.getArguments()[0] instanceof GlobalScope) {
            globalScope = (GlobalScope) frame.getArguments()[0];
            for (String currentFunctionName : globalScope.getAllFunctions().keySet()) {
                globalScope.setFunctionToNumberOfArguments(currentFunctionName, globalScope.getNumberOfArgumentsForFunction(currentFunctionName));
            }
        }
//        bytecode.printBytecode();
        while (pc < bytecode.getSize()) {
            Instruction instr = bytecode.getInstruction(pc);
            Opcode opcode = instr.getOpcode();
            Integer frameSlot = instr.getFrameSlot();
            int operand = instr.getOperand();
            // TODO: refactor the switch statement. Export common logic into separate methods, after the general structure is there
            // TODO: Fix throwing of errors with something else
            switch (opcode) {
                case OP_LITERAL_STRING -> {
                    if (ROPES_ENABLED) {
                        pushLiteralToStack(bytecode, operand, stack, StringRopes.class);
                    } else {
                        pushLiteralToStack(bytecode, operand, stack, String.class);
                    }
                }
                case OP_LITERAL_LONG -> pushLiteralToStack(bytecode, operand, stack, Long.class);
                case OP_LITERAL_BOOLEAN -> pushLiteralToStack(bytecode, operand, stack, Boolean.class);
                case OP_LITERAL_BIGINT -> pushLiteralToStack(bytecode, operand, stack, BigInteger.class);
                case OP_FUNCTION_NAME -> {
                    String literalValue = (String) bytecode.getElementFromConstantPool(operand);
                    stack.push(literalValue);
                    if (tableWithVariables.containsKey(literalValue) && currentFrameSlot != tableWithVariables.get(literalValue) && globalScope.getFunction(literalValue) == null) {
                        stack.push(null);
                    }
                }
                case OP_LOAD -> {
                    if (locals.get(currentDepth).isEmpty())
                        break;
                    if (frameSlot != null) {
                        currentFrameSlot = frameSlot;
                        Object value = frameSlot < locals.get(currentDepth).size() ? locals.get(currentDepth).get(frameSlot) : "NULL";
                        stack.push(value);
                        tableWithVariables.put(instr.getVariableName(), frameSlot);
                    }
                }

                case OP_READ_ARGUMENT -> {
                    if (locals.get(currentDepth).isEmpty()) {
//                        globalScope.increaseFunctionToNumberOfArguments(currentFunctionName);
                        if (globalScope.getNumberOfArgumentsForFunction(currentFunctionName) >= 0) {
                            if(!isEval) {
                                stack.push("NULL");
                                locals.get(currentDepth).add("NULL");
                            }
                        }
                        break;
                    }
                    if (frameSlot != null) {
                        currentFrameSlot = frameSlot;
                        Object value = frameSlot < locals.get(currentDepth).size() ? locals.get(currentDepth).get(frameSlot) : "NULL";
                        stack.push(value);
                        functionArguments.push(value);
                        tableWithVariables.put(instr.getVariableName(), frameSlot);
                    }
                }
                case OP_STORE -> {
                    if (frameSlot != null && !stack.isEmpty()) {
                        currentFrameSlot = frameSlot;
                        if (frameSlot < locals.get(currentDepth).size()) {
                            locals.get(currentDepth).set(frameSlot, stack.pop());
                        } else {
                            while (locals.get(currentDepth).size() <= frameSlot) {
                                locals.get(currentDepth).add("NULL");
                            }
                            locals.get(currentDepth).set(frameSlot, stack.pop());
                        }
                        tableWithVariables.put(instr.getVariableName(), frameSlot);
                        if (currentFunctionName.equals(previousFunctionName)) {
                            stackTraceElements.put(instr.getVariableName(), locals.get(currentDepth).get(frameSlot));
                            stackTracePerFunction.put(currentFunctionName, stackTraceElements);
                        } else {
                            stackTraceElements.replace(instr.getVariableName(), locals.get(currentDepth).get(frameSlot));
                            stackTracePerFunction.put(currentFunctionName, stackTraceElements);
                        }
                    }

                }
                case OP_ADD -> performArithmeticOperations(stack, "ADD");
                case OP_SUB -> performArithmeticOperations(stack, "SUB");
                case OP_DIV -> performArithmeticOperations(stack, "DIV");
                case OP_MUL -> performArithmeticOperations(stack, "MUL");

                case OP_LOGICAL_AND -> {
//                    if (!stack.contains(false) || !stack.contains(true)) {

                    if (stack.isEmpty()) {
                        stack.push(false);
                        break;
                    } else if (stack.peek() instanceof Boolean) {
                        if (stack.peek().equals(false)) {
                            stack.push(false);
                        } else {
                            stack.push(true);
                        }
                    }
                    Object right = stack.pop();
                    Object left = stack.pop();
                    if (!(left instanceof Boolean)) {
                        consoleMessages.append(errorMessages.generateBooleanTypeError(left, "&&"));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (!(right instanceof Boolean)) {
                        consoleMessages.append(errorMessages.generateBooleanTypeError(right, "&&"));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (left.equals(true) && right.equals(true)) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }
                }

                case OP_LOGICAL_OR -> {

                    if (stack.isEmpty()) {
                        stack.push(true);
                        break;
                    } else if (stack.peek() instanceof Boolean) {
                        if (stack.peek().equals(true)) {
                            stack.push(true);
                        } else {
                            stack.push(false);
                        }
                    }
                    Object left = stack.pop();
                    Object right = stack.pop();
                    if (!(left instanceof Boolean)) {
                        consoleMessages.append(errorMessages.generateTypeError(right, left, "||"));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (!(right instanceof Boolean)) {
                        consoleMessages.append(errorMessages.generateTypeError(right, left, "||"));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (left.equals(true) || right.equals(true)) {
                        stack.push(true);
                    } else {
                        stack.push(false);
                    }
                }

                case OP_NEW -> {
                    Map<Object, Object> newObject = new HashMap<>();
                    stack.push(newObject);
                }

                case OP_SET_PROPERTY -> {
                    Object propertyName = new Object();
                    if (!stack.isEmpty()) {
                        if (stack.peek() instanceof String) {
                            propertyName = stack.pop().toString();
                        } else if (stack.peek() instanceof Long) {
                            propertyName = stack.pop().toString();
                        } else if (stack.peek() instanceof Object) {
                            propertyName = stack.pop();
                        }
                    } else {
                        System.out.println("Here");
                        propertyName = stack.pop();
                    }

                    if (stack.size() < 2 || !((stack.get(stack.size() - 2) instanceof HashMap))) {
                        consoleMessages.append(errorMessages.generateUndefinedObjectProperty(propertyName.toString()));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    while (!(stack.get(stack.size() - 2) instanceof Map)) {
                        stack.pop();
                    }
                    Object value = stack.pop();
                    Object receiver = stack.pop();
                    if (receiver instanceof Map && ((propertyName instanceof String) || (propertyName instanceof Number) || (propertyName instanceof Object))) {
                        ((Map<Object, Object>) receiver).put(propertyName, value);
                    } else {
                        System.err.println("Element is not a valid array.");
                        System.exit(1);
                        return consoleMessages;
                    }

                }

                case OP_GET_PROPERTY -> {
                    if (!stack.isEmpty()) {
                        Object propertyName = null;
                        if (stack.peek() instanceof String) {
                            propertyName = stack.pop().toString();
                        } else if (stack.peek() instanceof Long) {
                            propertyName = stack.pop().toString();
                        } else if (stack.peek() instanceof Map) {
                            propertyName = stack.pop();
                        }
                        if (!(stack.peek() instanceof Map)) {
                            consoleMessages.append(errorMessages.generateUndefinedObjectProperty(propertyName.toString()));
                            System.err.println(consoleMessages.toString());
                            System.exit(1);
                        }
                        Object receiver = stack.pop();
                        if (receiver instanceof Map) {
                            if (propertyName instanceof String) {
                                // Sometimes the property name can be a function.
                                if (globalScope.getFunction((String) propertyName) != null) {
                                    RootCallTarget function = globalScope.getFunction((String) propertyName);
                                    int numberOfArguments = globalScope.getNumberOfArgumentsForFunction((String) propertyName);

                                    Object[] args = new Object[numberOfArguments];
                                    for (int i = numberOfArguments - 1; i >= 0; i--) {
                                        args[i] = stack.pop();
                                    }
                                    VirtualFrame newFrame = new VirtualFrame(args);
                                    Object returnValue = function.invoke(new ArrayList<>(), newFrame);
                                    stack.push(returnValue);
                                } else {
                                    Object propertyValue = ((Map<?, ?>) receiver).get(propertyName);
                                    if (propertyValue instanceof String && globalScope.getFunction((String) propertyValue) != null) {
                                        RootCallTarget function = globalScope.getFunction((String) propertyValue);
                                        int numberOfArguments = 0;
                                        if (globalScope.getNumberOfArgumentsForFunction((String) propertyValue) != null) {
                                            numberOfArguments = globalScope.getNumberOfArgumentsForFunction((String) propertyValue);
                                        }

                                        Object[] args = new Object[numberOfArguments];
                                        for (int i = numberOfArguments - 1; i >= 0; i--) {
                                            args[i] = stack.pop();
                                        }
                                        stack.push(propertyValue);
                                        for (int i = 0; i < args.length; i++) {
                                            stack.push(args[i]);
                                        }
                                    } else if (propertyValue == null) {
                                        consoleMessages.append(errorMessages.generateUndefinedObjectProperty(propertyName.toString()));
                                        System.err.println(consoleMessages.toString());
                                        System.exit(1);
                                        return consoleMessages;
                                    } else {
                                        stack.push(propertyValue);
                                    }
                                }
                            } else {
                                Object propertyValue = ((Map<?, ?>) receiver).get(propertyName);
                                if (propertyValue == null) {
                                    consoleMessages.append(errorMessages.generateUndefinedObjectProperty(propertyName.toString()));
                                    System.err.println(consoleMessages.toString());
                                    System.exit(1);
                                    return consoleMessages;
                                }
                                stack.push(propertyValue);
                            }
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

                case OP_JUMP_IF_FALSE_BOOLEAN -> {
                    if (!(stack.peek() instanceof Boolean)) {
                        break;
                    }
                    if (!((Boolean) stack.peek())) {
                        pc += operand;
                    }
//                    stack.pop();
                }
                case OP_JUMP_IF_TRUE_BOOLEAN -> {
                    if (!(stack.peek() instanceof Boolean)) {
                        break;
                    }
                    if ((Boolean) stack.peek()) {
                        pc += operand;
                    }
//                    stack.pop();
                }

                case OP_JUMP_IF_FALSE -> {
                    if (!(stack.peek() instanceof Boolean)) {
                        // TODO: Since I use the same thing for loops and if statements, I should somehow get the operator.
                        consoleMessages.append(errorMessages.generateTypeError(stack.peek(), "if"));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (!((Boolean) stack.peek())) {
                        pc += operand;
                    }
                    stack.pop();
                }
                case OP_JUMP_IF_TRUE -> {
                    if ((Boolean) stack.peek()) {
                        pc += operand;
                    }
                    stack.pop();
                }
                // TODO Extract the logic into separate method
                case OP_PRINT -> {
                    if (globalScope.getFunction("println") != null) {
                        RootCallTarget function = globalScope.getFunction("println");
                        Object returnValue = function.invoke(new ArrayList<>(), globalScope);
                        if (returnValue.equals("")) {
                            stack.push("NULL");
                        } else {
                            stack.push(returnValue);
                        }
                        break;
                    }
                    if (!stack.isEmpty()) {
                        Object valueToPrint = stack.pop();
                        if (operand > 1) {
                            valueToPrint = stack.pop();
                            stack.push("NULL");
                        }
                        if (operand == 0) {
                            if (currentFunctionName.equals("main")) {
                                System.out.println("NULL");
                                break;
                            }
                            System.out.println("NULL");
                            break;
                        }
                        if (valueToPrint == null) {
                            if (currentFunctionName.equals("main")) {
                                System.out.println("NULL");
                                break;
                            }
                            consoleMessages.append("NULL").append("\n");
                            break;
                        } else if (valueToPrint instanceof Map) {
                            if (currentFunctionName.equals("main")) {
                                System.out.println("Object");
                                break;
                            }
                            consoleMessages.append("Object").append("\n");
                            break;
                        }
                        if (currentFunctionName.equals("main")) {
                            System.out.println(valueToPrint);
                        } else {
                            System.out.println(valueToPrint);
                        }

                    } else {
                        if (currentFunctionName.equals("main")) {
//                            System.out.println("NULL");
                            break;
                        }
                        System.out.println("NULL");
                        break;
                    }

                }

                case OP_BUILTIN -> {
                    stack.push("Function");
                }
                // TODO: Extract some logic into separate method
                case OP_TYPEOF -> {
                    if (globalScope.getFunction("typeOf") != null) {
                        RootCallTarget function = globalScope.getFunction("typeOf");
                        Object returnValue = function.invoke(stack.pop(), globalScope);
                        if (returnValue.equals("")) {
                            stack.push("NULL");
                        } else {
                            stack.push(returnValue);
                        }
                        break;
                    }
                    Object valueToCheckTypeOf = stack.pop();
                    // No clue how to handle otherwise this test
                    if (stack.contains("Type: ")) {
                        stack.push("[foreign object]");
                        break;
                    } else if (valueToCheckTypeOf == null || valueToCheckTypeOf.equals("NULL")) {
                        stack.push("NULL");
                    } else if (valueToCheckTypeOf.equals("Number")) {
                        stack.push("String");
                    } else if (globalScope.getFunction(valueToCheckTypeOf.toString()) != null ||
                            valueToCheckTypeOf.equals("Function")) {
                        stack.push("Function");
                    } else if (valueToCheckTypeOf instanceof Long || valueToCheckTypeOf instanceof BigInteger) {
                        stack.push("Number");
                    } else if (valueToCheckTypeOf instanceof Boolean) {
                        stack.push("Boolean");
                    } else if (valueToCheckTypeOf instanceof String || valueToCheckTypeOf instanceof StringRopes) {
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
                    if (globalScope.getFunction("nanoTime") != null) {
                        RootCallTarget function = globalScope.getFunction("nanoTime");
                        Object returnValue = function.invoke(new ArrayList<>(), globalScope);
                        if (returnValue.equals("")) {
                            stack.push("NULL");
                        } else {
                            stack.push(returnValue);
                        }
                    } else {
                        stack.push(System.nanoTime());
                    }
                }

                case OP_GET_SIZE -> {
                    Object obj = stack.pop();
                    if (obj instanceof Map<?, ?>) {
                        stack.push(((Map<?, ?>) obj).size());
                    } else if (obj instanceof String) {
                        stack.push(((String) obj).length());
                    } else if (obj instanceof StringRopes) {
                        stack.push(((StringRopes) obj).getSizeOfRope());
                    } else {
                        System.err.println("Element is not a valid array.");
                        System.exit(1);
                        return consoleMessages;
                    }
                }

                case OP_HELLO_EQUALS_WORLD -> {
                    for (int i = 0; i < locals.get(currentDepth).size(); i++) {
                        if ("hello".equals(bytecode.getElementFromConstantPool(i))) {
                            locals.get(currentDepth).set(i, "world");
                            break;
                        }
                    }
                }

                case OP_EXECUTABLE -> {
                    if (stack.peek() != null) {
                        if (stack.pop() instanceof String)
                            stack.push(true);
                        else
                            stack.push(false);
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
                    } else if (strObj instanceof StringRopes && startObj instanceof Long && endObj instanceof Long) {
                        int start = Math.toIntExact((Long) startObj);
                        int end = Math.toIntExact((Long) endObj);
                        stack.push(((StringRopes) strObj).substring(start, end));
                    } else {
                        consoleMessages.append("Not a string: cannot substring");
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
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

                // TODO: Since we go back to the AST, we lose the prints. Also, we don't need to evaluate a function but simply redefine it.
                case OP_DEFINE_FUNCTION -> {
                    if (stack.isEmpty()) {
                        consoleMessages.append("Type error: operation \"defineFunction\" not defined for NULL\n");
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    if (!(stack.peek() instanceof String)) {
                        consoleMessages.append(errorMessages.generateTypeErrorForDefineFunction("defineFunction", stack.peek()));
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                        return consoleMessages;
                    }
                    String functionCode = (String) stack.pop();
                    int depth = currentDepth;
                    Object answer = evalStreamRedefine(CharStreams.fromString(functionCode));
                    currentDepth = depth;
                    stack.push(answer);
                }

                case OP_COMPARE -> {
                    if (stack.size() == 1) {
                        break;
                    }
                    Object right = stack.pop();
                    Object left = stack.pop();

                    try {
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
                    } catch (ToySyntaxErrorException e) {
                        return null;
                    }
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
                // TODO: Check how to handle function
                case OP_CALL -> {
                    int numberOfFunctionArguments = operand;
                    previousFunctionName = currentFunctionName;
                    Object[] args = new Object[numberOfFunctionArguments];
                    if (numberOfFunctionArguments > stack.size()) {
                        break;
                    }
                    for (int i = numberOfFunctionArguments - 1; i >= 0; i--) {
                        args[i] = stack.pop();
                    }

                    String functionName = null;
                    int index = stack.size() - 1;
                    while (index >= 0 && !(stack.get(index) instanceof String)) {
                        index--;
                    }
                    if (index >= 0) {
                        if (globalScope.getFunction((String) stack.get(index)) != null) {
                            functionName = (String) stack.get(index);
                            stack.remove(index);
                        } else if (stack.get(index).equals("null") || stack.get(index).equals("NULL")) {
                            break;

                        } else {
                            if (stack.get(index).equals("Function")) {
                                System.err.println("Undefined function: SLType[Function]");
                                System.exit(1);
                            }
                            String message = "Undefined function: " + stack.get(index) + "\n";
                            consoleMessages.append(message);
                            System.err.println(consoleMessages.toString());
                            System.exit(1);
                            return consoleMessages;
                        }

                    } else if (frameSlot != null && locals.get(currentDepth).get(frameSlot) instanceof String) {
                        functionName = (String) locals.get(currentDepth).get(frameSlot);
                        if (globalScope.getFunction(functionName) == null) {
                            functionName = null;
                        }
                    } else if (functionName == null) {
                        if (!stack.isEmpty()) {
                            String message = errorMessages.generateUndefinedFunction(String.valueOf(stack.pop()));
                            consoleMessages.append(message);
                            System.err.println(consoleMessages.toString());
                            System.exit(1);
                            return consoleMessages.append(message);
                        } else {
                            String message = errorMessages.generateUndefinedFunction(String.valueOf(locals.get(currentDepth).get(currentFrameSlot)));
                            consoleMessages.append(message);
                            System.err.println(consoleMessages.toString());
                            System.exit(1);
                            return consoleMessages.append(message);
                        }
                    }
                    RootCallTarget function = globalScope.getFunction(functionName);
                    if (function == null) {
                        throw new RuntimeException("Function not found: " + functionName);
                    }

//                    globalScope.setFunctionToNumberOfArguments(functionName, numberOfFunctionArguments);

                    // TODO Error here which throws an error, logic should be overall correct.
                    currentFunctionName = functionName;
                    if (stackTracePerFunction.containsKey(currentFunctionName)) {
                        stackTracePerFunction.remove(currentFunctionName);
                        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
                    } else {
                        stackTracePerFunction.put(currentFunctionName, new LinkedHashMap<>());
                    }


                    if (functionName.equals("null")) {
                        stack.push("NULL");
                        break;
                    }
                    // Create a new frame with the arguments
                    VirtualFrame newFrame = new VirtualFrame(args);
                    if(!isEval)
                        currentDepth++;

                    // Use the stack overflow error
                    if (currentDepth > STACKOVERFLOW_THRESHOLD) {
                        consoleMessages.append("Resource exhausted: Stack overflow");
                        System.err.println(consoleMessages.toString());
                        System.exit(1);
                    }
                    // Invoke the function and push the return value onto the stack
                    Object returnValue = function.invoke(new ArrayList<>(), newFrame);
                    stack.push(returnValue);
                    if(currentDepth > 0 && !isEval)
                        currentDepth--;
                    stackTracePerFunction.remove(functionName);
                }

                case OP_RETURN -> {
                    if (!stack.isEmpty()) {
                        return stack.pop();
                    } else {
//                        System.out.println("Stack is empty");
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
        return consoleMessages.toString().trim();
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
        if (type.equals(StringRopes.class)) {
            stack.push(new StringRopes(bytecode.getElementFromConstantPool(operand).toString()));
        } else {
            T literalValue = type.cast(bytecode.getElementFromConstantPool(operand));
            stack.push(literalValue);
        }
    }


    /**
     * Method which performs the arithmetic operations.
     *
     * @param stack     where the values are stored
     * @param operation the corresponding arithmetic operation
     */
    private void performArithmeticOperations(Stack<Object> stack, String operation) {
        if (stack.size() < 2) {
            return;
        }
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
        if (left.equals("NULL") && right.equals("NULL")) {
            consoleMessages.append(errorMessages.generateTypeErrorNull("+"));
            System.err.println(consoleMessages.toString());
            System.exit(1);
        }
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
            return (long) (((Long) left).intValue() + ((Long) right).intValue());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).add((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).add(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).add((BigInteger) right);
        } else if ((left instanceof String || right instanceof String)) {
            if (right == null) {
                right = "NULL";
            }
            return left.toString() + right.toString();
        } else {
            consoleMessages.append(errorMessages.generateTypeError(left, right, "+"));
            System.err.println(consoleMessages.toString());
            System.exit(1);
            return null;
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
        if (left instanceof Integer) {
            left = ((Integer) left).longValue();
        }
        if (right instanceof Integer) {
            right = ((Integer) right).longValue();
        }
        if (left instanceof Number && right instanceof Number && !(left instanceof BigInteger) && !(right instanceof BigInteger)) {
            return (long) (((Long) left).intValue() - ((Long) right).intValue());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).subtract((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).subtract(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).subtract((BigInteger) right);
        } else {
            consoleMessages.append(errorMessages.generateTypeError(left, right, "-"));
            System.err.println(consoleMessages.toString());
            System.exit(1);
            return null;
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
            long leftLong = ((Number) left).longValue();
            long rightLong = ((Number) right).longValue();
            BigInteger product = BigInteger.valueOf(leftLong).multiply(BigInteger.valueOf(rightLong));
            if (product.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                return product;
            }
            return leftLong * rightLong;
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).multiply((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).multiply(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).multiply((BigInteger) right);
        } else {
            consoleMessages.append(errorMessages.generateTypeError(left, right, "*"));
            System.err.println(consoleMessages.toString());
            System.exit(1);
            return null;
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
            return (long) (((Long) left).intValue() / ((Long) right).intValue());
        } else if (left instanceof BigInteger && right instanceof BigInteger) {
            return ((BigInteger) left).divide((BigInteger) right);
        } else if (left instanceof BigInteger && right instanceof Long) {
            return ((BigInteger) left).divide(BigInteger.valueOf((Long) right));
        } else if (left instanceof Long && right instanceof BigInteger) {
            return (BigInteger.valueOf((Long) left)).divide((BigInteger) right);
        } else {
            consoleMessages.append(errorMessages.generateTypeError(left, right, "/"));
            System.err.println(consoleMessages.toString());
            System.exit(1);
            return null;
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
        } else if (left instanceof String && right instanceof String || left.equals("NULL") || right.equals("NULL") || left instanceof Number || right instanceof Number) {
            return left.equals(right);
        } else if (left instanceof Boolean && right instanceof Boolean) {
            return left.equals(right);
        } else if (left instanceof HashMap && right instanceof HashMap) {
            return left == right;
        } else if (left.equals("null") || right.equals("null")) {
            return left == right;
        } else if(left.equals("None") || right.equals("None")) {
            return left == right;
        }
        consoleMessages.append(errorMessages.generateTypeError(left, right, "=="));
        System.err.println(consoleMessages.toString());
        System.exit(1);
        throw new ToySyntaxErrorException("Cannot compare non-numeric values with '=='.");
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
        consoleMessages.append(errorMessages.generateTypeError(left, right, "<"));
        System.err.println(consoleMessages.toString());
        System.exit(1);
        throw new ToySyntaxErrorException("Cannot compare non-numeric values with '<'.");
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
        consoleMessages.append(errorMessages.generateTypeError(left, right, "<="));
        System.err.println(consoleMessages.toString());
        System.exit(1);
        throw new ToySyntaxErrorException("Cannot compare non-numeric values with '<='.");
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
        consoleMessages.append(errorMessages.generateTypeError(left, right, ">"));
        System.err.println(consoleMessages.toString());
        System.exit(1);
        throw new ToySyntaxErrorException("Cannot compare non-numeric values with '>'.");
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
        consoleMessages.append(errorMessages.generateTypeError(left, right, ">="));
        System.err.println(consoleMessages.toString());
        System.exit(1);
        throw new ToySyntaxErrorException("Cannot compare non-numeric values with '>='.");
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
            } else if (right instanceof Long || right instanceof Integer) {
                return ((BigInteger) left).compareTo(BigInteger.valueOf(right.longValue()));
            } else {
                // TODO
            }
        } else if (left instanceof Long || left instanceof Integer) {
            if (right instanceof BigInteger) {
                return -((BigInteger) right).compareTo(BigInteger.valueOf(left.longValue()));
            } else if (right instanceof Long || right instanceof Integer) {
                return Long.compare(left.longValue(), right.longValue());
            } else {
                // TODO
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
            } else if (right instanceof Long || right instanceof Integer) {
                return left.equals(BigInteger.valueOf(right.longValue()));
            } else {
                //TODO

            }
        } else if (left instanceof Long) {
            if (right instanceof BigInteger) {
                return BigInteger.valueOf((Long) left).equals(right);
            } else if (right instanceof Long) {
                return left.longValue() == right.longValue();
            } else {
//                Todo
            }
        } else {
            if (left == null || right == null) {
                // TODO
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
        if (value == null || value.equals("NULL")) {
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
        lex.removeErrorListeners();
        parser.removeErrorListeners();
        lex.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.toylanguage();

        Map<String, RootCallTarget> allFunctions = factory.getAllFunctions();

        for (Map.Entry<String, RootCallTarget> entry : allFunctions.entrySet()) {
            globalScope.registerFunction(entry.getKey(), entry.getValue());
            globalScope.setFunctionToNumberOfArguments(entry.getKey(), factory.getFunctionParameterCount(entry.getKey()));
        }

        if (!allFunctions.isEmpty()) {
            RootCallTarget functionToEvaluate = allFunctions.values().iterator().next();
            String name = null;
            for (Map.Entry<String, RootCallTarget> entry : allFunctions.entrySet()) {
            name = entry.getKey();
            }
            Object ans = functionToEvaluate.invoke(new ArrayList<>(), globalScope);
            if(ans == null || ans.equals(""))
                return name;
            return ans;
        }
        return null;
    }

    /**
     * It applies the redefineFunction function on this code.
     *
     * @param charStream the actual program.
     * @return the result of the program after execution.
     */
    public static Object evalStreamRedefine(CharStream charStream) {
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
            globalScope.setFunctionToNumberOfArguments(entry.getKey(), factory.getFunctionParameterCount(entry.getKey()));

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
