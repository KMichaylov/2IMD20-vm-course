package nl.tue.vmcourse.toy.jit;

import org.codehaus.janino.SimpleCompiler;
import org.codehaus.commons.compiler.CompileException;

import java.lang.reflect.InvocationTargetException;

public class JITCompiler {

    // https://janino-compiler.github.io/janino/
    private final SimpleCompiler compiler = new SimpleCompiler();

    public Object compileAndRun(int arg1, int arg2) {
        String compiledToy = assembly();

        try {
            Class<?> compiledJava = createJavaClass(compiledToy);
            return executeJavaClass(compiledJava, arg1, arg2);
        } catch (Throwable e) {
            // TODO: handle exception and continue in the interpreter?
            throw new RuntimeException("FATAL: cannot JIT compile " + e);
        }
    }

    private Object executeJavaClass(Class<?> compiledJava, int arg1, int arg2) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Object calculatorInstance = compiledJava.getDeclaredConstructor().newInstance();
        // Set field to some value
        compiledJava.getField("value").set(calculatorInstance, arg1);
        // Invoke the method with a parameter
        return compiledJava.getMethod("add", int.class).invoke(calculatorInstance, arg2);
    }

    private Class<?> createJavaClass(String code) throws ClassNotFoundException, org.codehaus.commons.compiler.CompileException {
        // Compile the class from the provided code
        compiler.cook(code);

        // Load the compiled class and instantiate it
        return compiler.getClassLoader().loadClass("Foo");
    }

    private static String assembly() {
        return  "public class Foo {" +
                        "    public int value = 0;" +
                        "    public int add(int x) {" +
                        "        return value + x;" +
                        "    }" +
                        "}";
    }

}
