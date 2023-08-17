package h10;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ListSolver {

    public static <T> ListItem<T> makeFlatListInPlace(ListItem<ListItem<T>> listOfLists, T sentinel) {
        return makeFlatList(listOfLists, sentinel, true);
    }

    public static <T> ListItem<T> makeFlatListAsCopy(ListItem<ListItem<T>> listOfLists, T sentinel) {
        return makeFlatList(listOfLists, sentinel, false);
    }

    private static <T> ListItem<T> makeFlatList(ListItem<ListItem<T>> listOfLists, T sentinel, boolean inPlace) {
        ListItem<T> head = null, tail = null;
        for (ListItem<ListItem<T>> outer = listOfLists; outer != null; outer = outer.next) {
            for (ListItem<T> inner = outer.key; inner != null; inner = inner.next) {
                ListItem<T> tailNew = inPlace ? inner : new ListItem<T>(inner.key);
                if (head == null)
                    head = tail = tailNew;
                else
                    tail = tail.next = tailNew;
            }
            if (outer.next != null)
                if (head == null)
                    head = tail = new ListItem<>(sentinel);
                else
                    tail = tail.next = new ListItem<>(sentinel);
        }
        return head;
    }

    public static <T> ListItem<ListItem<T>> makeListOfListsAsCopy(ListItem<T> lst, Predicate<T> predicate) {
        return makeListOfLists(lst, predicate, false);
    }

    public static <T> ListItem<ListItem<T>> makeListOfListsInPlace(ListItem<T> lst, Predicate<T> predicate) {
        return makeListOfLists(lst, predicate, true);
    }

    private static <T> ListItem<ListItem<T>> makeListOfLists(ListItem<T> lst, Predicate<T> predicate, boolean inPlace) {
        ListItem<ListItem<T>> listOfLists = new ListItem<>();
        ListItem<ListItem<T>> listOfListsTail = listOfLists;
        ListItem<T> listTail = null;
        ListItem<T> pred = null;
        ListItem<T> current = lst;
        while (current != null) {
            if (predicate.test(current.key)) {

                listOfListsTail = listOfListsTail.next = new ListItem<>();
                if (inPlace && pred != null) {
                    pred.next = null;
                }

                listTail = null;

            } else {
                ListItem<T> tailNew = inPlace ? current : new ListItem<T>(current.key);
                if (listTail == null)
                    listOfListsTail.key = listTail = tailNew;
                else
                    listTail = listTail.next = tailNew;
            }
            pred = current;
            current = current.next;
        }
        return listOfLists;
    }

    /**
     *
     * @param list
     * @return
     * @param <T>
     */
    public static <T> ListItem<T> reverseList(ListItem<T> list) {
        ListItem<T> head = list;
        ListItem<T> p = head;
        while(p.next != null) {
            ListItem<T> nextItem = p.next;
            p.next = p.next.next;
            nextItem.next = head;
            head = nextItem;
        }
        return head;
    }


    public static <T> ListItem<T> mergeLists(ListItem<T> lst1, ListItem<T> lst2, Adder<T> adder) {
        return null;
    }

    public static <T> ListItem<T> shiftItemToLeft(ListItem<T> lst) {
        return null;
    }

    public static <T> ListItem<T> filterList(ListItem<T> lst, UnaryOperator<T> fct) {
        return null;
    }

    public static <T> ListOfListsIterator<T> iterator(ListItem<ListItem<T>> listOfLists) {
        return new ListOfListsIterator<>(listOfLists);
    }
}
