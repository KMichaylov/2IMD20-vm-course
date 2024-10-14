package nl.tue.vmcourse.toy.bci;

public class ErrorMessages {
    public String generateTypeError(Object left, Object right, String operation) {
        String leftType = left instanceof Number ? "Number" : left.getClass().getSimpleName();
        String rightType = right instanceof Number ? "Number" : right.getClass().getSimpleName();
        return "Type error: operation \"" + operation + "\" not defined for " + leftType + " " + left + ", " + rightType + " " + right + "\n";
    }

    public String generateTypeError(Object obj, String operation) {
        String objType = obj instanceof Number ? "Number" : obj.getClass().getSimpleName();
        return "Type error: operation \"" + operation + "\" not defined for " + objType + " \"" + obj + "\"\n";
    }

    public String generateDivisionByZeroError(Object left) {
        String leftType = left instanceof Number ? "Number" : left.getClass().getSimpleName();
        return "Type error: division by zero for " + leftType + " " + left + "\n";
    }
}
