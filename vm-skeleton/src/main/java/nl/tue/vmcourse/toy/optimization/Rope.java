package nl.tue.vmcourse.toy.optimization;

// TODO: If not fast enough, consider changing the String to char
public class Rope {
    private Node root;

    public Rope(String data) {
        this.root = new SingleNodeElement(data);
    }

    private Rope(Node node) {
        this.root = node;
    }

    public Rope concatenation(Rope ropeInstance){
        this.root = concatenation(this.root, ropeInstance.root);
        return this;
    }

    public Rope substring(int start, int end){
        Node sub = substring(this.root, start, end);
        return new Rope(sub);
    }

    private Node concatenation(Node left, Node right){
        return new ParentNode(left, right);
    }

    private Node substring(Node node, int start, int end){
        CustomPair<Node, Node> leftSplitRope = split(node, start);
        CustomPair<Node, Node> rightSplitRope = split(leftSplitRope.getSecondElement(), end - start);
        return rightSplitRope.getFirstElement();
    }

    private CustomPair<Node, Node> split(Node node, int index){
        if(node instanceof SingleNodeElement){
            SingleNodeElement leaf = (SingleNodeElement) node;
            String left = leaf.data.substring(0, index);
            String right = leaf.data.substring(index);
            return new CustomPair<>(new SingleNodeElement(left), new SingleNodeElement(right));
        }
        ParentNode internal = (ParentNode) node;
        if (index < internal.weight) {
            CustomPair<Node, Node> splitLeft = split(internal.left, index);
            return new CustomPair<>(splitLeft.getFirstElement(), concatenation(splitLeft.getSecondElement(), internal.right));
        } else {
            CustomPair<Node, Node> splitRight = split(internal.right, index - internal.weight);
            return new CustomPair<>(concatenation(internal.left, splitRight.getFirstElement()), splitRight.getSecondElement());
        }
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
