package h10;

public class Tuple<T1, T2> {

    private final T1 FIRST_ITEM;
    private final T2 SECOND_ITEM;

    public Tuple(T1 t1, T2 t2) {
        this.FIRST_ITEM = t1;
        this.SECOND_ITEM = t2;
    }

    @Override
    public String toString() {
        return "(" + this.FIRST_ITEM.toString() + ", " + this.SECOND_ITEM.toString() + ")";
    }

    public T1 getFirstItem() {
        return this.FIRST_ITEM;
    }

    public T2 getSecondItem() {
        return this.SECOND_ITEM;
    }
}
