package nl.tue.vmcourse.toy;

import nl.tue.vmcourse.toy.ast.ToyStatementNode;
import nl.tue.vmcourse.toy.bci.AstToBciAssembler;
import nl.tue.vmcourse.toy.bci.Bytecode;
import nl.tue.vmcourse.toy.bci.GlobalScope;
import nl.tue.vmcourse.toy.bci.ToyBciLoop;
import nl.tue.vmcourse.toy.interpreter.ToyRootNode;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.parser.ToyLangLexer;
import nl.tue.vmcourse.toy.parser.ToyLangParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;

import java.io.IOException;
import java.util.Map;

public class ToyLauncher {

    //    TODO: Maybe remove
    private static final GlobalScope globalScope = new GlobalScope();


    public static Object eval(String code) {
        return evalStream(CharStreams.fromString(code));
    }

    public static String parseReportErrors(String code) {
        CharStream charStream = CharStreams.fromString(code);
        String src = charStream.getText(Interval.of(0, charStream.size()));
        ToyLangLexer lex = new ToyLangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ToyLangParser parser = new ToyLangParser(tokens);
        ToyNodeFactory factory = new ToyNodeFactory(src);
        parser.setFactory(factory);
        lex.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        lex.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        try {
            parser.toylanguage();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return null;
    }

    private static Object evalStream(CharStream charStream) {
        String src = charStream.getText(Interval.of(0, charStream.size()));
        ToyLangLexer lex = new ToyLangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ToyLangParser parser = new ToyLangParser(tokens);
        ToyNodeFactory factory = new ToyNodeFactory(src);
        parser.setFactory(factory);
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.toylanguage();

        Map<String, RootCallTarget> allFunctions = factory.getAllFunctions();
        if (!allFunctions.isEmpty() && allFunctions.containsKey("main")) {
            RootCallTarget mainFunction = allFunctions.get("main");
            // TODO register builtins, initialize global scope, ...
            // TODO ME: Maybe check if here you can do the buildins or leave them as they are.
            // TODO ME: Basically, we should always start with the main function, see how to be able to check other functions.

            for (Map.Entry<String, RootCallTarget> entry : allFunctions.entrySet()) {
                // here, we add the global scope for functions and then create the bytecode for each function.
                globalScope.registerFunction(entry.getKey(), entry.getValue());
            }
            return mainFunction.invoke(globalScope);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        // TODO: change this when you will need to provide more arguments
        if (args.length < 1) {
            System.out.println("Usage: toy [file]");
            System.exit(1);
        }
        // TODO, ignores other args for now.
        CharStream charStream = CharStreams.fromFileName(args[args.length - 1]);
        try {
            Object result = evalStream(charStream);
            System.out.println(result);
        } catch (RuntimeException error) {
            System.err.println(error.getMessage());
        }
    }
}
