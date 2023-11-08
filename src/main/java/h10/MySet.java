package h10;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class MySet<T> {

    protected ListItem<T> head;

    public MySet(ListItem<T> head) {
        this.head = head;
    }


    protected boolean contains(ListItem<T> set, T key) {
        for(ListItem<T> p = set; p != null; p = p.next) {
            if(p.key.equals(key)) {
                return true;
            }
        }
        return false;
    }


    protected ListItem<T> getListOfItemsInSet(ListItem<T> lst1, ListItem<T> lst2) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;
        for(ListItem<T> p = lst2; p != null; p = p.next) {
            for(ListItem<T> curr = lst1; curr != null; curr = curr.next) {
                if(p.key.equals(curr.key)) {
                    tail.next = new ListItem<T>(curr.key);
                    tail = tail.next;
                }
            }
        }
        return head.next;
    }


    public abstract MySet<? extends T> makeSubset(Predicate<? super T> pred);

    public abstract MySet<? extends T> difference(MySet<T> other);

    public abstract MySet<T> intersection(ListItem<MySet<T>> others);

    public abstract MySet<T> union(ListItem<MySet<T>> others);

    public abstract MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes);



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MySet<?> mySet = (MySet<?>) o;
        return Objects.equals(head, mySet.head);
    }




    @Override
    public int hashCode() {
        return Objects.hash(head);
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
