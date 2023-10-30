package h10;

import java.util.Comparator;
import java.util.Objects;

public abstract class AbstractMySet<T> {

    protected ListItem<T> head;
    protected final Comparator<? super T> cmp;


    protected AbstractMySet(Comparator<? super T> cmp) {
        this.cmp = cmp;
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


}
