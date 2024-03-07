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
}
