package h10;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * An instance of this class represents an ordered set of pairwise different elements.
 *
 * @param <T> type of elements
 * @author Lars Waßmann, Nhan Huynh
 */
public abstract class MySet<T> {

    /**
     * The head of the set.
     */
    protected ListItem<T> head;

    /**
     * The comparator to compare elements and used to define the order of the set.
     */
    protected final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    public MySet(ListItem<T> head, Comparator<? super T> cmp) {
        if (!isOrdered(head, cmp)) {
            throw new IllegalArgumentException("The given elements are not ordered");
        }
        if (!isPairwiseDifferent(head, cmp)) {
            throw new IllegalArgumentException("The given elements are not pairwise different");
        }

        this.head = head;
        this.cmp = cmp;
    }

    /**
     * Returns {@code true} if the given list is ordered according to the given comparator.
     *
     * @param head the head of the list
     * @param cmp  the comparator to compare elements
     * @return {@code true} if the given list is ordered according to the given comparator
     */
    private boolean isOrdered(ListItem<T> head, Comparator<? super T> cmp) {
        if (head == null) {
            return true;
        }
        for (ListItem<T> current = head; current.next != null; current = current.next) {
            if (cmp.compare(current.key, current.next.key) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns {@code true} if the given list contains pairwise different elements according to the given comparator.
     *
     * @param head the head of the list
     * @param cmp  the comparator to compare elements
     * @return {@code true} if the given list contains pairwise different elements according to the given comparator
     */
    private boolean isPairwiseDifferent(ListItem<T> head, Comparator<? super T> cmp) {
        for (ListItem<T> current = head; current != null; current = current.next) {
            for (ListItem<T> other = current.next; other != null; other = other.next) {
                if (cmp.compare(current.key, other.key) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean contains(ListItem<T> set, T key) {
        for (ListItem<T> current = set; current != null; current = current.next) {
            if (cmp.compare(current.key, key) == 0) {
                return true;
            }
        }
        return false;
    }


    protected ListItem<T> getListOfItemsInSet(ListItem<T> lst1, ListItem<T> lst2) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;
        for (ListItem<T> p = lst2; p != null; p = p.next) {
            for (ListItem<T> curr = lst1; curr != null; curr = curr.next) {
                if (cmp.compare(curr.key, p.key) == 0) {
                    tail.next = new ListItem<T>(curr.key);
                    tail = tail.next;
                }
            }
        }
        return head.next;
    }

    /**
     * Returns the subset of this set that contains all elements satisfying the given predicate, more formally
     * {@code {x ∈ this | pred(x)}}.
     *
     * @param pred the predicate to apply to each element to determine if it should be included
     * @return the subset of this set that contains all elements satisfying the given predicate
     */
    public abstract MySet<T> subset(Predicate<? super T> pred);

    /**
     * Returns the difference of this set and the given set, more formally {@code this \ other}.
     *
     * @param other the set to subtract from this set
     * @return the difference of this set and the given set
     */
    public abstract MySet<T> difference(MySet<T> other);

    /**
     * Returns the intersection of this set and the given sets, more formally {@code this ∩ other1 ∩ ... ∩ otherN}.
     *
     * @param others the sets to intersect with this set
     * @return the intersection of this set and the given sets
     */
    public abstract MySet<T> intersection(ListItem<MySet<T>> others);

    /**
     * Returns the intersection of this set and the given set, more formally {@code this ∩ other}.
     *
     * @param other the set to intersect with this set
     * @return the intersection of this set and the given set
     */
    public MySet<T> intersection(MySet<T> other) {
        return intersection(new ListItem<>(other));
    }

    /**
     * Returns the union of this set and the given sets, more formally {@code this ∪ other1 ∪ ... ∪ otherN}.
     *
     * @param others the sets to union with this set
     * @return the union of this set and the given sets
     */
    public abstract MySet<T> union(ListItem<MySet<T>> others);

    /**
     * Returns the union of this set and the given set, more formally {@code this ∪ other}.
     *
     * @param other the set to union with this set
     * @return the union of this set and the given set
     */
    public MySet<T> union(MySet<T> other) {
        return union(new ListItem<>(other));
    }

    public abstract MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MySet<?> mySet = (MySet<?>) o;
        return Objects.equals(head, mySet.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListItem<T> current = head;
        while (current != null) {
            sb.append(current.key).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }

}
