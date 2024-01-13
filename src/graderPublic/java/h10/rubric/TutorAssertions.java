package h10.rubric;

import h10.ListItem;
import h10.MySet;
import h10.Sets;
import h10.util.VisitorElement;
import org.opentest4j.AssertionFailedError;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

/**
 * Utility class for tutor assertions.
 *
 * @author Nhan Huynh
 */
public final class TutorAssertions {

    /**
     * This class should not be instantiated.
     */
    private TutorAssertions() {

    }

    /**
     * Returns the identity hash code of the given item.
     *
     * @param item the item to get the identity hash code of
     * @return the identity hash code of the given item
     */
    public static String identityHashCode(ListItem<?> item) {
        return item.getClass().getName() + "@" + System.identityHashCode(item);
    }

    /**
     * Returns the identity hash code of the given element.
     *
     * @param element the element to get the identity hash code of
     * @return the identity hash code of the given element
     */
    public static String identityHashCode(VisitorElement<?> element) {
        return element.peek().getClass() + "@" + System.identityHashCode(element.peek());
    }

    /**
     * Asserts that the given input is copied to the given output.
     *
     * @param input   the input of the operation to check
     * @param output  the output of the operation to check
     * @param context the context to report the result to
     * @param <T>     the type of the elements in the set
     * @throws AssertionFailedError if the given input is not copied to the given output
     */
    public static <T extends Comparable<T>> void assertAsCopy(
        MySet<VisitorElement<T>> input,
        MySet<VisitorElement<T>> output,
        Context.Builder<?> context
    ) {
        List<String> inputHashCode = Sets.itemsStream(input).map(TutorAssertions::identityHashCode).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        context.add("Input hashcodes", inputHashCode);
        context.add("Output hashcodes", otherIdentityHashCodes);
        Sets.itemsStream(output)
            .forEach(other -> inputHashCode.forEach(currentHashCode -> {
                        String otherHashCode = identityHashCode(other);
                        Assertions2.assertNotEquals(
                            otherHashCode, currentHashCode,
                            context.build(),
                            result -> "Node %s (%s) was not copied, got %s"
                                .formatted(other, otherHashCode, currentHashCode)
                        );
                    }
                )
            );
    }

    /**
     * Asserts that the given input is not copied to the given output.
     *
     * @param input   the input of the operation to check
     * @param output  the output of the operation to check
     * @param context the context to report the result to
     * @param <T>     the type of the elements in the set
     * @throws AssertionFailedError if the given input is copied to the given output
     */
    public static <T extends Comparable<T>> void assertInPlace(
        MySet<VisitorElement<T>> input,
        MySet<VisitorElement<T>> output,
        Context.Builder<?> context
    ) {
        List<String> inputHashCode = Sets.itemsStream(input).map(TutorAssertions::identityHashCode).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        context.add("Input hashcodes", inputHashCode);
        context.add("Output hashcodes", otherIdentityHashCodes);
        Sets.itemsStream(output)
            .forEach(other -> {
                    String otherHashCode = identityHashCode(other);
                    Assertions2.assertTrue(inputHashCode.stream().anyMatch(otherHashCode::equals),
                        context.build(),
                        result -> "Cannot find the same node (%s) after the operation. Node was probably copied."
                            .formatted(otherHashCode));
                }
            );
    }

}
