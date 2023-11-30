package h10.utils.visitor;

/**
 * A wrapper around an element that keeps track of how many times it has been visited.
 *
 * @param <T> the type of the element
 * @author Nhan Huynh
 */
public class VisitorElement<T> {

    /**
     * The wrapped element.
     */
    private final T element;

    /**
     * The number of times the element has been visited.
     */
    private int visited = 0;

    /**
     * Creates a new {@link VisitorElement} with the given element.
     *
     * @param element the element to wrap
     */
    public VisitorElement(T element) {
        this.element = element;
    }

    /**
     * Returns the wrapped element. This method also increments the visited counter.
     *
     * @return the wrapped element
     */
    public T getElement() {
        visited++;
        return element;
    }

    /**
     * Returns the number of times the element has been visited.
     *
     * @return the number of times the element has been visited
     */
    public int getVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VisitorElement<?> other)) {
            return false;
        }
        return element.equals(other.element);
    }

    @Override
    public String toString() {
        return "VisitorElement{" +
            "element=" + element +
            ", visited=" + visited +
            '}';
    }
}
