package h10;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * An in-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
public class MySetInPlace<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    public MySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> current = this.head;
        ListItem<T> prev = null;
        ListItem<T> newHead = null;

        while (current != null) {
            if (!pred.test(current.key)) {
                ListItem<T> next = current.next;
                current.next = null;
                if (prev == null) {
                    current = next;
                } else {
                    prev.next = current = next;
                }
            } else {
                if (newHead == null) {
                    newHead = current;
                }
                prev = current;
                current = current.next;
            }
        }
        this.head = newHead;
        return this;
    }

    @Override
    public MySet<T> difference(MySet<? extends T> other) {

        ListItem<? extends T> p = other.head;
        while (p != null) {
            ListItem<T> current = this.head;
            ListItem<T> prev = null;

            while (current != null && !p.key.equals(current.key)) {
                prev = current;
                current = current.next;
            }

            if (current != null) {
                ListItem<T> next = current.next;
                if (prev != null) {
                    prev.next = next;
                }
                if (current == this.head) {
                    this.head = next;
                }
                current.next = null;
            }
            p = p.next;
        }

        return this;
    }

    @Override
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        if (this.head == null) {
            return this;
        }

        for (ListItem<MySet<T>> set = others; set != null; set = set.next) {
            if (set.key.head == null) {
                this.head = null;
                return this;
            }

            ListItem<T> intersection = getListOfItemsInSet(this.head, set.key.head);
            ListItem<T> current = this.head;
            ListItem<T> prev = null;
            ListItem<T> newHead = null;

            while (current != null) {
                ListItem<T> next = current.next;
                if (!contains(intersection, current.key)) {
                    current.next = null;
                    if (prev != null) {
                        prev.next = next;
                    }
                } else {
                    if (newHead == null) {
                        newHead = current;
                    }
                    prev = current;
                }
                current = next;
            }
            this.head = newHead;
        }
        return this;
    }

    @Override
    public MySet<T> union(ListItem<MySet<T>> others) {
        ListItem<T> tail = this.head;
        for (ListItem<T> p = this.head; p.next != null; p = p.next) {
            tail = tail.next;
        }

        for (ListItem<MySet<T>> set = others; set != null; set = set.next) {
            ListItem<T> current = set.key.head;
            ListItem<T> prev = null;
            ListItem<T> newHead = null;

            while (current != null) {
                ListItem<T> next = current.next;
                if (!contains(this.head, current.key)) {
                    tail = tail.next = current;
                    current.next = null;
                    if (prev != null) {
                        prev.next = next;
                    }
                } else {
                    if (newHead == null) {
                        newHead = current;
                    }
                    prev = current;
                }
                current = next;
            }
            set.key.head = newHead;
        }

        return this;
    }

    @Override
    public MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes) {
        ListItem<Tuple<T, ListItem<T>>> head = new ListItem<>();
        ListItem<Tuple<T, ListItem<T>>> tail = head;

        ListItem<T> index = indexes.head;
        ListItem<T> current = this.head;

        while (current != null) {
            ListItem<T> next = current.next;
            current.next = null;
            tail = tail.next = new ListItem<>(new Tuple<>(index.key, current));
            current = next;
        }

        index = index.next;
        ListItem<MySet<T>> set = others;

        while (index != null) {
            current = set.key.head;
            while (current != null) {
                ListItem<T> next = current.next;
                current.next = null;
                tail = tail.next = new ListItem<>(new Tuple<>(index.key, current));
                current = next;
            }
            set = set.next;
            index = index.next;
        }

        return new MySetInPlace<>(head, Comparator.comparing((Tuple<T, ListItem<T>> o) -> o.first(), cmp)
            .thenComparing((Tuple<T, ListItem<T>> o) -> o.second().key, cmp));
    }
}
