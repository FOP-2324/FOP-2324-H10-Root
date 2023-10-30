package h10;

import java.util.Comparator;
import java.util.function.Predicate;

public class MySetAsCopy<T> extends AbstractMySet<T> {

    protected MySetAsCopy(Comparator<? super T> cmp) {
        super(cmp);
    }

    @Override
    public MySet<T> makeSubset(Predicate<? super T> pred) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;
        for(ListItem<T> p = this.head; p != null; p = p.next) {
            if(pred.test(p.key)) {
                tail.next = new ListItem<>(p.key);
                tail = tail.next;
            }
        }
        AbstractMySet<T> subSet = new MySetAsCopy<>(this.cmp);
        subSet.head = head.next;
        return subSet;
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;

        for(ListItem<T> p = this.head; p != null; p = p.next) {
            tail = tail.next = new ListItem<T>(p.key);
        }


        return null;
    }

    @Override
    public MySet<T> intersection(ListItem<MySet<T>> others) {
        return null;
    }

    @Override
    public MySet<T> union(ListItem<MySet<T>> others) {
        return null;
    }

    @Override
    public MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes) {
        return null;
    }
}
