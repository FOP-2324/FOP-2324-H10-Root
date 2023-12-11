package h10;

import h10.utils.ListItems;
import h10.visitor.VisitorElement;

public final class Tracker<T> extends VisitorSet<T> {

    private Tracker(VisitorSet<T> toFix) {
        super(
            toFix.converter.apply(
                ListItems.map(toFix.head, element -> new VisitorElement<>(element.peek())), toFix.cmp),
            toFix.converter
        );

        for (ListItem<VisitorElement<T>> current = toFix.head,
             other = head; current != null && other != null;
             current = current.next, other = other.next
        ) {
            current.key.reduce(other.key.visited());
        }
    }

    public static <T> void fix(VisitorSet<T> toFix) {
        new Tracker<>(toFix);
    }
}
