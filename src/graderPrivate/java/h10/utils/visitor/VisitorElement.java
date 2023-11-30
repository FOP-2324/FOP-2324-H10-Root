package h10.utils.visitor;

public class VisitorElement<T> {

    private final T element;
    private int visited = 0;

    public VisitorElement(T element) {
        this.element = element;
    }

    public T getElement() {
        visited++;
        return element;
    }

    public int getVisited() {
        return visited;
    }

    public void reset() {
        visited = 0;
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
