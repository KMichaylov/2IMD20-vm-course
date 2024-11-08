package nl.tue.vmcourse.toy.optimization.ropes;

/**
 * Add structure for single node element with its data.
 */
public class SingleNodeElement extends Node{
        String data;

    /**
     * Constructor for instantiating a single node element with no children.
     * @param data the text data of the node
     */
    public SingleNodeElement(String data) {
            this.data = data;
            this.stringLength = data.length();
        }
}
