package h10.rubric;

import h10.ListItem;
import h10.MySet;
import h10.VisitorSet;
import h10.converter.ListItemConverter;
import h10.utils.ListItems;
import h10.visitor.VisitorElement;
import org.jetbrains.annotations.NotNull;
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

public abstract class ComplexTest extends SimpleTest {

    protected abstract BiFunction<VisitorSet<Integer>, VisitorSet<Integer>, MySet<VisitorElement<Integer>>> operation();

    public void testNotAddElements(
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
            assertVisitation(source, sourceVisitation, "Source", context);
            assertVisitation(other, otherVisitation, "Other", context);
        }
    }

    protected void assertVisitation(
        VisitorSet<Integer> set,
        Integer[] expectedVisitation,
        String setName,
        Context context
    ) {
        @NotNull Iterator<VisitorElement<Integer>> it = set.iterator();
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
