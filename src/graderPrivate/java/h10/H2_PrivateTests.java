package h10;

import h10.utils.Links;
import h10.utils.RubricOrder;
import h10.utils.converter.ListItemConverter;
import h10.utils.converter.ListItemTupleConverter;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

/**
 * Defines the public tests for H1.
 */
public abstract class H2_PrivateTests extends AbstractTest {

    /**
     * The path to the test resources.
     */
    private static final String TEST_RESOURCE_PATH = "h2/";

    /**
     * The name of the method to test.
     */
    private static final String METHOD_NAME = "cartesianProduct";

    @BeforeAll
    @Override
    public void globalSetup() {
        type = getType();
        method = Links.getMethodLink(type, METHOD_NAME);
    }

    /**
     * Returns the type of the set to test.
     *
     * @return the type of the set to test.
     */
    public abstract TypeLink getType();

    /**
     * Creates a new set from the given head.
     *
     * @param head the head of the list.
     * @return a new set from the given head.
     */
    public abstract MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head);

    /**
     * Tests if the cartesian product of the given sets is correct.
     *
     * @param head      the head of the first set
     * @param otherHead the head of the second set
     * @param expected  the expected cartesian product
     */
    private void test(
        ListItem<VisitorElement<Integer>> head,
        ListItem<VisitorElement<Integer>> otherHead,
        ListItem<ListItem<VisitorElement<Integer>>> expected
    ) {
        MySet<VisitorElement<Integer>> source = create(head);
        MySet<VisitorElement<Integer>> other = create(otherHead);
        Context.Builder<?> builder = defaultBuilder().add("Source", source.toString()).add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());

        for (ListItem<ListItem<VisitorElement<Integer>>> current = expected; current != null; current = current.next) {
            boolean found = false;
            for (ListItem<ListItem<VisitorElement<Integer>>> actual = result.head; actual != null; actual = actual.next) {
                if (current.key.equals(actual.key)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                continue;
            }
            ListItem<ListItem<VisitorElement<Integer>>> element = current;
            Assertions2.fail(builder.build(), r -> "Expected tuple %s, but found none".formatted(element.key));
        }
    }

    @RubricOrder(criteria = {5, 9})
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für simple Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion1_testcase1.json",
        TEST_RESOURCE_PATH + "criterion1_testcase2.json",
    })
    public void testSimple(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head,
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("otherHead") ListItem<VisitorElement<Integer>> otherHead,
        @ConvertWith(ListItemTupleConverter.VisitorNode.class) @Property("expected") ListItem<ListItem<VisitorElement<Integer>>> expected
    ) {
        test(head, otherHead, expected);
    }

    @RubricOrder(criteria = {6, 10})
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für komplexe Eingaben zurück.")
    @ParameterizedTest(name = "Source = {0}, Other {1}")
    @JsonClasspathSource({
        TEST_RESOURCE_PATH + "criterion2_testcase1.json",
        TEST_RESOURCE_PATH + "criterion2_testcase2.json",
        TEST_RESOURCE_PATH + "criterion2_testcase3.json",
        TEST_RESOURCE_PATH + "criterion2_testcase4.json",
    })
    public void testComplex(
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("head") ListItem<VisitorElement<Integer>> head,
        @ConvertWith(ListItemConverter.VisitorNode.class) @Property("otherHead") ListItem<VisitorElement<Integer>> otherHead,
        @ConvertWith(ListItemTupleConverter.VisitorNode.class) @Property("expected") ListItem<ListItem<VisitorElement<Integer>>> expected
    ) {
        test(head, otherHead, expected);
    }
}
