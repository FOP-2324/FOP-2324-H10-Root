package h10;

public class MySet<T> {

    private ListItem<T> setList;

    public ListItem<T> unionInPlace(MySet<T> set) {
        return union(set, true);
    }

    public ListItem<T> unionAsCopy(MySet<T> set) {
        return union(set, false);
    }

    private ListItem<T> union(MySet<T> set, boolean inPlace) {
        return null;
    }

    public ListItem<T> intersectionInPlace(MySet<T> set) {
        return intersection(set, true);
    }

    public ListItem<T> intersectionAsCopy(MySet<T> set) {
        return intersection(set, false);
    }

    private ListItem<T> intersection(MySet<T> set, boolean inPlace) {
        return null;
    }

    private boolean contains(T key) {
        return false;
    }
}
