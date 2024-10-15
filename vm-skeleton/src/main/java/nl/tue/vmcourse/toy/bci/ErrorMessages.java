package nl.tue.vmcourse.toy.bci;

public class ErrorMessages {

    private String returnTypeOfObject(Object obj) {
        if(obj instanceof Number) {
            return "Number";
        } else if(obj instanceof String) {
            return "String";
        } else if(obj instanceof Boolean) {
            return "Boolean";
        } else {
            return obj.getClass().getSimpleName();
        }
    }

    private String returnValueOfObject(Object obj) {
        if(obj instanceof String) {
            return "\"" + obj + "\"";
        } else {
            return obj.toString();
        }
    }


    public String generateTypeError(Object left, Object right, String operation) {
        String leftType = returnTypeOfObject(left);
        String rightType = returnTypeOfObject(right);
        String leftValue = returnValueOfObject(left);
        String rightValue = returnValueOfObject(right);
        return "Type error: operation \"" + operation + "\" not defined for " + leftType + " " + leftValue + ", " + rightType + " " + rightValue + "\n";
    }

    public String generateTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\"\n";
    }

    public String generateBooleanTypeError(Object obj, String operation) {
        String objType = returnTypeOfObject(obj);
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\", ANY\n";
    }

    public String generateTypeErrorForDefineFunction(String functionName,Object argument) {
        String objType = returnTypeOfObject(argument);
        return "Type error: operation \"" + functionName + "\" not defined for " + objType + " " + argument + "\n";
    }

    public String generateUndefinedFunction(String functionName) {
        return "Undefined function: " + functionName + "\n";
    }

}
