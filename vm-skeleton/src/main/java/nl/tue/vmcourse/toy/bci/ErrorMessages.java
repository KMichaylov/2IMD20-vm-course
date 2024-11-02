package nl.tue.vmcourse.toy.bci;

import java.util.Map;

/**
 * The following class is created to add in reporting error messages for the undefined or type errors.
 */
public class ErrorMessages {

    /**
     * The following method returns the type of the given object.
     *
     * @param obj which will be checked
     * @return the type of the object
     */
    private String returnTypeOfObject(Object obj) {
        if (obj == null || obj.equals("NULL")) {
            return null;
        } else if (obj instanceof Number) {
            return "Number";
        } else if (obj instanceof String) {
            return "String";
        } else if (obj instanceof Boolean) {
            return "Boolean";
        } else if (obj instanceof Object) {
            return "Object";
        } else {
            return obj.getClass().getSimpleName();
        }
    }

    /**
     * The following method simply returns the value of the object.
     *
     * @param obj for which value will be returned
     * @return the value of the object
     */
    private String returnValueOfObject(Object obj) {
        if (obj instanceof String && !obj.equals("NULL")) {
            return "\"" + obj + "\"";
        } else if (obj instanceof Map) {
            return "Object";
        } else {
            return obj.toString();
        }
    }

    /**
     * Generates a type error for the given 2 variables and operation.
     *
     * @param left      the left operand
     * @param right     the right operand
     * @param operation the operation(+, -, *, /)
     * @return the generated error message
     */
    public String generateTypeError(Object left, Object right, String operation) {
        String leftType = left == null ? "" : returnTypeOfObject(left);
        String rightType = right == null ? "" : returnTypeOfObject(right);
        String leftValue = returnValueOfObject(left);
        String rightValue = returnValueOfObject(right);
        String message = "Type error: operation \"" + operation + "\" not defined for " + (leftType == null ? "" : leftType) + " " + leftValue + ", " + (rightType == null ? "" : rightType) + " " + rightValue + "\n";
        return message.replaceAll(" {2}", " ");
    }

    /**
     * Generates a type error for the object and operation.
     *
     * @param obj       the object
     * @param operation the operation
     * @return the generated error message
     */
    public String generateTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\"\n";
    }

    /**
     * Generates a type error for the given operation.
     *
     * @param operation the operation(+, -, *, /)
     * @return the generated error message
     */
    public String generateTypeErrorNull(String operation) {
        return "Type error: operation \"" + operation + "\" not defined for " + "NULL" + ", " + "NULL" + "\n";
    }

    /**
     * Generates a type error for the object and operation for booleans.
     *
     * @param obj       the object
     * @param operation the operation
     * @return the generated error message
     */
    public String generateBooleanTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\", ANY\n";
    }

    /**
     * Generate a type error for the given function and argument.
     * @param functionName the name of the function
     * @param argument the argument of the function
     * @return the generated error message
     */
    public String generateTypeErrorForDefineFunction(String functionName, Object argument) {
        String objType = returnTypeOfObject(argument);
        return "Type error: operation \"" + functionName + "\" not defined for " + objType + " " + argument + "\n";
    }

    /**
     * Simply generates an undefined function error message.
     * @param functionName name of the function
     * @return the generated error message
     */
    public String generateUndefinedFunction(String functionName) {
        return "Undefined function: " + functionName + "\n";
    }

    /**
     * Simply generates an error message for the undefined object property.
     * @param propertyName of the object
     * @return the generated error message
     */
    public String generateUndefinedObjectProperty(String propertyName) {
        return "Undefined property: " + propertyName + "\n";
    }

}
