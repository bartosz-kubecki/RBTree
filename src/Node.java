/**
 * A node in a Red-black tree.
 */
public class Node {
    int key;
    int value;

    Node left;
    Node right;
    Node parent;

    /**
     * {@code True} for black node, {@code false} for red node.
     */
    boolean color;

    /**
     * @param key Key at which the value will be inserted.
     * @param value Value associated with the key.
     */
    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
