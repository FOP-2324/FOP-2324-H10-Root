package h10.visitor;

import java.util.Comparator;

public record VisitorComparator<T>(Comparator<? super T> underlying) implements Comparator<VisitorElement<T>> {

    @Override
    public int compare(VisitorElement<T> o1, VisitorElement<T> o2) {
        return underlying.compare(o1.get(), o2.get());
    }

    @Override
    public String toString() {
        return underlying.toString();
    }
}
