package nl.tue.vmcourse.toy.optimization;

/**
 * Just a parent node which has links to left and right children.
 */
public class ParentNode extends Node {
        Node left;
        Node right;

    /**
     * Constructor for the parent node.
     * @param left child of the parent node
     * @param right child of the parent node
     */
    public ParentNode(Node left, Node right) {
            this.left = left;
            this.right = right;
            this.stringLength = left.length();
        }
}
