package h10.utils.visitor;

import java.util.Comparator;

public class VisitorComparator<T> implements Comparator<VisitorElement<T>> {
    private final Comparator<? super T> underlying;

    public VisitorComparator(Comparator<? super T> underlying) {
        this.underlying = underlying;
    }

    @Override
    public int compare(VisitorElement<T> o1, VisitorElement<T> o2) {
        return underlying.compare(o1.getElement(), o2.getElement());
    }
}
