package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An out-of-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetAsCopy<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetAsCopy(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> current = head;
        ListItem<T> lastTrue = null;
        MySet<T> mySetAsCopy = new MySetAsCopy<>(null, cmp);
        while (current != null) {
            if (pred.test(current.key)) {
                ListItem<T> even = new ListItem<>(current.key);
                if (mySetAsCopy.head == null) {
                    mySetAsCopy.head = even;
                }
                if (lastTrue != null) {
                    lastTrue.next = even;
                }
                lastTrue = even;
            }
            current = current.next;
        }
        return mySetAsCopy;
    }

    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        Comparator<ListItem<T>> comparator = Comparator.comparing((ListItem<T> s) -> s.key, cmp).thenComparing(s -> s.next.key, cmp);
        MySet<ListItem<T>> mySetAsCopy = new MySetAsCopy<>(null, comparator);
        ListItem<T> currentX = this.head;
        ListItem<T> currentY;
        ListItem<ListItem<T>> currentTupel;
        ListItem<ListItem<T>> lastTupel = null;

        while (currentX != null) {
            currentY = other.head;
            while (currentY != null) {
                currentTupel = new ListItem<>(new ListItem<>(currentX.key));
                currentTupel.key.next = new ListItem<>(currentY.key);
                if (mySetAsCopy.head == null) {
                    mySetAsCopy.head = currentTupel;
                    lastTupel = currentTupel;
                } else {
                    lastTupel.next = currentTupel;
                    lastTupel = currentTupel;
                }
                currentY = currentY.next;
            }
            currentX = currentX.next;
        }
        return mySetAsCopy;
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        MySetAsCopy<T> mySetAsCopy = new MySetAsCopy<>(null, cmp);

        ListItem<T> lastElement = null;
        ListItem<T> currentM = this.head;
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
                ListItem<T> newElement = new ListItem<>(currentM.key);
                if (lastElement == null) {
                    mySetAsCopy.head = newElement;
                } else {
                    lastElement.next = newElement;
                }
                lastElement = newElement;
            }
            currentM = currentM.next;
        }
        return mySetAsCopy;
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        MySetAsCopy<T> mySetAsCopy = new MySetAsCopy<>(null, cmp);
        if (heads.next == null || heads.next.key == null) {
            mySetAsCopy.head = null;
            return mySetAsCopy;
        }
        ListItem<T> itemToCheck = head;
        ListItem<T> lastResultElement = null;
        while (itemToCheck != null) {
            ListItem<ListItem<T>> checkList = heads;
            boolean itemFound = false;
            while (checkList != null) {
                itemFound = false;
                ListItem<T> checkListItem = checkList.key;
                while (checkListItem != null) {
                    if (cmp.compare(checkListItem.key, itemToCheck.key) == 0) {
                        itemFound = true;
                        break;
                    }
                    checkListItem = checkListItem.next;
                }
                if (!itemFound) {
                    break;
                }
                checkList = checkList.next;
            }
            if (itemFound) {
                ListItem<T> newElement = new ListItem<>(itemToCheck.key);
                if (lastResultElement == null) {
                    mySetAsCopy.head = newElement;
                } else {
                    lastResultElement.next = newElement;
                }
                lastResultElement = newElement;
            }
            itemToCheck = itemToCheck.next;
        }
        return mySetAsCopy;
    }
}
