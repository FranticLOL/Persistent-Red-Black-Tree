package org.tree;

public enum Color {
    RED, BLACK;

    Color flip() {
        return this.equals(BLACK) ? RED : BLACK;
    }
}