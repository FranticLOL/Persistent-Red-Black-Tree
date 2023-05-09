package org.tree;

import junit.framework.TestCase;
public class PersistentRedBlackTreeTest
    extends TestCase
{
    private final PersistentRedBlackTree<Integer, Integer> initialTree = new PersistentRedBlackTree<>();

    public void testInsertionAndExistence()
    {
        var insert1 = initialTree.insert(5, 5);
        var insert2 = insert1.insert(2, 2);

        assertTrue(insert1.contains(5));
        assertFalse(insert1.contains(2));

        assertTrue(insert2.contains(2));
        assertTrue(insert2.contains(5));
    }

    public void testDeletion() {
        var insert1 = initialTree.insert(5, 5);
        var insert2 = insert1.insert(2, 2);
        var insert3 = insert2.insert(8, 8);
        var insert4 = insert3.insert(6, 6);

        var delete1 = insert1.delete(5);
        var delete2 = insert2.delete(2);
        var delete3 = insert4.delete(8);

        assertNull(delete1.getRoot());
        assertFalse(delete2.contains(2));
        assertFalse(delete3.contains(8));
        assertTrue(delete3.contains(6));
    }

    public void testRedBlackTreeProperties() {
        var insert1 = initialTree.insert(5, 5);
        var insert2 = insert1.insert(2, 2);
        var insert3 = insert2.insert(8, 8);
        var insert4 = insert3.insert(6, 6);

        assertEquals(insert4.getRoot().color, Color.BLACK);
        assertEquals(insert4.getRoot().left.color, Color.BLACK);
        assertEquals(insert4.getRoot().right.color, Color.BLACK);
        assertEquals(insert4.getRoot().right.left.color, Color.RED);
    }

    public void testPersistentProperties() {
        var insert1 = initialTree.insert(5, 5);
        var insert2 = insert1.insert(2, 2);
        var insert3 = insert2.insert(8, 8);
        var insert4 = insert3.insert(6, 6);

        assertSame(insert4.getRoot().left, insert2.getRoot().left);
        assertNotSame(insert4.getRoot(), insert2.getRoot());
    }
}
