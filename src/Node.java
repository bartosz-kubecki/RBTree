public class Node {
    int key;
    int value;

    Node left;
    Node right;
    Node parent;

    boolean color;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ": " +
                value + " " +
                (color ? "(RED)" : "(BLACK)") + "\n" +
                "Left: " + (left == null ? "null" : left.toString()) + "\n" +
                "Right: " + (right == null ? "null" : right.toString());
    }
}
