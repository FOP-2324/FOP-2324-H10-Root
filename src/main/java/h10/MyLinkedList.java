package h10;

public class MyLinkedList<T> {

    private ListItem<T> head;
    private ListItem<T> tail;

    public MyLinkedList(ListItem<T> head) {
        this.head = head;
        this.tail = head;
    }

    public void setHead(ListItem<T> head) {
        this.head = head;
    }

    public ListItem<T> getHead() {
        return this.head;
    }

    public void add(T key) {
        this.tail.next = new ListItem<T>(key);
        this.tail = this.tail.next;
    }
}
