package nl.tue.vmcourse.toy.bci;

import java.util.Map;

public class ErrorMessages {

    private String returnTypeOfObject(Object obj) {
        if (obj == null || obj.equals("NULL")) {
            return null;
        } else if (obj instanceof Number) {
            return "Number";
        } else if (obj instanceof String) {
            return "String";
        } else if (obj instanceof Boolean) {
            return "Boolean";
        } else if (obj instanceof Object){
            return "Object";
        }else {
            return obj.getClass().getSimpleName();
        }
    }

    private String returnValueOfObject(Object obj) {
        if (obj instanceof String && !obj.equals("NULL")) {
            return "\"" + obj + "\"";
        } else if (obj instanceof Map){
            return "Object";
        }
        else {
            return obj.toString();
        }
    }

    public String generateTypeError(Object left, Object right, String operation) {
        String leftType = left == null ? "" : returnTypeOfObject(left);
        String rightType = right == null ? "" : returnTypeOfObject(right);
        String leftValue = returnValueOfObject(left);
        String rightValue = returnValueOfObject(right);
        String message = "Type error: operation \"" + operation + "\" not defined for " + (leftType == null ? "" : leftType) + " " + leftValue + ", " + (rightType == null ? "" : rightType) + " " + rightValue + "\n";
        return message.replaceAll(" {2}", " ");
    }

    public String generateTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\"\n";
    }

    public String generateTypeErrorNull(String operation) {
        return "Type error: operation \"" + operation + "\" not defined for " + "NULL" + ", " + "NULL" + "\n";
    }

    public String generateBooleanTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\", ANY\n";
    }

    public String generateTypeErrorForDefineFunction(String functionName, Object argument) {
        String objType = returnTypeOfObject(argument);
        return "Type error: operation \"" + functionName + "\" not defined for " + objType + " " + argument + "\n";
    }

    public String generateUndefinedFunction(String functionName) {
        return "Undefined function: " + functionName + "\n";
    }

    public String generateUndefinedObjectProperty(String propertyName) {
        return "Undefined property: " + propertyName + "\n";
    }

}
