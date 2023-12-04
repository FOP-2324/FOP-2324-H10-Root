package h10;

import com.google.common.collect.Streams;
import h10.utils.ListItems;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class VisitorMySet<T> extends MySet<VisitorElement<T>> implements Iterable<VisitorElement<T>> {

    private final MySet<VisitorElement<T>> underlying;

    public VisitorMySet(MySet<VisitorElement<T>> underlying) {
        super(underlying.head, underlying.cmp);
        stream().forEach(VisitorElement::reset);
        this.underlying = underlying;
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
        return ListItems.iterator(head);
    }

    public @NotNull Iterator<ListItem<VisitorElement<T>>> visitorIterator() {
        return new Iterator<>() {

            ListItem<VisitorElement<T>> current = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListItem<VisitorElement<T>> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ListItem<VisitorElement<T>> tmp = current;
                current = current.next;
                return tmp;
            }
        };
    }

    public @NotNull Iterator<T> rawIterator() {
        return new Iterator<>() {

            private final Iterator<VisitorElement<T>> underlying = iterator();

            @Override
            public boolean hasNext() {
                return underlying.hasNext();
            }

            @Override
            public T next() {
                return underlying.next().peek();
            }
        };
    }

    public @NotNull Stream<VisitorElement<T>> stream() {
        return Streams.stream(this);
    }

    public @NotNull Stream<ListItem<VisitorElement<T>>> visitorStream() {
        return Streams.stream(this::visitorIterator);
    }

    public @NotNull Stream<T> rawStream() {
        return Streams.stream(this::rawIterator);
    }
}
