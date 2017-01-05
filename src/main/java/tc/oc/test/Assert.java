package tc.oc.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang.StringEscapeUtils;

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

    public static void assertEqualSequences(Iterable<?> expecteds, Iterable<?> actuals) {
        assertEquals(ImmutableList.copyOf(expecteds), ImmutableList.copyOf(actuals));
    }

    public static void assertSequence(Iterable<?> actuals, Object... expecteds) {
        assertEqualSequences(ImmutableList.copyOf(expecteds), actuals);
    }

    public static void assertSequence(Stream<?> actuals, Object... expecteds) {
        assertEqualSequences(ImmutableList.copyOf(expecteds), actuals.collect(Collectors.toList()));
    }

    public static void assertSequence(Iterable<?> expecteds, Stream<?> actuals) {
        assertEqualSequences(ImmutableList.copyOf(expecteds), actuals.collect(Collectors.toList()));
    }

    public static void assertEqualSets(Iterable<?> expecteds, Iterable<?> actuals) {
        assertEquals(ImmutableSet.copyOf(expecteds), ImmutableSet.copyOf(actuals));
    }

    public static void assertSet(Iterable<?> actuals, Object... expecteds) {
        assertEqualSets(ImmutableSet.copyOf(expecteds), actuals);
    }

    public static <T extends Comparable<T>> void assertContains(Range<T> range, T value) {
        if(!range.contains(value)) {
            throw new AssertionError("Expected a value in the range " + range + " but actual value is " + value);
        }
    }

    public static void assertSubstring(String part, String whole) {
        if(!whole.contains(part)) {
            fail("Expected \"" + StringEscapeUtils.escapeJava(part) +
                 "\" to be contained in \"" + StringEscapeUtils.escapeJava(whole) + "\"");
        }
    }

    public static void assertInstanceOf(Class<?> type, Object obj) {
        if(!type.isInstance(obj)) {
            fail("Expected instance of " + type.getName() + " instead of " + (obj == null ? "null" : "a " + obj.getClass().getName() + ": " + obj));
        }
    }

    public static void assertAssignableTo(TypeToken<?> to, TypeToken<?> type) {
        if(!to.isAssignableFrom(type)) {
            fail("Expected " + type + " to be assignable to " + to);
        }
    }

    public static void assertThrows(Class<? extends Throwable> expected, TestCodeBlock block) {
        try {
            block.run();
        } catch(Throwable throwable) {
            if(expected.isInstance(throwable)) return;
            throw Throwables.propagate(throwable);
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
