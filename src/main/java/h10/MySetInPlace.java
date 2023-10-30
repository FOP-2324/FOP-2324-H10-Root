package h10;

import java.util.Comparator;
import java.util.function.Predicate;

public class MySetInPlace<T> extends AbstractMySet<T> {

    protected MySetInPlace(Comparator<? super T> cmp) {
        super(cmp);
    }

    @Override
    public MySet<T> makeSubset(Predicate<? super T> pred) {
        ListItem<T> head = null;
        ListItem<T> tail = null;

        for(ListItem<T> p = this.head; p != null; p = p.next) {
            if(pred.test(p.key)) {
                if(head == null) {
                    head = tail = p;
                }
                else {
                    tail = tail.next = p;
                }
            }
        }

        AbstractMySet<T> subSet = new MySetInPlace<T>(this.cmp);
        subSet.head = head;
        return subSet;
    }

    @Override
    public MySet<T> difference(MySet<T> other) {
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