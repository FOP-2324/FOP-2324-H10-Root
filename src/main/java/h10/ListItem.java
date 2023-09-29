package h10;

public class ListItem<T> {

    public T key;
    public ListItem<T> next;

    public ListItem() {}

    public ListItem(T key) {
        this.key = key;
    }

    public String toString() {
        return ""+ this.key+"";
    }
}
