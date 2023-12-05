package h10;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DelegateMySet<T> extends MySet<T> implements Iterable<T> {

    protected final MySet<T> underlying;

    public DelegateMySet(MySet<T> underlying) {
        super(underlying.head, underlying.cmp);
        this.underlying = underlying;
    }

    protected <R> MySet<R> delegate(Function<MySet<T>, MySet<R>> f) {
        MySet<R> result = f.apply(underlying);
        if (result instanceof MySetInPlace<R>) {
            head = underlying.head;
        }
        return result;
    }

    @Override
    public MySet<T> subset(Predicate<? super T> pred) {
        return delegate(s -> s.subset(pred));
    }

    @Override
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        return delegate(s -> s.cartesianProduct(other));
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        return delegate(s -> s.difference(other));
    }

    @Override
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        return delegate(s -> s.intersectionListItems(heads));
    }

    public Comparator<? super T> getComparator() {
        return underlying.cmp;
    }

    public void setHead(ListItem<T> head) {
        this.head = head;
        this.underlying.head = head;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return ListItems.iterator(head);
    }

    public Stream<T> stream() {
        return ListItems.stream(head);
    }
}
