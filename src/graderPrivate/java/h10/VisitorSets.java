package h10;

import com.google.common.collect.Streams;
import h10.visitor.VisitorElement;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class VisitorSets {

    private VisitorSets() {
    }

    public static <T> Iterator<T> peekIterator(MySet<VisitorElement<T>> set) {
        return new Iterator<>() {
            private ListItem<VisitorElement<T>> current = set.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = current.key.peek();
                current = current.next;
                return element;
            }
        };
    }

    public static <T> Stream<T> peekStream(VisitorSet<T> set) {
        return Streams.stream(() -> peekIterator(set));
    }

    public static <T> Iterator<ListItem<VisitorElement<T>>> underlyingIterator(MySet<VisitorElement<T>> set) {
        return new Iterator<>() {
            private ListItem<VisitorElement<T>> current = set.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListItem<VisitorElement<T>> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ListItem<VisitorElement<T>> element = current;
                current = current.next;
                return element;
            }
        };
    }

    public static <T> Stream<ListItem<VisitorElement<T>>> underlyingStream(MySet<VisitorElement<T>> set) {
        return Streams.stream(() -> underlyingIterator(set));
    }
}
