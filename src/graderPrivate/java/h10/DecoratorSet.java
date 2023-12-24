package h10;

import h10.utils.ListItems;
import h10.utils.Streamable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DecoratorSet<T> extends MySet<T> implements Iterable<T>, Streamable<T> {

    protected final MySet<T> underlying;

    public DecoratorSet(MySet<T> underlying) {
        super(underlying.head, underlying.cmp);
        this.underlying = underlying;
    }

    public DecoratorSet(MySet<T> underlying, Comparator<? super T> cmp) {
        super(underlying.head, cmp);
        this.underlying = underlying;
    }

    @Override
    protected boolean isPairwiseDifferent(ListItem<T> head, Comparator<? super T> cmp) {
        // Do not check since underlying must fulfill the properties anyway
        return true;
    }

    @Override
    protected boolean isOrdered(ListItem<T> head, Comparator<? super T> cmp) {
        // Do not check since underlying must fulfill the properties anyway
        return true;
    }

    @Override
    public MySet<T> subset(Predicate<? super T> pred) {
        return delegate(s -> s.subset(pred));
    }

    protected <R> MySet<R> delegate(Function<MySet<T>, MySet<R>> f) {
        MySet<R> result = f.apply(underlying);

        // In-Place override head
        if (result instanceof MySetInPlace<R>) {
            head = underlying.head;
        }
        return result;
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

    public ListItem<T> getHead() {
        return underlying.head;
    }

    public void setHead(ListItem<T> head) {
        this.head = head;
        this.underlying.head = head;
    }

    public Comparator<? super T> getCmp() {
        return cmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DecoratorSet<?> that = (DecoratorSet<?>) o;
        return Objects.equals(underlying, that.underlying);
    }

    @Override
    public int hashCode() {
        return Objects.hash(underlying);
    }

    @Override
    public Iterator<T> iterator() {
        return ListItems.iterator(head);
    }

    @Override
    public Stream<T> stream() {
        return ListItems.stream(head);
    }

}
