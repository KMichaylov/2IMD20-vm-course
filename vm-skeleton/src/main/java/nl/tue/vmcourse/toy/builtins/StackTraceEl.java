package nl.tue.vmcourse.toy.builtins;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class StackTraceEl {

    private List<Map.Entry<String, Object>> elements;

    public StackTraceEl() {
        this.elements = new ArrayList<>();
    }

    public void addElement(String key, Object value) {
        elements.add(new SimpleEntry<>(key, value));
    }

    public void replaceEntry(String key, Object value) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getKey().equals(key)) {
                elements.set(i, new SimpleEntry<>(key, value));
                return;
            }
        }
        // If the key does not exist, add a new entry
        addElement(key, value);
    }

    public List<Map.Entry<String, Object>> getElements() {
        return elements;
    }

    public void clear() {
        elements.clear();
    }
}
