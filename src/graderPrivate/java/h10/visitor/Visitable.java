package h10.visitor;

import h10.ListItem;

import java.util.Iterator;
import java.util.stream.Stream;

public interface Visitable<T> extends Iterable<VisitorElement<T>> {

    Stream<VisitorElement<T>> stream();

    Iterator<T> peekIterator();

    Stream<T> peekStream();

    Iterator<ListItem<VisitorElement<T>>> underlyingIterator();

    Stream<ListItem<VisitorElement<T>>> underlyingStream();
}
