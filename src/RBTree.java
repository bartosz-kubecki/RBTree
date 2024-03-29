/**
 * A red-black tree with unique keys.
 */
public class RBTree {
    private Node root;

    static final boolean RED = false;
    static final boolean BLACK = true;

    /**
     * Do a right rotation of the tree at a given node.
     * @param node {@code Node} at which the tree is being rotated (root of the subtree that is being rotated).
     * The node needs to have a left child.
     */
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

    /**
     * Do a left rotation of the tree at a given node.
     * @param node {@code Node} at which the tree is being rotated (root of the subtree that is being rotated).
     * The node needs to have a right child.
     */
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

    /**
     * Fix red-black properties after adding a node.
     * @param node {@code Node} that has been added. Must be red.
     */
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

    /**
     * Add a node with a given key and value 0 to the tree.
     * @param key Key of the new node. Unique per tree.
     */
    public void add(int key) {
        add(key, 0);
    }


    /**
     * Create a new node with a given key and value, add it to the tree and fix red-black properties of the tree.
     * @param key Key of the new node. Unique per tree.
     * @param value Value of the new node.
     */
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

    /**
     * Fix Red-black properties of the tree when a node is being removed.
     * This method should be called before any changes to the structure of the tree are made.
     * @param node {@code Node} that is being removed.
     */
    private void fixAfterRemove(Node node) {
        if (node.color == RED) return;
        if (node == root) return;

        Node parent = node.parent;
        if (node == parent.right && parent.left != null) {
            if (parent.left.color == RED) {
                parent.left.color = BLACK;
                parent.color = RED;

                rotateRight(parent);
            }
            if ((parent.left.left == null || parent.left.left.color == BLACK) &&
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
            }
            if ((parent.right.left == null || parent.right.left.color == BLACK) &&
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
    }

    /**
     * Remove a node from the tree.
     * @param node {@code Node} to remove.
     */
    private void remove(Node node) {
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

        if (removedNode == node)  {
            if (node.parent == null) root = child;
            else if (node.parent.left == node) node.parent.left = child;
            else if (node.parent.right == node) node.parent.right = child;
            else throw new IllegalStateException("Node is not a child of its parent.");
            if (child != null) child.parent = node.parent;
        }
    }

    /**
     * Remove a node with a given key from the tree.
     * @param key Key of the node to remove.
     * @return Value of the removed node.
     */
    public int remove(int key) {
        Node node = getNode(key);
        remove(node);
        return node.value;
    }

    /**
     * Find a node with a given key in the tree.
     * @param key Key of the node to return.
     * @return {@code Node}
     */
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

    /**
     * Get a node with a given key from the tree.
     * @param key Key of the node to get.
     * @return Value of the node.
     */
    public int get(int key) {
        return getNode(key).value;
    }

    /**
     * Get a height of the tree.
     * @return The maximum depth of any leaf node from the root node.
     */
    public int height() {
        return height(root);
    }

    /**
     * @param node Node for which height will be returned.
     * @return The maximum depth of any leaf node from the given {@code node}.
     */
    private int height(Node node) {
        if (node == null) return 0;
        return (Math.max(height(node.left), height(node.right))) + 1;
    }

    /**
     * Append the {@code StringBuilder} with a formatted graphical representation of a subtree, starting at {@code node}.
     * @param sb {@code StringBuilder} to be appended with the result.
     * @param padding Padding to be applied to the result at each line.
     * @param pointer Pointer to be included on the first line of the result.
     * @param node Node from which the result will be generated.
     */
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

    /**
     * Convert the tree to a string that can be printed on the console.
     * @return Formatted graphical representation of the tree.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        nodeToString(sb, "", "Root: ", root);
        return sb.toString();
    }
}
