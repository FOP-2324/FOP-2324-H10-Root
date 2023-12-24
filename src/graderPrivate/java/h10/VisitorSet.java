package h10;

import h10.utils.ListItems;
import h10.visitor.Visitable;
import h10.visitor.VisitorComparator;
import h10.visitor.VisitorElement;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class VisitorSet<T> extends DecoratorSet<VisitorElement<T>> implements Visitable<T> {

    protected BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter;

    protected VisitorSet(
        ListItem<T> head,
        Comparator<? super T> cmp,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        this(converter.apply(ListItems.map(head, VisitorElement::new), new VisitorComparator<>(cmp)), converter);
    }

    protected VisitorSet(
        MySet<VisitorElement<T>> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        super(underlying);
        this.converter = converter;
    }

    public static <T> VisitorSet<T> of(
        MySet<VisitorElement<T>> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        VisitorSet<T> wrapped = new VisitorSet<>(underlying, converter);
        VisitorFix<T> visitorFix = new VisitorFix<>(wrapped);
        visitorFix.apply();
        return wrapped;
    }

    public static <T> VisitorSet<T> of(
        ListItem<T> head,
        Comparator<? super T> cmp,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        VisitorSet<T> wrapped = new VisitorSet<>(head, cmp, converter);
        VisitorFix<T> visitorFix = new VisitorFix<>(wrapped);
        visitorFix.apply();
        return wrapped;
    }

    public static <T> VisitorSet<T> convert(
        MySet<T> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        VisitorSet<T> wrapped = new VisitorSet<>(underlying.head, underlying.cmp, converter);
        VisitorFix<T> visitorFix = new VisitorFix<>(wrapped);
        visitorFix.apply();
        return wrapped;
    }

    public MySet<VisitorElement<T>> deepCopy() {
        ListItem<VisitorElement<T>> newHead = ListItems.map(head, Function.identity());
        return converter.apply(newHead, cmp);
    }

    @Override
    public Iterator<T> peekIterator() {
        return VisitorSets.peekIterator(this);
    }

    @Override
    public Stream<T> peekStream() {
        return VisitorSets.peekStream(this);
    }

    @Override
    public Iterator<ListItem<VisitorElement<T>>> underlyingIterator() {
        return VisitorSets.underlyingIterator(this);
    }

    @Override
    public Stream<ListItem<VisitorElement<T>>> underlyingStream() {
        return VisitorSets.underlyingStream(this);
    }

    private static class VisitorFix<T> extends VisitorSet<T> {

        private VisitorSet<T> toFix;

        public VisitorFix(VisitorSet<T> toFix) {
            super(
                toFix.converter.apply(
                    ListItems.map(toFix.head, element -> new VisitorElement<>(element.peek())), toFix.cmp),
                toFix.converter
            );
            this.toFix = toFix;
        }

        public void apply() {
            if (toFix == null) {
                return;
            }
            for (ListItem<VisitorElement<T>> current = toFix.head,
                 other = head; current != null && other != null;
                 current = current.next, other = other.next
            ) {
                current.key.reduce(other.key.visited());
            }
            toFix = null;
        }
    }
}
