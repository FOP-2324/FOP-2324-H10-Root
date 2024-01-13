package h10.rubric.h1;

import h10.H10_Test;
import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.Sets;
import h10.util.ListItems;
import h10.util.VisitorElement;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Defines a base class for testing a method for the H1 assignment (public tests). A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@DisplayName("H1 | subset(MySet)")
@TestForSubmission
public abstract class H1_TestsPublic extends H10_Test {

    /**
     * The name of the method to be tested.
     */
    protected static final String METHOD_NAME = "subset";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * Checks whether the given set is only visited once.
     *
     * @param set     the set to check
     * @param context the context to report the result to
     * @param <T>     the type of the elements in the set
     * @throws AssertionFailedError if the set is visited more than once
     */
    protected <T extends Comparable<T>> void assertVisitation(MySet<VisitorElement<T>> set, Context context) {
        Assertions2.assertTrue(
            Sets.stream(set).noneMatch(element -> element.visited() > 1),
            context,
            result -> "Nodes were visited more than once"
        );
    }

    /**
     * Returns a function that accepts an input and output set and a context and checks whether the given input and
     * output satisfies the requirement of the method (as copy or in place).
     *
     * @param <T> the type of the elements in the input
     * @return a function that accepts an input and output set and a context and checks whether the given input and
     */
    protected abstract <T extends Comparable<T>> TriConsumer<
        MySet<VisitorElement<T>>,
        MySet<VisitorElement<T>>,
        Context.Builder<?>
        > requirementCheck();

    /**
     * Checks whether the given input and output satisfies the requirement of the method (as copy or in place).
     *
     * @param <T>     the type of the elements in the input
     * @param input   the input to check
     * @param output  the output to check
     * @param context the context to report the result to
     * @throws AssertionFailedError if the input does not satisfy the requirement
     */
    protected <T extends Comparable<T>> void assertRequirement(
        MySet<VisitorElement<T>> input,
        MySet<VisitorElement<T>> output,
        Context.Builder<?> context
    ) {
        this.<T>requirementCheck().accept(input, output, context);
    }

    @Order(0)
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht "
        + "erfüllt wird.")
    @ExtendWith(JagrExecutionCondition.class)
    @ParameterizedTest
    @JsonParameterSetTest(value = "H1_Criteria_01.json", customConverters = CUSTOM_CONVERTERS)
    public void testDropAll(JsonParameterSet parameters) {
        Context.Builder<?> contextBuilder = contextBuilder();
        Predicate<? super VisitorElement<Integer>> predicate = new Predicate<>() {
            @Override
            public boolean test(VisitorElement<Integer> element) {
                // Explicitly call this to mark the node as visited
                element.visit();
                return false;
            }

            @Override
            public String toString() {
                return "Drop all";
            }
        };
        contextBuilder.add("Predicate", predicate);

        // Source
        ListItem<Integer> head = parameters.get("head");
        ListItem<VisitorElement<Integer>> visitableHead = ListItems.map(head, VisitorElement::new);
        MySet<VisitorElement<Integer>> source = createSet(visitableHead);
        contextBuilder.add("Source set (before operation)", source.toString());

        // Result
        MySet<VisitorElement<Integer>> result = source.subset(predicate);
        contextBuilder.add("Actual result set", result.toString());
        contextBuilder.add("Source set (after operation)", source.toString());

        // Expected
        MySet<VisitorElement<Integer>> expected = createSet(null);
        contextBuilder.add("Expected result set", expected.toString());

        // Validation of the result
        Context context = contextBuilder.build();
        Assertions2.assertFalse(Sets.iterator(result).hasNext(), context, r -> "The result set is not empty");
        assertVisitation(source, context);
        assertRequirement(source, result, contextBuilder);
    }

    @Order(1)
    @DisplayName("Die Methode subset(MySet) gibt das korrekte Ergebnis für eine komplexe Eingabe zurück.")
    @ExtendWith(JagrExecutionCondition.class)
    @ParameterizedTest
    @JsonParameterSetTest(value = "H1_Criteria_02.json", customConverters = CUSTOM_CONVERTERS)
    public void testComplex(JsonParameterSet parameters) {
        Context.Builder<?> contextBuilder = contextBuilder();
        Context.Builder<?> copyContextBuilder = contextBuilder();
        Predicate<VisitorElement<Integer>> predicate = new Predicate<>() {

            private final Predicate<Integer> underlying = parameters.get("predicate");

            @Override
            public boolean test(VisitorElement<Integer> x) {
                return underlying.test(x.get());
            }

            @Override
            public String toString() {
                return underlying.toString();
            }
        };
        contextBuilder.add("Predicate", predicate);
        copyContextBuilder.add("Predicate", predicate);

        // Source
        ListItem<Integer> head = parameters.get("head");
        ListItem<VisitorElement<Integer>> visitableHead = ListItems.map(head, VisitorElement::new);
        MySet<VisitorElement<Integer>> source = createSet(visitableHead);
        contextBuilder.add("Source set (before operation)", source.toString());
        copyContextBuilder.add("Source set (before operation)", source.toString());

        // Result
        MySet<VisitorElement<Integer>> result = source.subset(predicate);
        contextBuilder.add("Actual result set", result.toString());
        copyContextBuilder.add("Actual result set", result.toString());
        contextBuilder.add("Source set (after operation)", source.toString());
        copyContextBuilder.add("Source set (after operation)", source.toString());

        // Expected
        ListItem<Integer> expectedHead = parameters.get("expected");
        ListItem<VisitorElement<Integer>> expectedVisitableHead = ListItems.map(expectedHead, VisitorElement::new);
        MySet<VisitorElement<Integer>> expected = createSet(expectedVisitableHead);
        contextBuilder.add("Expected result set", expected.toString());
        copyContextBuilder.add("Expected result set", expected.toString());

        // Validation of the result
        Iterator<VisitorElement<Integer>> actualIt = Sets.iterator(result);
        Iterator<VisitorElement<Integer>> expectedIt = Sets.iterator(expected);
        Context context = contextBuilder.build();
        while (actualIt.hasNext() && expectedIt.hasNext()) {
            VisitorElement<Integer> a = actualIt.next();
            VisitorElement<Integer> e = expectedIt.next();
            Assertions2.assertEquals(
                e.peek(),
                a.peek(),
                contextBuilder.add("Expected node", e).add("Actual node", a).build(),
                r -> "The result set contains the wrong elements"
            );
        }

        // The expected size must be equal to actual size
        Assertions2.assertFalse(expectedIt.hasNext(), context,
            r -> "Expected list contains more element than actual list");
        Assertions2.assertFalse(actualIt.hasNext(), context,
            r -> "Actual list contains more element than expected list");
        assertVisitation(source, context);
        assertRequirement(source, result, copyContextBuilder);
    }
}
