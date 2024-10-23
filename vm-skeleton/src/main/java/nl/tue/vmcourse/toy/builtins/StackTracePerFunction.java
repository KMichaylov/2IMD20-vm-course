package nl.tue.vmcourse.toy.builtins;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StackTracePerFunction {
    private Map<String, Map<String, Object>> stackTracePerFunction;

    public StackTracePerFunction() {
        this.stackTracePerFunction = new LinkedHashMap<>();
    }

    public void put(String functionName, Map<String, Object> stackTraceElements) {
        stackTracePerFunction.put(functionName, stackTraceElements);
    }

    public Map<String, Object> get(String functionName) {
        return stackTracePerFunction.get(functionName);
    }

    public boolean containsKey(String functionName) {
        return stackTracePerFunction.containsKey(functionName);
    }

    public void remove(String functionName) {
        stackTracePerFunction.remove(functionName);
    }

    public Set<Map.Entry<String, Map<String, Object>>> entrySet() {
        return stackTracePerFunction.entrySet();
    }

    public int size() {
        return stackTracePerFunction.size();
    }

    public void clear() {
        stackTracePerFunction.clear();
    }
}
