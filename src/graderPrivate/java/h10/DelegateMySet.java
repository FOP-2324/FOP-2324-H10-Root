package h10;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DelegateMySet<T> extends MySet<T> implements Iterable<T> {

    protected final MySet<T> underlying;

    public DelegateMySet(MySet<T> underlying) {
        super(underlying.head, underlying.cmp);
        this.underlying = underlying;
    }

    @Override
    public MySet<T> subset(Predicate<? super T> pred) {
        return underlying.subset(pred);
    }

    @Override
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        return underlying.cartesianProduct(other);
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        return underlying.difference(other);
    }

    @Override
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        return underlying.intersectionListItems(heads);
    }

    public Comparator<? super T> getComparator() {
        return underlying.cmp;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return ListItems.iterator(head);
    }

    public Stream<T> stream() {
        return ListItems.stream(head);
    }
}
