package nl.tue.vmcourse.toy.optimization;

/**
 * The main class for the StringRopes implementation.
 */
public class StringRopes {
    private Node root;

    /**
     * Constructor which adds the string to the top node.
     * @param data that is a string.
     */
    public StringRopes(String data) {
        this.root = new SingleNodeElement(data);
    }

    /**
     * Concatenates the root node with the provided node.
     * @param otherRope the rope to concatenate with
     * @return the update StringRopes object
     */
    public StringRopes concatenation(StringRopes otherRope) {
        this.root = generateParent(this.root, otherRope.root);
        return this;
    }

    /**
     * Generates a parent node from both left and right nodes.
     * @param left node
     * @param right node
     * @return parent instance generated from the two nodes
     */
    private Node generateParent(Node left, Node right) {
        return new ParentNode(left, right);
    }

    /**
     * Function for the string representation of the node.
     * @param node the node for which we want the string representation
     * @return the string representation
     */
    private String getStringRepresentation(Node node) {
        if (node instanceof SingleNodeElement) {
            return ((SingleNodeElement) node).data;
        }
        ParentNode parent = (ParentNode) node;
        return getStringRepresentation(parent.left) + getStringRepresentation(parent.right);
    }

    @Override
    public String toString() {
        return getStringRepresentation(this.root);
    }

}
