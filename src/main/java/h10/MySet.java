package h10;

public class MySet<T> {

    private ListItem<T> setList;

    public MySet() {}

    public MySet(ListItem<T> lst) {
        this.setList = lst;
    }

    public MySet<T> unionInPlace(MySet<T> set) {
        return union(set, true);
    }

    public MySet<T> unionAsCopy(MySet<T> set) {
        return union(set, false);
    }

    private MySet<T> union(MySet<T> set, boolean inPlace) {
        return null;
    }

    public MySet<T> intersectionInPlace(MySet<T> set) {
        return intersection(set, true);
    }

    public MySet<T> intersectionAsCopy(MySet<T> set) {
        return intersection(set, false);
    }

    private MySet<T> intersection(MySet<T> set, boolean inPlace) {
        return null;
    }

    private boolean contains(T key) {
        return false;
    }
}
