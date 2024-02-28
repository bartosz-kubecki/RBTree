public class RBTree {
    private Node root;

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        if (parent == null) {
            root = leftChild;
        } else if (parent.left == node) {
            parent.left = leftChild;
        } else if (parent.right == node) {
            parent.right = leftChild;
        }

        leftChild.parent = parent;
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        if (parent == null) {
            root = rightChild;
        } else if (parent.left == node) {
            parent.left = rightChild;
        } else if (parent.right == node) {
            parent.right = rightChild;
        }

        rightChild.parent = parent;
    }

    public void add(int key, int value);

    public int remove(int key);

    public int get(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) {
                return node.value;
            } else if (key < node.key) {
                node = node.left;
            } else if (key) {
                node = node.right;
            }
        }

        throw new IllegalArgumentException("A Red-Black Tree doesn't have an element with key " + key);
    }

    public int height();
}
