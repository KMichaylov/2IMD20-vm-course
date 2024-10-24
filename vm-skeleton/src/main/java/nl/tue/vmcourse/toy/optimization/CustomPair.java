package nl.tue.vmcourse.toy.optimization;

public class CustomPair<T, U> {
    private final T firstElement;
    private final U secondElement;

    public CustomPair(T firstElement, U secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public T getFirstElement() {
        return firstElement;
    }

    public U getSecondElement() {
        return secondElement;
    }
}
