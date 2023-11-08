package h10;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * An out-of-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
public class MySetAsCopy<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    public MySetAsCopy(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;
        for (ListItem<T> p = this.head; p != null; p = p.next) {
            if (pred.test(p.key)) {
                tail.next = new ListItem<>(p.key);
                tail = tail.next;
            }
        }
        MySet<T> subSet = new MySetAsCopy<>(head, cmp);
        return subSet;
    }

    @Override
    public MySet<T> difference(MySet<? extends T> other) {
        ListItem<T> head = null;
        ListItem<T> tail = null;

        for (ListItem<T> p = this.head; p != null; p = p.next) {
            if (head == null) {
                head = new ListItem<>(p.key);
                tail = head;
            } else {
                tail = tail.next = new ListItem<T>(p.key);
            }
        }

        ListItem<? extends T> p = other.head;

        while (p != null) {
            ListItem<T> current = head;
            ListItem<T> prev = null;


            while (current != null && !p.key.equals(current.key)) {
                if (prev == null) {
                    prev = current;
                } else {
                    prev = prev.next;
                }
                current = current.next;
            }

            if (current != null) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    head = current.next;
                }
                current.next = null;
            }
            p = p.next;
        }


        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        ListItem<T> head = null;
        ListItem<T> tail = null;

        if (this.head == null) {
            return new MySetAsCopy<T>(null, cmp);
        }

        for (ListItem<T> p = this.head; p != null; p = p.next) {
            if (head == null) {
                head = new ListItem<>(p.key);
                tail = head;
            } else {
                tail = tail.next = new ListItem<T>(p.key);
            }
        }

        for (ListItem<MySet<T>> set = others; set != null; set = set.next) {
            if (set.key.head == null) {
                return new MySetAsCopy<>(null, cmp);
            }

            ListItem<T> intersection = getListOfItemsInSet(head, set.key.head);

            ListItem<T> current = head;
            ListItem<T> prev = null;

            while (current != null) {
                if (!contains(intersection, current.key)) {
                    if (prev != null) {
                        prev.next = current.next;
                        current.next = null;
                        current = prev.next;
                    } else {
                        ListItem<T> next = current.next;
                        current.next = null;
                        current = next;
                    }
                } else {
                    if (prev == null) {
                        prev = current;
                        head = current;
                    } else {
                        prev = prev.next;
                    }
                    current = current.next;
                }
            }

        }

        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public MySet<T> union(ListItem<MySet<T>> others) {
        ListItem<T> head = null;
        ListItem<T> tail = null;

        for (ListItem<T> p = this.head; p != null; p = p.next) {
            if (head == null) {
                head = new ListItem<>(p.key);
                tail = head;
            } else {
                tail = tail.next = new ListItem<T>(p.key);
            }
        }

        for (ListItem<MySet<T>> set = others; set != null; set = set.next) {
            ListItem<T> p = set.key.head;

            while (p != null) {
                if (!contains(head.next, p.key)) {
                    tail = tail.next = new ListItem<>(p.key);
                }
                p = p.next;
            }
        }

        return new MySetAsCopy<>(head, cmp);
    }

    @Override
    public MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes) {
        ListItem<Tuple<T, ListItem<T>>> head = null;
        ListItem<Tuple<T, ListItem<T>>> tail = null;

        ListItem<T> index = indexes.head;
        ListItem<T> current = this.head;

        while (current != null) {
            if (head == null) {
                head = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                tail = head;
            } else {
                tail = tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
            }
            current = current.next;
        }

        index = index.next;
        ListItem<MySet<T>> set = others;

        while (index != null) {
            current = set.key.head;
            while (current != null) {
                tail = tail.next = new ListItem<>(new Tuple<>(index.key, new ListItem<>(current.key)));
                current = current.next;
            }
            set = set.next;
            index = index.next;
        }

        return new MySetAsCopy<>(head, Comparator.comparing((Tuple<T, ListItem<T>> o) -> o.first(), cmp)
            .thenComparing((Tuple<T, ListItem<T>> o) -> o.second().key, cmp));
    }
}
