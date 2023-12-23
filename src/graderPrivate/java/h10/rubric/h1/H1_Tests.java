package h10.rubric.h1;

import h10.ListItem;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.converter.PredicateConverter;
import h10.rubric.SimpleTest;
import h10.utils.ListItems;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Iterator;
import java.util.function.Predicate;

@TestForSubmission
@DisplayName("H1 | subset(MySet)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class H1_Tests extends SimpleTest {

    protected static final String TEST_RESOURCE_PATH = "h1/";

    protected static final String METHOD_NAME = "subset";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Order(0)
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht "
        + "erfüllt wird.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
        TEST_RESOURCE_PATH + "criterion1_testcase3.json",
    })
    public void testPredicateFalse(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head
    ) {
        VisitorSet<Integer> source = visit(head);
        // Drop all elements
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
        Context.Builder<?> context = contextBuilder()
            .add("Comparator", DEFAULT_COMPARATOR)
            .add("Predicate", predicate)
            .add("Source", source.toString());
        VisitorSet<Integer> result = visit(source.subset(predicate));
        context.add("Result", result.toString());

        // Since we the predicate drop all elements, the head of the set must be null
        Assertions2.assertFalse(
            result.iterator().hasNext(),
            context.build(),
            r -> "Subset should be empty, but got %s.".formatted(result)
        );
    }

    @Order(1)
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
        VisitorSet<Integer> source = visit(head);
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

        Context.Builder<?> context = contextBuilder()
            .add("Comparator", DEFAULT_COMPARATOR)
            .add("Predicate", test)
            .add("Source", source.toString());

        VisitorSet<Integer> result = visit(source.subset(test));
        context.add("Result", result.toString());

        Iterator<Integer> expectedIt = ListItems.iterator(expected);
        Iterator<Integer> actualIt = result.peekIterator();

        while (expectedIt.hasNext() && actualIt.hasNext()) {
            int e = expectedIt.next();
            int a = actualIt.next();
            Assertions2.assertEquals(
                e, a,
                context.add("Expected node", e).add("Actual node", a).build(),
                r -> "Expected node %s, but was %s".formatted(e, a)
            );
        }

        // The expected size must be equal to actual size
        Assertions2.assertFalse(expectedIt.hasNext(), context.build(),
            r -> "Expected list contains more element than actual list");
        Assertions2.assertFalse(actualIt.hasNext(), context.build(),
            r -> "Actual list contains more element than expected list");
    }
}
