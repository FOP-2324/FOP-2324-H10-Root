package h10.rubric;

import h10.ListItem;
import h10.MySet;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.rubric.h3.H3_Tests;
import h10.rubric.h4.H4_Tests;
import h10.utils.ListItems;
import h10.visitor.VisitorElement;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junitpioneer.jupiter.json.Property;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.conversion.ArrayConverter;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

/**
 * This class is used to test the complex method implementation of the {@link MySet} interface.
 *
 * @author Nhan Huynh
 * @see H3_Tests
 * @see H4_Tests
 */
public abstract class ComplexTest extends SimpleTest {

    /**
     * Returns the operation to be tested.
     *
     * @return the operation to be tested
     */
    protected abstract BiFunction<VisitorSet<Integer>, VisitorSet<Integer>, MySet<VisitorElement<Integer>>> operation();

    /**
     * Tests whether the result of the operation is correct.
     *
     * @param sourceHead   the head of the source set
     * @param otherHead    the head of the other set
     * @param expectedHead the head of the expected set
     */
    protected void assertEqualElements(
        ListItem<Integer> sourceHead,
        ListItem<Integer> otherHead,
        ListItem<Integer> expectedHead
    ) {
        VisitorSet<Integer> source = visit(sourceHead);
        VisitorSet<Integer> other = visit(otherHead);
        VisitorSet<Integer> expected = visit(expectedHead);
        Context.Builder<?> builder = contextBuilder().add("Source", source.toString())
            .add("Other", other.toString())
            .add("Expected", expected.toString());

        VisitorSet<Integer> result = visit(operation().apply(source, other));
        builder.add("Result", result.toString());
        Assertions2.assertEquals(expected, result, builder.build(),
            r -> "Expected set %s, but given %s".formatted(expected, result));
    }

    public void testNotAddElements(
        ListItem<Integer> sourceHead,
        ListItem<Integer> otherHead,
        ListItem<Integer> expectedHead
    ) {
        assertEqualElements(sourceHead, otherHead, expectedHead);
    }

    public void testXSmallerY(
        ListItem<Integer> sourceHead,
        ListItem<Integer> otherHead,
        Integer[] sourceVisitation,
        Integer[] otherVisitation
    ) {
        assertPointers(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }

    protected void assertPointers(
        ListItem<Integer> sourceHead,
        ListItem<Integer> otherHead,
        Integer[] sourceVisitation,
        Integer[] otherVisitation
    ) {
        VisitorSet<Integer> source = visit((ListItem<Integer>) null);
        source.setHead(ListItems.map(sourceHead, VisitorElement::new));
        VisitorSet<Integer> other = visit((ListItem<Integer>) null);
        other.setHead(ListItems.map(otherHead, VisitorElement::new));

        // Used for in-place check
        VisitorSet<Integer> sourceCopy = visit(source.deepCopy());
        VisitorSet<Integer> otherCopy = visit(other.deepCopy());

        Context.Builder<?> builder = contextBuilder().add("Source before", source.toString())
            .add("Other before", other.toString());

        // Use MockedConstruction to disable the constructor of MySet where the visitation will be falsely counted
        try (MockedConstruction<?> construction = Mockito.mockConstruction(getClassType())) {
            MySet<VisitorElement<Integer>> result = source.difference(other);
            builder.add("Result", result.toString());
            builder.add("Source after", source.toString());
            builder.add("Other after", other.toString());
            builder.add("Source Visitation", List.of(sourceVisitation));
            builder.add("Other Visitation", List.of(otherVisitation));
            Context context = builder.build();
            assertVisitation(sourceCopy, sourceVisitation, "Source", context);
            assertVisitation(otherCopy, otherVisitation, "Other", context);
        }
    }

    /**
     * Asserts whether the visitation of the given set is correct.
     *
     * @param set                the set to be checked
     * @param expectedVisitation the expected visitation
     * @param setName            the name of the set
     * @param context            the context to be used
     */
    protected void assertVisitation(
        VisitorSet<Integer> set,
        Integer[] expectedVisitation,
        String setName,
        Context context
    ) {
        Iterator<VisitorElement<Integer>> it = set.iterator();
        for (Integer expected : expectedVisitation) {
            if (!it.hasNext()) {
                break;
            }
            VisitorElement<Integer> element = it.next();
            if (expected == 1) {
                Assertions2.assertTrue(element.visited() != 0, context,
                    r -> "Expected %s (%s) to be visited, but was not visited".formatted(element, setName));
            } else {
                Assertions2.assertTrue(element.visited() == 0, context,
                    r -> "Expected %s (%s) not to be visited, but was visited".formatted(element, setName));
            }
        }
    }

    public void testXGreaterY(
        @ConvertWith(ListItemConverter.Int.class) @Property("head") ListItem<Integer> sourceHead,
        @ConvertWith(ListItemConverter.Int.class) @Property("other") ListItem<Integer> otherHead,
        @ConvertWith(ArrayConverter.Auto.class) @Property("sourceVisitation") Integer[] sourceVisitation,
        @ConvertWith(ArrayConverter.Auto.class) @Property("otherVisitation") Integer[] otherVisitation
    ) {
        assertPointers(sourceHead, otherHead, sourceVisitation, otherVisitation);
    }
}
