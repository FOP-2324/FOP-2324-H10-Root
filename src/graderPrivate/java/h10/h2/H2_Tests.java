package h10.h2;

import h10.AbstractTest;
import h10.ListItem;
import h10.ListItems;
import h10.MySet;
import h10.MySets;
import h10.VisitorElement;
import h10.VisitorMySet;
import h10.utils.ListItemConverter;
import h10.utils.ListItemInListItemConverter;
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

import java.util.Objects;

@TestForSubmission
@DisplayName("H2 | cartesianProduct(MySet)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class H2_Tests extends AbstractTest {

    protected static final String TEST_RESOURCE_PATH = "h2/";

    protected static final String METHOD_NAME = "cartesianProduct";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @SafeVarargs
    protected final <T> ListItem<VisitorElement<T>> of(T... values) {
        ListItem<VisitorElement<T>> head = null;
        ListItem<VisitorElement<T>> tail = null;
        for (T value : values) {
            ListItem<VisitorElement<T>> node = new ListItem<>(new VisitorElement<>(value));
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }
        return head;
    }

    private void assertTuples(
        ListItem<Integer> head,
        ListItem<Integer> otherHead,
        ListItem<ListItem<Integer>> expected
    ) {
        VisitorMySet<Integer> source = create(head);
        VisitorMySet<Integer> other = create(otherHead);
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());

        ListItems.stream(expected).forEach(tuple -> {
            if (MySets.stream(result)
                .noneMatch(actual -> Objects.equals(tuple.key, actual.key.peek())
                    && tuple.next != null && actual.next != null
                    && Objects.equals(tuple.next.key, actual.next.key.peek()))) {
                Assertions2.fail(
                    builder.add("Expected tuple", tuple.toString()).build(),
                    r -> "Expected tuple %s not found in result".formatted(tuple.toString())
                );
            }
        });
    }

    @Order(0)
    @DisplayName( "Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für simple Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
    })
    public void testSimple(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head,
        @ConvertWith(ListItemConverter.Int.class) @Property("otherHead") ListItem<Integer> otherHead,
        @ConvertWith(ListItemInListItemConverter.Int.class) @Property("expected") ListItem<ListItem<Integer>> expected
    ) {
        assertTuples(head, otherHead, expected);
    }

    @Order(1)
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
        TEST_RESOURCE_PATH + "criterion2_testcase4.json",
    })
    public void testComplex(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> head,
        @ConvertWith(ListItemConverter.Int.class) @Property("otherHead") ListItem<Integer> otherHead,
        @ConvertWith(ListItemInListItemConverter.Int.class) @Property("expected") ListItem<ListItem<Integer>> expected
    ) {
        assertTuples(head, otherHead, expected);
    }
}
