package nl.tue.vmcourse.toy.optimization;

/**
 * Just a parent node which has links to left and right children.
 */
public class ParentNode extends Node {
        Node left;
        Node right;

        public ParentNode(Node left, Node right) {
            this.left = left;
            this.right = right;
            this.weight = left.length();  // Internal node's weight is the length of the left subtree
        }
}
