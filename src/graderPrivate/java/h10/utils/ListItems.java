package h10.utils;

import h10.ListItem;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
}
