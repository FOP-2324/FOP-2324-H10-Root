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
        // The previous element is the last element of the set which is not decoupled
        ListItem<T> previous = null;
        ListItem<T> current = head;

        while (current != null) {
            // Remove the element from the set when it does not satisfy the predicate
            // Previous will always be set to the current element if it should not be decoupled
            if (!pred.test(current.key)) {
                if (previous == null) {
                    // Case 1: Since previous is not set yet, this means we the head of the set should be decoupled
                    head = current.next;
                } else {
                    // Case 2: Previous is set to the last element which is not decoupled from the set
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
        // The previous element is the last element of the set which is not decoupled
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
                // Previous will always be set to the current element if it should not be decoupled
                if (previous == null) {
                    // Case 3.1: Since previous is not set yet, this means we the head of the set should be decoupled
                    head = current.next;
                } else {
                    // Case 3.1: Previous is set to the last element which is not decoupled from the set
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
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        ListItem<T> newHead = null;
        ListItem<T> tail = null;

        while (heads != null && heads.next != null && heads.key != null) {
            T current = heads.key.key;
            // Check if the current element is contained in all sets
            boolean common = true;
            for (ListItem<ListItem<T>> otherHeads = heads.next; otherHeads != null; ) {
                // Case 1: The other set is smaller than the current set. We do not have to check for common elements
                // anymore
                if (otherHeads.key == null) {
                    head = newHead;
                    return this;
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
                heads.key = heads.key.next;
                continue;
            }

            // Add the element to the new set if it is contained in all sets
            if (newHead == null) {
                newHead = heads.key;
            } else {
                tail.next = heads.key;
            }
            tail = heads.key;
            heads.key = heads.key.next;
        }
        head = newHead;
        tail.next = null;
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

    @Override
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        ListItem<ListItem<T>> newHead = null;
        ListItem<ListItem<T>> tail = null;
        ListItem<T> current = this.head;
        while (current != null) {
            // Item will be decoupled after processing
            ListItem<T> decoupled = current;
            ListItem<T> otherCurrent = other.head;
            while (otherCurrent != null) {
                ListItem<T> item = new ListItem<>(current.key);
                item.next = new ListItem<>(otherCurrent.key);
                ListItem<ListItem<T>> pair = new ListItem<>(item);
                if (newHead == null) {
                    newHead = pair;
                } else {
                    tail.next = pair;
                }
                tail = pair;

                // Decouple the other element from the set only if it is the last iteration since we need
                // the reference to the element in the set for all other iterations
                if (current.next == null) {
                    ListItem<T> otherDecoupled = otherCurrent;
                    otherCurrent = otherCurrent.next;
                    otherDecoupled.next = null;
                } else {
                    otherCurrent = otherCurrent.next;
                }
            }

            current = current.next;
            // Decouple the current element from the set
            decoupled.next = null;
        }

        return new MySetInPlace<>(newHead, Comparator.comparing((ListItem<T> o) -> o.key, cmp)
            .thenComparing((ListItem<T> o) -> {
                if (o.next == null) {
                    return null;
                }
                return o.next.key;
            }, cmp));
    }


}
