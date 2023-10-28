package h10;

import java.util.function.Predicate;

public interface MySet<T> {

    void add(T e);
    
    MySet<T> makeSubset(Predicate<? super T> pred);

    MySet<T> difference(MySet<T> other);

    MySet<T> intersection(ListItem<MySet<T>> others);

    MySet<T> union(ListItem<MySet<T>> others);

    MySet<Tuple<T, ListItem<T>>> disjointUnion(ListItem<MySet<T>> others, MySet<T> indexes);

}
