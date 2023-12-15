package h10;

import com.google.common.collect.Streams;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class Sets {

    private Sets() {
    }

    public static <T> Stream<T> stream(MySet<T> set) {
        return Streams.stream(() -> iterator(set));
    }

    public static <T> Iterator<T> iterator(MySet<T> set) {
        return new Iterator<>() {
            private ListItem<T> current = set.head;

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = current.key;
                current = current.next;
                return element;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }
        };
    }
}
