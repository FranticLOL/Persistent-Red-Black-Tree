package org.tree;

public class PersistentRedBlackTree<K extends Comparable<K>, V> {
    private final Node<K, V> root;

    public PersistentRedBlackTree() {
        root = null;
    }

    private PersistentRedBlackTree(Node<K, V> root) {
        this.root = root;
    }

    public PersistentRedBlackTree<K, V> insert(K key, V value) {
        Node<K, V> newRoot = insert(root, key, value);
        newRoot.color = Color.BLACK;
        return new PersistentRedBlackTree<>(newRoot);
    }

    public boolean contains(K key) {
        return search(key) != null;
    }
    public PersistentRedBlackTree<K, V> delete(K key) {
        if (root == null) {
            return null;
        }
        Node<K, V> newRoot = balance(deleteNode(root, key));

        return new PersistentRedBlackTree<>(newRoot);
    }

    protected Node<K, V> getRoot() {
        return root;
    }

    private Node<K, V> deleteNode(Node<K, V> node, K key) {
        if (node == null || !contains(key)) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            Node<K, V> left = deleteNode(node.left, key);
            if (left == null) {
                return new Node<>(node.key, node.value, node.color, null, node.right);
            } else if (node.left == left) {
                return node;
            } else {
                return new Node<>(node.key, node.value, node.color, left, node.right);
            }
        } else if (cmp > 0) {
            Node<K, V> right = deleteNode(node.right, key);
            if (right == null) {
                return new Node<>(node.key, node.value, node.color, node.left, null);
            } else if (node.right == right) {
                return node;
            } else {
                return new Node<>(node.key, node.value, node.color, node.left, right);
            }
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return copyNodeColor(node.right, Color.BLACK);
            } else if (node.right == null) {
                return copyNodeColor(node.left, Color.BLACK);
            } else {
                Node<K, V> successor = findMinimum(node.right);
                Node<K, V> right = deleteNode(node.right, successor.key);
                Node<K, V> left = copyNodeColor(node.left, Color.BLACK);
                Node<K, V> newRoot = copyNodeColor(successor, node.color);
                newRoot.left = left;
                newRoot.right = right;
                return balance(newRoot);
            }
        }
    }

    private Node<K, V> copyNodeColor(Node<K, V> node, Color color) {
        return new Node<>(node.key, node.value, color, node.left, node.right);
    }

    private Node<K, V> findMinimum(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    private Node<K, V> insert(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value, Color.RED);
        }

        if (key.compareTo(node.key) < 0) {
            Node<K, V> left = insert(node.left, key, value);
            return balance(new Node<>(node.key, node.value, node.color, left, node.right));
        } else if (key.compareTo(node.key) > 0) {
            Node<K, V> right = insert(node.right, key, value);
            return balance(new Node<>(node.key, node.value, node.color, node.left, right));
        } else {
            return new Node<>(node.key, value, node.color, node.left, node.right);
        }
    }

    private Node<K, V> search(K key) {
        Node<K, V> node = root;

        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (key.compareTo(node.key) > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    private Node<K, V> balance(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        node.color = Color.RED;
        return right;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> left = node.left;
        node.left = left.right;
        left.right = node;
        left.color = node.color;
        node.color = Color.RED;
        return left;
    }

    private void flipColors(Node<K, V> node) {
        node.color = node.color.flip();
        node.left.color = node.left.color.flip();
        node.right.color = node.right.color.flip();
    }

    private boolean isRed(Node<K, V> node) {
        return node != null && node.color == Color.RED;
    }
}