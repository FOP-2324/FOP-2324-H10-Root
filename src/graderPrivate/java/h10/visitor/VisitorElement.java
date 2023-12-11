package h10.visitor;

import java.util.Objects;

public class VisitorElement<T> {

    private final T element;

    private int visited = 0;

    public VisitorElement(T element) {
        this.element = element;
    }

    public T peek() {
        return element;
    }

    public T get() {
        visit();
        return element;
    }

    public void visit() {
        visited++;
    }

    public int visited() {
        return visited;
    }

    public void reduce(int count) {
        visited -= count;
    }

    public void reset() {
        visited = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisitorElement<?> that = (VisitorElement<?>) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, visited);
    }

    @Override
    public String toString() {
        return "{%s, visited=%d}".formatted(element, visited);
    }
}
