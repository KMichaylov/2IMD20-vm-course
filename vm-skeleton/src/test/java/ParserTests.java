import nl.tue.vmcourse.toy.ToyLauncher;
import org.junit.Assert;
import org.junit.Test;

public class ParserTests {

    @Test
    public void test01() {
        expectError("SYNTAX__ERROR;", "missing 'function'");
        expectError("function main() { return -; };", "extraneous input '-'");
        expectError("function main() { return --1; };", "extraneous input '-'");
        expectError("function main() { return ---1; };", "mismatched input '-'");
        expectError("function main() { return 'x'; };", "token recognition error");
        expectError("function main() { return +1; };", "extraneous input '+'");
    }

    @Test
    public void test02() {
        noError("function main() { return -1; }");
        noError("function main() { return true; }");
        noError("function main() { return false; }");
        noError("function main() { return \"xxx\"; }");
        noError("function main() { return -1 + -1; }");
        noError("function main() { return new(); }");
    }

    private static void expectError(String code, String expectedError) {
        String errorMessage = ToyLauncher.parseReportErrors(code);
        Assert.assertNotNull(errorMessage);
        Assert.assertFalse(errorMessage.isEmpty());
        Assert.assertTrue(errorMessage.contains(expectedError));
    }

    private static void noError(String code) {
        Assert.assertNull(ToyLauncher.parseReportErrors(code));
        Assert.assertNull(ToyLauncher.parseReportErrors(code));
    }
}
