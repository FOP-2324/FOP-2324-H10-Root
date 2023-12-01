package h10.utils;

import h10.ListItem;
import h10.utils.visitor.VisitorElement;

import java.util.List;

public class ListItems {

    private ListItems() {
    }

    public static ListItem<VisitorElement<Integer>> toListItem(List<Integer> elements) {
        ListItem<VisitorElement<Integer>> head = null;
        ListItem<VisitorElement<Integer>> tail = null;
        for (Integer element : elements) {
            ListItem<VisitorElement<Integer>> item = new ListItem<>(new VisitorElement<>(element));
            if (head == null) {
                head = item;
            } else {
                tail.next = item;
            }
            tail = item;
        }
        return head;
    }
}
