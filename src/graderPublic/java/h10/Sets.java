package h10;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A utility class for {@link MySet}s which provides additional operations on sets.
 *
 * @author Nhan Huynh
 * @see MySet
 */
public final class Sets {

    /**
     * This class cannot be instantiated.
     */
    private Sets() {

    }

    /**
     * Returns an iterator over the list items of the given set.
     *
     * @param set the set to iterate over
     * @param <T> the type of the elements in the set
     * @return an iterator over the list items of the given set
     */
    public static <T> Iterator<ListItem<T>> itemsIterator(MySet<T> set) {
        return new Iterator<>() {
            private ListItem<T> current = set.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public ListItem<T> next() {
                ListItem<T> value = current;
                current = current.next;
                return value;
            }
        };
    }

    /**
     * Returns an iterator over the given set.
     *
     * @param set the set to iterate over
     * @param <T> the type of the elements in the set
     * @return an iterator over the given set
     */
    public static <T> Iterator<T> iterator(MySet<T> set) {
        return new Iterator<>() {
            private Iterator<ListItem<T>> items = itemsIterator(set);

            @Override
            public boolean hasNext() {
                return items.hasNext();
            }

            @Override
            public T next() {
                return items.next().key;
            }
        };
    }

    /**
     * Returns a stream to the list items of the given set.
     *
     * @param set the set to stream
     * @param <T> the type of the elements in the set
     * @return a stream to the list items of the given set
     */
    public static <T> Stream<ListItem<T>> itemsStream(MySet<T> set) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(itemsIterator(set), Spliterator.ORDERED), false);
    }

    /**
     * Returns a stream of the given set.
     *
     * @param set the set to stream
     * @param <T> the type of the elements in the set
     * @return a stream of the given set
     */
    public static <T> Stream<T> stream(MySet<T> set) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(set), Spliterator.ORDERED), false);
    }
}
