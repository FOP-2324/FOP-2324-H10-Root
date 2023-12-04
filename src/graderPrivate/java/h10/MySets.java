package h10;

import java.util.Iterator;
import java.util.stream.Stream;

public class MySets {

    public static <T> Iterator<T> iterator(MySet<T> set) {
        return ListItems.iterator(set.head);
    }

    public static <T> Stream<T> stream(MySet<T> set) {
        return ListItems.stream(set.head);
    }

    public static <T> Iterator<T> rawVisitorIterator(MySet<VisitorElement<T>> set) {
        return ListItems.rawVIterator(set.head);
    }


    public static <T> Stream<T> rawVisitorStream(MySet<VisitorElement<T>> set) {
        return ListItems.rawStream(set.head);
    }

    public static <T> Iterator<ListItem<T>> itemsIterator(MySet<T> set) {
        return ListItems.itemsIterator(set.head);
    }

    public static <T> Stream<ListItem<VisitorElement<T>>> itemsStream(MySet<VisitorElement<T>> visitorElements) {
        return ListItems.itemsStream(visitorElements.head);
    }
}
