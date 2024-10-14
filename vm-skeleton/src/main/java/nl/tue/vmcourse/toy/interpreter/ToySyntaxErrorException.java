package nl.tue.vmcourse.toy.interpreter;

public class ToySyntaxErrorException extends RuntimeException {

    private final String message;

    public ToySyntaxErrorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
