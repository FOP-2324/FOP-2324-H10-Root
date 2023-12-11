package h10;

import com.google.common.collect.Streams;
import h10.visitor.VisitorElement;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class VisitorItems {

    private VisitorItems() {
    }

    public static <T> Iterator<T> peekIterator(ListItem<VisitorElement<T>> head) {
        return new Iterator<>() {
            private ListItem<VisitorElement<T>> current = head;

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

    public static <T> Stream<T> peekStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> peekIterator(head));
    }

    public static <T> Iterator<ListItem<T>> underlyingIterator(ListItem<T> head) {
        return new Iterator<>() {
            private ListItem<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListItem<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ListItem<T> element = current;
                current = current.next;
                return element;
            }
        };
    }

    public static <T> Stream<ListItem<VisitorElement<T>>> underlyingStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> underlyingIterator(head));
    }
}
