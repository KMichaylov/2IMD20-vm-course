package nl.tue.vmcourse.toy.optimization;

/**
 * Add structure for single node element with its data
 */
public class SingleNodeElement extends Node{
        String data;
        public SingleNodeElement(String data) {
            this.data = data;
            this.weight = data.length();
        }
}
