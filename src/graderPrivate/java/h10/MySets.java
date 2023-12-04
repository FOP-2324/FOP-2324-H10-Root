package h10;

import com.google.common.collect.Streams;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class MySets {

    public static <T> Iterator<T> iterator(MySet<T> set) {
        return ListItems.iterator(set.head);
    }

    public static <T> Iterator<ListItem<VisitorElement<T>>> visitorIterator(MySet<VisitorElement<T>> set) {
        return ListItems.visitorIterator(set.head);
    }

    public static <T> Iterator<T> rawVisitorIterator(MySet<VisitorElement<T>> set) {
        return ListItems.rawVisitorIterator(set.head);
    }

    public static <T> Stream<T> stream(MySet<T> set) {
        return ListItems.stream(set.head);
    }

    public static <T> Stream<ListItem<VisitorElement<T>>> visitorStream(MySet<VisitorElement<T>> set) {
        return ListItems.visitorStream(set.head);
    }

    public static <T> Stream<T> rawVisitorStream(MySet<VisitorElement<T>> set) {
        return ListItems.rawVisitorStream(set.head);
    }
}
