package h10.utils;

import com.google.common.collect.Streams;
import h10.ListItem;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

public final class ListItems {

    @SafeVarargs
    public static <T> ListItem<T> of(T... elements) {
        return convert(List.of(elements));
    }

    public static <T> ListItem<T> convert(List<T> elements) {
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

    public static <T, R> ListItem<R> map(ListItem<T> head, Function<? super T, ? extends R> mapper) {
        ListItem<R> newHead = null;
        ListItem<R> tail = null;
        for (ListItem<T> current = head; current != null; current = current.next) {
            ListItem<R> item = new ListItem<>(mapper.apply(current.key));
            if (newHead == null) {
                newHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        return newHead;
    }
}
