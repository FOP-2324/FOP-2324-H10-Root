package h10;

import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An in-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetInPlace<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> current = head;
        ListItem<T> lastTrue = null;
        while (current != null) {
            if (pred.test(current.key)) {
                if (lastTrue != null) {
                    lastTrue.next = current;
                } else {
                    head = current;
                }
                lastTrue = current;
            }
            current = current.next;
        }
        if (lastTrue != null) {
            lastTrue.next = null;
        } else {
            head = null;
        }
        return this;
    }

    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        Comparator<ListItem<T>> comparator = Comparator.comparing((ListItem<T> s) -> s.key, cmp).thenComparing((ListItem<T> s) -> s.next.key, cmp);
        MySetInPlace<ListItem<T>> mySetInPlace = new MySetInPlace<>(null, comparator);
        ListItem<T> currentX = this.head;
        ListItem<T> lastX = null;
        ListItem<T> currentY;
        ListItem<T> lastY = null;
        ListItem<ListItem<T>> currentTupel;
        ListItem<ListItem<T>> lastTupel = null;

        while (currentX != null) {
            currentY = other.head;
            if (lastX != null) {
                lastX.next = null;
            }
            if (lastY != null) {
                lastY.next = null;
            }
            while (currentY != null) {
                currentTupel = new ListItem<>(new ListItem<>(currentX.key));
                currentTupel.key.next = new ListItem<>(currentY.key);
                if (mySetInPlace.head == null) {
                    mySetInPlace.head = currentTupel;
                } else {
                    lastTupel.next = currentTupel;
                }
                lastTupel = currentTupel;
                lastY = currentY;
                currentY = currentY.next;
            }
            lastX = currentX;
            currentX = currentX.next;
        }
        return mySetInPlace;
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        MySetInPlace<T> mySetInPlace = new MySetInPlace<>(null, cmp);
        ListItem<T> currentM = this.head;
        ListItem<T> lastM = null;

        while (currentM != null) {
            boolean found = false;
            ListItem<T> currentN = other.head;
            while (currentN != null) {
                if (cmp.compare(currentM.key, currentN.key) == 0) {
                    found = true;
                    break;
                }
                currentN = currentN.next;
            }
            if (!found) {
                if (lastM == null) {
                    mySetInPlace.head = currentM;
                } else {
                    lastM.next = currentM;
                }
                lastM = currentM;
            }
            currentM = currentM.next;
        }
        if (lastM != null) {
            lastM.next = null;
        }
        return mySetInPlace;
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        ListItem<T> myListItem = this.head;
        ListItem<T> lastElement = null;

        if (heads.next == null || heads.next.key == null) {
            this.head = null;
            return this;
        }
        while (myListItem != null) {
            boolean itemFound = false;
            ListItem<ListItem<T>> currentSet = heads.next;
            ListItem<T> currentListItem = currentSet.key;
            while (currentSet != null) {
                while (currentListItem != null) {
                    if (cmp.compare(currentListItem.key, myListItem.key) == 0) {
                        itemFound = true;
                        break;
                    }
                    currentListItem = currentListItem.next;
                }
                if (!itemFound) {
                    break;
                }
                currentSet = currentSet.next;
            }
            if (!itemFound) {
                if (lastElement == null) {
                    this.head = myListItem.next;
                } else {
                    lastElement.next = myListItem.next;
                }
            } else {
                lastElement = myListItem;
            }
            myListItem = myListItem.next;
        }
        return this;
    }
}
