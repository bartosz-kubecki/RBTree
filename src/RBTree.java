public class RBTree {
    private Node root;
    static final boolean RED = false;
    static final boolean BLACK = true;

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

    private void fixAfterAdd(Node node) {
        Node parent = node.parent;

        if (node == root) {
            node.color = BLACK;
            return;
        }
        if (parent.color == BLACK) return;

        Node grandparent = parent.parent;
        Node uncle;
        if (grandparent.left == parent) uncle = grandparent.right;
        else if (grandparent.right == parent) uncle = grandparent.left;
        else throw new IllegalStateException("Parent is not a child of its parent.");

        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
            fixAfterAdd(grandparent);
            return;
        }

        if (parent == grandparent.left) {
            if (node == parent.right) {
                rotateLeft(parent);
                node = parent;
                parent = node.parent;
            }

            parent.color = BLACK;
            grandparent.color = RED;
            rotateRight(grandparent);
        } else {
            if (node == parent.left) {
                rotateRight(parent);
                node = parent;
                parent = node.parent;
            }

            parent.color = BLACK;
            grandparent.color = RED;
            rotateLeft(grandparent);
        }
    }

    public void add(int key) {
        add(key, 0);
    }
    public void add(int key, int value) {
        Node newNode = new Node(key, value);
        newNode.color = RED;

        Node node = root;
        Node parent = null;

        while (node != null) {
            if (key == node.key) {
                throw new IllegalArgumentException("Binary Search Tree already have an element with key " + key);
            }
            parent = node;
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

        fixAfterAdd(newNode);
    }

    private void fixAfterRemove(Node node){
        if (node.color == RED) return;
        if (node == root) return;

        Node parent = node.parent;
        if (node == parent.right && parent.left != null) {
            if (parent.left.color == RED) {
                parent.left.color = BLACK;
                parent.color = RED;

                rotateRight(parent);
            } else if ((parent.left.left == null || parent.left.left.color == BLACK) &&
                       (parent.left.right == null || parent.left.right.color == BLACK)) {
                parent.left.color = RED;

                if (parent.color == RED) {
                    parent.color = BLACK;
                } else {
                    fixAfterRemove(parent);
                }
            } else {
                if (parent.left.left == null || parent.left.left.color == BLACK) {
                    parent.left.right.color = BLACK;
                    parent.left.color = RED;
                    rotateLeft(parent.left);
                }
                parent.left.color = parent.color;
                parent.color = BLACK;
                parent.left.left.color = BLACK;
                rotateRight(parent);
            }

        } else if (node == parent.left && parent.right != null) {
            if (parent.right.color == RED) {
                parent.right.color = BLACK;
                parent.color = RED;

                rotateLeft(parent);
            } else if ((parent.right.left == null || parent.right.left.color == BLACK) &&
                    (parent.right.right == null || parent.right.right.color == BLACK)) {
                parent.right.color = RED;

                if (parent.color == RED) {
                    parent.color = BLACK;
                } else {
                    fixAfterRemove(parent);
                }
            } else {
                if (parent.right.right == null || parent.right.right.color == BLACK) {
                    parent.right.left.color = BLACK;
                    parent.right.color = RED;
                    rotateRight(parent.right);
                }
                parent.right.color = parent.color;
                parent.color = BLACK;
                parent.right.right.color = BLACK;
                rotateLeft(parent);
            }
        }

        System.out.println(node.key);
    }

    private Node remove(Node node) {
        Node child = null;
        Node removedNode = node;

        if (node.left == null ^ node.right == null) {
            child = node.left == null ? node.right : node.left;
        } else if (node.right != null) {
            Node inOrderSuccessor = node.right;
            while (inOrderSuccessor.left != null) {
                inOrderSuccessor = inOrderSuccessor.left;
            }
            remove(inOrderSuccessor);

            node.key = inOrderSuccessor.key;
            node.value = inOrderSuccessor.value;

            removedNode = inOrderSuccessor;
        }

        fixAfterRemove(removedNode);

        if (node.parent == null) {
            root = child;
        } else if (removedNode == node)  {
            if (node.parent.left == node) node.parent.left = child;
            if (node.parent.right == node) node.parent.right = child;
        }

        return removedNode;
    }
    public int remove(int key) {
        Node node = root;
        while (node != null && node.key != key) {
            node = key < node.key ? node.left : node.right;
        }

        if (node == null) {
            throw new IllegalArgumentException("Binary Search Tree doesn't have an element with key " + key);
        }

        return remove(node).key;
    }

    private Node getNode(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) {
                return node;
            }
            node = key < node.key ? node.left : node.right;
        }

        throw new IllegalArgumentException("Binary Search Tree doesn't have an element with key " + key);
    }

    public int get(int key) {
        return getNode(key).value;
    }

    public int height() {
        return height(root);
    }
    private int height(Node node) {
        if (node == null) return 0;
        return (Math.max(height(node.left), height(node.right))) + 1;
    }

    private void nodeToString(StringBuilder sb, String padding, String pointer, Node node) {
        if (node == null) return;

        sb.append(padding).append(pointer);
        sb.append(node.color == RED ? "\u001B[41m\u001B[30m" : "\u001B[40m");
        sb.append(String.format("%1$3s", node.key));
        sb.append("\u001B[0m\n");

        StringBuilder paddingBuilder = new StringBuilder();
        paddingBuilder.append(padding);
        if (node.parent != null) {
            paddingBuilder.append(
                    (node.parent.right != null && node.parent.right != node) ? "│   " : "    ");
        }
        String newPadding = paddingBuilder.toString();

        String leftPointer = (node.right == null) ? "└──Left: " : "├──Left: ";
        nodeToString(sb, newPadding, leftPointer, node.left);

        String rightPointer = "└──Right:";
        nodeToString(sb, newPadding, rightPointer, node.right);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        nodeToString(sb, "", "Root: ", root);
        return sb.toString();
    }
}
