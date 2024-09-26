package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.lang.RootCallTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * The following class is used to store the global scope of the project, namely functions, some variables, so
 * that we are aware how to locate them. It uses hashmaps, since this is a simple way to fetch the necessary data.
 */
public class GlobalScope {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, RootCallTarget> functions = new HashMap<>();

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public void registerFunction(String name, RootCallTarget function) {
        functions.put(name, function);
    }

    public RootCallTarget getFunction(String name) {
        return functions.get(name);
    }
}
