package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * An instance of this class represents an ordered set of pairwise different elements.
 *
 * @param <T> type of elements
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public abstract class MySet<T> {

    /**
     * The head of the set.
     */
    @DoNotTouch
    protected ListItem<T> head;

    /**
     * The comparator to compare elements and used to define the order of the set.
     */
    @DoNotTouch
    protected final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     */
    @DoNotTouch
    public MySet(ListItem<T> head, Comparator<? super T> cmp) {
        this.head = head;
        this.cmp = cmp;
    }

    /**
     * Returns the subset of this set that contains all elements satisfying the given predicate, more formally
     * {@code {x ∈ this | pred(x)}}.
     *
     * @param pred the predicate to apply to each element to determine if it should be included
     * @return the subset of this set that contains all elements satisfying the given predicate
     */
    @DoNotTouch
    public abstract MySet<T> subset(Predicate<? super T> pred);

    /**
     * Returns the cartesian product of this set and the given set, more formally {@code this × other}.
     *
     * @param other the set to multiply with this set
     * @return the cartesian product of this set and the given set
     */
    @DoNotTouch
    public abstract MySet<ListItem<T>> cartesianProduct(MySet<T> other);

    /**
     * Returns the difference of this set and the given set, more formally {@code this \ other}.
     *
     * @param other the set to subtract from this set
     * @return the difference of this set and the given set
     */
    @DoNotTouch
    public abstract MySet<T> difference(MySet<T> other);

    /**
     * Returns the intersection of sets, more formally {@code set1 ∩ set2 ∩ ... ∩ setN}.
     *
     * @param heads the sets to intersect
     * @return the intersection of sets
     */
    @DoNotTouch
    protected abstract MySet<T> intersectionListItems(ListItem<ListItem<T>> heads);

    /**
     * Returns a converted version of the sets as a list.
     *
     * @param others the sets to convert
     * @return the converted version of the sets as a list
     */
    @DoNotTouch
    protected ListItem<ListItem<T>> toListItem(ListItem<MySet<T>> others) {
        ListItem<ListItem<T>> heads = null;
        ListItem<ListItem<T>> tails = null;

        // Retrieve pointers to head pointer from all sets
        if (head != null) {
            heads = new ListItem<>(head);
            tails = heads;
        }
        for (ListItem<MySet<T>> otherSets = others; otherSets != null; otherSets = otherSets.next) {
            ListItem<T> otherHead = otherSets.key.head;
            ListItem<ListItem<T>> item = new ListItem<>(otherHead);
            if (heads == null) {
                heads = item;
            } else {
                tails.next = item;
            }
            tails = item;
        }
        return heads;
    }

    /**
     * Returns the intersection of this set and the given sets, more formally {@code this ∩ other1 ∩ ... ∩ otherN}.
     *
     * @param others the sets to intersect with this set
     * @return the intersection of this set and the given sets
     */
    @DoNotTouch
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        return intersectionListItems(toListItem(others));
    }

    /**
     * Returns the intersection of this set and the given set, more formally {@code this ∩ other}.
     *
     * @param other the set to intersect with this set
     * @return the intersection of this set and the given set
     */
    @DoNotTouch
    public MySet<T> intersection(MySet<T> other) {
        return intersection(new ListItem<>(other));
    }

    @Override
    @DoNotTouch
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MySet<?> mySet = (MySet<?>) o;
        return Objects.equals(head, mySet.head);
    }

    @Override
    @DoNotTouch
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    @DoNotTouch
    public String toString() {
        return String.valueOf(head);
    }

}
