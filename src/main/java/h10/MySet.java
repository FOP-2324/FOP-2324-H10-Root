package h10;

import java.util.Comparator;

public class MySet<T> {

    public ListItem<T> setList;
    private Comparator<T> cmp;

    public MySet() {}

    public MySet(ListItem<T> lst) {
        this.setList = lst;
    }

    public MySet(ListItem<T> lst, Comparator<T> cmp) {
        this.setList = lst;
        this.cmp = cmp;
    }

    public MySet<T> unionInPlace(MySet<T> set) {
        return union(set, true);
    }


    public MySet<T> unionAsCopy(MySet<T> set) {
        return union(set, false);
    }

    private MySet<T> union(MySet<T> set, boolean inPlace) {
        ListItem<T> head = new ListItem<T>();
        ListItem<T> tail = head;
        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            tail.next = inPlace ? p : new ListItem<T>(p.key);
            tail = tail.next;
        }

        for(ListItem<T> p = set.setList; p != null; p = p.next) {
            if(!contains(head.next, p.key)) {
                tail.next = inPlace ? p : new ListItem<T>(p.key);
                tail = tail.next;
            }
        }

        return new MySet<T>(head.next);
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

    private boolean contains(ListItem<T> set, T key) {
        for(ListItem<T> p = set; p != null; p = p.next) {
            if(this.cmp.compare(p.key, key) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        for(ListItem<T> p = this.setList; p != null; p = p.next) {
            builder.append(p.key);
            if(p.next != null) {
                builder.append(", ");
            }
        }

        builder.append("}");
        return builder.toString();
    }

}
