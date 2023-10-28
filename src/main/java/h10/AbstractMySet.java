package h10;

import java.util.Comparator;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMySet<?> that = (AbstractMySet<?>) o;
        return Objects.equals(head, that.head) && Objects.equals(cmp, that.cmp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, cmp);
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
