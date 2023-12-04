package h10.h1;

import h10.AbstractTest;
import h10.ListItem;
import h10.ListItems;
import h10.VisitorElement;
import h10.VisitorMySet;
import h10.utils.ListItemConverter;
import h10.utils.PredicateConverter;
import h10.utils.RubricOrder;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@DisplayName("H1 | subset(MySet)")
public abstract class H1_Tests extends AbstractTest {

    protected static final String TEST_RESOURCE_PATH = "h1/";

    protected static final String METHOD_NAME = "subset";

    protected @Nullable VisitorMySet<Integer> source;

    protected @Nullable VisitorMySet<Integer> result;

    protected @Nullable Context.Builder<?> context;

    @Override
    protected String getMethodName() {
        return METHOD_NAME;
    }

    @AfterEach
    public void tearDown() {
        assertVisitation();
        assertRequirement();
    }

    private void set(VisitorMySet<Integer> source, VisitorMySet<Integer> result, Context.Builder<?> context) {
        this.source = source;
        this.result = result;
        this.context = context;
    }

    public void assertVisitation() {
        Assumptions.assumeTrue(source != null);
        Assumptions.assumeTrue(context != null);
        // Nodes must be visited only once
        List<VisitorElement<Integer>> failed = source.stream()
            .filter(element -> element.getVisited() > 1)
            .toList();
        Assertions2.assertTrue(
            failed.isEmpty(),
            context.add("Nodes visited more than once", failed).build(),
            r -> "Expected no visited node more than once, got %s.".formatted(failed)
        );
    }

    public abstract void assertRequirement();

    @RubricOrder(criteria = {1, 3})
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht " +
        "erfüllt wird.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
        TEST_RESOURCE_PATH + "criterion1_testcase3.json",
    })
    public void testPredicateFalse(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head
    ) {
        VisitorMySet<Integer> source = create(head);
        // Drop all elements
        Predicate<? super VisitorElement<Integer>> predicate = new Predicate<>() {
            @Override
            public boolean test(VisitorElement<Integer> element) {
                // Explicitly call this in order to mark the node as visited
                element.visit();
                return false;
            }

            @Override
            public String toString() {
                return "Drop all";
            }
        };
        Context.Builder<?> context = defaultBuilder()
            .add("Comparator", DEFAULT_COMPARATOR)
            .add("Predicate", predicate)
            .add("Source", source.toString());
        VisitorMySet<Integer> result = new VisitorMySet<>(source.subset(predicate));
        context.add("Result", result.toString());

        // Since we the predicate drop all elements, the head of the set must be null
        Assertions2.assertFalse(
            result.iterator().hasNext(),
            context.build(),
            r -> "Subset should be empty, but got %s.".formatted(result)
        );

        // After test actions
        set(source, result, context);
    }

    @RubricOrder(criteria = {2, 3})
    @DisplayName("Die Methode subset(MySet) gibt das korrekte Ergebnis für eine komplexe Eingabe zurück.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
    })
    public void testPredicateComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head,
        @ConvertWith(PredicateConverter.BasicIntMath.class) @Property("predicate") Predicate<Integer> predicate,
        @ConvertWith(ListItemConverter.Int.class) @Property("expected") ListItem<Integer> expected
    ) {
        VisitorMySet<Integer> source = create(head);
        Predicate<VisitorElement<Integer>> test = new Predicate<>() {
            @Override
            public boolean test(VisitorElement<Integer> x) {
                return predicate.test(x.get());
            }

            @Override
            public String toString() {
                return predicate.toString();
            }
        };

        Context.Builder<?> context = defaultBuilder()
            .add("Comparator", DEFAULT_COMPARATOR)
            .add("Predicate", test)
            .add("Source", source.toString());
        VisitorMySet<Integer> result = new VisitorMySet<>(source.subset(test));
        context.add("Result", result.toString());

        Iterator<Integer> expectedIt = ListItems.iterator(expected);
        Iterator<Integer> actualIt = result.rawIterator();

        while (expectedIt.hasNext() && actualIt.hasNext()) {
            int e = expectedIt.next();
            int a = actualIt.next();
            Assertions2.assertEquals(
                e, a,
                context.add("Expected node", e).add("Actual node", a).build(),
                r -> "Expected node %s, but was %s".formatted(e, a)
            );
        }

        // Expected size must be equal to actual size
        Assertions2.assertFalse(expectedIt.hasNext(), context.build(), r -> "Expected list contains more element than actual list");
        Assertions2.assertFalse(actualIt.hasNext(), context.build(), r -> "Actual list contains more element than expected list");

        // After test actions
        set(source, result, context);
    }
}
