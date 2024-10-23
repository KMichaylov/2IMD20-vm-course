package nl.tue.vmcourse.toy.builtins;

import java.util.*;

public class StackTracePerFunction {
    private List<Map.Entry<String, StackTraceEl>> stackTracePerFunction;

    public StackTracePerFunction() {
        this.stackTracePerFunction = new ArrayList<>();
    }

    public void put(String functionName, StackTraceEl stackTraceElements) {
        stackTracePerFunction.add(new AbstractMap.SimpleEntry<>(functionName, stackTraceElements));
    }

    public StackTraceEl get(String functionName) {
        for (Map.Entry<String, StackTraceEl> entry : stackTracePerFunction) {
            if (entry.getKey().equals(functionName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(String functionName) {
        for (Map.Entry<String, StackTraceEl> entry : stackTracePerFunction) {
            if (entry.getKey().equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String functionName) {
        stackTracePerFunction.removeIf(entry -> entry.getKey().equals(functionName));
    }

    public Set<Map.Entry<String, StackTraceEl>> entrySet() {
        return new HashSet<>(stackTracePerFunction);
    }

    public int size() {
        return stackTracePerFunction.size();
    }

    public void clear() {
        stackTracePerFunction.clear();
    }
}
