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

    public void add(int key, int value) {
        Node newNode = new Node(key, value);
        newNode.color = false;

        Node node = root;
        Node parent = null;

        while (node != null) {
            if (key == node.key) {
                throw new IllegalStateException("Binary Search Tree already have an element with key " + key);
            }
            parent = node.parent;
            node = key < node.key ? node.left : node.right;
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public int remove(int key) {
        Node node = root;
        while (node != null && node.key != key) {
            node = key < node.key ? node.left : node.right;
        }

        if (node == null) {
            throw new IllegalArgumentException("Binary Search Tree doesn't have an element with key " + key);
        }

        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
            return node.value;
        }
    }

    public int get(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) {
                return node.value;
            }
            node = key < node.key ? node.left : node.right;
        }

        throw new IllegalArgumentException("Binary Search Tree doesn't have an element with key " + key);
    }

    public int height();
}
