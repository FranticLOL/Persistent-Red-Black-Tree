package org.tree;

class Node<K extends Comparable<K>, V> {
    K key;
    V value;
    Color color;
    Node<K, V> left;
    Node<K, V> right;

    public Node(K key, V value, Color color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public Node(K key, V value, Color color, Node<K, V> left, Node<K, V> right) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = left;
        this.right = right;
    }
}