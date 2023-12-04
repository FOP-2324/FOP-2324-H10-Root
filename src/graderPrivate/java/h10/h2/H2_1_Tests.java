package h10.h2;

import h10.DelegateMySet;
import h10.ListItem;
import h10.ListItems;
import h10.MySet;
import h10.MySetAsCopy;
import h10.VisitorElement;
import h10.VisitorMySet;
import h10.utils.RubricOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Comparator;
import java.util.List;

public class H2_1_Tests extends H2_Tests {

    @Override
    protected Class<?> getClassType() {
        return MySetAsCopy.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetAsCopy::new);
    }



    @RubricOrder(classes = {MySetAsCopy.class}, criteria = {7})
    @DisplayName("Der Comparator ist korrekt definiert.")
    @Test
    public void testComparator() {
        MySet<VisitorElement<Integer>> source = create(ListItems.of(1, 2));
        MySet<VisitorElement<Integer>> other = create(ListItems.of(3, 4));
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        DelegateMySet<ListItem<VisitorElement<Integer>>> result = new DelegateMySet<>(source.cartesianProduct(other));
        builder.add("Result", result.toString());
        Comparator<? super ListItem<VisitorElement<Integer>>> cmp = result.getComparator();

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
