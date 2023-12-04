package h10.h2;

import h10.ListItem;
import h10.ListItems;
import h10.MySet;
import h10.MySetInPlace;
import h10.VisitorElement;
import h10.VisitorMySet;
import h10.utils.RubricOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

public class H2_2_Tests extends H2_Tests {

    @Override
    protected Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    public VisitorMySet<Integer> create(ListItem<Integer> head) {
        return new VisitorMySet<>(head, DEFAULT_COMPARATOR, MySetInPlace::new);
    }

    @RubricOrder(classes = {MySetInPlace.class}, criteria = {8})
    @DisplayName("Die Elemente werden korrekt entkoppelt.")
    @Test
    public void testDecouple() {
        VisitorMySet<Integer> source = create(ListItems.of(1, 2));
        VisitorMySet<Integer> other = create(ListItems.of(3, 4));
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());

        // Decouple check
        source.itemStream().forEach(current -> {
            Assertions2.assertNull(current.next, builder.add("Not decoupled source node", current).build(),
                r -> "Source node %s was not decoupled".formatted(current));
        });
        other.itemStream().forEach(current -> {
            Assertions2.assertNull(current.next, builder.add("Not decoupled other node", current).build(),
                r -> "Other node %s was not decoupled".formatted(current));
        });
    }
}
