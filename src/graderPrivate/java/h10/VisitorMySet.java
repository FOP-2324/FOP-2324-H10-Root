package h10;

import com.google.common.collect.Streams;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class VisitorMySet<T> extends DelegateMySet<VisitorElement<T>> implements Iterable<VisitorElement<T>> {


    public VisitorMySet(MySet<VisitorElement<T>> underlying) {
        super(underlying);
        stream().forEach(VisitorElement::reset);
    }

    public VisitorMySet(
        ListItem<T> head,
        Comparator<? super T> cmp,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> mapper
    ) {
        this(toVisitor(head, cmp, mapper));
    }

    public VisitorMySet(
        MySet<T> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> mapper
    ) {
        this(underlying.head, underlying.cmp, mapper);
    }

    private static <T> MySet<VisitorElement<T>> toVisitor(
        ListItem<T> head,
        Comparator<? super T> cmp,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> mapper
    ) {
        ListItem<VisitorElement<T>> visitorHead = null;
        ListItem<VisitorElement<T>> tail = null;
        for (ListItem<T> current = head; current != null; current = current.next) {
            ListItem<VisitorElement<T>> item = new ListItem<>(new VisitorElement<>(current.key));
            if (visitorHead == null) {
                visitorHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        Comparator<? super VisitorElement<T>> visitorCmp = new VisitorComparator<>(cmp);
        return mapper.apply(visitorHead, visitorCmp);
    }

    private <R> MySet<R> delegate(Function<MySet<VisitorElement<T>>, MySet<R>> f) {
        MySet<R> result = f.apply(underlying);
        head = underlying.head;
        return result;
    }

    @Override
    public MySet<VisitorElement<T>> subset(Predicate<? super VisitorElement<T>> pred) {
        return delegate(set -> set.subset(pred));
    }

    @Override
    public MySet<ListItem<VisitorElement<T>>> cartesianProduct(MySet<VisitorElement<T>> other) {
        return delegate(set -> set.cartesianProduct(other));
    }


    @Override
    public MySet<VisitorElement<T>> difference(MySet<VisitorElement<T>> other) {
        return delegate(set -> set.difference(other));
    }

    @Override
    protected MySet<VisitorElement<T>> intersectionListItems(ListItem<ListItem<VisitorElement<T>>> heads) {
        return delegate(set -> set.intersectionListItems(heads));
    }

    @Override
    public @NotNull Iterator<VisitorElement<T>> iterator() {
        return MySets.iterator(this);
    }

    public Iterator<T> rawIterator() {
        return MySets.rawVisitorIterator(this);
    }

    public Stream<VisitorElement<T>> stream() {
        return MySets.stream(this);
    }

    public Stream<T> rawStream() {
        return MySets.rawVisitorStream(this);
    }

    public Iterator<ListItem<VisitorElement<T>>> itemsIterator() {
        return MySets.itemsIterator(this);
    }

    public Stream<ListItem<VisitorElement<T>>> itemStream() {
        return MySets.itemsStream(this);
    }
}
