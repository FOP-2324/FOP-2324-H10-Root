package h10;

import h10.utils.ListItems;
import h10.visitor.Visitable;
import h10.visitor.VisitorComparator;
import h10.visitor.VisitorElement;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class VisitorSet<T> extends DecoratorSet<VisitorElement<T>> implements Visitable<T> {

    protected BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter;

    public VisitorSet(
        MySet<VisitorElement<T>> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        super(underlying);
        this.converter = converter;
    }

    public VisitorSet(
        ListItem<T> head,
        Comparator<? super T> cmp,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        this(converter.apply(ListItems.map(head, VisitorElement::new), new VisitorComparator<>(cmp)), converter);
    }

    public static <T> VisitorSet<T> of(
        MySet<T> underlying,
        BiFunction<ListItem<VisitorElement<T>>, Comparator<? super VisitorElement<T>>, MySet<VisitorElement<T>>> converter
    ) {
        return new VisitorSet<>(underlying.head, underlying.cmp, converter);
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
}
