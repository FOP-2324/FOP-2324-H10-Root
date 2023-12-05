package h10;

import com.google.common.collect.Streams;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ListItems {

    @SafeVarargs
    public static <T> ListItem<T> of(T... elements) {
        ListItem<T> head = null;
        ListItem<T> tail = null;
        for (T element : elements) {
            ListItem<T> item = new ListItem<>(element);
            if (head == null) {
                head = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        return head;
    }

    public static <T> Iterator<T> iterator(ListItem<T> head) {
        return new Iterator<>() {
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

    public static <T> Stream<T> stream(ListItem<T> head) {
        return Streams.stream(() -> iterator(head));
    }

    public static <T> Iterator<T> rawVIterator(ListItem<VisitorElement<T>> head) {
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

    public static <T> Stream<T> rawStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> rawVIterator(head));
    }

    public static <T> Iterator<ListItem<T>> itemsIterator(ListItem<T> head) {
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

    public static <T> Stream<ListItem<VisitorElement<T>>> itemsStream(ListItem<VisitorElement<T>> head) {
        return Streams.stream(() -> itemsIterator(head));
    }
}
