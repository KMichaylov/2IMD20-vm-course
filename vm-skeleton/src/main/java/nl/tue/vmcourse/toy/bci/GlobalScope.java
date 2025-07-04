package nl.tue.vmcourse.toy.bci;

import nl.tue.vmcourse.toy.lang.RootCallTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * The following class is used to store the global scope of the project, namely functions so
 * that we are aware how to locate them. It uses hashmaps, since this is a simple way to fetch the necessary data.
 */
public class GlobalScope {
    private final Map<String, RootCallTarget> functions = new HashMap<>();
    private final Map<String, Integer> functionToNumberOfArguments = new HashMap<>();

    /**
     * Registers a function for the whole program.
     *
     * @param name     of the function
     * @param function the function object which is used for execution
     */
    public void registerFunction(String name, RootCallTarget function) {
        functions.put(name, function);
    }

    /**
     * Gets the function from the hashmap.
     *
     * @param name of the function
     * @return the function object
     */
    public RootCallTarget getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Directly sets the number of arguments for the specific function.
     *
     * @param name              of the function
     * @param numberOfArguments the function has
     */
    public void setFunctionToNumberOfArguments(String name, Integer numberOfArguments) {
        functionToNumberOfArguments.put(name, numberOfArguments);
    }

    /**
     * Juts return all functions from the global scope.
     *
     * @return functions as a hashmap
     */
    public Map<String, RootCallTarget> getAllFunctions() {
        return functions;
    }

    /**
     * Gets the number of arguments for the specific function.
     *
     * @param name of the function
     * @return the number of arguments as integer value
     */
    public Integer getNumberOfArgumentsForFunction(String name) {
        return functionToNumberOfArguments.get(name);
    }
}
