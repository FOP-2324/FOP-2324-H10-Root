package h10;

import java.util.Comparator;
import java.util.function.Predicate;

public class MySetInPlace<T> extends AbstractMySet<T> {

    protected MySetInPlace(Comparator<? super T> cmp) {
        super(cmp);
    }

    @Override
    public MySet<T> makeSubset(Predicate<? super T> pred) {
        return null;
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
