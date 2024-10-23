package nl.tue.vmcourse.toy.builtins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StackTraceGenerator {

    /**
     * Generates the string represantation of the stack trace
     *
     * @param functionName the name of the current function in the frame scope
     * @return the string representation
     */
    public static String generateStackTrace(String functionName, StackTraceEl elements, Map<String, StackTraceEl> stackTracePerFunction) {
        StringBuilder sb = new StringBuilder();
        List<Map.Entry<String, StackTraceEl>> entryList = new ArrayList<>(stackTracePerFunction.entrySet());
        if (stackTracePerFunction.get(functionName).getElements().isEmpty() && !functionName.equals("main")) {
            for (int i = stackTracePerFunction.size() - 1; i >= 0; i--) {
                Map.Entry<String, StackTraceEl> entry = entryList.get(i);
                sb.append("Frame: root ").append(entry.getKey()).append("\n");
            }
            stackTracePerFunction.put("main", new StackTraceEl());
        } else {
            sb.append("Frame: root ").append(functionName);
            for (int i = elements.getElements().size() - 1; i >= 0; i--) {
                Map.Entry<String, Object> entry = elements.getElements().get(i);
                String varName = entry.getKey();
                Object value = entry.getValue();
                sb.append(", ").append(varName).append("=").append(value == null ? "null" : value.toString());
            }
        }
        return sb.toString().trim();
    }
}
