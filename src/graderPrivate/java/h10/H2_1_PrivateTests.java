package h10;

import h10.utils.Links;
import h10.utils.ListItems;
import h10.utils.RubricOrder;
import h10.utils.visitor.VisitorElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Comparator;
import java.util.List;

/**
 * Defines the public tests for H1.1.
 *
 * @author Nhan Huynh
 */
@DisplayName("H2.1 | AsCopy")
public class H2_1_PrivateTests extends H2_PrivateTests {

    @Override
    public MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head) {
        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public TypeLink getType() {
        return Links.getTypeLink(MySetAsCopy.class);
    }


    @RubricOrder(types = {MySetAsCopy.class}, criteria = {7})
    @DisplayName("Der Comparator funktioniert korrekt.")
    @Test
    public void testComparator() {
        MySet<VisitorElement<Integer>> source = create(ListItems.toListItem(List.of(1, 2)));
        MySet<VisitorElement<Integer>> other = create(ListItems.toListItem(List.of(3, 4)));
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());
        Comparator<? super ListItem<VisitorElement<Integer>>> cmp = result.cmp;

        ListItem<VisitorElement<Integer>> data1 = ListItems.toListItem(List.of(1, 5));
        ListItem<VisitorElement<Integer>> data2 = ListItems.toListItem(List.of(1, 6));
        ListItem<VisitorElement<Integer>> data3 = ListItems.toListItem(List.of(1, 4));
        ListItem<VisitorElement<Integer>> data4 = ListItems.toListItem(List.of(2, 5));
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
