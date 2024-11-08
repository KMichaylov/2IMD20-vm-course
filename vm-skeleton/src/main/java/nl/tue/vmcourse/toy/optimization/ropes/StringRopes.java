package nl.tue.vmcourse.toy.optimization.ropes;

/**
 * The main class for the StringRopes implementation.
 */
public class StringRopes {
    private Node root;
    public StringRopes(String data) {
        this.root = new SingleNodeElement(data);
    }

    public StringRopes concatenation(StringRopes stringRopesInstance) {
        this.root = concatenation(this.root, stringRopesInstance.root);
        return this;
    }

    private Node concatenation(Node left, Node right) {
        return new ParentNode(left, right);
    }

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
