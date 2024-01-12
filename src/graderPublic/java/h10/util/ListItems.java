package h10.util;

import com.google.common.collect.Streams;
import h10.ListItem;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A utility class for {@link ListItem}s which provides additional operations on lists.
 *
 * @author Nhan Huynh
 * @see ListItem
 */
public final class ListItems {

    /**
     * This class cannot be instantiated.
     */
    private ListItems() {
    }

    /**
     * Creates a list item sequence of the given elements.
     *
     * @param elements the elements to create a list item sequence of
     * @param <T>      the type of the elements
     * @return the head of the list item sequence
     */
    @SafeVarargs
    public static <T> ListItem<T> of(T... elements) {
        return convert(List.of(elements));
    }

    /**
     * Converts the given list of elements to a list of list items.
     *
     * @param elements the elements to convert
     * @param <T>      the type of the elements
     * @return the head of list item sequence representing the given list of elements
     */
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

    /**
     * Returns an iterator over the elements in the given list, mapping each element using the given mapper function.
     *
     * @param head   the head of the list to iterate over
     * @param mapper the mapper function
     * @param <T>    the type of the elements in the given list
     * @param <R>    the type of the elements in the mapped list
     * @return an iterator over the elements in the given list, mapping each element using the given mapper function
     */
    public static <T, R> Iterator<R> iterator(ListItem<T> head, Function<T, R> mapper) {
        return new Iterator<>() {
            private ListItem<T> current = head;

            @Override
            public R next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                R element = mapper.apply(current.key);
                current = current.next;
                return element;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }
        };
    }

    /**
     * Returns an iterator over the elements in the given list.
     *
     * @param head the head of the list to iterate over
     * @param <T>  the type of the elements in the given list
     * @return an iterator over the elements in the given list
     */
    public static <T> Iterator<T> iterator(ListItem<T> head) {
        return iterator(head, Function.identity());
    }

    /**
     * Returns a stream of the elements in the given list, mapping each element using the given mapper function.
     *
     * @param head   the head of the list to stream
     * @param mapper the mapper function
     * @param <T>    the type of the elements in the given list
     * @param <R>    the type of the elements in the mapped list
     * @return a stream of the elements in the given list, mapping each element using the given mapper function
     */
    public static <T, R> Stream<R> stream(ListItem<T> head, Function<T, R> mapper) {
        return Streams.stream(() -> iterator(head, mapper));
    }

    /**
     * Returns a stream of the elements in the given list.
     *
     * @param head the head of the list to stream
     * @param <T>  the type of the elements in the given list
     * @return a stream of the elements in the given list
     */
    public static <T> Stream<T> stream(ListItem<T> head) {
        return stream(head, Function.identity());
    }

    /**
     * Maps the elements of the given list using the given mapper function to another type.
     *
     * @param head   the head of the list to map
     * @param mapper the mapper function
     * @param <T>    the type of the elements in the given list
     * @param <R>    the type of the elements in the mapped list
     * @return the head of the mapped list
     */
    public static <T, R> ListItem<R> map(ListItem<? extends T> head, Function<? super T, ? extends R> mapper) {
        ListItem<R> newHead = null;
        ListItem<R> tail = null;
        for (ListItem<? extends T> current = head; current != null; current = current.next) {
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
