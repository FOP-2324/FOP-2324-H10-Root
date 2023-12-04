package h10;

import com.google.common.collect.Streams;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ListItems {

    public static <T> Iterator<T> iterator(ListItem<T> head) {
        return new Iterator<T>() {
            private ListItem<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = current.key;
                current = current.next;
                return element;
            }
        };
    }

    public static <T> Iterator<ListItem<VisitorElement<T>>> visitorIterator(ListItem<VisitorElement<T>> head) {
        return new Iterator<>() {

            ListItem<VisitorElement<T>> current = head;

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

    public static <T> Iterator<T> rawVisitorIterator(ListItem<VisitorElement<T>> head) {
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

    public static <T> Stream<T> stream(ListItem<T> head) {
        return Streams.stream(() -> iterator(head));
    }

    public static <T> Stream<ListItem<VisitorElement<T>>> visitorStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> visitorIterator(head));
    }

    public static <T> Stream<T> rawVisitorStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> rawVisitorIterator(head));
    }
}
