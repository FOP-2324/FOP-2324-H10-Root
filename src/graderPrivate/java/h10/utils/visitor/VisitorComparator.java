package h10.utils.visitor;

import java.util.Comparator;

/**
 * A comparator for {@link VisitorElement}s.
 *
 * @param <T> the type of the elements
 * @author Nhan Huynh
 */
public class VisitorComparator<T> implements Comparator<VisitorElement<T>> {

    /**
     * The underlying comparator to use.
     */
    private final Comparator<? super T> underlying;

    /**
     * Create a new visitor comparator.
     *
     * @param underlying the underlying comparator to use
     */
    public VisitorComparator(Comparator<? super T> underlying) {
        this.underlying = underlying;
    }

    @Override
    public int compare(VisitorElement<T> o1, VisitorElement<T> o2) {
        return underlying.compare(o1.getElement(), o2.getElement());
    }
}
