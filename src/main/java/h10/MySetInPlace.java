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
        // Remember the previous element in order to decouple the current element from the set
        ListItem<T> previous = null;
        ListItem<T> current = head;

        while (current != null) {
            // Remove the element from the set when it does not satisfy the predicate
            if (!pred.test(current.key)) {
                if (previous == null) {
                    // Case 1: The element to remove is the head
                    head = current.next;
                } else {
                    // Case 2: The element to remove is not the head
                    // Since the previous element is the element which is not removed and the successor needs to be
                    // removed which is the current element, we can safely set the successor of the previous element to
                    // the successor of the current element to remove the current element from the set
                    previous.next = current.next;
                }
            } else {
                // Case 3: Remember the previous element which is not removed
                previous = current;
            }
            current = current.next;
        }
        return this;
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        ListItem<T> current = head;
        ListItem<T> otherCurrent = other.head;
        // Remember the previous element in order to decouple the current element from the set
        ListItem<T> previous = null;

        while (current != null && otherCurrent != null) {
            int compare = cmp.compare(current.key, otherCurrent.key);

            if (compare < 0) {
                // Case 1: Since the element in the other set is greater than the element in this set,
                // we need to check the next element in this set if it is equal, smaller or greater to the current
                // element in the other set
                // E.g. this set: 1, 2, 3, 4, 5 and other set: 2, 3, 4, 5, 6
                // Since 1 < 2, move the pointer of this set to the next element
                previous = current;
                current = current.next;
            } else if (compare > 0) {
                // Case 2: Since the element in the other set is smaller than the element in this set,
                // we need to check the next element in the other set if it is equal, smaller or greater to the current
                // element in this set
                // E.g. this set: 1, 2, 3, 4, 5 and other set: 0, 1, 2, 3, 4
                // Since 0 < 1, move the pointer of the other set to the next element
                otherCurrent = otherCurrent.next;
            } else {
                // Case 3: Elements are equal, remove the element from the current set
                if (previous == null) {
                    // Case 3.1: The element to remove is the head
                    head = current.next;
                } else {
                    // Case 3.1: The element to remove is not the head
                    // Since the previous element is the element which is not removed and the successor needs to be
                    // removed which is the current element, we can safely set the successor of the previous element to
                    // the successor of the current element to remove the current element from the set
                    previous.next = current.next;
                }
                current = current.next;
            }
        }

        // If there are remaining elements in the current set, keep them
        while (current != null) {
            previous = current;
            current = current.next;
        }

        // If there are remaining elements in the other set, remove them
        if (otherCurrent != null) {
            if (previous == null) {
                // This set was empty
                head = otherCurrent;
            } else {
                previous.next = otherCurrent;
            }
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
