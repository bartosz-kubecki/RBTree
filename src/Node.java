/**
 * Represents a single node in a Red-black tree.
 */
public class Node {
    int key;
    int value;

    Node left;
    Node right;
    Node parent;

    /**
     * {@code true} represents black node, {@code false} red node.
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
