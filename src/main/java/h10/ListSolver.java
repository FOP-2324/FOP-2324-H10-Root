package h10;

import java.util.Comparator;
import java.util.function.UnaryOperator;

public class ListSolver {

    public static <T> ListItem<T> makeFlatListInPlace(ListItem<ListItem<T>> listOfLists) {
        return makeFlatList(listOfLists, true);
    }

    public static <T> ListItem<T> makeFlatListAsCopy(ListItem<ListItem<T>> listOfLists) {
        return makeFlatList(listOfLists, false);
    }

    private static <T> ListItem<T> makeFlatList(ListItem<ListItem<T>> listOfLists, boolean inPlace) {
        return null;
    }

    public static <T> ListItem<ListItem<T>> makeListOfListsAsCopy(ListItem<T> lst, Comparator<T> cmp) {
        return makeListOfLists(lst, cmp, false);
    }

    public static <T> ListItem<ListItem<T>> makeListOfListsInPlace(ListItem<T> lst, Comparator<T> cmp) {
        return makeListOfLists(lst, cmp, true);
    }

    private static <T> ListItem<ListItem<T>> makeListOfLists(ListItem<T> lst, Comparator<T> cmp, boolean inPlace) {
        return null;
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
