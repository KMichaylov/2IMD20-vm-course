package nl.tue.vmcourse.toy.builtins;

public class PrintBuiltin {
    public Object invoke(String foo) {
        System.out.println(foo);
        return null;
    }
}
