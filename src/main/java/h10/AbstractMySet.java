package h10;

import java.util.Comparator;

public abstract class AbstractMySet<T> implements MySet<T> {

    protected ListItem<T> head;
    protected final Comparator<? super T> cmp;


    protected AbstractMySet(Comparator<? super T> cmp) {
        this.cmp = cmp;
    }

    @Override
    public void add(T e) {
        if (head == null) {
            head = new ListItem<>(e);
        } else {
            ListItem<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new ListItem<>(e);
        }
    }
}
