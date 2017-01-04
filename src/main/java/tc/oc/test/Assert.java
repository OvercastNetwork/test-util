package tc.oc.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public final class Assert {

    public static void assertEmpty(Object[] array) {
        assertEmpty(Arrays.asList(array));
    }

    public static void assertEmpty(Collection collection) {
        if(collection.isEmpty()) return;
        fail("Expected collection to be empty, but it contains " + collection.size() + " elements");
    }

    public static void assertSize(int size, Object[] array) {
        assertSize(size, Arrays.asList(array));
    }

    public static void assertSize(int size, Collection collection) {
        if(collection.size() == size) return;
        fail("Expected collection to contain " + size +
             " elements, but it actually contains " + collection.size());
    }

    public static void assertList(List actual, Object...expected) {
        assertEquals(Arrays.asList(expected), actual);
    }

    public static void assertThrows(Class<? extends Throwable> expected, TestCodeBlock block) throws Throwable {
        try {
            block.run();
        } catch(Throwable throwable) {
            if(expected.isInstance(throwable)) return;
            throw throwable;
        }
        fail("Expected " + expected.getSimpleName() + " to be thrown, but nothing was thrown");
    }

    public static void assertMutuallyEqual(Object a, Object b) {
        assertTrue(a + " should equal " + b, a.equals(b));
        assertTrue(b + " should equal " + a, b.equals(a));

        assertEquals(a + " and " + b + " should have identical hashCodes",
                     a.hashCode(), b.hashCode());
    }

    public static void assertMutuallyUnequal(Object a, Object b) {
        assertFalse(a + " should not equal " + b, a.equals(b));
        assertFalse(b + " should not equal " + a, b.equals(a));
    }
}
