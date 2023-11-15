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
        ListItem<T> newHead = null;
        ListItem<T> tail = null;
        for (ListItem<T> current = head; current != null; current = current.next) {
            if (pred.test(current.key)) {
                ListItem<T> item = new ListItem<>(current.key);
                if (newHead == null) {
                    newHead = item;
                } else {
                    tail.next = item;
                }
                tail = item;
            }
        }
        return new MySetAsCopy<>(newHead, cmp);
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        ListItem<T> newHead = null;
        ListItem<T> tail = null;

        ListItem<T> current = head;
        ListItem<T> otherCurrent = other.head;

        while (current != null && otherCurrent != null) {
            int compare = cmp.compare(current.key, otherCurrent.key);
            if (compare == 0) {
                // Case 1: Skip both elements on both sets if they are equal
                current = current.next;
                otherCurrent = otherCurrent.next;
                continue;
            }
            // Case 2: If they are not equal, always add the element to the new set
            ListItem<T> item = new ListItem<>(current.key);
            if (newHead == null) {
                newHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
            if (compare < 0) {
                // Case 2.1: Since the element in the other set is greater than the element in this set,
                // we need to check the next element in this set if it is equal, smaller or greater to the current
                // element in the other set
                // E.g. this set: 1, 2, 3, 4, 5 and other set: 2, 3, 4, 5, 6
                // Since 1 < 2, move the pointer of this set to the next element
                current = current.next;
            } else {
                // Case 2.2: Since the element in the other set is smaller than the element in this set,
                // we need to check the next element in the other set if it is equal, smaller or greater to the current
                // element in this set
                // E.g. this set: 1, 2, 3, 4, 5 and other set: 0, 1, 2, 3, 4
                // Since 0 < 1, move the pointer of the other set to the next element
                otherCurrent = otherCurrent.next;
            }
        }
        while (current != null) {
            // Case 3: If the other set is empty, add all remaining elements of this set to the new set
            ListItem<T> item = new ListItem<>(current.key);
            if (newHead == null) {
                newHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
            current = current.next;
        }
        while (otherCurrent != null) {
            // Case 4: If this set is empty, add all remaining elements of the other set to the new set
            ListItem<T> item = new ListItem<>(otherCurrent.key);
            if (newHead == null) {
                newHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
            otherCurrent = otherCurrent.next;
        }
        return new MySetAsCopy<>(newHead, cmp);
    }

    @Override
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        ListItem<T> newHead = null;
        ListItem<T> tail = null;

        while (heads != null && heads.next != null && heads.key != null) {
            T current = heads.key.key;
            heads.key = heads.key.next;
            // Check if the current element is contained in all sets
            boolean common = true;
            for (ListItem<ListItem<T>> otherHeads = heads.next; otherHeads != null; ) {
                // Case 1: The other set is smaller than the current set. We do not have to check for common elements
                // anymore
                if (otherHeads.key == null) {
                    return new MySetAsCopy<>(newHead, cmp);
                }
                T other = otherHeads.key.key;
                int comparison = cmp.compare(current, other);

                if (comparison == 0) {
                    // Case 2: Current set contains the element, check next set
                    otherHeads.key = otherHeads.key.next;
                    otherHeads = otherHeads.next;
                } else if (comparison < 0) {
                    // Case 3: Current set does not contain the element, check next element in current set
                    // Since the elements are ordered and the element x < y where x is in this set and y is in the other
                    // set, we can safely assume that the element is not contained in the other sets or else we would
                    // have found it already
                    common = false;
                    break;
                } else {
                    // Case 4: Current set is greater than the other set, check successor element of the other set
                    otherHeads.key = otherHeads.key.next;
                }
            }

            // If the element is not contained in all sets, skip it
            if (!common) {
                continue;
            }

            // Add the element to the new set if it is contained in all sets
            ListItem<T> item = new ListItem<>(current);
            if (newHead == null) {
                newHead = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }

        return new MySetAsCopy<>(newHead, cmp);
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
