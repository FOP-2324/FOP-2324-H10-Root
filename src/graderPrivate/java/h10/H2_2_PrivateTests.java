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

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the public tests for H1.1.
 *
 * @author Nhan Huynh
 */
@DisplayName("H2.1 | AsCopy")
public class H2_2_PrivateTests extends H2_PrivateTests {

    @Override
    public MySet<VisitorElement<Integer>> create(ListItem<VisitorElement<Integer>> head) {
        return new MySetInPlace<>(head, cmp);
    }

    @Override
    public TypeLink getType() {
        return Links.getTypeLink(MySetInPlace.class);
    }

    @RubricOrder(types = {MySetInPlace.class}, criteria = {8})
    @DisplayName("Die Elemente werden korrekt entkoppelt.")
    @Test
    public void testDecouple() {
        MySet<VisitorElement<Integer>> source = create(ListItems.toListItem(List.of(1, 2)));
        MySet<VisitorElement<Integer>> other = create(ListItems.toListItem(List.of(3, 4)));
        List<ListItem<VisitorElement<Integer>>> sourceData = new ArrayList<>();
        List<ListItem<VisitorElement<Integer>>> otherData = new ArrayList<>();
        for (ListItem<VisitorElement<Integer>> current = source.head; current != null; current = current.next) {
            sourceData.add(current);
        }
        for (ListItem<VisitorElement<Integer>> current = other.head; current != null; current = current.next) {
            otherData.add(current);
        }
        Context.Builder<?> builder = defaultBuilder()
            .add("Source", source.toString())
            .add("Other", other.toString());
        MySet<ListItem<VisitorElement<Integer>>> result = source.cartesianProduct(other);
        builder.add("Result", result.toString());

        // Decouple check
        for (ListItem<VisitorElement<Integer>> current : sourceData) {
            Assertions2.assertNull(current.next, builder.add("Not decoupled source node", current).build(),
                r -> "Source node %s was not decoupled".formatted(current));
        }
        for (ListItem<VisitorElement<Integer>> current : otherData) {
            Assertions2.assertNull(current.next, builder.add("Not decoupled other node", current).build(),
                r -> "Other node %s was not decoupled".formatted(current));
        }

    }
}
