package h10;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

/**
 * Defines assertions utilities for H10 testing.
 */
public class AssertionUtils {

    /**
     * Prevents instantiation.
     */
    private AssertionUtils() {

    }

    /**
     * Asserts that the output is a copy of the input.
     *
     * @param input   the input to check
     * @param output  the output to check
     * @param context the context to supply if the assertion fails
     */
    public static void assertAsCopy(MySet<?> input, MySet<?> output, Context.Builder<?> context) {
        for (ListItem<?> current = input.head; current != null; current = current.next) {
            for (ListItem<?> other = output.head; other != null; other = other.next) {
                String currentHash = toString(current);
                String otherHash = toString(other);
                Assertions2.assertNotEquals(
                    currentHash, otherHash,
                    context.add("Node before subset", currentHash)
                        .add("Node after subset", otherHash)
                        .build(),
                    r -> "Node %s was not copied, got %s".formatted(currentHash, otherHash)
                );
            }
        }
    }

    /**
     * Asserts that the output contains the same references as the input.
     *
     * @param input   the input to check
     * @param output  the output to check
     * @param context the context to supply if the assertion fails
     */
    public static void assertInPlace(MySet<?> input, MySet<?> output, Context.Builder<?> context) {
        int index = 0;
        for (ListItem<?> other = output.head; other != null; other = other.next, index++) {
            boolean found = false;
            for (ListItem<?> current = input.head; current != null; current = current.next) {
                String currentHash = toString(current);
                String otherHash = toString(other);
                if (currentHash.equals(otherHash)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String hash = toString(other);
                int currentIndex = index;
                Assertions2.fail(context.build(), r -> "Node %s at %s was copied".formatted(hash, currentIndex));
            }
        }
    }

    /**
     * Returns the original hashcode of the item.
     *
     * @param item the item to get the hashcode from.
     * @return the original hashcode of the item.
     */
    public static String toString(ListItem<?> item) {
        return item.getClass().getName() + "@" + System.identityHashCode(item);
    }
}
