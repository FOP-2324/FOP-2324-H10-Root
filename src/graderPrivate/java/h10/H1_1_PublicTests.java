package h10;

import h10.utils.converter.IntPredicateConverter;
import h10.utils.Links;
import h10.utils.converter.ListItemConverter;
import h10.utils.RubricOrder;
import h10.utils.visitor.VisitorElement;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.function.Predicate;

@DisplayName("H1.1 | subset as-copy")
public class H1_1_PublicTests extends AsCopyTest {

    private static final String TEST_RESOURCE_PATH = "h1/";

    private static final String METHOD_NAME = "subset";

    private @Nullable MySet<VisitorElement<Integer>> validateInput;

    private @Nullable MySet<VisitorElement<Integer>> validateOutput;
    private @Nullable Context.Builder<?> validateContext;

    @BeforeAll
    @Override
    public void globalSetup() {
        super.globalSetup();
        method = Links.getMethodLink(type, METHOD_NAME);
    }

    @AfterEach
    public void tearDown() {
        Assumptions.assumeTrue(validateInput != null);
        Assumptions.assumeTrue(validateContext != null);
        Assumptions.assumeTrue(validateOutput != null);
        // Check visit count
        for (ListItem<VisitorElement<Integer>> current = validateInput.head; current != null; current = current.next) {
            Assertions2.assertEquals(
                1, current.key.getVisited(),
                validateContext.add("Node was visited more than once", current.key).build(),
                r -> "Node was visited more than once"
            );
        }

        // Check as copy
        for (ListItem<VisitorElement<Integer>> current = validateInput.head; current != null; current = current.next) {
            for (ListItem<VisitorElement<Integer>> other = validateOutput.head; other != null; other = other.next) {
                String currentHash = toString(current);
                String otherHash = toString(other);
                Assertions2.assertNotEquals(
                    currentHash, otherHash,
                    validateContext.add("Node before subset", currentHash)
                        .add("Node after subset", otherHash)
                        .build(),
                    r -> "Node %s was not copied, got %s".formatted(currentHash, otherHash)
                );
            }
        }
    }

    @RubricOrder(1)
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht " +
        "erfüllt wird.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
        TEST_RESOURCE_PATH + "criterion1_testcase3.json",
    })
    public void testPredicateFalse(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head
    ) {
        MySet<VisitorElement<Integer>> source = new MySetAsCopy<>(head, cmp);
        reset(source);
        Predicate<VisitorElement<Integer>> predicate = element -> {
            element.getElement();
            return false;
        };
        Context.Builder<?> builder = defaultBuilder().add("Source", source.toString());
        MySet<VisitorElement<Integer>> result = source.subset(predicate);
        builder.add("Result", result.toString());

        validateInput = source;
        validateContext = builder;
        validateOutput = result;
    }

    @RubricOrder(2)
    @DisplayName("Die Methode subset(MySet) gibt das korrekte Ergebnis für eine komplexe Eingabe zurück.")
    @ParameterizedTest(name = "Elements = {0}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
    })
    public void testPredicateComplex(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head,
        @ConvertWith(IntPredicateConverter.class) @Property("predicate") Predicate<VisitorElement<Integer>> predicate,
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("expected") ListItem<VisitorElement<Integer>> expected
    ) {
        MySet<VisitorElement<Integer>> source = new MySetAsCopy<>(head, cmp);
        reset(source);
        Context.Builder<?> builder = defaultBuilder().add("Source", source.toString());
        MySet<VisitorElement<Integer>> result = source.subset(predicate);
        builder.add("Result", result.toString());

        ListItem<VisitorElement<Integer>> current = expected;
        ListItem<VisitorElement<Integer>> actual = result.head;
        int index = 0;
        while (current != null && actual != null) {
            int currentIndex = index;
            ListItem<VisitorElement<Integer>> expectedElement = current;
            ListItem<VisitorElement<Integer>> actualElement = actual;
            Assertions2.assertEquals(
                current.key, actual.key,
                builder.add("Expected node at " + currentIndex, current.key)
                    .add("Actual node at " + currentIndex, actual.key)
                    .build(),
                r -> "Expected node %s but was %s".formatted(expectedElement.key, actualElement.key)
            );
            index++;
            current = current.next;
            actual = actual.next;
        }

        Assertions2.assertNull(current, builder.build(), r -> "Expected list contains more element than actual list");
        Assertions2.assertNull(actual, builder.build(), r -> "Actual list contains more element than expected list");

        validateInput = source;
        validateContext = builder;
        validateOutput = result;
    }
}
