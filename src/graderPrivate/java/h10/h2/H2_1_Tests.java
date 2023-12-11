package h10.h2;

import h10.DecoratorSet;
import h10.ListItem;
import h10.utils.ListItems;
import h10.MySet;
import h10.MySetAsCopy;
import h10.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

@TestForSubmission
@DisplayName("H2.1 | As-Copy")
public class H2_1_Tests extends H2_Tests {

    @Override
    public Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    protected BiFunction<ListItem<VisitorElement<Integer>>, Comparator<? super VisitorElement<Integer>>, MySet<VisitorElement<Integer>>> mapper() {
        return MySetAsCopy::new;
    }

    @Order(2)
    @DisplayName("Die Methode cartesianProduct(MySet) definiert den Comparator für ein Tupel korrekt.")
    @Test
    public void testComparator() {
        MySet<VisitorElement<Integer>> source = create(ListItems.of(1, 2));
        MySet<VisitorElement<Integer>> other = create(ListItems.of(3, 4));
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        DecoratorSet<ListItem<VisitorElement<Integer>>> result = new DecoratorSet<>(source.cartesianProduct(other));
        builder.add("Result", result.toString());
        Comparator<? super ListItem<VisitorElement<Integer>>> cmp = result.getCmp();

        ListItem<VisitorElement<Integer>> data1 = of(1, 5);
        ListItem<VisitorElement<Integer>> data2 = of(1, 6);
        ListItem<VisitorElement<Integer>> data3 = of(1, 4);
        ListItem<VisitorElement<Integer>> data4 = of(2, 5);
        List<ListItem<VisitorElement<Integer>>> data = List.of(data1, data2, data3, data4);


        int i = 1;
        for (ListItem<VisitorElement<Integer>> d : data) {
            builder = builder.add("Data " + i++, d);
        }
        Context context = builder.build();
        Assertions2.assertEquals(-1, cmp.compare(data1, data2), context,
            r -> "Data 1 %s should be < Data 2 %s".formatted(data.get(0), data.get(1)));
        Assertions2.assertEquals(1, cmp.compare(data1, data3), context,
            r -> "Data 1 %s should be > Data 3 %s".formatted(data.get(0), data.get(2)));
        Assertions2.assertEquals(-1, cmp.compare(data1, data4), context,
            r -> "Data 1 %s should be < Data 4 %s".formatted(data.get(0), data.get(3)));
        Assertions2.assertEquals(1, cmp.compare(data4, data1), context,
            r -> "Data 4 %s should be > Data 1 %s".formatted(data.get(3), data.get(0)));
        Assertions2.assertEquals(0, cmp.compare(data1, data1), context,
            r -> "Data 1 %s should be = Data 1 %s".formatted(data.get(0), data.get(0)));
    }
}
